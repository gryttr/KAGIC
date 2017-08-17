package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityPepo;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIRoseHurtByTarget extends EntityAITarget {
    private final EntityPepo pepo;
    private EntityLivingBase attacker;
    private int timestamp;
    
    public EntityAIRoseHurtByTarget(EntityPepo pepo) {
        super(pepo, false);
        this.pepo = pepo;
        this.setMutexBits(1);
    }
    public boolean shouldExecute() {
        if (this.pepo.getMaster() != null) {
            EntityRoseQuartz master = this.pepo.getMaster();
            if (master == null) {
                return false;
            }
            else {
                this.attacker = master.getAttackingEntity();
                int i = master.getRevengeTimer();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.pepo.shouldAttackEntity(this.attacker, master);
            }
        }
        return false;
    }
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityRoseQuartz master = this.pepo.getMaster();
        if (master != null) {
            this.timestamp = master.getRevengeTimer();
        }
        super.startExecuting();
    }
}