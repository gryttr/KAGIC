package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityAIFollowDiamond extends EntityAIBase {
	private final EntityGem theGem;
    private EntityLivingBase theOwner;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowDiamond(EntityGem theGemIn, double followSpeedIn) {
        this.theGem = theGemIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        EntityLivingBase owner = this.getDiamond();
        if (owner == null || (owner instanceof EntityPlayer && ((EntityPlayer) owner).isSpectator()) || owner.isInvisible() || this.theGem.isSitting() || this.theGem.isAttacking) {
        	return false;
        }
        else {
            this.theOwner = owner;
            return true;
        }
    }
    public boolean shouldContinueExecuting() {
        return this.theOwner != null && !this.theGem.getNavigator().noPath() && !this.theGem.isSitting() && !this.theGem.isAttacking;
    }
    public void startExecuting() {
        this.oldWaterCost = this.theGem.getPathPriority(PathNodeType.WATER);
        this.theGem.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    public void resetTask() {
        this.theOwner = null;
        this.theGem.getNavigator().clearPath();
        this.theGem.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    public void updateTask() {
        if (!this.theGem.isSitting() && this.theGem.getDistance(this.theOwner) > (this.theOwner.width * 3) + 2) {
        	if (this.theGem.getDistance(this.theOwner) > 24.0D) {
                int x = MathHelper.floor(this.theOwner.posX - this.theGem.width);
                int y = MathHelper.floor(this.theOwner.getEntityBoundingBox().minY);
                int z = MathHelper.floor(this.theOwner.posZ - this.theGem.width);
                for (int i = 0; i <= 4; ++i) {
                    for (int j = 0; i <= 4; ++i) {
                        if ((i < 1 || j < 1 || i > 3 || j > 3) && this.canHandleClearance(this.theGem.height)) {
                            this.theGem.setLocationAndAngles((double)((float)(x + j) + 0.5F), (double) y, (double)((float)(z + i) + 0.5F), this.theGem.rotationYaw, this.theGem.rotationPitch);
                            this.theGem.getNavigator().clearPath();
                            return;
                        }
                    }
                }
            }
        	else {
        		this.theGem.getNavigator().tryMoveToEntityLiving(this.theOwner, this.followSpeed);
        	}
        }
    }
    public EntityLivingBase getDiamond() {
    	int servitude = this.theGem.getServitude();
    	if (servitude == EntityGem.SERVE_HUMAN) {
    		if (this.theGem.followingGem) {
	    		for (EntityGem gem : this.theGem.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.theGem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D))) {
	    			if (!this.theGem.equals(gem) && this.theGem.isOwner(gem) && this.theGem.getInsigniaColor() == gem.getInsigniaColor()) {
	    				return gem;
	    			}
	    		}
    		}
    		return this.theGem.getLeaderEntity();
    	}
    	else if (servitude == EntityGem.SERVE_YELLOW_DIAMOND) {
    		try {
    			return this.theGem.world.<EntityYellowDiamond>getEntitiesWithinAABB(EntityYellowDiamond.class, this.theGem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D)).get(0);
    		}
    		catch (Exception e) {
    			return null;
    		}
    	}
    	else if (servitude == EntityGem.SERVE_BLUE_DIAMOND) {
    		try {
    			return this.theGem.world.<EntityBlueDiamond>getEntitiesWithinAABB(EntityBlueDiamond.class, this.theGem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D)).get(0);
    		}
    		catch (Exception e) {
    			return null;
    		}
    	}
    	else {
    		return null;
    	}
    }
    public boolean canHandleClearance(float clearance) {
    	for (int i = 0; i < (int) Math.ceil(clearance); ++i) {
    		if (!this.theGem.world.isAirBlock(this.theGem.getPosition().up(i))) {
    			return false;
    		}
    	}
    	return true;
    }
}