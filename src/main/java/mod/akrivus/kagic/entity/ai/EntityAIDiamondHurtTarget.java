package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIDiamondHurtTarget extends EntityAITarget {
    private final EntityGem gem;
    private EntityLivingBase target;
    private int timestamp;
    
    public EntityAIDiamondHurtTarget(EntityGem gemIn) {
        super(gemIn, false);
        this.gem = gemIn;
        this.setMutexBits(1);
    }
    public boolean shouldExecute() {
        if (!this.gem.isTamed()) {
            return false;
        }
        else {
            EntityLivingBase entitylivingbase = this.getDiamond();
            if (entitylivingbase == null) {
                return false;
            }
            else {
                this.target = entitylivingbase.getLastAttackedEntity();
                int i = entitylivingbase.getLastAttackedEntityTime();
                
                if (i != this.timestamp && this.isSuitableTarget(this.target, false) && this.gem.shouldAttackEntity(this.gem, this.target)) {
                	this.gem.isAttacking = true;
                }
                else {
                	this.gem.isAttacking = false;
                }
                return this.gem.isAttacking;
            }
        }
    }
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.target);
        EntityLivingBase entitylivingbase = this.getDiamond();

        if (entitylivingbase != null) {
            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
    protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
    	if (target instanceof EntityGem) {
    		EntityGem gem = (EntityGem) target;
    		if (gem.getServitude() != this.gem.getServitude()) {
    			return super.isSuitableTarget(target, includeInvincibles);
    		}
    		else {
    			if (gem.getServitude() == EntityGem.SERVE_HUMAN && gem.isOwnerId(this.gem.getOwnerId())) {
    				return false;
    			}
    		}
    	}
    	boolean suitable = super.isSuitableTarget(target, includeInvincibles);
    	return suitable;
    }
    public EntityLivingBase getDiamond() {
    	int servitude = this.gem.getServitude();
    	if (servitude == EntityGem.SERVE_HUMAN) {
    		if (this.gem.getLeaderEntity() == null) {
	    		for (EntityGem gem : this.gem.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D))) {
	    			if (!this.gem.equals(gem) && this.gem.isOwner(gem) && this.gem.getInsigniaColor() == gem.getInsigniaColor()) {
	    				return gem;
	    			}
	    		}
    		}
    		return this.gem.getLeaderEntity();
    	}
    	else if (servitude == EntityGem.SERVE_YELLOW_DIAMOND) {
    		try {
    			return this.gem.world.<EntityYellowDiamond>getEntitiesWithinAABB(EntityYellowDiamond.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D)).get(0);
    		}
    		catch (Exception e) {
    			return null;
    		}
    	}
    	else if (servitude == EntityGem.SERVE_BLUE_DIAMOND) {
    		try {
    			return this.gem.world.<EntityBlueDiamond>getEntitiesWithinAABB(EntityBlueDiamond.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D)).get(0);
    		}
    		catch (Exception e) {
    			return null;
    		}
    	}
    	else {
    		return null;
    	}
    }
}