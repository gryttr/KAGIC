package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {
    private final EntityPeridot gem;
    private final World world;
    private int currentTask = -1;
    private int delay = 0;
    public EntityAIHarvestFarmland(EntityPeridot gemIn, double speedIn) {
        super(gemIn, speedIn, 16);
        this.gem = gemIn;
        this.world = gemIn.world;
    }
    public boolean shouldExecute() {
    	if (this.gem.isFarmer()) {
        	if (delay > 20 + this.gem.getRNG().nextInt(20)) {
                this.runDelay = 0;
                return super.shouldExecute();
            }
        	else {
        		++this.delay;
        	}
        }
        return false;
    }
    public boolean continueExecuting() {
        return super.continueExecuting() && this.currentTask >= 0 && this.gem.isFarmer();
    }
    public void startExecuting() {
        super.startExecuting();
    }
    public void resetTask() {
        super.resetTask();
    }
    public void updateTask() {
        super.updateTask();
        this.gem.getLookHelper().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.gem.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (block instanceof BlockBeetroot) {
            	if (iblockstate.getValue(BlockBeetroot.BEETROOT_AGE) >= 3) {
            		this.world.destroyBlock(blockpos, true);
            	}
            }
            else if (this.currentTask == 0 && block instanceof BlockCrops && iblockstate.getValue(BlockCrops.AGE) >= 7) {
                this.world.destroyBlock(blockpos, true);
            }
            else if (this.currentTask == 0 && block instanceof BlockNetherWart && iblockstate.getValue(BlockNetherWart.AGE) >= 3) {
                this.world.destroyBlock(blockpos, true);
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR) {
                InventoryBasic inventory = this.gem.gemStorage;
                for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = inventory.getStackInSlot(i);
                    boolean flag = false;
                    if (!itemstack.isEmpty()) {
                        if (this.world.getBlockState(blockpos.down()).getBlock() == Blocks.FARMLAND) {
	                    	if (itemstack.getItem() == Items.WHEAT_SEEDS) {
	                            this.world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 3);
	                            flag = true;
	                        }
	                        else if (itemstack.getItem() == Items.POTATO) {
	                            this.world.setBlockState(blockpos, Blocks.POTATOES.getDefaultState(), 3);
	                            flag = true;
	                        }
	                        else if (itemstack.getItem() == Items.CARROT) {
	                            this.world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
	                            flag = true;
	                        }
	                        else if (itemstack.getItem() == Items.BEETROOT_SEEDS) {
	                            this.world.setBlockState(blockpos, Blocks.BEETROOTS.getDefaultState(), 3);
	                            flag = true;
	                        }
                        }
                        else if (this.world.getBlockState(blockpos.down()).getBlock() == Blocks.SOUL_SAND) {
	                        if (itemstack.getItem() == Items.NETHER_WART) {
	                        	this.world.setBlockState(blockpos, Blocks.NETHER_WART.getDefaultState(), 3);
	                            flag = true;
	                        }
                        }
                    }
                    if (flag) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                        	inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        }
                        break;
                    }
                }
            }
            this.currentTask = -1;
        }
    }

	private boolean hasSeeds() {
		InventoryBasic inventory = this.gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.CARROT || item == Items.POTATO) {
				return true;
			}
		}
		return false;
	}
    
	private boolean hasNetherWart() {
		InventoryBasic inventory = this.gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Items.NETHER_WART) {
				return true;
			}
		}
		return false;
	}

	protected boolean shouldMoveTo(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        BlockPos cropPos = pos.up();
        IBlockState iblockstate = world.getBlockState(cropPos);
        Block crop = iblockstate.getBlock();
        if (block == Blocks.FARMLAND) {
            if (crop instanceof BlockBeetroot) {
            	if (iblockstate.getValue(BlockBeetroot.BEETROOT_AGE) >= 3 && (this.currentTask == 0 || this.currentTask < 0)) {
            		this.currentTask = 0;
            		return true;
            	}
            }
            else if (crop instanceof BlockCrops && iblockstate.getValue(BlockCrops.AGE) >= 7 && (this.currentTask == 0 || this.currentTask < 0)) {
                this.currentTask = 0;
                return true;
            }
        }
        else if (block == Blocks.SOUL_SAND) {
            if (crop instanceof BlockNetherWart && iblockstate.getValue(BlockNetherWart.AGE) >= 3 && (this.currentTask == 0 || this.currentTask < 0)) {
                this.currentTask = 0;
                return true;
            }
        }
        if (iblockstate.getMaterial() == Material.AIR && (this.currentTask == 1 || this.currentTask < 0)) {
        	if (block == Blocks.FARMLAND && this.hasSeeds()) {
        		this.currentTask = 1;
            	return true;
            } else if (block == Blocks.SOUL_SAND && this.hasNetherWart()) {
            	this.currentTask = 1;
            	return true;
            }
        }
        return false;
    }
}