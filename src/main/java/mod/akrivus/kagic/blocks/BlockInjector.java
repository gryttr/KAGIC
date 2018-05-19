package mod.akrivus.kagic.blocks;

import java.util.Random;

import mod.akrivus.kagic.event.InjectionEvent;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.util.injector.Injector;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInjector extends Block {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected final boolean isEquipped;
	protected final boolean analog;
	public BlockInjector(boolean isEquipped, boolean analog) {
		super(Material.GLASS, MapColor.MAGENTA);
		this.setUnlocalizedName(analog ? "analog_injector": "injector");
		this.isEquipped = isEquipped;
		this.analog = analog;
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		if (this.isEquipped) {
			this.setLightLevel(0.5F);
		}
		else {
			this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		}
	}
	public void injectGemSeed(World worldIn, BlockPos pos) {
		if (!(worldIn.getBlockState(pos.up()).getBlock() instanceof BlockIncubator)) {
			Injector.tellNearbyPlayers(worldIn, pos, "injector_missing_incubator", true);
		}
		else if (!(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockGemDrill)) {
			Injector.tellNearbyPlayers(worldIn, pos, "injector_missing_drill", true);
		}
		else if (this.isEquipped) {
			int newPosY = 0;
			if (this.analog) {
				newPosY = this.calcDistanceUsingRedstoneSignals(worldIn, pos);
			}
			else {
				newPosY = this.calcDistanceAutomatically(worldIn, pos);
			}
			InjectionEvent e1 = new InjectionEvent(worldIn, pos, newPosY, true);
			if (MinecraftForge.EVENT_BUS.post(e1)) return;
			if (newPosY > 5) {
				worldIn.setBlockState(new BlockPos(pos.getX(), newPosY, pos.getZ()), ModBlocks.GEM_SEED.getDefaultState());
				worldIn.setBlockState(pos, ModBlocks.INJECTOR.getDefaultState().withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING)));
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.NEUTRAL, 1.0F, 1.0F, true);
				worldIn.playSound(null, pos, ModSounds.BLOCK_INJECTOR_FIRE, SoundCategory.BLOCKS, 1000.0F, 1.0F);
				Injector.tellNearbyPlayers(worldIn, pos, "injector_success", false, pos.getX(), newPosY, pos.getZ());
				Injector.awardNearbyPlayers(worldIn, pos);
			}
			else {
				Injector.tellNearbyPlayers(worldIn, pos, "injector_failure", true);
			}
		}
		else {
			Injector.tellNearbyPlayers(worldIn, pos, "injector_not_equipped", true);
		}
	}
	/*********************************************************
	 * Methods related to block creation and placing.        *
	 *********************************************************/
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        Injector.onInjectorPlacement(worldIn, pos);
    }

	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        this.setDefaultFacing(worldIn, pos, state);
        if (worldIn.isBlockPowered(pos) && this.isEquipped) {
			this.injectGemSeed(worldIn, pos);
        }
    }
    /*********************************************************
     * Methods related to block activation and interaction.  *
     *********************************************************/
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (playerIn.isSneaking()) {
				if (this.analog) {
					if (!this.isEquipped) {
						worldIn.setBlockState(pos, ModBlocks.INJECTOR.getDefaultState().withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING)));
					}
				}
				else {
					if (!this.isEquipped) {
						worldIn.setBlockState(pos, ModBlocks.ANALOG_INJECTOR.getDefaultState().withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING)));
					}
				}
			}
			else {
				IBlockState unequippedState = ModBlocks.INJECTOR.getDefaultState();
				IBlockState equippedState = ModBlocks.EQUIPPED_INJECTOR.getDefaultState();
				if (this.analog) {
					unequippedState = ModBlocks.ANALOG_INJECTOR.getDefaultState();
					equippedState = ModBlocks.EQUIPPED_ANALOG_INJECTOR.getDefaultState();
				}
				if (this.isEquipped && heldItem.isEmpty()) {
					worldIn.playSound(null, pos, ModSounds.BLOCK_INJECTOR_OPEN, SoundCategory.BLOCKS, 0.3F, 1.0F);
		    		worldIn.setBlockState(pos, unequippedState.withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING)));
		    		EntityItem activatedBase = new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.ACTIVATED_GEM_BASE));
		    		worldIn.spawnEntity(activatedBase);
		    	}
		    	else  if (!this.isEquipped) {
		    		if (heldItem.getItem() == ModItems.ACTIVATED_GEM_BASE) {
		    			worldIn.setBlockState(pos, equippedState.withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING)));
		    			worldIn.playSound(null, pos, ModSounds.BLOCK_INJECTOR_CLOSE, SoundCategory.BLOCKS, 0.3F, 1.0F);
		    			if (!playerIn.capabilities.isCreativeMode) {
		    				heldItem.splitStack(1);
		    			}
		    			Injector.onGemBasePlacement(worldIn, pos);
		    		}
		    	}
		    	if (heldItem.getItem() instanceof ItemBlock) {
		    		return false;
		    	}
			}
		}
        return true;
    }

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
		if (worldIn.isBlockPowered(pos) && this.isEquipped) {
			this.injectGemSeed(worldIn, pos);
        }
    }
	public int calcDistanceUsingRedstoneSignals(World worldIn, BlockPos pos) {
		int totalPower = 6;
		totalPower += worldIn.getBlockState(pos.north()).getWeakPower(worldIn, pos.north(), EnumFacing.NORTH);
		totalPower += worldIn.getBlockState(pos.south()).getWeakPower(worldIn, pos.south(), EnumFacing.SOUTH);
		totalPower += worldIn.getBlockState(pos.east()).getWeakPower(worldIn, pos.east(), EnumFacing.EAST);
		totalPower += worldIn.getBlockState(pos.west()).getWeakPower(worldIn, pos.west(), EnumFacing.WEST);
		BlockPos newp = pos.down(totalPower);
		if (newp.getY() < 5 || worldIn.getBlockState(newp).getBlock() == Blocks.BEDROCK) {
			totalPower -= (5 - newp.getY());
		}
		if (worldIn.getBlockState(newp).getBlock() == ModBlocks.GEM_SEED) {
			totalPower += 256;
		}
		return pos.getY() - totalPower;
    }
	public int calcDistanceAutomatically(World worldIn, BlockPos pos) {
    	int bestDepth = -1;
    	int worstDepth = 5;
		for (bestDepth = pos.down(6).getY(); bestDepth > worstDepth; --bestDepth) {
			boolean aerated = false;
			BlockPos newBlockPos = new BlockPos(pos.getX(), bestDepth, pos.getZ());
			if (worldIn.getBlockState(newBlockPos).getBlock() == ModBlocks.GEM_SEED 
					|| worldIn.getBlockState(newBlockPos).getBlock() == Blocks.BEDROCK 
					|| !worldIn.isSideSolid(newBlockPos, EnumFacing.UP) 
					|| !worldIn.isSideSolid(newBlockPos.up(), EnumFacing.DOWN)) {
				bestDepth -= 3;
			}
			else {
				boolean inContactWithAir = false;
				for (int x = -9; x <= 9; ++x) {
					BlockPos temp = new BlockPos(newBlockPos.getX() + x, bestDepth, newBlockPos.getZ());
					if (Math.abs(x) > 1) {
						if (worldIn.isAirBlock(temp) && (worldIn.canSeeSky(temp) || worldIn.getLight(newBlockPos) > 0.9)) {
							aerated = true;
						}
					}
					else if (x != 0) {
						inContactWithAir = worldIn.isAirBlock(temp);
					}
				}
				for (int z = -9; z <= 9; ++z) {
					BlockPos temp = new BlockPos(newBlockPos.getX(), bestDepth, newBlockPos.getZ() + z);
					if (Math.abs(z) > 1) {
						if (worldIn.isAirBlock(temp) && (worldIn.canSeeSky(temp) || worldIn.getLight(newBlockPos) > 0.9)) {
							aerated = true;
						}
					}
					else if (z != 0) {
						inContactWithAir = worldIn.isAirBlock(temp);
					}
				}
				if (aerated && !inContactWithAir) {
					return bestDepth;
				}
			}
    	}
		return bestDepth;
    }
	
	/*********************************************************
	 * Methods related to block destruction and removal.     *
	 *********************************************************/
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (this.isEquipped && worldIn.getBlockState(pos).getBlock() != ModBlocks.INJECTOR) {
				EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.ACTIVATED_GEM_BASE));
				worldIn.spawnEntity(item);
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.INJECTOR);
    }
	
	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.INJECTOR);
    }
	
    /*********************************************************
     * Methods related to block states and direction.        *
     *********************************************************/
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
    public static void setState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        worldIn.setBlockState(pos, ModBlocks.INJECTOR.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        worldIn.setBlockState(pos, ModBlocks.INJECTOR.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
    }
    
	@Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

	@Override
	public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

	@Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    /*********************************************************
     * Methods related to block rendering.                   *
     *********************************************************/
	@Override
   @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
