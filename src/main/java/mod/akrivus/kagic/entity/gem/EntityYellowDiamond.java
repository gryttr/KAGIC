package mod.akrivus.kagic.entity.gem;

import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIAlignGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.util.PoofDamage;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityYellowDiamond extends EntityGem {
	public static final HashMap<Block, Double> YELLOW_DIAMOND_YIELDS = new HashMap<Block, Double>();
	private BossInfoServer healthBar = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);
	private int lastSpecialAttack = 0;
	private int lastRecruitAttack = 0;
	public EntityYellowDiamond(World worldIn) {
		super(worldIn);
		this.setSize(3.0F, 13.8F);
		this.stepHeight = 2.0F;
		
		this.setCutPlacement(GemCuts.DIAMOND, GemPlacements.CHEST);
		
		// Boss stuff.
		this.experienceValue = 18000;
		this.isImmuneToFire = true;
		this.isDiamond = true;
		
		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAlignGems(this, 1.0D));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
                if (input instanceof EntityGem) {
                	return ((EntityGem) input).getServitude() == EntityGem.SERVE_HUMAN || input instanceof EntityYellowDiamond;
                }
                return input != null && (input instanceof EntityPlayer || input instanceof EntityDragon || input instanceof EntityWither);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(4.0D);
        this.droppedGemItem = ModItems.YELLOW_DIAMOND_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_YELLOW_DIAMOND_GEM;
	}
	
	/*********************************************************
	 * Methods related to loading.                           *
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("lastSpecialAttack", this.lastSpecialAttack);
        compound.setInteger("lastRecruitAttack", this.lastRecruitAttack);
        this.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
	}
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.lastSpecialAttack = compound.getInteger("lastSpecialAttack");
        this.lastRecruitAttack = compound.getInteger("lastRecruitAttack");
        this.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
    }
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	this.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
    	EntityPearl pearl = new EntityPearl(this.world);
		pearl.setPosition(this.posX, this.posY, this.posZ);
		pearl.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
		pearl.setGemPlacement(GemPlacements.CHEST.id);
		pearl.setSpecialSkin(2);
		pearl.onInitialSpawn(difficulty, null);
		this.world.spawnEntity(pearl);
    	return super.onInitialSpawn(difficulty, livingdata);
    }
    
    /*********************************************************
     * Methods related to combat.                            *
     *********************************************************/
    protected void updateAITasks() {
    	super.updateAITasks();
    	this.healthBar.setPercent(this.getHealth() / this.getMaxHealth());
    	if (this.getAttackTarget() != null) {
	    	if (this.hurtResistantTime > 0 && this.lastRecruitAttack < 200 && this.lastRecruitAttack % 10 == 0 && this.rand.nextBoolean()) {
	    		EntityTopaz warrior = new EntityTopaz(this.world);
				warrior.setPosition(this.posX, this.posY, this.posZ);
				warrior.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
				warrior.setRevengeTarget(this.getAttackTarget());
				warrior.setColor(0);
				warrior.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), null);
				this.world.spawnEntity(warrior);
	    	}
	    	++this.lastRecruitAttack;
	    	if (this.lastSpecialAttack > 100 && this.rand.nextBoolean()) {
	    		AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(8.0, (double) this.world.getHeight(), 8.0);
	            List<EntityGem> list = this.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, axisalignedbb);
	            int count = 0;
	            for (EntityGem gem : list) {
	            	if (gem.isOwner(this.getAttackTarget())) {
	            		if (count < list.size() / 2 + 1) {
		            		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.world, gem.posX, gem.posY, gem.posZ, true);
		            		this.world.addWeatherEffect(lightningBolt);
			            	gem.attackEntityFrom(new PoofDamage(), gem.getHealth());
			            	++count;
	            		}
	            	}
	            }
	    		this.lastSpecialAttack = 0;
	    	}
	    	++this.lastSpecialAttack;
    	}
    }
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.world.playSound(null, player.getPosition(), ModSounds.RECORD_YELLOW_DIAMOND, SoundCategory.MUSIC, 100.0F, 1.0F);
        this.world.playSound(null, player.getPosition(), ModSounds.YELLOW_DIAMOND_INTRO, SoundCategory.HOSTILE, 10.0F, 1.0F);
        this.healthBar.addPlayer(player);
    }
    public void removeTrackingPlayer(EntityPlayerMP player) {
    	this.world.playSound(null, player.getPosition(), ModSounds.YELLOW_DIAMOND_OUTRO, SoundCategory.HOSTILE, 10.0F, 1.0F);
        super.removeTrackingPlayer(player);
        this.healthBar.removePlayer(player);
    }
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source.getTrueSource() instanceof EntityLivingBase && this.getHealth() / 20 < amount &&  this.rand.nextInt(4) == 0 && !this.world.isRemote) {
    		EntityGem warrior = new EntityTopaz(this.world);
			warrior.setPosition(this.posX, this.posY, this.posZ);
			warrior.setServitude(EntityGem.SERVE_YELLOW_DIAMOND);
			warrior.setRevengeTarget(this.getAttackTarget());
			warrior.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), null);
			this.world.spawnEntity(warrior);
		}
		return super.attackEntityFrom(source, amount);
	}
	public boolean attackEntityAsMob(Entity entityIn) {
		int divider = entityIn instanceof EntityPlayer ? 5 : 1;
		if (entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((40 + this.rand.nextInt(40))) / divider)) {
            entityIn.motionX += -this.motionX;
            entityIn.motionZ += -this.motionZ;
            entityIn.motionY += 0.4D;
        }
		return super.attackEntityAsMob(entityIn);
	}
    
    /*********************************************************
	 * Methods related to entity death.                      *
	 *********************************************************/
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			this.dropItem(ModItems.RECORD_YELLOW_DIAMOND, 1);
			if (cause.getTrueSource() instanceof EntityPlayer) {
				List<EntityGem> list = this.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
				EntityPlayer player = (EntityPlayer)cause.getTrueSource();
				for(EntityGem gem : list) {
					if (gem.getServitude() == EntityGem.SERVE_YELLOW_DIAMOND && !gem.equals(this)) {
			    		gem.setOwnerId(player.getUniqueID());
			    		gem.setLeader(player);
			    		gem.setServitude(EntityGem.SERVE_HUMAN);
			        	gem.getNavigator().clearPathEntity();
			        	gem.setAttackTarget(null);
			        	gem.setHealth(gem.getMaxHealth());
			        	gem.playTameEffect();
			        	gem.world.setEntityState(gem, (byte) 7);
			        	gem.playObeySound();
					}
				}
			}
		}
		super.onDeath(cause);
	}
    
	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/
	public float getSoundPitch() {
    	return 1.0F;
    }
	public int getTalkInterval() {
    	return 200;
    }
	public SoundEvent getAmbientSound() {
		return ModSounds.YELLOW_DIAMOND_LIVING;
	}
	public SoundEvent getHurtSound() {
		return ModSounds.YELLOW_DIAMOND_HURT;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.YELLOW_DIAMOND_DEATH;
	}
}
