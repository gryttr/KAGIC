package mod.heimrarnadalr.kagic.world.structure;

import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class StructureData {
	private short width;
	private short height;
	private short length;
	private Map<BlockPos, IBlockState> structureBlocks;
	
	public StructureData(short width, short height, short length, Map<BlockPos, IBlockState> structureBlocks) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.structureBlocks = structureBlocks;
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
}
