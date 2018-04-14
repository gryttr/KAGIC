package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class PlantSaplingsPeridot extends Speak<EntityPeridot> {
	private BlockPos plantLocation = null;
	private int goal = 4;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	public PlantSaplingsPeridot() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"plant",
			"place",
			"locate",
			"grow",
			"farm"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"sapling",
			"saplings",
			"tree",
			"trees"
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
					this.goal = 4;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityPeridot gem) {
		return this.plantLocation != null && this.amountBeforeGoal < this.goal;
	}
	@Override
	public void init(EntityPeridot gem) {
		ArrayList<BlockPos> places = new ArrayList<BlockPos>();
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(state)) {
						BlockPos place = gem.getPosition().add(x, y, z);
						if (this.plantLocation == null || this.plantLocation.getDistance(place.getX(), place.getY(), place.getZ()) > 6 && this.hasSapling(gem)) {
							BlockPos above = place.up();
							if (gem.world.isAirBlock(above)) {
								places.add(above);
							}
						}
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < places.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(places.get(i));
			if (distance < minDistance) {
				this.plantLocation = places.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityPeridot gem) {
		if (this.plantLocation != null) {
			BlockPos lookPos = this.plantLocation.down();
			gem.lookAt(lookPos);
			if (gem.getDistanceSqToCenter(this.plantLocation) < 5) {
				if (this.lastBlockBreak > 20) {
					boolean placed = gem.world.setBlockState(this.plantLocation, Block.getBlockFromItem(this.getSapling(gem).getItem()).getDefaultState(), 3);
					if (placed) {
						if (this.goal > 0) {
							++this.amountBeforeGoal;
							this.getSapling(gem).shrink(1);
						}
						if (this.amountBeforeGoal < this.goal) {
							this.init(gem);
						}
					}
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.plantLocation);
			}
		}
	}
	@Override
	public void reset(EntityPeridot gem) {
		this.amountBeforeGoal = 0;
		this.goal = 0;
	}
	public boolean isCorrectBlock(IBlockState state) {
		return state.getBlock() instanceof BlockGrass;
	}
	@Override
	public String toString() {
		return "planting trees";
	}
	public ItemStack getSapling(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) {
				return inventory.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	public boolean hasSapling(EntityPeridot gem) {
		InventoryBasic inventory = gem.gemStorage;
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			Item item = inventory.getStackInSlot(i).getItem();
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) {
				return true;
			}
		}
		return false;
	}
}
