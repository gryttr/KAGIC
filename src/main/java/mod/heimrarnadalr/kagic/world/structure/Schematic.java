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
	
	
	public static Map<Vec3i, IBlockState> loadSchematic(String schematic) {
		NBTTagCompound schematicData;
		try {
			schematicData = CompressedStreamTools.readCompressed(Schematic.class.getResourceAsStream(schematic));
		} catch (IOException e) {
			KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic);
			return null;
		}
		
		Map<Vec3i, IBlockState> structure = new HashMap<Vec3i, IBlockState>();
		
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
					Vec3i pos = new BlockPos(x, y, z);
					IBlockState blockState = Block.getBlockById(Byte.toUnsignedInt(blocks[index])).getStateFromMeta(Byte.toUnsignedInt(data[index]));
					structure.put(pos, blockState);
				}
			}
		}
		return structure;
	}
	
	public static void GenerateStructureAtPoint(String schematic, World world, BlockPos pos, boolean keepTerrain) {
		Map<Vec3i, IBlockState> structure = Schematic.loadSchematic(schematic);
		for (Vec3i offset : structure.keySet()) {
			if (keepTerrain && structure.get(offset).getBlock() == Blocks.AIR) {
				continue;
			}
			world.setBlockState(pos.add(offset), structure.get(offset));
		}
	}
}
