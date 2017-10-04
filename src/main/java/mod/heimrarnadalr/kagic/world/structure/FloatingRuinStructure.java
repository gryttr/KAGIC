package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FloatingRuinStructure extends RuinStructure {
	protected int minHeight;
	protected int maxHeight;

	public FloatingRuinStructure(String type, int minHeight, int maxHeight, boolean keepTerrain, boolean randomRotation) {
		super(type, 0, Blocks.AIR.getDefaultState(), keepTerrain, randomRotation);
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	@Override
	protected boolean checkCorners(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int heightOffset = minHeight + rand.nextInt(this.maxHeight - this.minHeight);
		pos = pos.up(heightOffset);
		return super.generate(world, rand, pos);
	}
}
