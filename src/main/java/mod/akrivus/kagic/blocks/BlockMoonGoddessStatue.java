package mod.akrivus.kagic.blocks;

import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.tileentity.TileEntityMoonGoddessStatue;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMoonGoddessStatue extends Block implements ITileEntityProvider {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	protected static final AxisAlignedBB MOON_GODDESS_STATUE_AABB_NORTH = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.9375D, 0.6875D);
	protected static final AxisAlignedBB MOON_GODDESS_STATUE_AABB_SOUTH = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.9375D, 0.625D);
	protected static final AxisAlignedBB MOON_GODDESS_STATUE_AABB_EAST  = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.625D, 0.9375D, 0.6875D);
	protected static final AxisAlignedBB MOON_GODDESS_STATUE_AABB_WEST  = new AxisAlignedBB(0.375D, 0.0D, 0.3125D, 0.6875D, 0.9375D, 0.6875D);
	
	public BlockMoonGoddessStatue() {
		super(Material.CIRCUITS, MapColor.DIAMOND);
		this.setUnlocalizedName("moon_goddess_statue");
		
		this.setResistance(4);
		this.setHardness(0.8f);
		
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMoonGoddessStatue();
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMoonGoddessStatue) {
			if (((TileEntityMoonGoddessStatue) te).isFullMoon()) {
				return ((TileEntityMoonGoddessStatue) te).getLightLevelForNightStage();
			}
		}
		return 0;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing facing = EnumFacing.getHorizontal(MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		world.setBlockState(pos, state.withProperty(FACING, facing), 3);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}	
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int facing = this.getMetaFromState(state);
		switch(facing) {
			case 2:
				return MOON_GODDESS_STATUE_AABB_NORTH;
			case 3:
				return MOON_GODDESS_STATUE_AABB_SOUTH;
			case 4:
				return MOON_GODDESS_STATUE_AABB_WEST;
			case 5:
				return MOON_GODDESS_STATUE_AABB_EAST;
			default:
				return MOON_GODDESS_STATUE_AABB_NORTH;					
		}				
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return true;
	}
}
