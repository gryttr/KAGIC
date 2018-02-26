package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.humans.EntityConnie;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIForageForFood extends EntityAITarget {
	private EntityConnie connie;
	private EntityAnimal animal;

	public EntityAIForageForFood(EntityConnie connie) {
		super(connie, true, true);
		this.connie = connie;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<EntityAnimal> list = this.connie.world.<EntityAnimal>getEntitiesWithinAABB(EntityAnimal.class, this.connie.getEntityBoundingBox().grow(24.0F, 8.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntityAnimal animal : list) {
			if (this.checkInitiator() && this.checkTarget(animal)) {
				double newDistance = this.connie.getDistanceSq(animal);
				if (newDistance <= distance) {
					distance = newDistance;
					this.animal = animal;
				}
			}
		}
		if (this.checkTarget(this.animal)) {
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.animal);
		super.startExecuting();
	}
	
	private boolean checkInitiator() {
		return this.connie.getHealth() > 0 && !this.connie.isDead && !this.connie.hasFood;
	}

	private boolean checkTarget(EntityAnimal animal) {
		return animal != null && !animal.isDead && !animal.isChild();
	}
}