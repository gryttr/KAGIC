package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFormStevonnie extends EntityAIBase {
	private EntitySteven steven;
	private EntityConnie connie;

	public <T extends EntityGem> EntityAIFormStevonnie(EntitySteven steven) {
		this.steven = steven;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<EntityConnie> list = this.steven.world.<EntityConnie>getEntitiesWithinAABB(EntityConnie.class, this.steven.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntityConnie connie : list) {
			if (this.checkInitiator() && this.checkTarget(connie)) {
				double newDistance = this.steven.getDistanceSq(connie);
				if (newDistance <= distance) {
					distance = newDistance;
					this.connie = connie;
				}
			}
		}
		if (this.checkTarget(this.connie)) {
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.steven.getLookHelper().setLookPositionWithEntity(this.connie, 30.0F, 30.0F);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.checkInitiator() && this.checkTarget(this.connie);
	}
	
	@Override
	public void updateTask() {
		if (this.steven.getDistanceSq(this.connie) > this.steven.width * 3) {
			this.steven.getNavigator().tryMoveToEntityLiving(this.connie, this.steven.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 2.0);
		}
		else {
			/*
			 * Stevonnie happens here.
			 */
			this.resetTask();
		}
	}

	@Override
	public void resetTask() {
		this.steven.getNavigator().clearPath();
		this.connie = null;
	}
	
	private boolean checkInitiator() {
		return this.steven.getHealth() > 0 && !this.steven.isDead;
	}

	private boolean checkTarget(EntityConnie connie) {
		return connie != null && connie.getHealth() > 0 && !connie.isDead && connie.getRevengeTarget() != null && connie.getRevengeTarget() != this.steven && !connie.getRevengeTarget().isDead;
	}
}