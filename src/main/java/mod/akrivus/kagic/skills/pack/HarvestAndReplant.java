package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;

public class HarvestAndReplant extends Speak<EntityPeridot> {
	private BlockPos harvestLocation = null;
	private int goal = 0;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	public HarvestAndReplant() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"collect",
			"plant",
			"pull",
			"get",
			"cut"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean triggered(EntityPeridot gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.goal = Integer.parseInt(this.collectedNumbers.get(0));
				}
				catch (Exception ex) {
					this.goal = 0;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityPeridot gem) {
		return this.harvestLocation != null && this.amountBeforeGoal <= this.goal && gem.isFarmer();
	}
	@Override
	public void init(EntityPeridot gem) {
		ArrayList<BlockPos> plants = new ArrayList<BlockPos>();
		this.harvestLocation = null;
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectFarmBlock(state)) {
						BlockPos above = gem.getPosition().add(x, y + 1, z);
						if (this.isCorrectPlant(gem.world.getBlockState(above)) || gem.world.isAirBlock(above)) {
							plants.add(above);
						}
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < plants.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(plants.get(i));
			if (distance < minDistance) {
				this.harvestLocation = plants.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityPeridot gem) {
		if (this.harvestLocation != null) {
			BlockPos lookPos = this.harvestLocation.down();
			gem.lookAt(lookPos);
			if (gem.getDistanceSqToCenter(this.harvestLocation) < 5) {
				IBlockState harvest = gem.world.getBlockState(this.harvestLocation);
				if (this.lastBlockBreak > 20) {
					boolean success = false;
					if (gem.world.isAirBlock(this.harvestLocation) && this.hasSeeds(gem)) {
						gem.setHeldItem(EnumHand.OFF_HAND, this.getSeed(gem).copy());
						gem.placeBlock(this.getPlant(gem), this.harvestLocation);
						this.getSeed(gem).shrink(1);
					}
					else if (this.isCorrectPlant(harvest)) {
						success = gem.world.destroyBlock(this.harvestLocation, true);
					}
					else {
						this.init(gem);
					}
					if (success) {
						if (this.goal > 0) {
							++this.amountBeforeGoal;
						}
						if (this.amountBeforeGoal <= this.goal) {
							this.init(gem);
						}
					}
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.harvestLocation);
			}
		}
	}
	@Override
	public void reset(EntityPeridot gem) {
		this.harvestLocation = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
	}
	public boolean isCorrectFarmBlock(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND;
	}
	public boolean isCorrectPlant(IBlockState state) {
		return state.getBlock().getUnlocalizedName().contains(this.selectedNoun) && ((BlockCrops) state.getBlock()).isMaxAge(state);
	}
	public IBlockState getPlant(EntityPeridot gem) {
		return ((IPlantable) this.getSeed(gem).getItem()).getPlant(gem.world, this.harvestLocation);
	}
	public ItemStack getSeed(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item.getUnlocalizedName().contains(this.selectedNoun)) {
				if (item instanceof ItemSeeds || item instanceof ItemSeedFood) {
					return inventory.getStackInSlot(i);
				}
			}
		}
		return ItemStack.EMPTY;
	}
	public boolean hasSeeds(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item.getUnlocalizedName().contains(this.selectedNoun)) {
				if (item instanceof ItemSeeds || item instanceof ItemSeedFood) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.harvestLocation;
	}
}
