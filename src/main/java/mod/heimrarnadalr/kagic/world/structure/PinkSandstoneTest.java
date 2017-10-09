package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PinkSandstoneTest extends RuinStructure {

	public PinkSandstoneTest(String type) {
		super(type, 1, ModBlocks.PINK_SANDSTONE.getDefaultState(), true, true);
		this.structures.add("/assets/kagic/structures/PinkSandstoneTest.schematic");
	}

	@Override
	public boolean checkCorners(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	protected boolean checkDistance(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		//KAGIC.instance.chatInfoMessage("Pink Sandstone Test generate called");
		if (rand.nextInt(5) != 0) {
			return false;
		}
		//KAGIC.instance.chatInfoMessage("Random passed; checking world conditions");
		return super.generate(world, rand, pos);
	}
}