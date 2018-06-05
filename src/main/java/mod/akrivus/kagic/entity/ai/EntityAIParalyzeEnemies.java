package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class EntityAIParalyzeEnemies extends EntityAIBase {
	private final EntityAquamarine observer;
	private EntityLivingBase target;
    public EntityAIParalyzeEnemies(EntityAquamarine observerIn) {
        this.observer = observerIn;
        this.setMutexBits(7);
    }
    public boolean shouldExecute() {
        List<EntityMob> list = this.observer.world.<EntityMob>getEntitiesWithinAABB(EntityMob.class, this.observer.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityMob entity : list) {
        	if (!entity.isGlowing() && (this.observer.isOnSameTeam(entity.getAttackTarget()) || this.observer.isOnSameTeam(entity.getRevengeTarget()))) {
	            double newDistance = entity.getDistanceSq(this.observer);
	            if (newDistance <= maxDistance) {
	                maxDistance = newDistance;
	                this.target = entity;
	            }
        	}
        }
        return this.target != null && !this.observer.isDefective();
    }
    public void startExecuting() {
    	this.target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 8));
    	this.target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600));
    	if (this.observer.isPrimary()) {
    		this.target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 600));
    	}
    }
    public void resetTask() {
        this.target = null;
    }
}