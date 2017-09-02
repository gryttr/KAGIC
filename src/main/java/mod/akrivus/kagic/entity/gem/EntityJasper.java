package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModAchievements;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.Block;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJasper extends EntityGem {
	public static final HashMap<Block, Double> JASPER_YIELDS = new HashMap<Block, Double>();
	public static final HashMap<Integer, ResourceLocation> JASPER_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private static final DataParameter<Boolean> CHARGED = EntityDataManager.<Boolean>createKey(EntityJasper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> MARK_1_COLOR = EntityDataManager.<Integer>createKey(EntityJasper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MARK_1 = EntityDataManager.<Integer>createKey(EntityJasper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MARK_2_COLOR = EntityDataManager.<Integer>createKey(EntityJasper.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MARK_2 = EntityDataManager.<Integer>createKey(EntityJasper.class, DataSerializers.VARINT);
	private int charge_ticks = 0;
	private int hit_count = 0;
	private int regenTicks = 0;

	private static final int NUM_HAIRSTYLES = 1;
	private static final Map<Integer, Integer> MARK1S = new LinkedHashMap<Integer, Integer>();
	static {
		MARK1S.put(0, 6);
		MARK1S.put(1, 1);
		MARK1S.put(2, 1);		
	}

	private static final Map<Integer, Integer> MARK2S = new LinkedHashMap<Integer, Integer>();
	static {
		MARK2S.put(0, 0);
		MARK2S.put(1, 1);
		MARK2S.put(2, 1);		
	}

	private static final Map<Integer, ArrayList<Integer>> SKIN_COLORS = new LinkedHashMap<Integer, ArrayList<Integer>>();
	static {
		ArrayList<Integer> normal = new ArrayList<Integer>();
		normal.add(0xFFD69D);
		normal.add(0xFC9C6F);
		normal.add(0xFA8669);
		normal.add(0xFFA351);
		normal.add(0xF58059);
		normal.add(0xE8745B);
		SKIN_COLORS.put(0, normal);

		ArrayList<Integer> ocean = new ArrayList<Integer>();
		ocean.add(0x70BCC7);
		ocean.add(0x478397);
		SKIN_COLORS.put(1, ocean);

		ArrayList<Integer> biggs = new ArrayList<Integer>();
		biggs.add(0xBC6A68);
		SKIN_COLORS.put(2, biggs);
	}
	
	private static final Map<Integer, ArrayList<Integer>> HAIR_COLORS = new LinkedHashMap<Integer, ArrayList<Integer>>();
	static {
		ArrayList<Integer> normal = new ArrayList<Integer>();
		normal.add(0xFEFFEC);
		normal.add(0xFFFCE8);
		normal.add(0xFFF0D4);
		normal.add(0xFDE7D9);
		normal.add(0xFFD69D);
		HAIR_COLORS.put(0, normal);

		ArrayList<Integer> ocean = new ArrayList<Integer>();
		ocean.add(0xFaF9Fc);
		HAIR_COLORS.put(1, ocean);

		ArrayList<Integer> biggs = new ArrayList<Integer>();
		biggs.add(0xFDF8EA);
		HAIR_COLORS.put(2, biggs);
	}
	
	private static final Map<Integer, ArrayList<Integer>> MARK_1_COLORS = new LinkedHashMap<Integer, ArrayList<Integer>>();
	static {
		ArrayList<Integer> normal = new ArrayList<Integer>();
		normal.add(0xF58059);
		normal.add(0xED4A3C);
		normal.add(0xF62F46);
		normal.add(0xF1162C);
		normal.add(0xD01729);
		normal.add(0xAC0522);
		MARK_1_COLORS.put(0, normal);

		ArrayList<Integer> ocean = new ArrayList<Integer>();
		ocean.add(0x478397);
		ocean.add(0x173F55);
		MARK_1_COLORS.put(1, ocean);

		ArrayList<Integer> biggs = new ArrayList<Integer>();
		biggs.add(0xD7AB85);
		biggs.add(0xC68976);
		MARK_1_COLORS.put(2, biggs);
	}
	
	private static final Map<Integer, ArrayList<Integer>> MARK_2_COLORS = new LinkedHashMap<Integer, ArrayList<Integer>>();
	static {
		ArrayList<Integer> normal = new ArrayList<Integer>();
		normal.add(0);
		MARK_2_COLORS.put(0, normal);

		ArrayList<Integer> ocean = new ArrayList<Integer>();
		ocean.add(0xF47B95);
		ocean.add(0xFFC3D3);
		MARK_2_COLORS.put(1, ocean);

		ArrayList<Integer> biggs = new ArrayList<Integer>();
		biggs.add(0xA2504E);
		MARK_2_COLORS.put(2, biggs);
	}
	
	public EntityJasper(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 2.3F);
		this.isSoldier = true;

		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.NOSE);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.droppedGemItem = ModItems.JASPER_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_JASPER_GEM;
        
        // Register entity data.
        this.dataManager.register(CHARGED, false);
        this.dataManager.register(MARK_1_COLOR, 0);
        this.dataManager.register(MARK_1, 0);
        this.dataManager.register(MARK_2_COLOR, 0);
        this.dataManager.register(MARK_2, 0);
	}

	public float[] getGemColor() {
		switch (this.getSpecial()) {
		case 1:
			return new float[] { 88F / 255F, 211F / 255F, 207F / 255F };
		case 2:
			return new float[] { 212F / 255F, 135F / 255F, 104F / 255F };
		default:
			return new float[] { 255F / 255F, 63F / 255F, 1F / 255F };
		}
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.TINY.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.NOSE.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("charged", this.dataManager.get(CHARGED).booleanValue());
        compound.setInteger("charge_ticks", this.charge_ticks);
        compound.setInteger("hit_count", this.hit_count);
        compound.setInteger("mark1color", this.getMark1Color());
        compound.setInteger("mark1", this.getMark1());
        compound.setInteger("mark2color", this.getMark2Color());
        compound.setInteger("mark2", this.getMark2());
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(CHARGED, compound.getBoolean("charged"));
        this.charge_ticks = compound.getInteger("charge_ticks");
        this.hit_count = compound.getInteger("hit_count");
        
        if (compound.hasKey("mark1color")) {
        	this.setMark1Color(compound.getInteger("mark1color"));
        } else {
        	this.setMark1Color(this.generateMark1Color());
        }
        if (compound.hasKey("mark1")) {
        	this.setMark1(compound.getInteger("mark1"));
        } else {
        	this.setMark1(this.generateMark1());
        }

        if (compound.hasKey("mark2color")) {
        	this.setMark2Color(compound.getInteger("mark2color"));
        } else {
        	this.setMark2Color(this.generateMark2Color());
        }
        if (compound.hasKey("mark2")) {
        	this.setMark2(compound.getInteger("mark2"));
        } else {
        	this.setMark2(this.generateMark2());
        }

    }

    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	//0 - normal jasper
    	//1 - ocean jasper
    	//2 - biggs jasper
        int special = this.rand.nextInt(3);
    	switch (this.world.getBiome(this.getPosition()).getTempCategory()) {
		case COLD:
			special = this.rand.nextInt(6) == 0 ? 1 : special;
			break;
		case MEDIUM:
			special = this.rand.nextInt(6) == 0 ? 2 : special;
			break;
		case OCEAN:
			special = this.rand.nextInt(6) == 0 ? 1 : special;
			break;
		case WARM:
			special = this.rand.nextInt(6) == 0 ? 0 : special;
			break;
    	}
    	this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.jasper_%1$d.name", special)).getUnformattedComponentText());
        this.setSpecial(special);

		this.setSkinColor(this.generateSkinColor());
		this.setHairStyle(this.generateHairStyle());
		this.setHairColor(this.generateHairColor());
		this.setMark1(this.generateMark1());
		this.setMark1Color(this.generateMark1Color());
		if (this.hasSecondMarking()) {
			this.setMark2(this.generateMark2());
			this.setMark2Color(this.generateMark2Color());
		}
		
		return super.onInitialSpawn(difficulty, livingdata);
    }
	
	/*********************************************************
	 * Methods related to entity interaction.                *
	 *********************************************************/
	public boolean alternateInteract(EntityPlayer player) {
		super.alternateInteract(player);
		KAGIC.instance.chatInfoMessage("mark1Color is " + this.getMark1Color());
		KAGIC.instance.chatInfoMessage("mark1 is " + this.getMark1());
		KAGIC.instance.chatInfoMessage("mark2Color is " + this.getMark2Color());
		KAGIC.instance.chatInfoMessage("mark2 is " + this.getMark2());
		return false;
	}

	public boolean isCharged() {
		return this.dataManager.get(CHARGED);
	}
	public void setCharged(boolean charged) {
		this.dataManager.set(CHARGED, charged);
	}
	public String getSpecialSkin() {
		switch (this.getSpecial()) {
		case 0:
			return "";
		case 1:
			return "ocean_";
		case 2:
			return "biggs_";
		}
		return null;
	}
	
	@Override
	public int getSpecial() {
		return this.dataManager.get(SPECIAL);
	}
	
	public void whenDefective() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.setSize(0.63F, 2.3F);
	}
	
	/*********************************************************
	 * Methods related to entity combat.                     *
	 *********************************************************/
	public boolean attackEntityAsMob(Entity entityIn) {
        if (!this.world.isRemote) {
        	this.charge_ticks += 20;
			this.hit_count += 1;
			if (this.isCharged()) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(8.0, (double) this.world.getHeight(), 8.0);
	            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
	            for (EntityLivingBase entity : list) {
	            	if (!this.isOwner(entity)) {
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
		            	if (shouldAttack) {
			            	if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)))) {
					            entity.motionY += 0.9D;
					            this.applyEnchantments(this, entity);
					        }
		            	}
	            	}
	            }
	            /*if (this.getServitude() == EntityGem.SERVE_HUMAN) {
	            	this.getOwner().addStat(ModAchievements.FIGHTING_IS_MY_LIFE);
	            }*/
			}
			else {
				if (entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(10 + this.rand.nextInt(15)))) {
		            entityIn.motionY += 0.4D;
		            this.applyEnchantments(this, entityIn);
		        }
			}
        }
		return super.attackEntityAsMob(entityIn);
	}
	
	/*********************************************************
	 * Methods related to entity living.                     *
	 *********************************************************/
	public void onLivingUpdate() {
		if (!this.isInWater() || !this.isAirBorne) {
			regenTicks += 1;
		}
		if (this.hit_count > 12) {
			this.charge_ticks -= 1;
			this.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 80));
			this.setCharged(true);

			if (this.charge_ticks < 1) {
				this.hit_count = 0;
				this.setCharged(false);
			}
		}
		if (regenTicks > 200 && !(this.isDead || this.getHealth() <= 0.0F) && this.getHealth() < this.getMaxHealth()) {
			this.heal(1.0F);
			regenTicks = 0;
		}
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to entity death.                      *
	 *********************************************************/
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			if (cause.getTrueSource() instanceof EntityAmethyst) {
				this.dropItem(ModItems.RECORD_DUEL_OF_THE_QUARTZ, 1);
			}
		}
		switch (this.getSpecial()) {
		case 0:
			this.droppedGemItem = ModItems.JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_JASPER_GEM;
			break;
    	case 1:
    		this.droppedGemItem = ModItems.OCEAN_JASPER_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_OCEAN_JASPER_GEM;
    		break;
    	case 2:
    		this.droppedGemItem = ModItems.BIGGS_JASPER_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BIGGS_JASPER_GEM;
    		break;
		}
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to entity sounds.                     *
	 *********************************************************/
	public SoundEvent getHurtSound() {
		return ModSounds.JASPER_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.JASPER_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.JASPER_DEATH;
	}
	
	/*********************************************************
	 * Methods related to entity rendering.                  *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		return Colors.arbiLerp(this.SKIN_COLORS.get(this.getSpecial()));
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(this.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		return Colors.arbiLerp(this.HAIR_COLORS.get(this.getSpecial()));
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
	
	public boolean hasSecondMarking() {
		return this.getSpecial() != 0;
	}
	
	protected int generateMark1Color() {
		return Colors.arbiLerp(this.MARK_1_COLORS.get(this.getSpecial()));
	}
	
	protected int generateMark1() {
		return this.rand.nextInt(this.MARK1S.get(this.getSpecial()));
	}
	
	protected int generateMark2Color() {
		return Colors.arbiLerp(this.MARK_2_COLORS.get(this.getSpecial()));
	}	
	
	protected int generateMark2() {
		if (this.hasSecondMarking()) {
			return this.rand.nextInt(this.MARK2S.get(this.getSpecial()));
		} else {
			return 0;
		}
	}
	
	public int getMark1Color() {
		return this.dataManager.get(MARK_1_COLOR);
	}
	
	public void setMark1Color(int mark1Color) {
		this.dataManager.set(MARK_1_COLOR, mark1Color);
	}
	
	public int getMark1() {
		return this.dataManager.get(MARK_1);
	}
	
	public void setMark1(int mark1) {
		this.dataManager.set(MARK_1, mark1);
	}
	
	public int getMark2Color() {
		return this.dataManager.get(MARK_2_COLOR);
	}
	
	public void setMark2Color(int mark2Color) {
		this.dataManager.set(MARK_2_COLOR, mark2Color);
	}
	
	public int getMark2() {
		return this.dataManager.get(MARK_2);
	}
	
	public void setMark2(int mark2) {
		this.dataManager.set(MARK_2, mark2);
	}
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return isCharged() ? 15728880 : super.getBrightnessForRender();
    }
    public float getBrightness() {
        return isCharged() ? 1.0F : super.getBrightness();
    }	
}