package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BuriedRuinStructure extends RuinStructure {
	protected int minDepth;
	protected int maxDepth;

	public BuriedRuinStructure(String type, int minDepth, int maxDepth, boolean randomRotation) {
		super(type, 0, Blocks.AIR.getDefaultState(), false, randomRotation);
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
	}

	@Override
	protected boolean checkCorners(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int depthOffset = 0;
		if (this.minDepth == this.maxDepth) {
			depthOffset = minDepth;
		} else {
			depthOffset = minDepth + rand.nextInt(this.maxDepth - this.minDepth);
		}
		pos = pos.down(depthOffset);
		return super.generate(world, rand, pos);
	}
}
