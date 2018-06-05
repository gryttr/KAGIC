package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class MilkCows extends Speak<EntityGem> {
	private EntityCow otherCow = null;
	private int lastHitTime = 0;
	public MilkCows() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"collect",
			"grab",
			"get"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"milk",
			"cows",
			"cow"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.TARGETTING);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.otherCow != null && this.otherCow.getGrowingAge() == 0 && gem.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.BUCKET && gem.isSoldier;
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityCow> cows = gem.world.<EntityCow>getEntitiesWithinAABB(EntityCow.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!cows.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityCow cow : cows) {
				double distance = gem.getDistanceSq(cow);
				if (gem.getDistanceSq(cow) < minDistance && cow.getGrowingAge() == 0 && !cow.isInLove()) {
					this.otherCow = cow;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.otherCow != null) {
			gem.lookAt(this.otherCow);
			if (gem.getDistanceSq(this.otherCow) < 5) {
				if (this.lastHitTime > 5) {
					gem.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.MILK_BUCKET));
					this.lastHitTime = 0;
				}
				++this.lastHitTime;
			}
			else {
				gem.tryToMoveTo(this.otherCow.getPosition());
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.otherCow = null;
	}
	@Override
	public String toString() {
		return "milking " + this.otherCow.getName();
	}
}
