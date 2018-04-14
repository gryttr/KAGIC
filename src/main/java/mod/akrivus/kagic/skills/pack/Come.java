package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.linguistics.LinguisticsHelper;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.entity.EntityLivingBase;

public class Come extends Speak<EntityGem> {
	private EntityLivingBase destination = null;
	public Come() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"come",
			"go"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>();
		this.canBeStopped = true;
		this.can(RunWith.TARGETTING);
		this.priority(Priority.LOW);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.destination != null && !this.destination.isDead;
	}
	@Override
	public void init(EntityGem gem) {
		if (this.selectedNoun.equals("me")) {
			this.destination = gem.lastPlayerSpokenTo;
		}
		else {
			List<EntityLivingBase> entities = gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, gem.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D), null);
			if (!entities.isEmpty()) {
				double minDistance = Double.MAX_VALUE;
				for (EntityLivingBase entity : entities) {
					double distance = gem.getDistanceSq(entity);
					if (gem.getDistanceSq(entity) < minDistance && !gem.equals(entity)) {
						if (LinguisticsHelper.getDistance(entity.getName(), this.selectedNoun) < 3) {
							this.destination = entity;
							minDistance = distance;
						}
					}
				}
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.destination != null) {
			gem.lookAt(this.destination);
			if (gem.getDistanceSq(this.destination) > 5) {
				gem.tryToMoveTo(this.destination.getPosition());	
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.destination = null;
	}
	@Override
	public String toString() {
		return "coming to " + this.destination.getName();
	}
}
