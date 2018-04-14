package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.linguistics.LinguisticsHelper;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class BreedLivestock extends Speak<EntityPeridot> {
	private EntityAnimal otherAnimal = null;
	private int goal = 4;
	private int amountBeforeGoal = 0;
	private int lastHitTime = 0;
	public BreedLivestock() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"collect",
			"birth",
			"raise",
			"help"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.TARGETTING);
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
		return this.otherAnimal != null && this.otherAnimal.getGrowingAge() == 0 && !this.otherAnimal.isInLove() && !this.getBreedingItem(gem, this.otherAnimal).isEmpty() && this.amountBeforeGoal < this.goal;
	}
	@Override
	public void init(EntityPeridot gem) {
		List<EntityAnimal> animals = gem.world.<EntityAnimal>getEntitiesWithinAABB(EntityAnimal.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!animals.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityAnimal animal : animals) {
				double distance = gem.getDistanceSq(animal);
				if (gem.getDistanceSq(animal) < minDistance && animal.getGrowingAge() == 0 && !animal.isInLove()) {
					if (LinguisticsHelper.getDistance(animal.getName(), this.selectedNoun) < 3) {
						this.otherAnimal = animal;
						minDistance = distance;
					}
				}
			}
		}
		if (this.otherAnimal != null) {
			gem.setAttackTarget(this.otherAnimal);
		}
	}
	@Override
	public void run(EntityPeridot gem) {
		if (this.otherAnimal != null) {
			gem.lookAt(this.otherAnimal);
			gem.setHeldItem(EnumHand.OFF_HAND, this.getBreedingItem(gem, this.otherAnimal).copy());
			if (gem.getDistanceSq(this.otherAnimal) < 5) {
				if (this.lastHitTime > 10) {
					this.getBreedingItem(gem, this.otherAnimal).shrink(1);
					this.otherAnimal.setInLove(this.commandingPlayer);
					++this.amountBeforeGoal;
					this.lastHitTime = 0;
				}
				++this.lastHitTime;
			}
			else {
				gem.tryToMoveTo(this.otherAnimal.getPosition());
			}
			if (this.amountBeforeGoal < this.goal) {
				this.otherAnimal = null;
				this.init(gem);
			}
		}
	}
	@Override
	public void reset(EntityPeridot gem) {
		this.otherAnimal = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
	}
	@Override
	public String toString() {
		return "breeding " + this.otherAnimal.getName();
	}
	public ItemStack getBreedingItem(EntityPeridot gem, EntityAnimal animal) {
		for (int i = 0; i < gem.harvest.getSizeInventory(); ++i) {
			ItemStack stack = gem.harvest.getStackInSlot(i);
			if (animal.isBreedingItem(stack)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
}
