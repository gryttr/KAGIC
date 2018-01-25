package mod.heimrarnadalr.kagic.world.structure;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
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
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Schematic {
	
	
	public static StructureData loadSchematic(String schematic) {
		NBTTagCompound schematicData;
		try {
			schematicData = CompressedStreamTools.readCompressed(Schematic.class.getResourceAsStream(schematic));
		} catch (Exception e) {
			KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic + "; trying uncompressed read");
			try {
				File file = new File(Schematic.class.getResource(schematic).toExternalForm());
				schematicData = CompressedStreamTools.read(file);
			} catch (Exception e1) {
				KAGIC.instance.chatInfoMessage("Failed to load schematic " + schematic);
				return null;
			}
		}
		
		Map<BlockPos, IBlockState> structureBlocks = new HashMap<BlockPos, IBlockState>();
		
		boolean schematicaFormat = false;
		short length = schematicData.getShort("Length");
		short width = schematicData.getShort("Width");
		short height = schematicData.getShort("Height");
		byte[] blocks = schematicData.getByteArray("Blocks");
		byte[] additionalBlocks = null;
		HashMap<Short, String> additionalBlockNames = new HashMap<Short, String>();
		if (schematicData.hasKey("AddBlocks") && schematicData.hasKey("SchematicaMapping")) {
			schematicaFormat = true;
			byte[] addBlocks = schematicData.getByteArray("AddBlocks");
			additionalBlocks = new byte[addBlocks.length * 2];
			for (int i = 0; i < addBlocks.length; ++i) {
				additionalBlocks[i * 2] = (byte) ((addBlocks[i] >> 4) & 0xF);
				additionalBlocks[i * 2 + 1] = (byte) (addBlocks[i] & 0xF);
			}

			NBTTagCompound blockNames = (NBTTagCompound) schematicData.getTag("SchematicaMapping");
			for (String name : blockNames.getKeySet()) {
				additionalBlockNames.put(blockNames.getShort(name), name);
			}
		}
		byte[] data = schematicData.getByteArray("Data");
		NBTTagList tileEntities = schematicData.getTagList("TileEntities", 10);
		NBTTagList entities = schematicData.getTagList("Entities", 10);

		//i = (y * Length + z) * Width + x		
		for (short y = 0; y < height; ++y) {
			for (short z = 0; z < length; ++z) {
				for (short x = 0; x < width; ++x) {
					try {
						int index = (y * length + z) * width + x;
						BlockPos pos = new BlockPos(x, y, z);
						IBlockState blockState = null;
						if (schematicaFormat && additionalBlocks != null) {
							short blockID = (short) Byte.toUnsignedInt(blocks[index]);
							blockID |= ((additionalBlocks[index] & 0xFF) << 8);
							blockState = Block.getBlockFromName(additionalBlockNames.get(blockID)).getStateFromMeta(Byte.toUnsignedInt(data[index]));						
						} else {
							blockState = Block.getBlockById(Byte.toUnsignedInt(blocks[index])).getStateFromMeta(Byte.toUnsignedInt(data[index]));						
						}
						structureBlocks.put(pos, blockState);
					} catch (Exception e) {
						KAGIC.instance.chatInfoMessage("Unable to create block at " + x + ", " + y + ", " + z);
						structureBlocks.put(new BlockPos(x, y, z), Blocks.HAY_BLOCK.getDefaultState());
					}
				}
			}
		}
		return new StructureData(width, height, length, structureBlocks, tileEntities, entities, schematicaFormat);
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
			if (keepTerrain && structureBlocks.get(offset).getBlock() == Blocks.AIR || structureBlocks.get(offset).getBlock() == Blocks.STRUCTURE_VOID) {
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
				if (te instanceof TileEntityGalaxyPadCore) {
					world.setBlockState(pos.add(tePos), ModBlocks.GALAXY_PAD_CORE.getDefaultState());
				} else if (te instanceof TileEntityWarpPadCore) {
					world.setBlockState(pos.add(tePos), ModBlocks.WARP_PAD_CORE.getDefaultState());
				} else if (te instanceof TileEntityLockableLoot) {
					KAGIC.instance.chatInfoMessage("Found chest at unrotated pos " + x + ", " + y + ", " + z);
					structure.chests.add((TileEntityLockableLoot) te);
				} else {
					KAGIC.instance.chatInfoMessage("Found tile entity of type " + te.getClass().getName());
				}
				world.setTileEntity(pos.add(tePos), te);
			}
		}
		
		/* WorldEdit saves entities with their global coordinates, instead of structure relative coordinates*/
		if (structure.schematicaFormat) {
			KAGIC.instance.chatInfoMessage("Attempting to spawn entities from structure file");
			for (NBTBase nbt : structure.getEntities()) {
				NBTTagList nbtPos = ((NBTTagCompound) nbt).getTagList("Pos", 6);
				double x = nbtPos.getDoubleAt(0);
				double y = nbtPos.getDoubleAt(1);
				double z = nbtPos.getDoubleAt(2);
				BlockPos ePos = getRotatedPos(new BlockPos(x, y, z), structure.getWidth(), structure.getLength(), rotation);
				Entity e = EntityList.createEntityFromNBT((NBTTagCompound) nbt, world);
				if (e != null) {
					e.setLocationAndAngles(pos.getX() + ePos.getX() + 0.5, pos.getY() + ePos.getY(), pos.getZ() + ePos.getZ() + 0.5, e.rotationYaw, e.rotationPitch);
					world.spawnEntity(e);
				}
			}
		}
	}
	
	public static BlockPos getRotatedPos(BlockPos original, int width, int length, byte rotation) {
		if (rotation < 0 || rotation > 3) {
			KAGIC.instance.chatInfoMessage("WARNING: using nonstandard rotation " + rotation);
		}
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
