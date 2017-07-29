package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.annotation.Nullable;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIScareMobs;
import mod.akrivus.kagic.entity.ai.EntityAISitStill;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityAgate extends EntityGem {
	public static final HashMap<Block, Double> AGATE_YIELDS = new HashMap<Block, Double>();
	public static final ArrayList<ResourceLocation> AGATE_HAIR_STYLES = new ArrayList<ResourceLocation>();
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityAgate.class, DataSerializers.VARINT);
	public EntityAgate(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 2.3F);
		this.isSoldier = true;
		
		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(2, new EntityAISitStill(this, 1.0D));
		this.tasks.addTask(3, new EntityAIScareMobs(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.droppedGemItem = ModItems.AGATE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_AGATE_GEM;
        
        // Register entity data.
        this.dataManager.register(COLOR, Integer.valueOf(new Random().nextInt(15)));
	}
	public boolean isCorrectGemCut() {
    	switch (GemCuts.values()[this.getGemCut()]) {
    	case BISMUTH:
    		return false;
    	case PERIDOT:
    		return false;
    	default:
    		return true;
    	}
    }
    public float[] getGemColor() {
    	if (this.isHolly()) {
    		return new float[] { 147F / 255F, 103F / 255F, 248F / 255F };
    	}
    	return EntitySheep.getDyeRgb(EnumDyeColor.values()[this.getColor()]);
    }
    public void convertGems(int placement) {
    	this.setGemCut(GemCuts.TEARDROP.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.BACK_OF_HEAD.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.FOREHEAD.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.RIGHT_SHOULDER.id);
    		break;
    	case 3:
    		this.setGemPlacement(GemPlacements.LEFT_SHOULDER.id);
    		break;
    	}
    }
    
	/*********************************************************
	 * Methods related to loading.                           *
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("color", this.dataManager.get(COLOR).intValue());
	}
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(COLOR, compound.getInteger("color"));
        if (compound.hasKey("holly")) {
        	this.setSpecial(compound.getBoolean("holly") ? 1 : 0);
        	this.setGemPlacement(GemPlacements.BACK_OF_HEAD.id);
        	this.setGemCut(GemCuts.TEARDROP.id);
        }
    }
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    	boolean holly = this.rand.nextInt(9) == 0;
        this.setHairStyle(this.rand.nextInt(EntityAgate.AGATE_HAIR_STYLES.size()));
    	this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.agate_%1$d.name", this.getColor())).getUnformattedComponentText());
        if (holly) {
        	this.setCustomNameTag(new TextComponentTranslation("entity.kagic.agate_16.name").getUnformattedComponentText());
        	this.setGemPlacement(GemPlacements.BACK_OF_HEAD.id);
        	this.setGemCut(GemCuts.TEARDROP.id);
        	this.setSpecial(1);
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    /*********************************************************
	 * Methods related to interaction.                       *
	 *********************************************************/
	public int getColor() {
		if (this.isHolly()) {
			return 3;
		}
		return this.dataManager.get(COLOR);
	}
	public boolean isHolly() {
		return this.getSpecial() == 1 || this.getName().toLowerCase().contains("holly");
	}
	
	/*********************************************************
     * Methods related to death.                             *
     *********************************************************/
    public void onDeath(DamageSource cause) {
    	switch (this.getColor()) {
    	case 0:
    		this.droppedGemItem = ModItems.WHITE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_WHITE_AGATE_GEM;
    		break;
    	case 1:
    		this.droppedGemItem = ModItems.ORANGE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_ORANGE_AGATE_GEM;
    		break;
    	case 2:
    		this.droppedGemItem = ModItems.MAGENTA_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_MAGENTA_AGATE_GEM;
    		break;
    	case 3:
    		this.droppedGemItem = ModItems.LIGHT_BLUE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIGHT_BLUE_AGATE_GEM;
    		break;
    	case 4:
    		this.droppedGemItem = ModItems.YELLOW_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_YELLOW_AGATE_GEM;
    		break;
    	case 5:
    		this.droppedGemItem = ModItems.LIME_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIME_AGATE_GEM;
    		break;
    	case 6:
    		this.droppedGemItem = ModItems.PINK_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_PINK_AGATE_GEM;
    		break;
    	case 7:
    		this.droppedGemItem = ModItems.GRAY_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_GRAY_AGATE_GEM;
    		break;
    	case 8:
    		this.droppedGemItem = ModItems.LIGHT_GRAY_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIGHT_GRAY_AGATE_GEM;
    		break;
    	case 9:
    		this.droppedGemItem = ModItems.CYAN_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_CYAN_AGATE_GEM;
    		break;
    	case 10:
    		this.droppedGemItem = ModItems.PURPLE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_PURPLE_AGATE_GEM;
    		break;
    	case 11:
    		this.droppedGemItem = ModItems.BLUE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BLUE_AGATE_GEM;
    		break;
    	case 12:
    		this.droppedGemItem = ModItems.BROWN_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BROWN_AGATE_GEM;
    		break;
    	case 13:
    		this.droppedGemItem = ModItems.GREEN_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_GREEN_AGATE_GEM;
    		break;
    	case 14:
    		this.droppedGemItem = ModItems.RED_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_RED_AGATE_GEM;
    		break;
    	case 15:
    		this.droppedGemItem = ModItems.BLACK_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BLACK_AGATE_GEM;
    		break;
    	}
    	if (this.isHolly()) {
    		this.droppedGemItem = ModItems.HOLLY_BLUE_AGATE_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_HOLLY_BLUE_AGATE_GEM;
    	}
    	super.onDeath(cause);
    }
	
	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/
	public SoundEvent getHurtSound() {
		return ModSounds.AGATE_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.AGATE_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.AGATE_DEATH;
	}
}
