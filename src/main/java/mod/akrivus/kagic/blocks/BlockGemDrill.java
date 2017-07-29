package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.util.injector.Injector;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGemDrill extends Block {
	public BlockGemDrill() {
		super(Material.GLASS, MapColor.IRON);
		this.setUnlocalizedName("gem_drill");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        Injector.onGemDrillPlacement(worldIn, pos);
    }
	/*********************************************************
     * Methods related to block rendering.                   *
     *********************************************************/
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
