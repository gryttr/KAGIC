package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityTopaz;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIEnvelopEntities extends EntityAIBase {
	private final EntityTopaz topaz;
	private EntityLivingBase target;
	public EntityAIEnvelopEntities(EntityTopaz topaz) {
		this.topaz = topaz;
		this.setMutexBits(3);
	}
	public boolean shouldExecute() {
		this.target = this.topaz.getAttackTarget();
		return this.topaz.getPassengers().isEmpty() && this.target != null && !this.target.isRiding();
	}
	public boolean continueExecuting() {
		return this.shouldExecute() && !this.target.isDead;
	}
	public void resetTask() {
		this.topaz.getNavigator().clearPathEntity();
		this.target = null;
	}
	public void updateTask() {
		if (this.topaz.getDistanceSqToEntity(this.target) < 4.0F) {
			this.topaz.isPeaceful = this.target.startRiding(this.topaz, true);
			this.resetTask();
		}
	}
}
