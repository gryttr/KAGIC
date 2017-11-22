package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.ModBiomes;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenStrawberryBush extends WorldGenerator {
	private static final int STRAWBERRY_CHANCE_RECIPROCAL = 10;
	private static final IBlockState BUSH_LEAVES = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)).withProperty(BlockLeaves.DECAYABLE, Boolean.valueOf(false));

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == Blocks.CONCRETE) {
			return false;
		}
		
		int width = 4 + rand.nextInt(4);
		int height = 2 + rand.nextInt(3);
		int length = 4 + rand.nextInt(4);
		
		for (int x = -width; x < width; ++x) {
			for (int y = -3; y < height; ++y) {
				for (int z = -length; z < length; ++z) {
					float a = ((x + 0.5F) * (x + 0.5F)) / (width * width);
					float b = ((y + 0.5F) * (y + 0.5F)) / (height * height);
					float c = ((z + 0.5F) * (z + 0.5F)) / (length * length);
					if (a + b + c <= 1) {
						BlockPos genPos = pos.add(x, y, z);
						if (world.getBlockState(genPos).getBlock().isReplaceable(world, genPos)
								&& world.getBiome(genPos) == ModBiomes.STRAWBERRYBATTLEFIELD
								&& world.getBlockState(genPos).getMaterial() != Material.WATER
								&& world.getBlockState(genPos.down()).getMaterial() != Material.WATER) {
							if (rand.nextInt(STRAWBERRY_CHANCE_RECIPROCAL) == 0) {
								world.setBlockState(genPos, ModBlocks.GIANT_STRAWBERRY.getDefaultState(), 2);
							} else {
								world.setBlockState(genPos, BUSH_LEAVES, 2);
							}
						}
					}
				}
			}
		}
		return true;
	}

}
