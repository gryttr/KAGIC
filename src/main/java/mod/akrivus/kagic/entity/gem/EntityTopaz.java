package mod.akrivus.kagic.entity.gem;

import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.ai.EntityAITopazFuse;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModAchievements;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTopaz extends EntityGem {
	public static final HashMap<Block, Double> TOPAZ_YIELDS = new HashMap<Block, Double>();
		
	public EntityTopaz(World worldIn) {
		super(worldIn);
		this.isSoldier = true;
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.DRUM, GemPlacements.RIGHT_KNEE);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAITopazFuse(this, 1.0D));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(6, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.droppedGemItem = ModItems.TOPAZ_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_TOPAZ_GEM;
	}

	public float[] getGemColor() {
		if (this.getSpecial() == 1) {
			return new float[] { 93F / 255F, 115F / 255F, 255F / 255F };
		}
		else if (this.getSpecial() == 2) {
			return new float[] { 72F / 255F, 218F / 255F, 168F / 255F };
		}
    	return new float[] { 250F / 255F, 255F / 255F, 93F / 255F };
    }
	public String getColor() {
		switch (this.getSpecial()) {
		case 1:
			return "blue_";
		case 2:
			return "green_";
		default:
			return "";
		}
	}
	public void setColor(int color) {
		this.setSpecial(color);
	}
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.DRUM.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.LEFT_CHEEK.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_CHEEK.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	this.setSpecial(this.rand.nextInt(7) == 0 ? 1 : 0);
		return super.onInitialSpawn(difficulty, livingdata);
    }
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }
	
    /*********************************************************
     * Methods related to entity interaction.                *
     *********************************************************/
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    	return super.processInteract(player, hand);
    }
    public boolean alternateInteract(EntityPlayer player) {
    	this.wantsToFuse = true;
    	return true;
    }
    public boolean onSpokenTo(EntityPlayer player, String message) {
    	boolean spokenTo = super.onSpokenTo(player, message);
    	if (!spokenTo) {
    		message = message.toLowerCase();
    		if (this.isBeingCalledBy(player, message)) {
    			this.getLookHelper().setLookPositionWithEntity(player, 30.0F, 30.0F);
    			if (this.isOwner(player)) {
    				if (this.isMatching("regex.kagic.fuse", message)) {
    					this.wantsToFuse = true;
    					return true;
    				}
    				else if (this.isMatching("regex.kagic.unfuse", message)) {
    					this.wantsToFuse = false;
    					if (this.isFusion()) {
    						this.unfuse();
    					}
    					return true;
    				}
    			}
    		}
    	}
    	return spokenTo;
    }
    public void whenFused() {
		this.setSize(0.9F * this.getFusionCount(), 2.3F * this.getFusionCount());
    }
    
    /*********************************************************
	 * Methods related to living.                            *
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.isFusion()) {
			this.whenFused();
		}
		super.onLivingUpdate();
	}
	public boolean canFuseWith(EntityTopaz other) {
		if (this.canFuse() && other.canFuse() && this.getServitude() == other.getServitude() && this.getGemPlacement() != other.getGemPlacement()) {
			if ((this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwnerId().equals(other.getOwnerId())) || this.getServitude() != EntityGem.SERVE_HUMAN) {
				return true;
			}
		}
		return false;
	}
	public boolean canFuse() {
		return super.canFuse() && this.wantsToFuse && !this.isFusion();
	}
	public EntityTopaz fuse(EntityTopaz other) {
		EntityTopaz topaz = new EntityTopaz(this.world);
		NBTTagCompound primeCompound = new NBTTagCompound();
		this.writeEntityToNBT(primeCompound);
		topaz.fusionMembers.add(primeCompound);
		NBTTagCompound otherCompound = new NBTTagCompound();
		other.writeEntityToNBT(otherCompound);
		topaz.fusionMembers.add(otherCompound);
		if (this.getServitude() == EntityGem.SERVE_HUMAN) {
			topaz.setOwnerId(this.getOwnerId());
			topaz.setLeader(this.getOwner());
		}
		topaz.setServitude(this.getServitude());
		topaz.setFusionCount(this.getFusionCount() + other.getFusionCount());
		topaz.generateFusionPlacements();
		topaz.setPosition((this.posX + other.posX) / 2, (this.posY + other.posY) / 2, (this.posZ + other.posZ) / 2);
		topaz.setSpecial(this.getSpecial() == other.getSpecial() ? this.getSpecial() : 2);
		topaz.setAttackTarget(this.getAttackTarget());
		topaz.setRevengeTarget(this.getAttackingEntity());
		
		topaz.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D * topaz.getFusionCount());
		topaz.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D * topaz.getFusionCount());
		topaz.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D / topaz.getFusionCount());
		topaz.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0F);
		topaz.stepHeight = topaz.getFusionCount();
		topaz.setHealth(topaz.getMaxHealth());

    	ItemStack weapon = this.getHeldItem(EnumHand.MAIN_HAND);
    	if (weapon.getItem() == Items.AIR) {
    		weapon = other.getHeldItem(EnumHand.MAIN_HAND);
    	}
    	ItemStack second = this.getHeldItem(EnumHand.OFF_HAND);
    	if (second.getItem() == Items.AIR) {
    		second = other.getHeldItem(EnumHand.OFF_HAND);
    	}
    	topaz.setFusionWeapon(weapon);
    	topaz.setFusionWeapon(second);

    	topaz.setSize(0.9F * topaz.getFusionCount(), 2.3F * topaz.getFusionCount());
		return topaz;
	}
	public void unfuse() {
		for (int i = 0; i < this.fusionMembers.size(); ++i) {
			EntityTopaz topaz = new EntityTopaz(this.world);
			topaz.readFromNBT(this.fusionMembers.get(i));
			topaz.setUniqueId(MathHelper.getRandomUUID(this.world.rand));
			topaz.setHealth(topaz.getMaxHealth());
			topaz.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			topaz.setRevengeTarget(null);
			topaz.setAttackTarget(null);
			topaz.wantsToFuse = false;
			this.world.spawnEntity(topaz);
		}
		this.world.removeEntity(this);
	}
    /*********************************************************
     * Methods related to entity combat.                     *
     *********************************************************/
    public boolean attackEntityFrom(DamageSource source, float amount) {
		return super.attackEntityFrom(source, amount);
	}
	public boolean attackEntityAsMob(Entity entityIn) {
		/*if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
        	this.getOwner().addStat(ModAchievements.DO_IT_YOURSELF);
        }*/
		return super.attackEntityAsMob(entityIn);
	}
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		super.attackEntityWithRangedAttack(target, distanceFactor);
	}
	public void onDeath(DamageSource cause) {
		if (this.getSpecial() == 1) {
			this.droppedGemItem = ModItems.BLUE_TOPAZ_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_BLUE_TOPAZ_GEM;
		}
		else {
			this.droppedGemItem = ModItems.TOPAZ_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_TOPAZ_GEM;
		}
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }
	public SoundEvent getHurtSound() {
		return ModSounds.TOPAZ_STEP;
	}
	public SoundEvent getObeySound() {
		return ModSounds.TOPAZ_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.TOPAZ_DEATH;
	}
}
