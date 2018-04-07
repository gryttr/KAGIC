package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVarying extends Block {
	public BlockVarying(String unlocalizedName, int resistance, int hardness, int level) {
		super(ModBlocks.DRAINED);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setHarvestLevel("pickaxe", level);
		this.setTickRandomly(true);
	}
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
		for (int x = -2; x < 2; ++x) {
			for (int y = -2; y < 2; ++y) {
				for (int z = -2; z < 2; ++z) {
					BlockPos newp = pos.add(x, y, z);
					Block block = worldIn.getBlockState(pos.up()).getBlock();
					if (block instanceof BlockBush) {
						if (block instanceof BlockIris) {}
						else {
							worldIn.destroyBlock(newp, false);
						}
					}
				}
			}
		}
	}
}
