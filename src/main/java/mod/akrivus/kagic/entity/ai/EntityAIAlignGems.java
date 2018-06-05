package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAIAlignGems extends EntityAIBase {
	private final EntityGem alignedGem;
	private final double movementSpeed;
	private EntityGem unalignedGem;

	public EntityAIAlignGems(EntityGem alignedGem, double speed) {
		this.alignedGem = alignedGem;
		this.movementSpeed = speed;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.alignedGem.ticksExisted % 20 == 0) {
			List<EntityGem> list = this.alignedGem.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.alignedGem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
			double distance = Double.MAX_VALUE;
			for (EntityGem gem : list) {
				if (this.alignedGem.isTamed() && !gem.isTamed()) {
					double newDistance = this.alignedGem.getDistanceSq(gem);
					if (newDistance <= distance) {
						distance = newDistance;
						this.unalignedGem = gem;
					}
				}
			}
			return this.unalignedGem != null && this.alignedGem.getNavigator().getPathToEntityLiving(unalignedGem) != null;
		}
		return false;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.unalignedGem != null 
				&& !this.unalignedGem.isDead 
				&& !this.unalignedGem.isTamed() 
				/*&& this.alignedGem.canEntityBeSeen(this.unalignedGem)*/
				&& !this.alignedGem.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.alignedGem.getLookHelper().setLookPositionWithEntity(this.unalignedGem, 30.0F, 30.0F);
		this.unalignedGem.moveToBlockPosAndAngles(new BlockPos(this.alignedGem), this.unalignedGem.rotationYaw, this.unalignedGem.rotationPitch);
	}
	
	@Override
	public void resetTask() {
		this.alignedGem.getNavigator().clearPath();
		this.unalignedGem = null;
	}
	
	@Override
	public void updateTask() {
		if (this.alignedGem.getDistanceSq(this.unalignedGem) > this.alignedGem.width * 3) {
			this.alignedGem.getNavigator().tryMoveToEntityLiving(this.unalignedGem, this.movementSpeed);
		}
		else if (!this.unalignedGem.isTamed()) {
			if (this.alignedGem.getServitude() == EntityGem.SERVE_HUMAN) {
				this.unalignedGem.setOwnerId(this.alignedGem.getOwnerId());
			}
			this.unalignedGem.setServitude(this.alignedGem.getServitude());
			this.unalignedGem.getNavigator().clearPath();
			this.unalignedGem.setAttackTarget(null);
			this.unalignedGem.setHealth(this.unalignedGem.getMaxHealth());
			this.unalignedGem.playTameEffect();
			this.unalignedGem.world.setEntityState(this.unalignedGem, (byte) 7);
			if (!(this.unalignedGem instanceof EntityZircon)) {
				this.unalignedGem.setInsigniaColor(this.alignedGem.getInsigniaColor());
			}
			if (this.alignedGem.uniformColorChanged) {
				this.unalignedGem.setUniformColor(this.alignedGem.getUniformColor());
			}
			this.unalignedGem.playObeySound();
			this.resetTask();
		}
	}
}