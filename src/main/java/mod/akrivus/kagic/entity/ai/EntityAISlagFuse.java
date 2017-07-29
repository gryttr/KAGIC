package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntitySlag;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISlagFuse extends EntityAIBase {
    private final EntitySlag slag;
    private final double movementSpeed;
    private EntitySlag otherSlag;
    public EntityAISlagFuse(EntitySlag slag, double speed) {
        this.slag = slag;
        this.movementSpeed = speed;
        this.setMutexBits(1);
    }
    public boolean shouldExecute() {
    	if (this.slag.canFuse()) {
	    	List<EntitySlag> list = this.slag.world.<EntitySlag>getEntitiesWithinAABB(EntitySlag.class, this.slag.getEntityBoundingBox().expand(16.0D, 8.0D, 16.0D));
	        double distance = Double.MAX_VALUE;
	        for (EntitySlag slag : list) {
	            if (slag.canFuse() && slag.compatIndex != this.slag.compatIndex) {
	                double newDistance = this.slag.getDistanceSqToEntity(slag);
	                if (newDistance <= distance) {
	                    distance = newDistance;
	                    this.otherSlag = slag;
	                }
	            }
	        }
	    	return this.otherSlag != null;
    	}
    	return false;
    }
    public boolean continueExecuting() {
        return this.otherSlag != null && !this.otherSlag.isDead && this.slag.canEntityBeSeen(this.otherSlag);
    }
    public void startExecuting() {
		this.slag.getLookHelper().setLookPositionWithEntity(this.otherSlag, 30.0F, 30.0F);
    }
    public void resetTask() {
    	this.slag.getNavigator().clearPathEntity();
        this.otherSlag = null;
    }
    public void updateTask() {
    	if (this.slag.getDistanceSqToEntity(this.otherSlag) > this.otherSlag.width * 2) {
			this.slag.getNavigator().tryMoveToEntityLiving(this.otherSlag, this.movementSpeed);
		}
    	else if (this.slag.compatIndex > this.otherSlag.compatIndex) {
    		this.slag.world.spawnEntity(this.slag.fuse(this.otherSlag));
    		this.otherSlag.setDead();
    		this.slag.setDead();
        	this.resetTask();
    	}
    }
}