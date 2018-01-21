package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIStay extends EntityAIBase {
    private final EntityGem theEntity;
    private boolean isSitting;
    public EntityAIStay(EntityGem entityIn) {
        this.theEntity = entityIn;
        this.setMutexBits(5);
    }

    public boolean shouldExecute() {
        if (!this.theEntity.isTamed()) {
            return false;
        }
        else if (this.theEntity.isInWater()) {
            return false;
        }
        else if (!this.theEntity.onGround) {
            return false;
        }
        else {
            EntityLivingBase entitylivingbase = this.theEntity.getOwner();
            return entitylivingbase == null ? true : (this.theEntity.getDistanceSq(entitylivingbase) < 144.0D && entitylivingbase.getLastAttackedEntity() != null ? false : this.isSitting);
        }
    }

    public void startExecuting() {
        this.theEntity.getNavigator().clearPath();
        this.theEntity.isSitting = true;
    }

    public void resetTask() {
        this.theEntity.isSitting = false;
    }

    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}