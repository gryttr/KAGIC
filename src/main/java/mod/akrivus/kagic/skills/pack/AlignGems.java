package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import mod.akrivus.kagic.skills.Speak;

public class AlignGems extends Speak<EntityGem> {
	private EntityGem unalignedGem = null;
	public AlignGems() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tame",
			"find",
			"recruit"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"gem",
			"gems"
		}));
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.unalignedGem != null && !this.unalignedGem.isDead && !this.unalignedGem.isTamed();
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityGem> entities = gem.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityGem entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity) && !entity.isTamed()) {
					this.unalignedGem = entity;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.unalignedGem != null) {
			gem.lookAt(this.unalignedGem);
			if (gem.getDistanceSq(this.unalignedGem) > 5) {
				gem.tryToMoveTo(this.unalignedGem.getPosition());	
			}
			else {
				if (gem.getServitude() == EntityGem.SERVE_HUMAN) {
					this.unalignedGem.setOwnerId(gem.getOwnerId());
				}
				this.unalignedGem.setServitude(gem.getServitude());
				this.unalignedGem.getNavigator().clearPath();
				this.unalignedGem.setAttackTarget(null);
				this.unalignedGem.setHealth(this.unalignedGem.getMaxHealth());
				this.unalignedGem.playTameEffect();
				this.unalignedGem.world.setEntityState(this.unalignedGem, (byte) 7);
				if (!(this.unalignedGem instanceof EntityZircon)) {
					this.unalignedGem.setInsigniaColor(gem.getInsigniaColor());
				}
				if (gem.uniformColorChanged) {
					this.unalignedGem.setUniformColor(gem.getUniformColor());
				}
				this.unalignedGem.playObeySound();
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.unalignedGem = null;
	}
	@Override
	public String toString() {
		return "recruiting " + this.unalignedGem.getName();
	}
}
