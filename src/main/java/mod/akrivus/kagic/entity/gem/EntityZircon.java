package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityZircon extends EntityGem {
	public static final HashMap<Block, Double> ZIRCON_YIELDS = new HashMap<Block, Double>();
	public static final ArrayList<ResourceLocation> ZIRCON_HAIR_STYLES = new ArrayList<ResourceLocation>();
	public EntityZircon(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.9F);
		this.seePastDoors();
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper) input).getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(6, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.droppedGemItem = ModItems.ZIRCON_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_ZIRCON_GEM;
	}

	public float[] getGemColor() {
		switch (this.getSpecial()) {
		case 0:
			return EntitySheep.getDyeRgb(EnumDyeColor.values()[this.getInsigniaColor()]);
		case 1:	
			return new float[] { 7F / 255F, 68F / 255F, 100F / 255F };
		}
		return new float[] { 7F / 255F, 68F / 255F, 100F / 255F };
    }
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	livingdata = super.onInitialSpawn(difficulty, livingdata);
    	this.setInsigniaColor(this.rand.nextInt(16));
		this.setHairStyle(this.rand.nextInt(EntityZircon.ZIRCON_HAIR_STYLES.size()));
		this.setHasVisor(true);
    	return livingdata;
	}

	public SoundEvent getHurtSound() {
		return ModSounds.ZIRCON_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.ZIRCON_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.ZIRCON_DEATH;
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		return super.processInteract(player, hand);
    }
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	public String getSpecialSkin() {
		return "0";
	}
}
