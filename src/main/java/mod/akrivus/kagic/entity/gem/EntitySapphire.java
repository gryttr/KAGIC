package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.blocks.BlockRoseTears;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIFutureVision;
import mod.akrivus.kagic.entity.ai.EntityAIRetroVision;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntitySapphire extends EntityGem implements INpc {
	public static final HashMap<IBlockState, Double> SAPPHIRE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double SAPPHIRE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double SAPPHIRE_DEPTH_THRESHOLD = 0;
	public static final HashMap<Integer, ResourceLocation> SAPPHIRE_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private int luckTicks = 0;

	private static final List<Integer> SKIN_COLORS = new ArrayList<Integer>(Arrays.asList(
			0,									//White
			4, 4, 4, 4,							//Yellow
			6, 6, 6,							//Pink
			10,	10,								//Purple
			11, 11, 11, 11, 11,	11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
			13,	13,								//Green
			15,									//Black
			16, 16								//Paddy
	)); 
	private static final int NUM_HAIRSTYLES = 2;

	public EntitySapphire(World worldIn) {
		super(worldIn);
		//Width must be 0.6, or she will get stuck trying to pass through doors
		this.setSize(0.6F, 1.2F);
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
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(2, new EntityAIFutureVision(this));
		this.tasks.addTask(3, new EntityAIRetroVision(this));
		this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
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

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		int color = this.SKIN_COLORS.get(this.rand.nextInt(this.SKIN_COLORS.size()));
		this.itemDataToGemData(color);
		return livingdata;
	}
	
	@Override
	public void itemDataToGemData(int data) {
		this.setSpecial(data);
		if (data == 16) {
			this.setCustomNameTag(new TextComponentTranslation("entity.kagic.sapphire_16.name").getUnformattedComponentText());
			this.setSkinColor(this.generateSkinColor());
			this.setHairColor(0xF9D5BD);
			this.setGemColor(this.generateGemColor());
			this.setUniformColor(1);
			this.nativeColor = 1;
			this.setInsigniaColor(1);
		}
		else {
			this.setCustomNameTag(new TextComponentTranslation("entity.kagic.sapphire.name").getUnformattedComponentText());
			this.setSkinColor(this.generateSkinColor());
			this.setHairColor(data == 11 ? 0xB9F5FC : this.getSkinColor());
			this.setGemColor(this.generateGemColor());
			this.setUniformColor(data);
			this.nativeColor = data;
			this.setInsigniaColor(data);
		}
	}
	
	@Override
	protected int generateGemColor() {
		switch (this.getSpecial()) {
    	case 0:
    		return 0xFFFFFF;
    	case 4:
    		return 0xFEFE4C;
    	case 6:
    		return 0xE8759B;
    	case 10:
    		return 0x7B3BAE;
    	case 11:
    		return 0x3B54BA;
    	case 13:
    		return 0x4C6519;
    	case 15:
    		return 0x333333;
    	}
		return 0xDE7565;
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("skinColor")) {
			if (compound.getInteger("skinColor") == 0) {
				this.setSpecial(11);
				this.setSkinColor(this.generateSkinColor());
				this.setHairColor(this.getSkinColor());
				this.setUniformColor(11);
				this.setInsigniaColor(11);
			}
		}
		else {
			this.setSpecial(11);
			this.setSkinColor(this.generateSkinColor());
			this.setHairColor(this.getSkinColor());
			this.setUniformColor(11);
			this.setInsigniaColor(11);
		}
		super.readEntityFromNBT(compound);
	}
	
	@Override
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
	
	@Override
	protected SoundEvent getAmbientSound() {
		if (this.isDefective()) {
			return ModSounds.PADPARADSCHA_LIVING;
		}
		return ModSounds.SAPPHIRE_LIVING;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.SAPPHIRE_HURT;
	}
	
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.SAPPHIRE_OBEY;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.SAPPHIRE_DEATH;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block block) {
		//Sapphires have no legs and are thus completely silent
	}
	
	@Override
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
	
	@Override
	public void onLivingUpdate() {
		if (this.isPrimary()) {
	    	BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos(0, 0, 0);
	        for (BlockPos.MutableBlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.getPosition().add(-2, -1.0D, -2), this.getPosition().add(2, -1.0D, 2))) {
	            if (blockpos1.distanceSqToCenter(this.posX, this.posY, this.posZ) <= 4) {
	                blockpos.setPos(blockpos1.getX(), blockpos1.getY() + 1, blockpos1.getZ());
	                IBlockState iblockstate = this.world.getBlockState(blockpos);
	                if (iblockstate.getMaterial() == Material.AIR) {
	                    IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
	                    if (iblockstate1.getMaterial() == Material.WATER && !(iblockstate1.getBlock() instanceof BlockRoseTears) && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && this.world.mayPlace(Blocks.FROSTED_ICE, blockpos1, false, EnumFacing.DOWN, null)) {
	                        this.world.setBlockState(blockpos1, Blocks.FROSTED_ICE.getDefaultState());
	                        this.world.scheduleUpdate(blockpos1.toImmutable(), Blocks.FROSTED_ICE, this.rand.nextInt(60) + 60);
	                    }
	                    else if (iblockstate1.getMaterial() == Material.LAVA && ((Integer)iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && this.world.mayPlace(Blocks.COBBLESTONE, blockpos1, false, EnumFacing.DOWN, null)) {
	                        this.world.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState());
	                    }
	                }
	            }
	        }
		}
        if (this.luckTicks > 80 && !(this.isDead || this.getHealth() <= 0.0F)) {
			this.futureVision();
			this.luckTicks = 0;
		}
		this.luckTicks += 1;
		if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.5D;
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
	            					if (this.isDefective()) {
	            						entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100));
	            					}
	            					else {
	            						entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
	            					}
	            				}
	            			}
	            			else {
	            				if (this.isDefective()) {
            						entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100));
            					}
            					else {
            						entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
            					}
	            			}
	            		}
	            	}
	            	if (this.isOwner(entity)) {
	            		if (this.isDefective()) {
    						entity.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 100));
    					}
    					else {
    						entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100));
    					}
	            	}
            	}
            }
        }
    }

	/*********************************************************
	 * Methods related to death.							 *
	 *********************************************************/
	@Override
	public void onDeath(DamageSource cause) {
		switch (this.getSpecial()) {
		case 0:
			this.droppedGemItem = ModItems.WHITE_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_WHITE_SAPPHIRE_GEM;
			break;
		case 1:
			this.droppedGemItem = ModItems.ORANGE_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_ORANGE_SAPPHIRE_GEM;
			break;
		case 4:
			this.droppedGemItem = ModItems.YELLOW_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_YELLOW_SAPPHIRE_GEM;
			break;
		case 6:
			this.droppedGemItem = ModItems.PINK_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_PINK_SAPPHIRE_GEM;
			break;
		case 10:
			this.droppedGemItem = ModItems.PURPLE_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_PURPLE_SAPPHIRE_GEM;
			break;
		case 11:
			this.droppedGemItem = ModItems.BLUE_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_BLUE_SAPPHIRE_GEM;
			break;
		case 13:
			this.droppedGemItem = ModItems.GREEN_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_GREEN_SAPPHIRE_GEM;
			break;
		case 15:
			this.droppedGemItem = ModItems.BLACK_SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_BLACK_SAPPHIRE_GEM;
			break;
		case 16:
			this.droppedGemItem = ModItems.PADPARADSCHA_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_PADPARADSCHA_GEM;
			break;
		default:
			this.droppedGemItem = ModItems.SAPPHIRE_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_SAPPHIRE_GEM;
		}
		super.onDeath(cause);
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		int colorIndex = this.getSpecial();
		int colorValue = 0;
		if (this.getSpecial() == 11) {
			colorValue = 0x7298EB;
		}
		else if (this.getSpecial() < 16) {
			EnumDyeColor color = EnumDyeColor.values()[colorIndex];
			try {
				colorValue = ReflectionHelper.getPrivateValue(EnumDyeColor.class, color, "colorValue", "field_193351_w", "w");
			} catch (Exception e) {}
		}
		else {
			colorValue = 0xFF8D32;
		}
		return colorValue;
	}

	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntitySapphire.NUM_HAIRSTYLES);
	}

	@Override
	public boolean hasHairVariant(GemPlacements placement) {
		switch(placement) {
		case RIGHT_EYE:
			return true;
		case LEFT_EYE:
			return true;
		default:
			return false;
		}
	}
	
	public static EntitySapphire convertFrom(EntityPadparadscha pad) {
		EntitySapphire sapphire = new EntitySapphire(pad.world);
		sapphire.setPosition(pad.posX, pad.posY, pad.posZ);
		sapphire.itemDataToGemData(16);
		return sapphire;
	}
}
