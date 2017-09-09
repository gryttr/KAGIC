package mod.akrivus.kagic.entity.gem;

import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIFutureVision;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySapphire extends EntityGem {
	public static final HashMap<IBlockState, Double> SAPPHIRE_YIELDS = new HashMap<IBlockState, Double>();
	public static final HashMap<Integer, ResourceLocation> SAPPHIRE_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private boolean spawnedPadparadscha;
	private int luckTicks = 0;
	public EntitySapphire(World worldIn) {
		super(worldIn);
		this.setSize(0.7F, 1.6F);
		this.seePastDoors();
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper) input).getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(2, new EntityAIFutureVision(this));
        this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(6, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.droppedGemItem = ModItems.SAPPHIRE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_SAPPHIRE_GEM;
	}

	public float[] getGemColor() {
    	return new float[] { 155F / 255F, 202F / 255F, 255F / 255F };
    }
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.LEFT_EYE.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	}
    }
	public SoundEvent getAmbientSound() {
		return ModSounds.SAPPHIRE_LIVING;
	}
	public SoundEvent getHurtSound() {
		return ModSounds.SAPPHIRE_HURT;
	}
	public SoundEvent getObeySound() {
		return ModSounds.SAPPHIRE_OBEY;
	}
	public SoundEvent getDeathSound() {
		return ModSounds.SAPPHIRE_DEATH;
	}
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {
						if (this.isCoreItem(stack)) {
							return super.processInteract(player, hand);
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public void onLivingUpdate() {
        if (this.isDefective()) {
        	if (this.spawnedPadparadscha) {
        		this.setDead();
        	}
        	else if (!this.world.isRemote) {
        		EntityPadparadscha gem = new EntityPadparadscha(this.world);
        		gem.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        		this.world.spawnEntity(gem);
        		gem.onInitialSpawn(gem.world.getDifficultyForLocation(gem.getPosition()), null);
        		this.spawnedPadparadscha = true;
        	}
        }
        else {
        	BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(0, 0, 0);
	        for (BlockPos.MutableBlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.getPosition().add(-2, -1.0D, -2), this.getPosition().add(2, -1.0D, 2))) {
	            if (blockpos1.distanceSqToCenter(this.posX, this.posY, this.posZ) <= 4) {
	                blockpos.setPos(blockpos1.getX(), blockpos1.getY() + 1, blockpos1.getZ());
	                IBlockState iblockstate = this.world.getBlockState(blockpos);
	                if (iblockstate.getMaterial() == Material.AIR) {
	                    IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
	                    if (iblockstate1.getMaterial() == Material.WATER && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && this.world.mayPlace(Blocks.FROSTED_ICE, blockpos1, false, EnumFacing.DOWN, null)) {
	                        this.world.setBlockState(blockpos1, Blocks.FROSTED_ICE.getDefaultState());
	                        this.world.scheduleUpdate(blockpos1.toImmutable(), Blocks.FROSTED_ICE, this.rand.nextInt(60) + 60);
	                    }
	                    else if (iblockstate1.getMaterial() == Material.LAVA && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && this.world.mayPlace(Blocks.COBBLESTONE, blockpos1, false, EnumFacing.DOWN, null)) {
	                        this.world.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState());
	                    }
	                }
	            }
	        }
	        if (this.luckTicks > 80 && !(this.isDead || this.getHealth() <= 0.0F)) {
				this.futureVision();
				this.luckTicks = 0;
			}
			this.luckTicks += 1;
        }
		super.onLivingUpdate();
	}
	private void futureVision() {
        if (!this.world.isRemote) {
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(8.0, (double) this.world.getHeight(), 8.0);
            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            for (EntityLivingBase entity : list) {
            	if (!entity.isDead || entity.getHealth() > 0.0F) {
	            	if (entity instanceof EntityGem) {
	            		EntityGem gem = (EntityGem) entity;
	            		if (this.getServitude() == gem.getServitude()) {
	            			if (this.getServitude() == EntityGem.SERVE_HUMAN) {
	            				if (this.isOwnerId(gem.getOwnerId())) {
	            					entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
	            				}
	            			}
	            			else {
	            				entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
	            			}
	            		}
	            	}
	            	if (this.isOwner(entity)) {
	            		entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
	            	}
            	}
            }
        }
    }
}
