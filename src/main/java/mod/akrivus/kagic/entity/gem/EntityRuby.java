package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIPickUpItems;
import mod.akrivus.kagic.entity.ai.EntityAIRubyFuse;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRuby extends EntityGem {
	public static final HashMap<IBlockState, Double> RUBY_YIELDS = new HashMap<IBlockState, Double>();
	private static final DataParameter<Integer> ANGER = EntityDataManager.<Integer>createKey(EntityRuby.class, DataSerializers.VARINT);
	private int angerTicks = 0;
	
	private static final int SKIN_COLOR_BEGIN = 0xE0316F; 
	private static final int SKIN_COLOR_MID = 0xE52C5C; 
	private static final int SKIN_COLOR_END = 0xED294C; 


	private static final int HAIR_COLOR_BEGIN = 0x3B0015;
	private static final int HAIR_COLOR_END = 0x3A0015; 
	
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityRuby(World worldIn) {
		super(worldIn);
		this.setSize(0.7F, 1.8F);
		this.isImmuneToFire = true;
		this.isSoldier = true;
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_KNEE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_KNEE);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper)input).getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
        
        // Other entity AIs.
		this.tasks.addTask(2, new EntityAIPickUpItems(this, 1.0D));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIRubyFuse(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.droppedGemItem = ModItems.RUBY_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_RUBY_GEM;
        
        // Register entity data.
        this.dataManager.register(ANGER, 0);
	}

    public float[] getGemColor() {
    	return new float[] { 238F / 255F, 35F / 255F, 49F / 255F };
    }
    public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.LEFT_HAND.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_HAND.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.BACK.id);
    		break;
    	case 3:
    		this.setGemPlacement(GemPlacements.LEFT_EYE.id);
    		break;
    	case 4:
    		this.setGemPlacement(GemPlacements.RIGHT_EYE.id);
    		break;
    	case 5:
    		this.setGemPlacement(GemPlacements.FOREHEAD.id);
    		break;
    	case 6:
    		this.setGemPlacement(GemPlacements.BELLY.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to loading.                           *
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("anger", this.getAnger());
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAnger(compound.getInteger("anger"));
    }
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setSkinColor(this.generateSkinColor());
		this.setHairStyle(this.generateHairStyle());
		this.setHairColor(this.generateHairColor());
    	this.setSpecial(this.rand.nextInt(6));
        return super.onInitialSpawn(difficulty, livingdata);
    }
	
    /*********************************************************
	 * Methods related to interaction.                       *
	 *********************************************************/
    public void whenDefective() {
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.setSize(0.7F, 0.9F);
        this.stepHeight = 0.5F;
    }
    public void whenFused() {
    	//Everything that used to be here was moved to EntityRuby#fuse()
    	//because it doesn't need to be done 20 times a second
    	this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
    }
    public boolean canPickupItem(Item itemIn) {
        return this.isDefective() && (itemIn instanceof ItemSword || itemIn instanceof ItemTool || itemIn instanceof ItemBow);
    }
	
	/*********************************************************
	 * Methods related to living.                            *
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.getAnger() > 2) {
			if (!this.isInWater()) {
				for (int k = 0; k < 8; ++k) {
	                this.world.spawnParticle(EnumParticleTypes.FLAME, (double) this.posX - 0.5 + Math.random(), (double) this.posY + Math.random(), (double) this.posZ - 0.5 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
	            }
			}
		}
		else if (this.getAnger() > 3) {
			if (this.isInWater()) {
				this.world.setBlockToAir(this.getPosition());
                this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F);
                for (int k = 0; k < 8; ++k) {
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) this.posX - 0.5 + Math.random(), (double) this.posY + Math.random(), (double) this.posZ - 0.5 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                }
			}
			else if (this.onGround) {
				this.world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState());
			}
		}
		else if (this.getAnger() > 4) {
			this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60));
			this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60));
		}
		else if (this.getAnger() > 6) {
			this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 3));
		}
		if (this.world.getBlockState(this.getPosition()).getMaterial() == Material.LAVA) {
			this.heal(1.0F);
		}
		this.angerTicks += 1;
		if (this.angerTicks > 200 && this.getAnger() > 0) {
			this.setAnger(this.getAnger() - 1);
			this.angerTicks = 0;
		}
		if (this.ticksExisted % 10 + this.rand.nextInt(50) == 0) {
			this.setSpecial(this.rand.nextInt(6));
		}
		if (this.isFusion()) {
			if (this.canFuse()) {
				this.whenFused();
			}
			else {
				this.unfuse();
			}
		}
		super.onLivingUpdate();
	}
	public boolean alternateInteract(EntityPlayer player) {
    	this.wantsToFuse = true;
    	super.alternateInteract(player);
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
	public boolean canFuseWith(EntityRuby other) {
		if (this.canFuse() && other.canFuse() && this.getServitude() == other.getServitude() && this.getGemPlacement() != other.getGemPlacement()) {
			if ((this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwnerId().equals(other.getOwnerId())) || this.getServitude() != EntityGem.SERVE_HUMAN) {
				if (this.wantsToFuse && other.wantsToFuse) {
					return true;
				}
				if ((this.getAttackingEntity() != null && this.getAttackingEntity().equals(other.getAttackingEntity())) || (this.getAttackTarget() != null && this.getAttackTarget().equals(other.getAttackTarget()))) {
					return true;
				}
				if ((this.getHealth() / this.getMaxHealth() <= 0.5 || other.getHealth() / other.getMaxHealth() <= 0.5) && this.getHealth() > 0.0f && other.getHealth() > 0.0f) {
					return true;
				}
			}
		}
		return false;
	}
	public EntityRuby fuse(EntityRuby other) {
		EntityRuby ruby = new EntityRuby(this.world);
		if (this.isFusion()) {
			for (NBTTagCompound compound : this.fusionMembers) {
				ruby.fusionMembers.add(compound);
			}
		}
		else {
			NBTTagCompound primeCompound = new NBTTagCompound();
			this.writeToNBT(primeCompound);
			ruby.fusionMembers.add(primeCompound);
		}
		if (other.isFusion()) {
			for (NBTTagCompound compound : other.fusionMembers) {
				ruby.fusionMembers.add(compound);
			}
		}
		else {
			NBTTagCompound otherCompound = new NBTTagCompound();
			other.writeToNBT(otherCompound);
			ruby.fusionMembers.add(otherCompound);
		}
		if (this.getServitude() == EntityGem.SERVE_HUMAN) {
			ruby.setOwnerId(this.getOwnerId());
			ruby.setLeader(this.getOwner());
		}
		ruby.setServitude(this.getServitude());
		ruby.setFusionCount(this.getFusionCount() + other.getFusionCount());
		ruby.generateFusionPlacements();
		ruby.setPosition((this.posX + other.posX) / 2, (this.posY + other.posY) / 2, (this.posZ + other.posZ) / 2);
		ruby.setAnger(this.getAnger() + other.getAnger());
		ruby.setAttackTarget(this.getAttackTarget());
		ruby.setRevengeTarget(this.getAttackingEntity());
		
		ruby.setSkinColor(this.getSkinColor());
		ruby.setHairColor(this.getHairColor());
		ruby.setHairStyle(this.getHairStyle());
		ruby.setHasVisor(this.hasVisor());

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D * ruby.getFusionCount());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D * ruby.getFusionCount());
    	ruby.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0F);
    	ruby.stepHeight = ruby.getFusionCount();
    	ruby.setHealth(ruby.getMaxHealth());
    	
    	ItemStack weapon = this.getHeldItem(EnumHand.MAIN_HAND);
    	if (weapon.getItem() == Items.AIR) {
    		weapon = other.getHeldItem(EnumHand.MAIN_HAND);
    	}
    	ItemStack second = this.getHeldItem(EnumHand.OFF_HAND);
    	if (second.getItem() == Items.AIR) {
    		second = other.getHeldItem(EnumHand.OFF_HAND);
    	}
    	ruby.setFusionWeapon(weapon);
    	ruby.setFusionWeapon(second);
    	
    	ruby.setSize(0.7F * ruby.getFusionCount(), 1.8F * ruby.getFusionCount());
     	return ruby;
	}
	
	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);
	}
	
	public void unfuse() {
		for (int i = 0; i < this.fusionMembers.size(); ++i) {
			EntityRuby ruby = new EntityRuby(this.world);
			ruby.readFromNBT(this.fusionMembers.get(i));
			ruby.setUniqueId(MathHelper.getRandomUUID(this.world.rand));
			ruby.setHealth(ruby.getMaxHealth());
			ruby.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			ruby.setRevengeTarget(null);
			ruby.setAttackTarget(null);
			ruby.wantsToFuse = false;
			if (ruby.isFusion()) {
				ruby.unfuse();
				this.world.removeEntity(ruby);
			}
			else {
				this.world.spawnEntity(ruby);
			}
		}
		this.world.removeEntity(this);
	}
	
	/*********************************************************
	 * Methods related to combat.                            *
	 *********************************************************/
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() instanceof EntityLivingBase && !this.isOwner((EntityLivingBase) source.getTrueSource())) {
			if (source.isMagicDamage()) {
				this.setAnger(this.getAnger() + 4 + (int)(amount / 2));
			}
			else {
				this.setAnger(this.getAnger() + 1 + (int)(amount / 4));
			}
		}
		else if (source.isProjectile()) {
			this.setAnger(this.getAnger() + 2 + (int)(amount / 3));
		}
		/*if (this.getAnger() > 3) {
			if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
            	this.getOwner().addStat(ModAchievements.IM_AN_ETERNAL_FLAME);
            }
		}*/
		if (this.isDefective()) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
		this.setSpecial(this.rand.nextInt(6));
		return super.attackEntityFrom(source, amount);
	}
	public boolean attackEntityAsMob(Entity entityIn) {
		int anger = this.getAnger();
		if (anger > 7) {
			anger = 7;
		}
		if (this.rand.nextInt(8 - anger) == 1) {
			entityIn.setFire(anger * 2 + 4);
		}
		return super.attackEntityAsMob(entityIn);
	}
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityTippedArrow arrow = new EntityTippedArrow(this.world, this);
		double distanceFromTargetX = target.posX - this.posX;
        double distanceFromTargetY = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - arrow.posY;
        double distanceFromTargetZ = target.posZ - this.posZ;
        double distanceFromTargetS = (double) MathHelper.sqrt(distanceFromTargetX * distanceFromTargetX + distanceFromTargetY * distanceFromTargetY);
        arrow.setThrowableHeading(distanceFromTargetX, distanceFromTargetY + distanceFromTargetS * 0.20000000298023224D, distanceFromTargetZ, 1.6F, 2.0F);
        arrow.setDamage((double)(distanceFactor * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getDifficultyId() * 0.11F));
        
        // Enchantments.
        int power = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
        int punch = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
        boolean flame = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0;
        int anger = this.getAnger();
        
        if (anger > 7) {
			anger = 7;
		}
        if (power > 0) {
            arrow.setDamage(arrow.getDamage() + (double) power * 0.5D + 0.5D);
        }
        if (punch > 0) {
            arrow.setKnockbackStrength(punch);
        }
        if (flame) {
            arrow.setFire(100 + (anger * 100 + 40));
        }
        else if (this.rand.nextInt(8 - anger) == 1) {
			arrow.setFire(anger * 100 + 40);
		}
        ItemStack itemstack = this.getHeldItem(EnumHand.OFF_HAND);
        if (itemstack.getItem() == Items.TIPPED_ARROW) {
            arrow.setPotionEffect(itemstack);
        }
        this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(arrow);
    }
	public void jump() {
		if (this.isDefective()) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
		super.jump();
	}
	public void fall(float distance, float damageMultiplier) {
		if (this.isDefective()) {
			this.entityDropItem(this.getHeldItem(EnumHand.MAIN_HAND), 0.0F);
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
		super.fall(distance, damageMultiplier);
	}
	public int getAnger() {
		return this.dataManager.get(ANGER);
	}
	public void setAnger(int anger) {
		this.dataManager.set(ANGER, anger);
	}
	public int getSpecialSkin() {
		return this.getSpecial();
	}
	
	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		ArrayList skinColors = new ArrayList();
		skinColors.add(this.SKIN_COLOR_BEGIN);
		skinColors.add(this.SKIN_COLOR_MID);
		skinColors.add(this.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(this.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList hairColors = new ArrayList();
		hairColors.add(this.HAIR_COLOR_BEGIN);
		hairColors.add(this.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	
	@Override
	public boolean hasInsigniaVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		case CHEST:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean hasUniformVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		case CHEST:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean hasHairVariant(GemPlacements placement) {
		switch(placement) {
		case FOREHEAD:
			return true;
		default:
			return false;
		}
	}
	
	public SoundEvent getAmbientSound() {
		return ModSounds.RUBY_LIVING;
	}
	public SoundEvent getHurtSound() {
		return ModSounds.RUBY_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.RUBY_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.RUBY_DEATH;
	}
}
