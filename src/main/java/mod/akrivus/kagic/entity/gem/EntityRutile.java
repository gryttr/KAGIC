package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityRutile extends EntityGem {
	public static final HashMap<IBlockState, Double> RUTILE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double RUTILE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double RUTILE_DEPTH_THRESHOLD = 0;
	private static final int SKIN_COLOR_BEGIN = 0x5F243F; 
	private static final int SKIN_COLOR_MID = 0xC4695C; 
	private static final int SKIN_COLOR_END = 0xC09E61; 

	private static final int HAIR_COLOR_BEGIN = 0x0E0005;
	private static final int HAIR_COLOR_MID = 0x832C13;
	private static final int HAIR_COLOR_END = 0x5C360A; 
	
	private static final int NUM_HAIRSTYLES = 1;
	public EntityRutile(World worldIn) {
		super(worldIn);
		this.nativeColor = 14;
		this.setSize(0.6F, 1.9F);
		this.seePastDoors();
		this.visorChanceReciprocal = 20;
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.TINY, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.TINY, GemPlacements.BELLY);

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
        this.droppedGemItem = ModItems.RUTILE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_RUTILE_GEM;
	}

	public float[] getGemColor() {
    	return new float[] { 183F / 255F, 81F / 255F, 61F / 255F };
    }
	protected SoundEvent getAmbientSound() {
		return ModSounds.RUTILE_LIVING;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.RUTILE_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.RUTILE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.RUTILE_DEATH;
	}
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityRutile.SKIN_COLOR_BEGIN);
		skinColors.add(EntityRutile.SKIN_COLOR_MID);
		skinColors.add(EntityRutile.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityRutile.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityRutile.HAIR_COLOR_BEGIN);
		hairColors.add(EntityRutile.HAIR_COLOR_MID);
		hairColors.add(EntityRutile.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		return super.processInteract(player, hand);
    }
	public void onLivingUpdate() {
        if (this.isTamed() && !this.isSitting()) {	
	        if (this.world.isAirBlock(this.getPosition()) && this.world.isBlockNormalCube(this.getPosition().down(), false) && this.world.getLightFor(EnumSkyBlock.SKY, this.getPosition()) < 8) {
	            this.world.setBlockState(this.getPosition(), ModBlocks.RUTILE_TRAIL.getDefaultState());
	        }
		}
		super.onLivingUpdate();
	}
}
