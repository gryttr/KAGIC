package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;

public class TameCats extends Speak<EntityGem> {
	private EntityOcelot ocelot = null;
	public TameCats() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"tame",
			"find"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] { 
			"kitten",
			"kittens",
			"cat",
			"cats",
			"ocelot",
			"ocelots"
		}));
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.ocelot != null && !this.ocelot.isDead && !this.ocelot.isTamed();
	}
	@Override
	public void init(EntityGem gem) {
		List<EntityOcelot> entities = gem.world.<EntityOcelot>getEntitiesWithinAABB(EntityOcelot.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
		if (!entities.isEmpty()) {
			double minDistance = Double.MAX_VALUE;
			for (EntityOcelot entity : entities) {
				double distance = gem.getDistanceSq(entity);
				if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity) && !entity.isTamed()) {
					this.ocelot = entity;
					minDistance = distance;
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.ocelot != null) {
			gem.lookAt(this.ocelot);
			if (gem.getDistanceSq(this.ocelot) > 5) {
				gem.tryToMoveTo(this.ocelot.getPosition());	
			}
			else {
				this.ocelot.setTamedBy(gem.getOwner());
				this.ocelot.setTameSkin(1 + gem.world.rand.nextInt(3));
				this.ocelot.setSitting(false);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.ocelot = null;
	}
	@Override
	public String toString() {
		return "taming " + this.ocelot.getName();
	}
}
