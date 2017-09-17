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
	protected boolean checkCorners(World world, BlockPos pos, byte rotation) {
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		return super.generate(world, rand, new BlockPos(pos.getX(), world.getSeaLevel() - depth, pos.getZ()));
	}
}
