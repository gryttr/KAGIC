package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.EntityPepo;
import mod.akrivus.kagic.entity.ai.EntityAIProtectionFuse;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.entity.pepo.EntityCactus;
import mod.akrivus.kagic.entity.pepo.EntityMelon;
import mod.akrivus.kagic.entity.pepo.EntityPumpkin;
import mod.akrivus.kagic.entity.pepo.EntityStrawberry;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemGem;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class EntityRoseQuartz extends EntityQuartzSoldier implements INpc {
	public static final HashMap<IBlockState, Double> ROSE_QUARTZ_YIELDS = new HashMap<IBlockState, Double>();
	public static final double ROSE_QUARTZ_DEFECTIVITY_MULTIPLIER = 1;
	public static final double ROSE_QUARTZ_DEPTH_THRESHOLD = 72;
	public static final HashMap<Integer, ResourceLocation> ROSE_QUARTZ_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private BlockPos lastSurgeLocation;
	private int regenTicks = 0;

	public static HashMap<Item, Item> ROSE_RECIPES = new HashMap<Item, Item>();
	static {
		ROSE_RECIPES.put(Items.POISONOUS_POTATO, Items.POTATO);
		ROSE_RECIPES.put(Items.ROTTEN_FLESH, Items.LEATHER);
		ROSE_RECIPES.put(Items.WHEAT, Items.BREAD);
	}
	
	public static final int SKIN_COLOR_BEGIN = 0xFDDAC9;
	
	public static final int SKIN_COLOR_END = 0xFDDCCB;
	
	private static final int NUM_HAIRSTYLES = 1;
	
	public static final int HAIR_COLOR_BEGIN = 0xFDAECB;
	
	public static final int HAIR_COLOR_END = 0xFF5EEC;
	
	public EntityRoseQuartz(World worldIn) {
		super(worldIn);
		this.nativeColor = 2;
		
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
		this.tasks.addTask(3, new EntityAIProtectionFuse(this, EntityPearl.class, EntityRainbowQuartz.class, 16D));
		this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
		
		// Apply targetting.
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityMob>(this, EntityMob.class, 10, true, false, new Predicate<EntityMob>() {
			public boolean apply(EntityMob input) {
				return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
			}
		}));
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);

		this.droppedGemItem = ModItems.ROSE_QUARTZ_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_ROSE_QUARTZ_GEM;
	}

	protected int generateGemColor() {
		return 0xFFA2E6;
	}
	public void convertGems(int placement) {
		this.setGemCut(GemCuts.FACETED.id);
		switch (placement) {
		case 0:
			this.setGemPlacement(GemPlacements.CHEST.id);
			break;
		case 1:
			this.setGemPlacement(GemPlacements.BELLY.id);
			break;
		}
	}
	
	/*********************************************************
	 * Methods related to entity loading.					*
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return super.onInitialSpawn(difficulty, livingdata);
	}

	/*********************************************************
	 * Methods related to interaction.					   *
	 *********************************************************/
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND && !this.world.isRemote) {
			if (this.isTamed() && this.isOwner(player)) {
				ItemStack stack = player.getHeldItemMainhand();
				Item item = stack.getItem();
				if (item instanceof ItemGem) {
					if (((ItemGem) item).isCracked) {
						ItemStack result = new ItemStack(ModItems.GEM_TABLE.get(item));
						result.setTagCompound(stack.getTagCompound());
						this.entityDropItem(result, 1);
						if (!player.capabilities.isCreativeMode) {
							stack.shrink(1);
						}
						this.playObeySound();
						return true;
					}
				} else if (ROSE_RECIPES.containsKey(item)) {
					if (!player.capabilities.isCreativeMode) {
						stack.shrink(1);
					}
					this.entityDropItem(new ItemStack(ROSE_RECIPES.get(item)), 1);
				} else if (item == Items.BUCKET && !this.isDefective()) {
					stack.shrink(1);

					if (stack.isEmpty()) {
						player.setHeldItem(hand, FluidUtil.getFilledBucket(new FluidStack(ModBlocks.FLUID_ROSE_TEARS, 1000)));
					} else if (!player.inventory.addItemStackToInventory(FluidUtil.getFilledBucket(new FluidStack(ModBlocks.FLUID_ROSE_TEARS, 1000)))) {
						player.dropItem(FluidUtil.getFilledBucket(new FluidStack(ModBlocks.FLUID_ROSE_TEARS, 1000)), false);
					}
					this.playObeySound();
					return true;
				}
			}
		}
		return super.processInteract(player, hand);
	}
	public void whenDefective() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
		this.setSize(0.63F, 2.3F);
	}
	
	/*********************************************************
	 * Methods related to living.							*
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.regenTicks > 80 && !this.isDefective() && !(this.isDead || this.getHealth() <= 0.0F)) {
			this.startCryingLikeAnEmo();
			this.regenTicks = 0;
		}
		if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.5D;
		}
		this.regenTicks += 1;
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to death.							 *
	 *********************************************************/
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.world.isRemote) {
			if (this.lastSurgeLocation == null || this.getPosition().distanceSq(this.lastSurgeLocation) > 16) {
				for (BlockPos.MutableBlockPos pos : BlockPos.getAllInBoxMutable(this.getPosition().add(-8, -2, -8), this.getPosition().add(8, 2, 8))) {
					IBlockState state = this.world.getBlockState(pos);
					if (state.getBlock() == Blocks.MELON_BLOCK) {
						this.world.destroyBlock(pos, false);
						EntityMelon pepo = new EntityMelon(this.world);
						pepo.setPosition(pos.getX(), pos.getY(), pos.getZ());
						pepo.setHealth(pepo.getMaxHealth());
						this.world.spawnEntity(pepo);
						pepo.setMaster(this);
					}
					else if (state.getBlock() == Blocks.PUMPKIN) {
						this.world.destroyBlock(pos, false);
						EntityPumpkin pepo = new EntityPumpkin(this.world);
						pepo.setPosition(pos.getX(), pos.getY(), pos.getZ());
						pepo.setHealth(pepo.getMaxHealth());
						this.world.spawnEntity(pepo);
						pepo.setMaster(this);
					}
					else if (state.getBlock() == Blocks.LIT_PUMPKIN) {
						this.world.destroyBlock(pos, false);
						EntityPumpkin pepo = new EntityPumpkin(this.world);
						pepo.setPosition(pos.getX(), pos.getY(), pos.getZ());
						pepo.setHealth(pepo.getMaxHealth());
						this.world.spawnEntity(pepo);
						pepo.setMaster(this);
						pepo.setLit(true);
					}
					else if (state.getBlock() == Blocks.CACTUS) {
						this.world.destroyBlock(pos, false);
						EntityCactus pepo = new EntityCactus(this.world);
						pepo.setPosition(pos.getX(), pos.getY(), pos.getZ());
						pepo.setHealth(pepo.getMaxHealth());
						this.world.spawnEntity(pepo);
						pepo.setMaster(this);
					}
					else if (state.getBlock() == ModBlocks.GIANT_STRAWBERRY) {
						this.world.destroyBlock(pos, false);
						EntityStrawberry pepo = new EntityStrawberry(this.world);
						pepo.setPosition(pos.getX(), pos.getY(), pos.getZ());
						pepo.setHealth(pepo.getMaxHealth());
						this.world.spawnEntity(pepo);
						pepo.setMaster(this);
					}
				}
			}
			List<EntityPepo> list = this.world.<EntityPepo>getEntitiesWithinAABB(EntityPepo.class, this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D));
			for (EntityPepo pepo : list) {
				if (pepo.getMaster() == null) {
					pepo.setMaster(this);
				}
			}
			/*if (list.size() > 0) {
				if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
					this.getOwner().addStat(ModAchievements.REVOLUTION);
				}
			}*/
			this.lastSurgeLocation = this.getPosition();
		}
		return super.attackEntityFrom(source, amount);
	}
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			if (cause.getTrueSource() != null) {
				if (!this.isOwner((EntityLivingBase) cause.getTrueSource())) {
					this.dropItem(ModItems.RECORD_ROSES_FOUNTAIN, 1);
				}
			}
		}
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to combat.							*
	 *********************************************************/
	private void startCryingLikeAnEmo() {
		if (!this.world.isRemote) {
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(16.0, (double) this.world.getHeight(), 16.0);
			List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for (EntityLivingBase entity : list) {
				if (!entity.isDead || entity.getHealth() > 0.0F) {
					if (entity instanceof EntityGem) {
						EntityGem gem = (EntityGem) entity;
						if (this.getServitude() == gem.getServitude()) {
							if (this.getServitude() == EntityGem.SERVE_HUMAN) {
								if (this.isOwnerId(gem.getOwnerId())) {
									entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
								}
							}
							else {
								entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
							}
						}
					}
					if (this.isOwner(entity)) {
						entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
					}
				}
			}
		}
	}
	
	/*********************************************************
	 * Methods related to sounds.							*
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.ROSE_QUARTZ_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.ROSE_QUARTZ_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.ROSE_QUARTZ_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.						 *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_BEGIN);
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityRoseQuartz.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_BEGIN);
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_END);
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
}
