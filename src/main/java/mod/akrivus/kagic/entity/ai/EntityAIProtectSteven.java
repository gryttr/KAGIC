package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIProtectSteven extends EntityAITarget {
	private EntityConnie connie;
	private EntitySteven steven;
	private long lastScream;

	public EntityAIProtectSteven(EntityConnie connie) {
		super(connie, true, true);
		this.connie = connie;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntitySteven steven : list) {
			if (this.checkInitiator() && this.checkTarget(steven)) {
				double newDistance = this.connie.getDistanceSq(steven);
				if (newDistance <= distance) {
					distance = newDistance;
					this.steven = steven;
				}
			}
		}
		if (this.checkTarget(this.steven)) {
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.connie.getRevengeTarget());
		if (this.connie.world.getWorldTime() - this.lastScream > 20) {
			this.connie.playProtectSound(this.steven.getHealth());
			this.lastScream = this.connie.world.getWorldTime();
		}
		super.startExecuting();
	}
	
	private boolean checkInitiator() {
		return this.connie.getHealth() > 0 && !this.connie.isDead;
	}

	private boolean checkTarget(EntitySteven steven) {
		return steven != null && steven.getRevengeTarget() != null && steven.getRevengeTarget() != this.connie && !steven.getRevengeTarget().isDead;
	}
}