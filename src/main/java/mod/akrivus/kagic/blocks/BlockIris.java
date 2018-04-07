package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.util.injector.InjectorResult;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIris extends BlockBush {
	public BlockIris() {
		super(Material.GRASS, MapColor.CYAN);
		this.setUnlocalizedName("iris");
		this.setLightLevel(4.0F);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setTickRandomly(true);
	}
	protected boolean canSustainBush(IBlockState state) {
        return true;
    }
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return true;
    }
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        InjectorResult.drainBlock(worldIn, pos.down());
	}
}
