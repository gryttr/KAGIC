package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIDiamondHurtByTarget extends EntityAITarget {
    private final EntityGem gem;
    private EntityLivingBase attacker;
    private int timestamp;

    public EntityAIDiamondHurtByTarget(EntityGem gemIn) {
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
                this.attacker = entitylivingbase.getAITarget();
                int i = entitylivingbase.getRevengeTimer();
                if (i != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.gem.shouldAttackEntity(this.attacker, entitylivingbase)) {
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
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = this.getDiamond();
        
        if (entitylivingbase != null) {
            this.timestamp = entitylivingbase.getRevengeTimer();
        }
        super.startExecuting();
    }
    
    public EntityLivingBase getDiamond() {
    	int servitude = this.gem.getServitude();
    	if (servitude == EntityGem.SERVE_HUMAN) {
    		return this.gem.getLeaderEntity();
    	}
    	else if (servitude == EntityGem.SERVE_YELLOW_DIAMOND) {
    		try {
    			return this.gem.world.<EntityYellowDiamond>getEntitiesWithinAABB(EntityYellowDiamond.class, this.gem.getEntityBoundingBox().expand(24.0D, 8.0D, 24.0D)).get(0);
    		}
    		catch (Exception e) {
    			return null;
    		}
    	}
    	else if (servitude == EntityGem.SERVE_BLUE_DIAMOND) {
    		try {
    			return this.gem.world.<EntityBlueDiamond>getEntitiesWithinAABB(EntityBlueDiamond.class, this.gem.getEntityBoundingBox().expand(24.0D, 8.0D, 24.0D)).get(0);
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