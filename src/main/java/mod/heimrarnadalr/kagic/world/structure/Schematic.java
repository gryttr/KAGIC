package mod.heimrarnadalr.kagic.world.structure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
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
		NBTTagList tileEntities = schematicData.getTagList("TileEntities", 10);
		NBTTagList entities = schematicData.getTagList("Entities", 10);

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
		return new StructureData(width, height, length, structureBlocks, tileEntities, entities);
	}
	
	public static void GenerateStructureAtPoint(StructureData structure, World world, BlockPos pos, boolean keepTerrain, byte rotation) {
		KAGIC.instance.chatInfoMessage("Using rotation " + rotation);
		Map<BlockPos, IBlockState> structureBlocks = structure.getStructureBlocks();

		Rotation rot;
		switch (rotation % 4) {
		case 1:
			rot = Rotation.CLOCKWISE_90;
			break;
		case 2:
			rot = Rotation.CLOCKWISE_180;
			break;
		case 3:
			rot = Rotation.COUNTERCLOCKWISE_90;
			break;
		default:
			rot = Rotation.NONE;
		}
		
		for (BlockPos offset : structureBlocks.keySet()) {
			if (keepTerrain && structureBlocks.get(offset).getBlock() == Blocks.AIR) {
				continue;
			}
			
			BlockPos bP = Schematic.getRotatedPos(offset, structure.getWidth(), structure.getLength(), rotation);

			world.setBlockState(pos.add(bP), structureBlocks.get(offset).withRotation(rot));
		}
		
		for (NBTBase nbt : structure.getTileEntities()) {
			TileEntity te = TileEntity.create(world, (NBTTagCompound) nbt);
			if (te != null) {
				int x = ((NBTTagCompound) nbt).getInteger("x");
				int y = ((NBTTagCompound) nbt).getInteger("y");
				int z = ((NBTTagCompound) nbt).getInteger("z");
				BlockPos tePos = getRotatedPos(new BlockPos(x, y, z), structure.getWidth(), structure.getLength(), rotation);
				if (te instanceof TileEntityWarpPadCore) {
					world.setBlockState(pos.add(tePos), ModBlocks.WARP_PAD_CORE.getDefaultState());
				} else if (te instanceof TileEntityChest) {
					KAGIC.instance.chatInfoMessage("Found chest at unrotated pos " + x + ", " + y + ", " + z);
					structure.chests.add((TileEntityChest) te);
				} else {
					KAGIC.instance.chatInfoMessage("Found tile entity of type " + te.getClass().getName());
				}
				world.setTileEntity(pos.add(tePos), te);
			}
		}
		
		/* WorldEdit saves entities with their global coordinates, instead of structure relative coordinates
		for (NBTBase nbt : structure.getEntities()) {
			NBTTagList nbtPos = ((NBTTagCompound) nbt).getTagList("Pos", 6);
			double x = nbtPos.getDoubleAt(0);
			double y = nbtPos.getDoubleAt(1);
			double z = nbtPos.getDoubleAt(2);
			BlockPos ePos = getRotatedPos(new BlockPos(x, y, z), structure.getWidth(), structure.getLength(), rotation);
			Entity e = EntityList.createEntityFromNBT((NBTTagCompound) nbt, world);
			if (e != null) {
				e.setLocationAndAngles(pos.getX() + ePos.getX(), pos.getY() + ePos.getY(), pos.getZ() + ePos.getZ(), e.rotationYaw, e.rotationPitch);
				world.spawnEntity(e);
			}
		}*/
	}
	
	public static BlockPos getRotatedPos(BlockPos original, int width, int length, byte rotation) {
		int rotationCorrectionX = width % 2 == 0 ? -1 : 0;
		int rotationCorrectionZ = length % 2 == 0 ? -1 : 0;
		
		BlockPos bP = original;
		if (rotation % 4 != 0) {
			//translate origin to center
			bP = original.add(-width / 2, 0, -length / 2);
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
			if (rotation == 1 || rotation == 3) {
				bP = bP.add(length / 2, 0, width / 2);
			} else {
				bP = bP.add(width / 2, 0, length / 2);
			}
		}
		return bP;
	}
}
