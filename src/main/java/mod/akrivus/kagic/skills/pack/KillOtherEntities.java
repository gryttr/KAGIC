package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityQuartzSoldier;
import mod.akrivus.kagic.linguistics.LinguisticsHelper;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLivingBase;

public class KillOtherEntities extends Speak<EntityGem> {
	private EntityLivingBase otherEntity = null;
	private int goal = 1;
	private int amountBeforeGoal = 0;
	public KillOtherEntities() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"kill",
			"destroy",
			"slay",
			"hunt"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.TARGETTING);
		this.task(true);
	}
	@Override
	public boolean triggered(EntityGem gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.goal = Integer.parseInt(this.collectedNumbers.get(0));
				}
				catch (Exception ex) {
					this.goal = 1;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.otherEntity != null && this.amountBeforeGoal < this.goal;
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityLivingBase> entities = gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityLivingBase entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity)) {
					if (LinguisticsHelper.getDistance(entity.getName(), this.selectedNoun) < 3) {
						this.otherEntity = entity;
						minDistance = distance;
					}
				}
			}
		}
		if (this.otherEntity != null) {
			gem.setAttackTarget(this.otherEntity);
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.otherEntity != null) {
			if (this.otherEntity.isDead) {
				++this.amountBeforeGoal;
			}
		}
		if (this.amountBeforeGoal < this.goal) {
			this.init(gem);
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.otherEntity = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.otherEntity;
	}
}
