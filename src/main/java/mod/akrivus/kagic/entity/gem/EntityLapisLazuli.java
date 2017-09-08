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
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityLapisLazuli extends EntityGem {
	public static final HashMap<Block, Double> LAPIS_LAZULI_YIELDS = new HashMap<Block, Double>();
	public static final HashMap<Integer, ResourceLocation> LAPIS_LAZULI_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	public int ticksFlying = 0;

	private static final int SKIN_COLOR_BEGIN = 0x4FEEFB;
	private static final int SKIN_COLOR_END = 0x4FEEFB;
	private static final int NUM_HAIRSTYLES = 1;
	private static final int HAIR_COLOR_BEGIN = 0x1B6AD6;
	private static final int HAIR_COLOR_END = 0x1B6AD6;
	
	public EntityLapisLazuli(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.9F);
		this.visorChanceReciprocal = 20;
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.TEARDROP, GemPlacements.BELLY);
		
		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper)input).getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(6, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
        // Apply target AI.
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.droppedGemItem = ModItems.LAPIS_LAZULI_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_LAPIS_LAZULI_GEM;
	}

	public float[] getGemColor() {
    	return new float[] { 30F / 255F, 143F / 255F, 244F / 255F };
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.TEARDROP.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.FOREHEAD.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.BACK.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to loading.                           *
	 *********************************************************/
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setSkinColor(this.generateSkinColor());
		this.setHairStyle(this.generateHairStyle());
		this.setHairColor(this.generateHairColor());
        return super.onInitialSpawn(difficulty, livingdata);
    }

	/*********************************************************
	 * Methods related to interaction.                       *
	 *********************************************************/
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {
						if (this.isCoreItem(stack)) {
							return super.processInteract(player, hand);
						}
						else if (stack.getItem() instanceof ItemHoe) {
		        			boolean toolChanged = true;
		        			if (this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()) {
								if (!this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqualIgnoreDurability(stack)) {
									this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), 0.0F);
								}
								else {
									toolChanged = false;
								}
							}
		        			if (toolChanged) {
								ItemStack heldItem = stack.copy();
								this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
								this.playObeySound();
								if (!player.capabilities.isCreativeMode) {
									stack.shrink(1);
								}
		        			}
							return true;
		        		}
						else if (!this.isDefective()){
							player.rotationYaw = this.rotationYaw;
					        player.rotationPitch = this.rotationPitch;
					        player.startRiding(this);
					        //player.addStat(ModAchievements.GIVE_ME_A_LIFT);
					        this.playObeySound();
							return true;
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        passenger.setPosition(this.posX, this.posY - 1.25F, this.posZ);
    }
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }
	public boolean canBeSteered() {
		if (this.world.isRemote) {
			return true;
		}
		else {
	        Entity entity = this.getControllingPassenger();
	        if (entity instanceof EntityLivingBase) {
	        	EntityLivingBase rider = (EntityLivingBase) entity;
	        	return this.isOwner(rider);
	        }
	        return false;
        }
    }
	public void travel(float strafe, float up, float forward) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
        if (this.isBeingRidden() && this.canBeSteered()) {
        	this.rotationYaw = entity.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = Math.max(-90f, -90f - entity.rotationPitch * 2f);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 3.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
            forward = ((EntityLivingBase) entity).moveForward;
            strafe = ((EntityLivingBase) entity).moveStrafing;
            if (this.canPassengerSteer()) {
                if (this.isInWater()) {
                	this.moveRelative(strafe, up, 0.91F, 0.02F);
                	this.motionY = forward / 10;
                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.800000011920929D;
                    this.motionY *= 0.800000011920929D;
                    this.motionZ *= 0.800000011920929D;
                }
                else if (this.isInLava()) {
                	this.moveRelative(strafe, up, 0.91F, 0.02F);
                	this.motionY = forward / 10;
                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.5D;
                    this.motionY *= 0.5D;
                    this.motionZ *= 0.5D;
                }
                else {
	                float f = 0.91F * (this.isPrimary() ? 1.06F : 1.0F);
	                if (!this.onGround) {
		                float f1 = 0.16277136F / (f * f * f);
			            this.moveRelative(strafe, up, 0.91F, 0.2F * f1);
	                }
	                this.motionY = forward / 10;
		            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		            this.motionX *= (double) f;
		            this.motionY *= (double) f;
		            this.motionZ *= (double) f;
                }
	        }
	        else {
	            this.motionX = 0.0D;
	            this.motionY = 0.0D;
	            this.motionZ = 0.0D;
	        }
	        this.prevLimbSwingAmount = 0f;/*this.limbSwingAmount;
	        double d1 = this.posX - this.prevPosX;
	        double d0 = this.posZ - this.prevPosZ;
	        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
	        if (f2 > 1.0F) {
	            f2 = 1.0F;
	        }
	        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
	        this.limbSwing += this.limbSwingAmount;*/
            this.limbSwingAmount = 0f;
        }
        else {
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = 0.02F;
            super.travel(strafe, up, forward);
        }
	}
	public boolean isFarmer() {
		return this.isTamed() && this.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemHoe;
	}
	private boolean hasWater(World worldIn, BlockPos pos) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER) {
                return worldIn.isAirBlock(pos.up());
            }
        }
        return false;
    }
	
	/*********************************************************
	 * Methods related to living.                            *
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.isBeingRidden() && !this.onGround) {
			++this.ticksFlying;
		}
		else {
			this.ticksFlying = 0;
		}
		if (this.isFarmer() && this.ticksExisted % 20 == 0) {
	        for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(this.getPosition().add(-2, -2, -2), this.getPosition().add(2, -1, 2))) {
                IBlockState iblockstate = this.world.getBlockState(pos);
                if (iblockstate.getBlock() == Blocks.FARMLAND && iblockstate.getValue(BlockFarmland.MOISTURE) < 7) {
                    this.world.setBlockState(pos, iblockstate.withProperty(BlockFarmland.MOISTURE, 7), 2);
                }
                else if ((iblockstate.getBlock() == Blocks.DIRT || iblockstate.getBlock() == Blocks.GRASS) && this.hasWater(this.world, pos)) {
                	this.world.setBlockState(pos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7), 2);
                	this.world.playSound(null, this.getPosition(), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
		}
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to combat.                            *
	 *********************************************************/
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isBeingRidden() && source.getTrueSource() != null) {
			if (this.getPassengers().get(0).equals(source.getTrueSource())) {
				return false;
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	public void fall(float distance, float damageMultiplier) {
		if (!this.isBeingRidden()) {
			super.fall(distance, damageMultiplier);
		}
	}
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		if (!this.isBeingRidden()) {
			super.updateFallState(y, onGroundIn, state, pos);
		}
	}
	public boolean isOnLadder() {
        return false;
    }
	
	/*********************************************************
	 * Methods related to death.                             *
	 *********************************************************/
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			if (cause.getTrueSource() instanceof EntitySkeleton) {
				this.dropItem(ModItems.RECORD_LAPIS_FLIGHT, 1);
			}
		}
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/
	public SoundEvent getHurtSound() {
		return ModSounds.LAPIS_LAZULI_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.LAPIS_LAZULI_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.LAPIS_LAZULI_DEATH;
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		ArrayList skinColors = new ArrayList();
		skinColors.add(this.SKIN_COLOR_BEGIN);
		skinColors.add(this.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	
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
}
