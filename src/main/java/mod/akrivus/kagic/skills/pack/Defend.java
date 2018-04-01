package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.linguistics.LinguisticsHelper;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class Defend extends Speak<EntityGem> {
	private EntityLivingBase principle = null;
	private EntityLivingBase enemy = null;
	private int lastHitTime;
	public Defend() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"defend",
			"guard",
			"protect"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.principle != null && !this.principle.isDead;
	}
	@Override
	public void init(EntityGem gem) {
		if (this.principle == null) {
			if (this.selectedNoun.equals("me")) {
				this.principle = gem.lastPlayerSpokenTo;
			}
			else {
				List<EntityLivingBase> entities = gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
				if (!entities.isEmpty()) {
					double minDistance = Double.MAX_VALUE;
					for (EntityLivingBase entity : entities) {
						double distance = gem.getDistanceSq(entity);
						if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity)) {
							if (LinguisticsHelper.getDistance(entity.getName(), this.selectedNoun) < 3) {
								this.principle = entity;
								minDistance = distance;
							}
						}
					}
				}
			}
		}
		if (this.enemy == null && this.principle != null) {
			List<EntityLivingBase> entities = gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLiving.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
			if (!entities.isEmpty()) {
				double minDistance = Double.MAX_VALUE;
				for (EntityLivingBase entity : entities) {
					double distance = gem.getDistanceSq(entity);
					if (this.principle.getDistanceSq(entity) < minDistance && !gem.equals(entity)) {
						boolean attackable = false;
						if (entity instanceof EntityLiving) {
							EntityLiving living = (EntityLiving) entity;
							if (living.getAttackTarget() != null) {
								attackable = living.getAttackTarget().equals(this.principle);
							}
						}
						if (this.principle.getRevengeTarget() != null) {
							attackable = this.principle.getRevengeTarget().equals(entity);
						}
						if (attackable) {
							this.enemy = entity;
							minDistance = distance;
						}
					}
				}
			}
			if (this.enemy != null) {
				gem.setAttackTarget(this.enemy);
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.principle != null) {
			if (gem.getAttackTarget() != null) {
				gem.lookAt(this.enemy);
				if (gem.getDistanceSq(this.enemy) < 5) {
					if (this.lastHitTime > 5) {
						gem.attackEntityAsMob(this.enemy);
						this.lastHitTime = 0;
					}
					++this.lastHitTime;
				}
				else {
					gem.tryToMoveTo(this.enemy.getPosition());
				}
				if (this.enemy.isDead) {
					this.enemy = null;
					this.init(gem);
				}
			}
			else {
				gem.lookAt(this.principle);
				if (gem.getDistanceSq(this.principle) > 5) {
					gem.tryToMoveTo(this.principle.getPosition());
				}
				else {
					this.init(gem);
				}
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.principle = null;
		this.enemy = null;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.principle;
	}
}
