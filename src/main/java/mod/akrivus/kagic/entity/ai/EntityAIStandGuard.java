package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAIStandGuard extends EntityAIBase {
	private final EntityAIBase wanderAI;
	private final EntityGem gem;

public EntityAIStandGuard(EntityGem gem, double movementSpeed) {
		this.wanderAI = new EntityAIWander(gem, movementSpeed);
		this.gem = gem;
		this.setMutexBits(5);
	}
	
	@Override
	public boolean shouldExecute() {
		return this.noNearbyAuthorities() && this.wanderAI.shouldExecute();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return (this.gem.ticksExisted % 20 == 0 && this.noNearbyAuthorities() || true) && this.wanderAI.shouldContinueExecuting();
	}
	
	@Override
	public void startExecuting() {
		this.wanderAI.startExecuting();
	}

	private boolean noNearbyAuthorities() {
		List<EntityLivingBase> list = this.gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
		for (EntityLivingBase entity : list) {
			if (this.gem.isOwner(entity) || (entity instanceof EntityAgate && ((EntityAgate) entity).isOwner(this.gem.getOwner()))) {
				/*if (this.gem.getServitude() == EntityGem.SERVE_HUMAN && this.gem.getOwner() != null && entity instanceof EntityAgate) {
					this.gem.getOwner().addStat(ModAchievements.SHOULDERS_SQUARE);
				}*/
				if (!(this.gem instanceof EntityHessonite)) {
					this.gem.canTalk = false;
				}
				return false;
			}
		}
		this.gem.canTalk = true;
		return true;
	}
}
