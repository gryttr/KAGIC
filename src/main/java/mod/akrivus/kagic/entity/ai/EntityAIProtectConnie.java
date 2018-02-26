package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIProtectConnie extends EntityAITarget {
	private EntitySteven steven;
	private EntityConnie connie;
	private long lastScream;

	public EntityAIProtectConnie(EntitySteven steven) {
		super(steven, true, true);
		this.steven = steven;
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
			this.connie.heal(5.0F);
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.connie.getRevengeTarget());
		if (this.steven.world.getWorldTime() - this.lastScream > 20) {
			this.steven.playProtectSound(this.connie.getHealth());
			this.lastScream = this.steven.world.getWorldTime();
		}
		super.startExecuting();
	}
	
	private boolean checkInitiator() {
		return this.steven.getHealth() > 0 && !this.steven.isDead;
	}

	private boolean checkTarget(EntityConnie connie) {
		return connie != null && connie.getRevengeTarget() != null && connie.getRevengeTarget() != this.steven && !connie.getRevengeTarget().isDead;
	}
}