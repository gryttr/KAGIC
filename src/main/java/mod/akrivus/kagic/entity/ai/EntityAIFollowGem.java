package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowGem extends EntityAIBase {
	private final EntityLiving follower;
	private EntityGem gem;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowGem(EntityLiving followerIn, double followSpeedIn) {
        this.follower = followerIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        List<EntityGem> list = this.follower.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.follower.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityGem gem : list) {
            double newDistance = gem.getDistanceSq(this.follower);
            if (newDistance <= maxDistance) {
                maxDistance = newDistance;
                this.gem = gem;
            }
        }
        return this.gem != null;
    }
    public boolean shouldContinueExecuting() {
        return this.gem != null && !this.follower.getNavigator().noPath();
    }
    public void startExecuting() {
        this.oldWaterCost = this.follower.getPathPriority(PathNodeType.WATER);
        this.follower.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    public void resetTask() {
        this.gem = null;
        this.follower.getNavigator().clearPath();
        this.follower.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    public void updateTask() {
        if (this.follower.getDistanceSq(this.gem) > (this.gem.width * 3) + 3) {
        	this.follower.getNavigator().tryMoveToEntityLiving(this.gem, this.followSpeed);
        }
    }
}