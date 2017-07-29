package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRockMelt extends Block {
	public final boolean dropsDust;
	public BlockRockMelt(boolean dropsDust) {
		super(Material.GLASS, MapColor.GOLD);
		this.setUnlocalizedName("rock_melt");
		this.dropsDust = dropsDust;
		this.setLightLevel(1.0F);
		if (this.dropsDust) {
			this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		}
		
	}
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
    }
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
    }
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.checkAndDropBlock(worldIn, pos, state);
    }
    private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
            return false;
        }
        else {
            return true;
        }
    }
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).isOpaqueCube();
    }
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.dropsDust ? Items.BLAZE_POWDER : Item.getItemFromBlock(Blocks.AIR);
    }
    public int quantityDropped(Random random) {
        return 1;
    }
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }
}
