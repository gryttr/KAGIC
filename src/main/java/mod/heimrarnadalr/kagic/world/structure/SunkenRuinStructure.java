package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SunkenRuinStructure extends RuinStructure {
	protected int depth;
	protected int maxHeight;

	public SunkenRuinStructure(String type, int depth, IBlockState foundation, boolean keepTerrain, boolean randomRotation) {
		super(type, 0, foundation, keepTerrain, randomRotation);
		this.depth = depth;
	}

	@Override
	protected boolean checkCorners(World world, BlockPos pos) {
		int xFar = pos.getX() + ((this.rotation % 2 == 0) ? this.width : this.length) - 1;
		int zFar = pos.getZ() + ((this.rotation % 2 == 0) ? this.length : this.width) - 1;
		BlockPos corner1 = pos;
		BlockPos corner2 = new BlockPos(xFar, pos.getY(), pos.getZ());
		BlockPos corner3 = new BlockPos(pos.getX(), pos.getY(), zFar);
		BlockPos corner4 = new BlockPos(xFar, pos.getY(), zFar);
		
		if (!checkBiome(world, corner1)) {
			return false;
		}
		if (!checkBiome(world, corner2)) {
			return false;
		}
		if (!checkBiome(world, corner3)) {
			return false;
		}
		if (!checkBiome(world, corner4)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		return super.generate(world, rand, new BlockPos(pos.getX(), world.getSeaLevel() - depth, pos.getZ()));
	}
}
