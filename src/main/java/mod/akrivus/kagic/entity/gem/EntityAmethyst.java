package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIProtectionFuse;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.skills.SkillBase;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAmethyst extends EntityQuartzSoldier implements IAnimals {
	public static final HashMap<IBlockState, Double> AMETHYST_YIELDS = new HashMap<IBlockState, Double>();
	public static final double AMETHYST_DEFECTIVITY_MULTIPLIER = 1.5;
	public static final double AMETHYST_DEPTH_THRESHHOLD = 72;
	public static final HashMap<Integer, ResourceLocation> AMETHYST_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private static final DataParameter<Boolean> CHARGED = EntityDataManager.<Boolean>createKey(EntityAmethyst.class, DataSerializers.BOOLEAN);
	
	//13811681
	public static final int SKIN_COLOR_BEGIN = 0xD2BFE1; 
	//12097994
	public static final int SKIN_COLOR_MID = 0xB899CA; 
	//11875965
	public static final int SKIN_COLOR_END = 0xB5367D; 


	public static final int HAIR_COLOR_BEGIN = 0xBD79C9;
	
	public static final int HAIR_COLOR_MID_1 = 0x9877B1;

	public static final int HAIR_COLOR_MID_2 = 0xF9E4FF;

	public static final int HAIR_COLOR_MID_3 = 0xFFFDFF; 
	//Amethyst
	public static final int HAIR_COLOR_MID_4 = 0xDCD3EF; 
	
	public static final int HAIR_COLOR_MID_5 = 0xAD859F;
	
	public static final int HAIR_COLOR_END = 0xC47DA3; 
	
	private static final int NUM_HAIRSTYLES = 5;
	
	private int charge_ticks = 0;
	private int hit_count = 0;
	
	public EntityAmethyst(World worldIn) {
		super(worldIn);
		this.nativeColor = 8;
		
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

		// Apply entity AI.
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIProtectionFuse(this, EntityPearl.class, EntityOpal.class, 16D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        
        // Apply targeting.
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        
        this.droppedGemItem = ModItems.AMETHYST_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_AMETHYST_GEM;
        
        // Register entity data.
        this.dataManager.register(CHARGED, false);
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	this.setSpecial(0);
		return super.onInitialSpawn(difficulty, livingdata);
    }
	
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("charged", this.dataManager.get(CHARGED).booleanValue());
        compound.setInteger("charge_ticks", this.charge_ticks);
        compound.setInteger("hit_count", this.hit_count);
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(CHARGED, compound.getBoolean("charged"));
        this.charge_ticks = compound.getInteger("charge_ticks");
        this.hit_count = compound.getInteger("hit_count");
    }

    @Override
    protected int generateGemColor() {
    	return 0xDC64FD;
    }
    
    @Override
    public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_SHOULDER.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.BELLY.id);
    		break;
    	case 3:
    		this.setGemPlacement(GemPlacements.LEFT_SHOULDER.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity interaction.                *
	*********************************************************/
    public boolean isCharged() {
		return this.dataManager.get(CHARGED);
    }
    
	public void setCharged(boolean charged) {
		this.dataManager.set(CHARGED, charged);
	}
	
	@Override
	public void whenDefective() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.setSize(0.82F, 1.48F);
	}
	
    /*********************************************************
     * Methods related to entity combat.                     *
     *********************************************************/
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if (source.getTrueSource() instanceof EntityLivingBase && !this.isOwner((EntityLivingBase) source.getTrueSource())) {
			this.charge_ticks += 20;
			this.hit_count += 1;
		}
		return super.attackEntityFrom(source, amount);
	}
	public boolean attackEntityAsMob(Entity entityIn) {
		if (!this.world.isRemote) {
			boolean smite = this.rand.nextInt(3) == 1;
			if (smite) {
				this.isImmuneToFire = true;
			}
			else {
				this.isImmuneToFire = false;
			}
			if (this.isCharged()) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(12.0, (double) this.world.getHeight(), 12.0);
	            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
	            for (EntityLivingBase entity : list) {
	            	if (!this.isOwner(entity)) {
		            	boolean shouldAttack = true;
		            	if (entity instanceof EntityGem) {
		            		EntityGem gem = (EntityGem) entity;
		            		if (this.getServitude() == gem.getServitude()) {
		            			if (this.getServitude() == EntityGem.SERVE_HUMAN) {
		            				shouldAttack = !this.isOwnerId(gem.getOwnerId());
		            			}
		            			else {
		            				shouldAttack = false;
		            			}
		            		}
		            	}
		            	if (shouldAttack) {
			            	if (smite) {
			            		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.world, entity.posX, entity.posY, entity.posZ, true);
			            		this.world.addWeatherEffect(lightningBolt);
			            		entity.setFire(12);
			            	}
		            		entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80));
		    				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80));
		    				entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80));
		            	}
	            	}
	            }
	            /*if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
	            	this.getOwner().addStat(ModAchievements.STEP_OFF);
	            }*/
	        }
			else {
				if (smite) {
            		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.world, entityIn.posX, entityIn.posY, entityIn.posZ, true);
            		this.world.addWeatherEffect(lightningBolt);
            		entityIn.setFire(12);
            	}
        		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80));
        		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80));
        		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80));
			}
		}
		
		return super.attackEntityAsMob(entityIn);
	}
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		if (!this.world.isRemote) {
			this.attackEntityAsMob(target);
		}
		super.attackEntityWithRangedAttack(target, distanceFactor);
	}
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to entity living.                     *
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.hit_count > 7) {
			this.charge_ticks -= 1;
			this.setCharged(true);

			if (this.charge_ticks < 7) {
				this.hit_count = 0;
				this.setCharged(false);
			}
		}
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.AMETHYST_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.AMETHYST_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.AMETHYST_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public int generateSkinColor() {
		return Colors.triLerp(EntityAmethyst.SKIN_COLOR_BEGIN, EntityAmethyst.SKIN_COLOR_MID, EntityAmethyst.SKIN_COLOR_END);
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityAmethyst.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityAmethyst.HAIR_COLOR_BEGIN);
		hairColors.add(EntityAmethyst.HAIR_COLOR_MID_1);
		hairColors.add(EntityAmethyst.HAIR_COLOR_MID_2);
		hairColors.add(EntityAmethyst.HAIR_COLOR_MID_3);
		hairColors.add(EntityAmethyst.HAIR_COLOR_MID_4);
		hairColors.add(EntityAmethyst.HAIR_COLOR_MID_5);
		hairColors.add(EntityAmethyst.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}

	@Override
	public boolean hasUniformVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean hasCape() {
		return true;
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
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return isCharged() ? 15728880 : super.getBrightnessForRender();
	}
	
    public float getBrightness() {
        return isCharged() ? 1.0F : super.getBrightness();
    }
}
