package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.EntityWolf;

public class TameDogs extends Speak<EntityGem> {
	private EntityWolf wolf = null;
	public TameDogs() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tame",
			"find"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"wolf",
			"wolves",
			"dogs",
			"dog",
			"puppy",
			"puppies"
		}));
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.wolf != null && !this.wolf.isDead && !this.wolf.isTamed();
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityWolf> entities = gem.world.<EntityWolf>getEntitiesWithinAABB(EntityWolf.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityWolf entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity) && !entity.isTamed()) {
					this.wolf = entity;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.wolf != null) {
			gem.lookAt(this.wolf);
			if (gem.getDistanceSq(this.wolf) > 5) {
				gem.tryToMoveTo(this.wolf.getPosition());	
			}
			else {
				this.wolf.setTamedBy(gem.getOwner());
				this.wolf.setSitting(false);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.wolf = null;
	}
	@Override
	public String toString() {
		return "taming " + this.wolf.getName();
	}
}
