package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGalaxyPadCore extends Block implements ITileEntityProvider {
	public BlockGalaxyPadCore() {
		super(Material.ROCK);
        this.setUnlocalizedName("galaxy_pad_core");
        this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
        
        this.setResistance(4);
        this.setHardness(0.8f);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGalaxyPadCore();
	}
	
	private TileEntityGalaxyPadCore getTE(World world, BlockPos pos) {
		return (TileEntityGalaxyPadCore) world.getTileEntity(pos);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack heldItem = playerIn.getHeldItem(hand);

			if (heldItem.getItem() == ModItems.GEM_STAFF) {
				TileEntityGalaxyPadCore entityPad = this.getTE(worldIn, pos);
				if (entityPad.isValidPad()) {
						playerIn.openGui(KAGIC.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
						return true;
				 } else {
					entityPad.validateWarpPad();
					if (entityPad.isValidPad()) {
						playerIn.openGui(KAGIC.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		((TileEntityGalaxyPadCore) world.getTileEntity(pos)).destroy();
		super.breakBlock(world, pos, state);
	}
}
