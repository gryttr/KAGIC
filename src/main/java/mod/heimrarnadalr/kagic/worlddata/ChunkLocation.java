package mod.heimrarnadalr.kagic.worlddata;

import net.minecraft.util.math.BlockPos;

public class ChunkLocation {
	private int x;
	private int z;
	
	public ChunkLocation(int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public ChunkLocation(BlockPos pos) {
		this.x = pos.getX() >> 4;
		this.z = pos.getZ() >> 4;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public ChunkLocation add(int x, int z) {
		return new ChunkLocation(this.getX() + x, this.getZ() + z);
	}
	
	@Override
	public String toString() {
		return this.x + ", " + this.z;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChunkLocation)) {
			return false;
		}
		ChunkLocation otherChunk = (ChunkLocation) other;
		return this.getX() == otherChunk.getX() && this.getZ() == otherChunk.getZ();
	}
	
	@Override
	public int hashCode() {
		return (this.getX() << 16) + (this.getZ() & 0xFFFF);
	}
}
