package mod.akrivus.kagic.entity.gem;

import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIAttackAquamarine;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIParalyzeEnemies;
import mod.akrivus.kagic.entity.ai.EntityAIScan;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.skills.SkillBase;
import mod.akrivus.kagic.util.flying.EntityFlyHelper;
import mod.akrivus.kagic.util.flying.PathNavigateFlying;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAquamarine extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> AQUAMARINE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double AQUAMARINE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double AQUAMARINE_DEPTH_THRESHOLD = 0;
	public static final HashMap<Integer, ResourceLocation> AQUAMARINE_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	public boolean wantsToScan;
	private int lastScanTime;
	public EntityAquamarine(World worldIn) {
		super(worldIn);
		this.nativeColor = 11;
		this.moveHelper = new EntityFlyHelper(this);
		this.setSize(0.4F, 0.8F);
		this.seePastDoors();
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(2, new EntityAIScan(this));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(3, new EntityAIAttackAquamarine(this, 1.0D));
        this.tasks.addTask(4, new EntityAIParalyzeEnemies(this));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(6, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.droppedGemItem = ModItems.AQUAMARINE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_AQUAMARINE_GEM;
	}
	protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateFlying(this, worldIn);
    }

	protected int generateGemColor() {
    	return 0x4FFCE7;
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.TINY.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.LEFT_CHEEK.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_CHEEK.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.FOREHEAD.id);
    		break;
    	}
    }
	
	public void setDefective(boolean defective) {
		super.setDefective(defective);
		if (defective) {
			this.moveHelper = new EntityMoveHelper(this);
		}
		else {
			this.moveHelper = new EntityFlyHelper(this);
		}
	}
	
	/*********************************************************
     * Methods related to entity interaction.                *
     *********************************************************/
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		return super.processInteract(player, hand);
    }
	public boolean alternateInteract(EntityPlayer player) {
		this.wantsToScan = true;
		return true;
	}
	
	/*********************************************************
     * Methods related to entity life/death.                 *
     *********************************************************/
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.lastScanTime > 20) {
			this.wantsToScan = false;
			this.lastScanTime = 0;
		}
		++this.lastScanTime;
	}
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
    }
	
	/********************************************************
	 * Methods related to entity flight.                    *
	 ********************************************************/
	public void fall(float distance, float damageMultiplier) {
		super.fall(distance, damageMultiplier);
    }
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    	super.updateFallState(y, onGroundIn, state, pos);
    }
    public void travel(float strafe, float up, float forward) {
        if (this.isInWater()) {
            this.moveRelative(strafe, up, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        }
        else if (this.isInLava()) {
            this.moveRelative(strafe, up, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }
        else {
            float friction = 0.91F;
            if (this.onGround) {
            	friction = this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ))).getBlock().slipperiness * 0.91F;
            }
            float factor = 0.16277136F / (friction * friction * friction);
            this.moveRelative(strafe, up, forward, this.onGround ? 0.1F * factor : 0.02F);
            friction = 0.91F;
            if (this.onGround) {
            	friction = this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ))).getBlock().slipperiness * 0.91F;
            }
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double) friction;
            this.motionY *= (double) friction;
            this.motionZ *= (double) friction;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }
        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }
    public boolean isOnLadder() {
        return false;
    }
	
	/*********************************************************
     * Methods related to entity sounds.                     *
     *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.AQUAMARINE_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.AQUAMARINE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.AQUAMARINE_DEATH;
	}
}
