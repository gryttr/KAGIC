package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityTopaz;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAITopazTarget extends EntityAITarget {
    private final EntityTopaz gem;
    public EntityAITopazTarget(EntityTopaz gemIn) {
        super(gemIn, false);
        this.gem = gemIn;
        this.setMutexBits(1);
    }
    public boolean shouldExecute() {
        if (this.gem.getAttackTarget() != null && !this.gem.getHeldEntities().isEmpty()) {
            return true;
        }
        return false;
    }
    public void startExecuting() {
        if (this.gem.getHeldEntities().contains(this.gem.getAttackTarget())) {
        	this.gem.setAttackTarget(null);
        }
        super.startExecuting();
    }
}