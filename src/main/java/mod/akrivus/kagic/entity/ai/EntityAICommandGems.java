package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICommandGems extends EntityAIBase {
	private final EntityGem gem;
	private final double movementSpeed;
	private EntityGem target;

	public EntityAICommandGems(EntityGem gem, double speed) {
		this.gem = gem;
		this.movementSpeed = speed;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.gem.getHeldItemMainhand().getItem() == ModItems.COMMANDER_STAFF) {
			List<EntityGem> list = this.gem.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.gem.getEntityBoundingBox().grow(24.0D, 16.0D, 24.0D));
			double distance = Double.MAX_VALUE;
			for (EntityGem gem : list) {
				if (!this.gem.equals(gem) && gem.isOwner(this.gem) && gem.getInsigniaColor() == this.gem.getInsigniaColor() && gem.isSitting()) {
					double newDistance = this.gem.getDistanceSq(gem);
					if (newDistance <= distance) {
						distance = newDistance;
						this.target = gem;
					}
				}
			}
			return this.target != null;
		}
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.target != null && !this.target.isDead && this.target.getInsigniaColor() == this.gem.getInsigniaColor() && this.target.isSitting() && !this.gem.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.gem.getNavigator().tryMoveToEntityLiving(this.target, this.movementSpeed);
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.target = null;
	}
	
	@Override
	public void updateTask() {
		if (this.gem.getDistanceSq(this.target) > this.gem.width * 3) {
			this.gem.getNavigator().tryMoveToEntityLiving(this.target, this.movementSpeed);
		}
		else if (this.target.isSitting()) {
			this.target.setSitting(null, false);
		}
	}
}