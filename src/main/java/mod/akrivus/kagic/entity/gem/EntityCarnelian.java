package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCarnelian extends EntityQuartzSoldier {
	public static final HashMap<IBlockState, Double> CARNELIAN_YIELDS = new HashMap<IBlockState, Double>();
	public static final HashMap<Integer, ResourceLocation> CARNELIAN_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private static final DataParameter<Boolean> CHARGED = EntityDataManager.<Boolean>createKey(EntityCarnelian.class, DataSerializers.BOOLEAN);
	private int charge_ticks = 0;
	private int hit_count = 0;
	
	public static final int SKIN_COLOR_BEGIN = 0xE1764D;
	
	public static final int SKIN_COLOR_END = 0xC5307D;
	
	private static final int NUM_HAIRSTYLES = 5;
	
	public static final int HAIR_COLOR_BEGIN = 0xF24807;
	
	public static final int HAIR_COLOR_END = 0x4D0043;
	
	public EntityCarnelian(World worldIn) {
		super(worldIn);
		this.nativeColor = 14;
		this.isImmuneToFire = true;

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

		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_KNEE);

		// Apply entity AI.
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        
        // Apply targetting.
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        
        this.droppedGemItem = ModItems.CARNELIAN_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_CARNELIAN_GEM;
        
        // Register entity data.
        this.dataManager.register(CHARGED, false);
	}

	public float[] getGemColor() {
    	return new float[] { 244F / 255F, 55F / 255F, 74F / 255F };
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.LEFT_SHOULDER.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_SHOULDER.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	case 3:
    		this.setGemPlacement(GemPlacements.BELLY.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.setSize(0.72F, 1.38F);
	}
    
    /*********************************************************
     * Methods related to entity combat.                     *
     *********************************************************/
    public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.isExplosion()) {
			return false;
		}
		else {
			if (source.getTrueSource() instanceof EntityLivingBase && !this.isOwner((EntityLivingBase) source.getTrueSource())) {
				this.charge_ticks += 20;
				this.hit_count += 1;
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	public boolean attackEntityAsMob(Entity entityIn) {
		if (!this.world.isRemote) {
			boolean smite = this.rand.nextInt(7) == 1;
			if (this.isCharged()) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).expand(12.0, (double) this.world.getHeight(), 12.0);
	            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
	            for (EntityLivingBase entity : list) {
	            	if (!this.isOwner(entity)) {
		            	if (smite) {
		            		boolean shouldAttack = true;
			            	if (entity instanceof EntityGem) {
			            		EntityGem gem = (EntityGem) entity;
			            		if (this.getServitude() == gem.getServitude()) {
			            			if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
			            				shouldAttack = !this.isOwnerId(gem.getOwnerId());
			            			}
			            			else {
			            				shouldAttack = false;
			            			}
			            		}
			            	}
			            	if (shouldAttack && this.world.getGameRules().getBoolean("mobGriefing")) {
			            		Explosion explosion = new Explosion(this.world, this, entity.posX, entity.posY, entity.posZ, 1, true, true);
			            		explosion.doExplosionA();
			            		explosion.doExplosionB(true);
			            	}
			            	else {
			            		entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 80));
			            	}
		            	}
	            	}
	            }
	            /*if (this.getServitude() == EntityGem.SERVE_HUMAN) {
	            	this.getOwner().addStat(ModAchievements.IM_NOT_THE_SHORTEST);
	            }*/
			}
			else {
				if (smite && this.world.getGameRules().getBoolean("mobGriefing")) {
            		Explosion explosion = new Explosion(this.world, this, entityIn.posX, entityIn.posY, entityIn.posZ, 1, true, true);
            		explosion.doExplosionA();
            		explosion.doExplosionB(true);
				}
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
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityCarnelian.SKIN_COLOR_BEGIN);
		skinColors.add(EntityCarnelian.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityCarnelian.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityCarnelian.HAIR_COLOR_BEGIN);
		hairColors.add(EntityCarnelian.HAIR_COLOR_END);
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
