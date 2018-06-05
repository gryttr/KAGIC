package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.gem.EntityRuby;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAIRideHorses extends EntityAIBase {
	private final EntityRuby follower;
	private AbstractHorse target;
    private final double followSpeed;
    public EntityAIRideHorses(EntityRuby followerIn, double followSpeedIn) {
        this.follower = followerIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        List<EntityLivingBase> list = this.follower.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.follower.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        boolean lookingForHorses = false;
        for (int i = 0; i < list.size(); ++i) {
        	EntityLivingBase entity = list.get(i);
        	if (this.follower.isOwner(entity) && entity.getRidingEntity() instanceof AbstractHorse) {
        		if (!lookingForHorses) {
        			lookingForHorses = true;
        			i = -1;
        		}
        	}
        	else if (lookingForHorses && entity instanceof AbstractHorse) {
        		AbstractHorse horse = (AbstractHorse) entity;
        		if (horse.isHorseSaddled() && !horse.isBeingRidden()) {
		            double newDistance = entity.getDistanceSq(this.follower);
		            if (newDistance <= maxDistance) {
		                maxDistance = newDistance;
		                this.target = horse;
		            }
        		}
        	}
        }
        return this.shouldContinueExecuting();
    }
    public boolean shouldContinueExecuting() {
        return !this.follower.isRiding() && this.target != null && this.target.isHorseSaddled() && !this.target.isBeingRidden();
    }
    public void resetTask() {
        this.target = null;
        this.follower.getNavigator().clearPath();
    }
    public void updateTask() {
        if (this.follower.getDistanceSq(this.target) > (this.target.width * 3) + 3) {
        	this.follower.getNavigator().tryMoveToEntityLiving(this.target, this.followSpeed);
        }
        else if (this.shouldContinueExecuting()) {
        	this.follower.startRiding(this.target);
        }
    }
}
