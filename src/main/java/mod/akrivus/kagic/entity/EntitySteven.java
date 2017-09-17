package mod.akrivus.kagic.entity;

import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntitySteven extends EntityCreature {
	public EntitySteven(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.5F);

		// Other entity AIs.
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
	}
	public boolean canBreatheUnderwater() {
        return true;
    }
    public boolean canDespawn() {
		return false;
    }
    public boolean shouldAttackEntity(EntityLivingBase var1, EntityLivingBase var2) {
        return true;
    }
    public boolean attackEntityAsMob(Entity entityIn) {
        float amount = (float)(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        this.swingArm(EnumHand.MAIN_HAND);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), amount);
    }
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
	protected SoundEvent getAmbientSound() {
		return ModSounds.PEPO_LIVING;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.PEPO_LIVING;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.PEPO_LIVING;
	}
}
