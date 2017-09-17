package mod.heimrarnadalr.kagic.worlddata;

import net.minecraft.util.math.BlockPos;

public class GalaxyPadLocation {
	private final int dimension;
	private final BlockPos pos;
	
	public GalaxyPadLocation(int dimension, BlockPos pos) {
		this.dimension = dimension;
		this.pos = pos;
	}

	public int getDimension() {
		return this.dimension;
	}
	
	public BlockPos getPos() {
		return this.pos;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		
		if (!(other instanceof GalaxyPadLocation)) {
			return false;
		}
		
		GalaxyPadLocation otherLoc = (GalaxyPadLocation) other;
		return this.dimension == otherLoc.dimension && this.pos.equals(otherLoc.pos);
	}
	
	@Override
	public int hashCode() {
		return this.pos.hashCode() * 31 + this.dimension;
	}
}
