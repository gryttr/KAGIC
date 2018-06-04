package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;

public class TameHorses extends Speak<EntityGem> {
	private AbstractHorse horse = null;
	public TameHorses() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tame",
			"find"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"horse",
			"horses"
		}));
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.horse != null && !this.horse.isDead && !this.horse.isTame();
	}
	@Override
	public void init(EntityGem gem) {
		List<AbstractHorse> entities = gem.world.<AbstractHorse>getEntitiesWithinAABB(AbstractHorse.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (AbstractHorse entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !entity.isTame()) {
					this.horse = entity;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.horse != null) {
			gem.lookAt(this.horse);
			if (gem.getDistanceSq(this.horse) > 5) {
				gem.tryToMoveTo(this.horse.getPosition());	
			}
			else {
				this.horse.setTamedBy(gem.getOwner());
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.horse = null;
	}
	@Override
	public String toString() {
		return "taming " + this.horse.getName();
	}
}
