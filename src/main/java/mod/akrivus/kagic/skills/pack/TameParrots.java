package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.EntityParrot;

public class TameParrots extends Speak<EntityGem> {
	private EntityParrot parrot = null;
	public TameParrots() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tame",
			"find"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"parrot",
			"parrots",
			"bird",
			"birds"
		}));
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.parrot != null && !this.parrot.isDead && !this.parrot.isTamed();
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityParrot> entities = gem.world.<EntityParrot>getEntitiesWithinAABB(EntityParrot.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityParrot entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity) && !entity.isTamed()) {
					this.parrot = entity;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.parrot != null) {
			gem.lookAt(this.parrot);
			if (gem.getDistanceSq(this.parrot) > 5) {
				gem.tryToMoveTo(this.parrot.getPosition());	
			}
			else {
				this.parrot.setTamedBy(gem.getOwner());
				this.parrot.setSitting(false);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.parrot = null;
	}
	@Override
	public String toString() {
		return "taming " + this.parrot.getName();
	}
}
