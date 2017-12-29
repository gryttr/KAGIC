package mod.heimrarnadalr.kagic.world.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;

public class StructureData {
	private short width;
	private short height;
	private short length;
	private Map<BlockPos, IBlockState> structureBlocks;
	private NBTTagList tileEntities;
	private NBTTagList entities;
	public List<TileEntityLockableLoot> chests = new ArrayList<TileEntityLockableLoot>();
	public boolean schematicaFormat;
	
	public StructureData(short width, short height, short length, Map<BlockPos, IBlockState> structureBlocks, NBTTagList tileEntities, NBTTagList entities, boolean schematicaFormat) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.structureBlocks = structureBlocks;
		this.tileEntities = tileEntities;
		this.entities = entities;
		this.schematicaFormat = schematicaFormat;
	}
	
	public void setWidth(short width) {
		this.width = width;
	}

	public short getWidth() {
		return this.width;
	}

	public void setHeight(short height) {
		this.height = height;
	}

	public short getHeight() {
		return this.height;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public short getLength() {
		return this.length;
	}
	
	public void setStructureBlocks(Map<BlockPos, IBlockState> structureBlocks) {
		this.structureBlocks = structureBlocks;
	}
	
	public Map<BlockPos, IBlockState> getStructureBlocks(){
		return this.structureBlocks;
	}

	public void setTileEntities(NBTTagList tileEntities) {
		this.tileEntities = tileEntities;
	}
	
	public NBTTagList getTileEntities(){
		return this.tileEntities;
	}

	public void setEntities(NBTTagList entities) {
		this.entities = entities;
	}
	
	public NBTTagList getEntities(){
		return this.entities;
	}
}
