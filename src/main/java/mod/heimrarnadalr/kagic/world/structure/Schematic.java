package mod.heimrarnadalr.kagic.world.structure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class Schematic {
	
	
	public static StructureData loadSchematic(String schematic) {
		NBTTagCompound schematicData;
		try {
			schematicData = CompressedStreamTools.readCompressed(Schematic.class.getResourceAsStream(schematic));
		} catch (IOException e) {
			KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic);
			return null;
		}
		
		Map<BlockPos, IBlockState> structureBlocks = new HashMap<BlockPos, IBlockState>();
		
		short length = schematicData.getShort("Length");
		short width = schematicData.getShort("Width");
		short height = schematicData.getShort("Height");
		byte[] blocks = schematicData.getByteArray("Blocks");
		byte[] data = schematicData.getByteArray("Data");

		//i = (y * Length + z) * Width + x
		
		for (short y = 0; y < height; ++y) {
			for (short z = 0; z < length; ++z) {
				for (short x = 0; x < width; ++x) {
					int index = (y * length + z) * width + x;
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState blockState = Block.getBlockById(Byte.toUnsignedInt(blocks[index])).getStateFromMeta(Byte.toUnsignedInt(data[index]));
					structureBlocks.put(pos, blockState);
				}
			}
		}
		return new StructureData(width, height, length, structureBlocks);
	}
	
	public static void GenerateStructureAtPoint(String schematic, World world, BlockPos pos, boolean keepTerrain, byte rotation) {
		KAGIC.instance.chatInfoMessage("Using rotation " + rotation);
		StructureData structure = Schematic.loadSchematic(schematic);
		Map<BlockPos, IBlockState> structureBlocks = structure.getStructureBlocks();

		int rotationCorrectionX = structure.getWidth() % 2 == 0 ? -1 : 1;
		int rotationCorrectionZ = structure.getLength() % 2 == 0 ? -1 : 1;
		for (BlockPos offset : structureBlocks.keySet()) {
			BlockPos bP = offset;
			if (rotation % 4 != 0) {
				//translate origin to center
				bP = offset.add(-structure.getWidth() / 2, 0, -structure.getLength() / 2);
				//Rotate around center
				switch (rotation % 4) {
				case 1:
					bP = new BlockPos(-bP.getZ() + rotationCorrectionX, bP.getY(), bP.getX());
					break;
				case 2:
					bP = new BlockPos(-bP.getX() + rotationCorrectionX, bP.getY(), -bP.getZ() + rotationCorrectionZ);
					break;
				case 3:
					bP = new BlockPos(bP.getZ(), bP.getY(), -bP.getX() + rotationCorrectionZ);
					break;
				}
				//translate origin back to corner
				bP = bP.add(structure.getWidth() / 2, 0, structure.getLength() / 2);
			}
			if (keepTerrain && structureBlocks.get(offset).getBlock() == Blocks.AIR) {
				continue;
			}
			world.setBlockState(pos.add(bP), structureBlocks.get(offset));
		}
	}
}
