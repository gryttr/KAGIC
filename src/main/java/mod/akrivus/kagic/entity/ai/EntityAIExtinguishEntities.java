package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIExtinguishEntities extends EntityAIBase {
	private final EntityLapisLazuli follower;
	private EntityLivingBase target;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIExtinguishEntities(EntityLapisLazuli followerIn, double followSpeedIn) {
        this.follower = followerIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        List<EntityLivingBase> list = this.follower.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.follower.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityLivingBase entity : list) {
        	if (this.follower.isOnSameTeam(entity) && entity.isBurning()) {
	            double newDistance = entity.getDistanceSq(this.follower);
	            if (newDistance <= maxDistance) {
	                maxDistance = newDistance;
	                this.target = entity;
	            }
        	}
        }
        return this.target != null;
    }
    public boolean shouldContinueExecuting() {
        return this.target != null && this.target.isBurning() && !this.follower.getNavigator().noPath();
    }
    public void startExecuting() {
        this.oldWaterCost = this.follower.getPathPriority(PathNodeType.WATER);
        this.follower.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    public void resetTask() {
        this.target = null;
        this.follower.getNavigator().clearPath();
        this.follower.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    public void updateTask() {
        if (this.follower.getDistanceSq(this.target) > (this.target.width * 3) + 3) {
        	this.follower.getNavigator().tryMoveToEntityLiving(this.target, this.followSpeed);
        }
        else {
        	this.target.extinguish();
        }
    }
}