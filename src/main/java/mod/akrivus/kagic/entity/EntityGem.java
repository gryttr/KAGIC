package mod.akrivus.kagic.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.entity.ai.EntityAIAttackRangedBow;
import mod.akrivus.kagic.entity.ai.EntityAIFightWars;
import mod.akrivus.kagic.entity.ai.EntityAIPredictFights;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModConfigs;
import mod.akrivus.kagic.init.ModEnchantments;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemAutonomyContract;
import mod.akrivus.kagic.items.ItemGem;
import mod.akrivus.kagic.items.ItemJointContract;
import mod.akrivus.kagic.items.ItemLiberationContract;
import mod.akrivus.kagic.items.ItemPeaceTreaty;
import mod.akrivus.kagic.items.ItemTransferContract;
import mod.akrivus.kagic.items.ItemWarDeclaration;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.akrivus.kagic.util.PoofDamage;
import mod.akrivus.kagic.util.ShatterDamage;
import mod.akrivus.kagic.util.SlagDamage;
import mod.heimrarnadalr.kagic.worlddata.GalaxyPadLocation;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import mod.heimrarnadalr.kagic.worlddata.WorldDataGalaxyPad;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.DyeUtils;

public class EntityGem extends EntityCrystalSkills implements IEntityOwnable, IRangedAttackMob, IEntityAdditionalSpawnData {
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityGem.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	protected static final DataParameter<Optional<UUID>> GEUUID = EntityDataManager.<Optional<UUID>>createKey(EntityGem.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	protected static final DataParameter<String> SPECIFIC_NAME = EntityDataManager.<String>createKey(EntityGem.class, DataSerializers.STRING);
	protected static final DataParameter<Boolean> SELECTED = EntityDataManager.<Boolean>createKey(EntityGem.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityGem.class, DataSerializers.BOOLEAN);
	
	protected static final DataParameter<Integer> INSIGNIA_COLOR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> UNIFORM_COLOR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> SKIN_COLOR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> HAIR_COLOR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> GEM_COLOR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> HAIR = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> GEM_CUT = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> GEM_PLACEMENT = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Boolean> VISOR = EntityDataManager.<Boolean>createKey(EntityGem.class, DataSerializers.BOOLEAN);
	
	protected static final DataParameter<Integer> FUSION_COUNT = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	protected static final DataParameter<String> FUSION_PLACEMENTS = EntityDataManager.<String>createKey(EntityGem.class, DataSerializers.STRING);
	
	protected static final DataParameter<Boolean> DEFECTIVE = EntityDataManager.<Boolean>createKey(EntityGem.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> PRIMARY = EntityDataManager.<Boolean>createKey(EntityGem.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Integer> SPECIAL = EntityDataManager.<Integer>createKey(EntityGem.class, DataSerializers.VARINT);
	
	public static final int SERVE_NONE = 0;
	public static final int SERVE_HUMAN = 1;
	public static final int SERVE_YELLOW_DIAMOND = 2;
	public static final int SERVE_BLUE_DIAMOND = 3;
	public static final int SERVE_WHITE_DIAMOND = 4;
	public static final int SERVE_REBELLION = 5;
	public static final int SERVE_ITSELF = 6;
	public static final int SERVE_HOSTILE = 7;
	
	private final EntityAIBase rangedAttack = new EntityAIAttackRangedBow(this, 0.6D, 20, 16.0F);
	private final EntityAIBase meleeAttack = new EntityAIAttackMelee(this, 1.2D, true);
	private final EntityAITarget diamondAttackAI = new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
		public boolean apply(EntityLivingBase input) {
			if (input instanceof EntityGem) {
				return ((EntityGem) input).getServitude() == EntityGem.SERVE_HUMAN;
			}
			return input != null && input instanceof EntityPlayer;
		}
	});
	private final EntityAITarget rebelAttackAI = new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
		public boolean apply(EntityLivingBase input) {
			if (input instanceof EntityGem) {
				return ((EntityGem) input).isTamed() || ((EntityGem) input).isDiamond;
			}
			return input != null && input instanceof EntityPlayer;
		}
	});
	private final EntityAITarget warDeclarationAI = new EntityAIFightWars(this);
	protected EntityAIStay stayAI;
	
	public ArrayList<NBTTagCompound> fusionMembers = new ArrayList<NBTTagCompound>();
	public boolean wantsToFuse;
	public int compatIndex;
	
	protected ArrayList<UUID> jointOwners = new ArrayList<UUID>();
	public boolean followingGem = false;
	private UUID leader = null;
	private BlockPos restPosition;

	public ItemGem droppedGemItem;
	public ItemGem droppedCrackedGemItem;
	public int fallbackServitude = -1;
	
	public int nativeColor = 12;
	public boolean uniformColorChanged = false;
	public boolean commanded = false;
	
	public boolean isSoldier;
	public boolean isDiamond;
	public boolean isSpaceBorn;
	public boolean isAttacking;
	public boolean isPeaceful;
	public boolean isSitting;
	public boolean canTalk;
	public boolean killsPlayers;
	
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;
	
	private int dimensionOfCreation;
	private int timeUntilBetrayal;
	private int servitude = 0;
	
	protected float pitch = 1.0F;
	protected int visorChanceReciprocal = 3; // 1 in visorChanceReciprocal gems will have a visor
	protected HashMap<GemCuts, ArrayList<GemPlacements>> cutPlacements = new HashMap<GemCuts, ArrayList<GemPlacements>>();
	
	public EntityGem(World worldIn) {
		super(worldIn);
		this.seePastDoors();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.stepHeight = 0.6F;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIRestrictOpenDoor(this));
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.<UUID>absent());
		this.dataManager.register(GEUUID, Optional.<UUID>absent());
		this.dataManager.register(SPECIFIC_NAME, "");
		this.dataManager.register(SELECTED, false);
		this.dataManager.register(GEM_PLACEMENT, -1);
		this.dataManager.register(GEM_CUT, -1);
		this.dataManager.register(VISOR, false);
		this.dataManager.register(INSIGNIA_COLOR, 12);
		this.dataManager.register(UNIFORM_COLOR, 12);
		this.dataManager.register(SKIN_COLOR, 0);
		this.dataManager.register(HAIR_COLOR, 0);
		this.dataManager.register(GEM_COLOR, 0);
		this.dataManager.register(HAIR, 0);
		this.dataManager.register(SWINGING_ARMS, false);
		this.dataManager.register(DEFECTIVE, false);
		this.dataManager.register(PRIMARY, false);
		this.dataManager.register(SPECIAL, 0);
		this.dataManager.register(FUSION_COUNT, 1);
		this.dataManager.register(FUSION_PLACEMENTS, "");
		this.compatIndex = worldIn.rand.nextInt(20);
	}

	/*********************************************************
	 * Methods related to entity loading.					*
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("gemPlacement", this.getGemPlacement().id);
		compound.setInteger("gemCut", this.getGemCut().id);
		compound.setBoolean("hasVisor", this.hasVisor());
		compound.setInteger("insigniaColor", this.getInsigniaColor());
		compound.setInteger("uniformColor", this.getUniformColor());
		compound.setInteger("skinColor", this.getSkinColor());
		compound.setInteger("hair", this.getHairStyle());
		compound.setInteger("hairColor", this.getHairColor());
		compound.setInteger("gemColor", this.getGemColor());
		compound.setBoolean("defective", this.isDefective());
		compound.setBoolean("primary", this.isPrimary());
		compound.setInteger("special", this.getSpecial());
		compound.setString("specificName", this.getSpecificName());
		NBTTagList fusionMembers = new NBTTagList();
		for (int i = 0; i < this.fusionMembers.size(); ++i) {
			fusionMembers.appendTag(this.fusionMembers.get(i));
		}
		compound.setInteger("fusionCount", this.getFusionCount());
		compound.setString("fusionPlacements", this.getFusionPlacements());
		compound.setTag("fusionMembers", fusionMembers);
		compound.setBoolean("uniformColorChanged", this.uniformColorChanged);
		if (this.getOwnerId() == null) {
			compound.setString("ownerId", "");
		}
		else {
			compound.setString("ownerId", this.getOwnerId().toString());
		}
		if (this.getGemId() == null) {
			compound.setString("gemId", MathHelper.getRandomUUID().toString());
		}
		else {
			compound.setString("gemId", this.getGemId().toString());
		}
		if (this.getLeader() == null) {
			compound.setString("leaderId", "");
		}
		else {
			compound.setString("leaderId", this.leader.toString());
		}
		compound.setBoolean("followingGem", this.followingGem);
		NBTTagList owners = new NBTTagList();
		for (int i = 0; i < this.jointOwners.size(); ++i) {
			UUID ownerId = this.jointOwners.get(i);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ownerId", ownerId.toString());
			owners.appendTag(nbt);
		}
		compound.setTag("jointOwners", owners);
		if (this.restPosition != null) {
			compound.setDouble("restX", this.restPosition.getX());
			compound.setDouble("restY", this.restPosition.getY());
			compound.setDouble("restZ", this.restPosition.getZ());
		}
		compound.setBoolean("sitting", this.isSitting());
		compound.setInteger("servitude", this.servitude);
		compound.setBoolean("commanded", this.commanded);
		compound.setInteger("fallbackServitude", this.fallbackServitude);
		compound.setInteger("timeUntilBetrayal", this.timeUntilBetrayal);
		compound.setInteger("dimensionOfCreation", this.dimensionOfCreation);
		compound.setBoolean("wantsToFuse", this.wantsToFuse);
		compound.setBoolean("killsPlayers", this.killsPlayers);
		compound.setFloat("pitch", this.pitch);
	}
	
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("gemCut")) {
			this.setGemCut(compound.getInteger("gemCut"));
			this.setGemPlacement(compound.getInteger("gemPlacement"));
		}
		else {
			this.convertGems(compound.getInteger("gemPlacement"));
		}

		if (!this.isGemPlacementDefined() || !this.isGemCutDefined() || !this.isCorrectCutPlacement()) {
			this.setNewCutPlacement();
		}

		this.applyGemPlacementBuffs(false);
		this.setSpecial(compound.getInteger("special"));
		this.setHasVisor(compound.getBoolean("hasVisor"));
		if (compound.hasKey("insigniaColor")) {
			this.setInsigniaColor(compound.getInteger("insigniaColor"));
		}
		else {
			this.setInsigniaColor(12);
		}
		
		if (compound.hasKey("uniformColor")) {
			this.setUniformColor(compound.getInteger("uniformColor"));
		}
		else {
			this.setUniformColor(this.getInsigniaColor());
		}

		if (compound.hasKey("skinColor")) {
			if (compound.getInteger("skinColor") == 0) {
				this.setSkinColor(this.generateSkinColor());
			} else {
				this.setSkinColor(compound.getInteger("skinColor"));
			}
		}
		else {
			this.setSkinColor(this.generateSkinColor());
		}

		if (compound.hasKey("hairColor")) {
			if (compound.getInteger("hairColor") == 0) {
				this.setHairColor(this.generateHairColor());
			} else {
				this.setHairColor(compound.getInteger("hairColor"));
			}
		}
		else {
			this.setHairColor(this.generateHairColor());
		}

		this.setHairStyle(compound.getInteger("hair"));
		
		if (compound.hasKey("gemColor")) {
			this.setGemColor(compound.getInteger("gemColor"));
		}
		else {
			this.setGemColor(this.generateGemColor());
		}
		
		this.setDefective(compound.getBoolean("defective"));
		if (this.isDefective()) {
			this.whenDefective();
		}
		this.setPrimary(compound.getBoolean("primary"));
		if (this.isPrimary()) {
			this.whenPrimary();
		}
		this.setSpecificName(compound.getString("specificName"));
		this.followingGem = compound.getBoolean("followingGem");
		NBTTagList fusionMembers = compound.getTagList("fusionMembers", 10);
		for (int i = 0; i < fusionMembers.tagCount(); ++i) {
			this.fusionMembers.add(fusionMembers.getCompoundTagAt(i));
		}
		this.setFusionCount(compound.getInteger("fusionCount"));
		this.setFusionPlacements(compound.getString("fusionPlacements"));
		this.uniformColorChanged = compound.getBoolean("uniformColorChanged");
		String ownerId;
		if (compound.hasKey("ownerId", 8)) {
			ownerId = compound.getString("ownerId");
		}
		else {
			String tempId = compound.getString("ownerId");
			ownerId = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), tempId);
		}
		if (!ownerId.isEmpty()) {
			this.setOwnerId(UUID.fromString(ownerId));
		}
		String gemId;
		if (compound.hasKey("gemId", 8)) {
			gemId = compound.getString("gemId");
		}
		else {
			gemId = MathHelper.getRandomUUID().toString();
		}
		if (!gemId.isEmpty()) {
			this.setGemId(UUID.fromString(gemId));
		}
		String leaderId;
		if (compound.hasKey("leaderId", 8)) {
			leaderId = compound.getString("leaderId");
		}
		else {
			String tempId = compound.getString("leaderId");
			leaderId = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), tempId);
		}
		if (!leaderId.isEmpty()) {
			this.setLeader(UUID.fromString(leaderId));
		}
		NBTTagList owners = compound.getTagList("jointOwners", 10);
		for (int i = 0; i < owners.tagCount(); ++i) {
			NBTTagCompound nbt = owners.getCompoundTagAt(i);
			String jointId;
			if (nbt.hasKey("ownerId", 8)) {
				jointId = nbt.getString("ownerId");
			}
			else {
				String tempId = nbt.getString("ownerId");
				jointId = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), tempId);
			}
			if (!jointId.isEmpty()) {
				this.jointOwners.add(UUID.fromString(jointId));
			}
		}
		if (compound.hasKey("restX") && compound.hasKey("restY") && compound.hasKey("restZ")) {
			this.restPosition = new BlockPos(compound.getDouble("restX"), compound.getDouble("restY"), compound.getDouble("restZ"));
		}
		if (this.stayAI != null) {
			this.stayAI.setSitting(compound.getBoolean("sitting"));
		}
		this.isSitting = compound.getBoolean("sitting");
		this.servitude = compound.getInteger("servitude");
		this.commanded = compound.getBoolean("commanded");
		this.fallbackServitude = compound.getInteger("fallbackServitude");
		this.timeUntilBetrayal = compound.getInteger("timeUntilBetrayal");
		this.wantsToFuse = compound.getBoolean("wantsToFuse");
		this.setKillsPlayers(compound.getBoolean("killsPlayers"));
		if (compound.hasKey("pitch" )) {
			this.pitch = compound.getFloat("pitch");
		}
		else {
			this.pitch = 0.7F + (this.rand.nextFloat() / 2);
		}
		if (compound.hasKey("dimensionOfCreation")) {
			this.setDimensionOfCreation(compound.getInteger("dimensionOfCreation"));
		}
		else {
			this.setDimensionOfCreation(this.dimension);
		}
		this.setAttackAI();
	}
	
	public void setNewCutPlacement() {
		Set<GemCuts> cuts = this.cutPlacements.keySet();
		int cutIndex = this.rand.nextInt(cuts.size());
		GemCuts cut = (GemCuts) cuts.toArray()[cutIndex];
		
		ArrayList<GemPlacements> placements = this.cutPlacements.get(cut);
		int placementIndex = this.rand.nextInt(placements.size());
		GemPlacements placement = placements.get(placementIndex);
		
		this.setGemCut(cut.id);
		this.setGemPlacement(placement.id);		
	}
	
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setHealth(this.getMaxHealth());
		this.setGemId(MathHelper.getRandomUUID());
		if (!this.isGemPlacementDefined() || !this.isGemCutDefined() || !this.isCorrectCutPlacement()) {
			this.setNewCutPlacement();
		}
		this.setUniformColor(this.nativeColor);
		this.setSkinColor(this.generateSkinColor());
		this.setHairStyle(this.generateHairStyle());
		this.setHairColor(this.generateHairColor());
		this.setGemColor(this.generateGemColor());
		this.applyGemPlacementBuffs(true);
		this.setHasVisor(this.rand.nextInt(visorChanceReciprocal) == 0);
		this.setDimensionOfCreation(this.dimension);
		this.setAttackAI();
		if (this.fallbackServitude == -1) {
				if (ModConfigs.canRebel) {
					if ((this.isDefective() && this.rand.nextInt(5) == 0) || this.rand.nextInt(50) == 0) {
						this.fallbackServitude = EntityGem.SERVE_REBELLION;
					}
					else {
						this.fallbackServitude = 0;
					}
				}
				else {
					this.fallbackServitude = 0;
				}
		}
		this.pitch = 0.7F + (this.rand.nextFloat() / 2);
		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected int generateSkinColor() {
		return 0;
	}
	
	protected int generateHairStyle() {
		return 0;
	}
	
	protected int generateHairColor() {
		return 0;
	}
	
	protected int generateGemColor() {
		return 0xFFFFFF;
	}

	public boolean canChangeInsigniaColorByDefault() {
		return true;
	}
	
	public boolean canChangeUniformColorByDefault() {
		return true;
	}
	
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	public boolean canDespawn() {
		return false;
	}
	
	public String getSpecificName() {
		return this.dataManager.get(SPECIFIC_NAME);
	}
	public void setSpecificName(String specific_name) {
		this.dataManager.set(SPECIFIC_NAME, specific_name);
	}
	
	/*********************************************************
	 * Methods related to entity living.					 *
	 *********************************************************/
	public void onLivingUpdate() {
		if (this.world.isRemote) {
			if (this.getServitude() == EntityGem.SERVE_REBELLION) {
				for (int i = 0; i < 2; ++i) {
					this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
				}
			}
			else if (this.getServitude() == EntityGem.SERVE_YELLOW_DIAMOND) {
				for (int i = 0; i < 2; ++i) {
					this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
				}
			}
		}
		else {
			if (this.getSpecificName() == "") {
				this.setSpecificName(this.generateSpecificName(this.getPosition()));
			}
			if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getAttackTarget() instanceof EntityPlayer) {
				this.setAttackTarget(null);
			}
			if (this.isPeaceful) {
				this.setAttackTarget(null);
				this.isPeaceful = false;
			}
			if (this.fallbackServitude > 0 && ModConfigs.canRebel) {
				if (this.timeUntilBetrayal > (this.rand.nextDouble() * 4) * 24000) {
					this.setServitude(this.fallbackServitude);
				}
				++this.timeUntilBetrayal;
			}
		}
		this.world.profiler.startSection("looting");
        if (!this.world.isRemote && this.canPickUpLoot() && !this.dead && !this.world.getGameRules().getBoolean("mobGriefing")) {
            for (EntityItem entityitem : this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().grow(1.0D, 0.0D, 1.0D))) {
                if (!entityitem.isDead && !entityitem.getItem().isEmpty() && !entityitem.cannotPickup()) {
                    this.updateEquipmentIfNeeded(entityitem);
                }
            }
        }
        this.world.profiler.endSection();
        if (!(this instanceof EntityFusionGem)) {
        	this.updateArmSwingProgress();
        }
		super.onLivingUpdate();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
        this.updateCape();
	}
	
	public boolean hasCape() {
		return false;
	}
	
    private void updateCape() {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double d0 = this.posX - this.chasingPosX;
        double d1 = this.posY - this.chasingPosY;
        double d2 = this.posZ - this.chasingPosZ;
        if (d0 > 10.0D) {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }
        if (d2 > 10.0D) {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }
        if (d1 > 10.0D) {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }
        if (d0 < -10.0D) {
            this.chasingPosX = this.posX;
            this.prevChasingPosX = this.chasingPosX;
        }
        if (d2 < -10.0D) {
            this.chasingPosZ = this.posZ;
            this.prevChasingPosZ = this.chasingPosZ;
        }
        if (d1 < -10.0D) {
            this.chasingPosY = this.posY;
            this.prevChasingPosY = this.chasingPosY;
        }
        this.chasingPosX += d0 * 0.25D;
        this.chasingPosZ += d2 * 0.25D;
        this.chasingPosY += d1 * 0.25D;
    }

	/*********************************************************
	 * Methods related to entity interaction.				*
	 *********************************************************/
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (stack.getItem() == ModItems.GEM_STAFF || stack.getItem() == ModItems.COMMANDER_STAFF) {
					if (this.isTamed()) {
						if (player.isSneaking()) {
							if (this.isOwner(player)) {
								if (this.getHeldItemMainhand().getItem() == ModItems.COMMANDER_STAFF) {
									this.entityDropItem(this.getHeldItemMainhand(), 0.0F);
									this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
									this.playObeySound();
								}
								else {
									this.alternateInteract(player);
									this.playObeySound();
								}
							}
						}
						else {
							if (this.isOwner(player)) {
								this.setSitting(player, !this.isSitting());
								this.playObeySound();
							}
							else {
								player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_you", this.getName()));
								return true;
							}
						}
					}
					else if (stack.getItem() != ModItems.COMMANDER_STAFF) {
						this.setOwnerId(player.getUniqueID());
						this.setLeader(player);
						this.setServitude(EntityGem.SERVE_HUMAN);
						this.navigator.clearPath();
						this.setAttackTarget(null);
						this.setHealth(this.getMaxHealth());
						this.playTameEffect();
						this.world.setEntityState(this, (byte) 7);
						this.playObeySound();
						//player.addStat(ModAchievements.I_MADE_YOU);
						player.sendMessage(new TextComponentTranslation("command.kagic.now_serves_you", this.getName()));
						return true;
					}
				}
				else if (stack.getItem() == Items.WATER_BUCKET && player.isSneaking()) {
					if (this.isTamed()) {
						if (this.isOwner(player)) {
							this.setUniformColor(this.nativeColor);
							return true;
						}
					}
				}
				else if (stack.getItem() == ModItems.TRANSFER_CONTRACT) {
					if (this.isTamed()) {
						if (this.isOwner(player)) {
							player.sendMessage(new TextComponentTranslation("command.kagic.already_serves_you", this.getName()));
							return true;
						}
						else {
							ItemTransferContract contract = (ItemTransferContract) stack.getItem();
							if (this.isOwnerId(contract.getOwner(stack))) {
								if (contract.getOwner(stack).equals(this.getOwnerId())) {
									if (this.leader.equals(this.getOwnerId())) {
										this.setLeader(player);
									}
									this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.ownership_transfered", this.getName(), player.getName()));
								}
								else {
									for (UUID ownerId : this.jointOwners) {
										if (contract.getOwner(stack).equals(ownerId)) {
											if (this.leader.equals(this.getOwnerId())) {
												this.setLeader(player);
											}
											try {
												this.world.getPlayerEntityByUUID(EntityPlayer.getUUID(player.getGameProfile())).sendMessage(new TextComponentTranslation("command.kagic.ownership_transfered", this.getName(), player.getName()));
											}
											catch (Exception e) {
												// Failed.
											}
											ownerId = EntityPlayer.getUUID(player.getGameProfile());
										}
									}
								}
								this.playObeySound();
								player.sendMessage(new TextComponentTranslation("command.kagic.now_serves_you", this.getName()));
								this.setOwnerId(EntityPlayer.getUUID(player.getGameProfile()));
								if (!player.capabilities.isCreativeMode) {
									stack.shrink(1);
								}
								return true;
							}
							else {
								player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
								return true;
							}
						}
					}
				}
				else if (stack.getItem() == ModItems.JOINT_CONTRACT) {
					if (this.isTamed()) {
						if (this.isOwner(player)) {
							player.sendMessage(new TextComponentTranslation("command.kagic.already_serves_you", this.getName()));
							return true;
						}
						else {
							ItemJointContract contract = (ItemJointContract) stack.getItem();
							if (this.isOwnerId(contract.getOwner(stack))) {
								this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.joint_ownership_added", this.getName(), player.getName()));
								for (UUID ownerId : this.jointOwners) {
									try {
										this.world.getPlayerEntityByUUID(ownerId).sendMessage(new TextComponentTranslation("command.kagic.joint_ownership_added", this.getName(), player.getName()));
									}
									catch (Exception e) {
										// Failed.
									}
								}
								this.jointOwners.add(EntityPlayer.getUUID(player.getGameProfile()));
								this.playObeySound();
								player.sendMessage(new TextComponentTranslation("command.kagic.now_serves_you", this.getName()));
								if (!player.capabilities.isCreativeMode) {
									stack.shrink(1);
								}
								return true;
							}
							else {
								player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
								return true;
							}
						}
					}
				}
				else if (stack.getItem() == ModItems.LIBERATION_CONTRACT) {
					if (this.isTamed()) {
						ItemLiberationContract contract = (ItemLiberationContract) stack.getItem();
						if (this.isOwnerId(contract.getOwner(stack))) {
							if (contract.getOwner(stack).equals(this.getOwnerId())) {
								this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.gem_liberated_by", this.getName(), player.getName()));
							}
							else {
								for (UUID ownerId : this.jointOwners) {
									if (contract.getOwner(stack).equals(ownerId)) {
										try {
											this.world.getPlayerEntityByUUID(EntityPlayer.getUUID(player.getGameProfile())).sendMessage(new TextComponentTranslation("command.kagic.gem_liberated_by", this.getName(), player.getName()));
										}
										catch (Exception e) {
											// Failed.
										}
										ownerId = EntityPlayer.getUUID(player.getGameProfile());
									}
								}
							}
							this.playObeySound();
							player.sendMessage(new TextComponentTranslation("command.kagic.gem_liberated", this.getName()));
							if (!player.capabilities.isCreativeMode) {
								stack.shrink(1);
							}
							this.setServitude(EntityGem.SERVE_NONE);
							this.setOwnerId((UUID) null);
							this.setLeader((UUID) null);
							return true;
						}
						else {
							player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
							return true;
						}
					}
				}
				else if (stack.getItem() == ModItems.AUTONOMY_CONTRACT) {
					if (this.isTamed()) {
						ItemAutonomyContract contract = (ItemAutonomyContract) stack.getItem();
						if (this.isOwnerId(contract.getOwner(stack))) {
							if (contract.getOwner(stack).equals(this.getOwnerId())) {
								if (this.leader.equals(this.getOwnerId())) {
									this.setLeader(player);
								}
								this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.gem_autonomous_by", this.getName(), player.getName()));
							}
							else {
								for (UUID ownerId : this.jointOwners) {
									if (contract.getOwner(stack).equals(ownerId)) {
										if (this.leader.equals(this.getOwnerId())) {
											this.setLeader(player);
										}
										try {
											this.world.getPlayerEntityByUUID(EntityPlayer.getUUID(player.getGameProfile())).sendMessage(new TextComponentTranslation("command.kagic.gem_liberated_by", this.getName(), player.getName()));
										}
										catch (Exception e) {
											// Failed.
										}
										ownerId = EntityPlayer.getUUID(player.getGameProfile());
									}
								}
							}
							this.playObeySound();
							player.sendMessage(new TextComponentTranslation("command.kagic.gem_autonomous", this.getName()));
							if (!player.capabilities.isCreativeMode) {
								stack.shrink(1);
							}
							this.setOwnerId((UUID) null);
							this.setLeader((UUID) null);
							this.setServitude(EntityGem.SERVE_ITSELF);
							return true;
						}
						else {
							player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
							return true;
						}
					}
				}
				else if (stack.getItem() == ModItems.WAR_DECLARATION) {
					if (this.isTamed()) {
						ItemWarDeclaration contract = (ItemWarDeclaration) stack.getItem();
						if (this.isOwnerId(contract.getOwner(stack))) {
							if (contract.getOwner(stack).equals(this.getOwnerId())) {
								if (this.leader.equals(this.getOwnerId())) {
									this.setLeader(player);
								}
								this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.gem_war_by", this.getName(), player.getName()));
							}
							else {
								for (UUID ownerId : this.jointOwners) {
									if (contract.getOwner(stack).equals(ownerId)) {
										if (this.leader.equals(this.getOwnerId())) {
											this.setLeader(player);
										}
										try {
											this.world.getPlayerEntityByUUID(EntityPlayer.getUUID(player.getGameProfile())).sendMessage(new TextComponentTranslation("command.kagic.gem_war_by", this.getName(), player.getName()));
										}
										catch (Exception e) {
											// Failed.
										}
										ownerId = EntityPlayer.getUUID(player.getGameProfile());
									}
								}
							}
							this.playObeySound();
							player.sendMessage(new TextComponentTranslation("command.kagic.gem_war", this.getName()));
							if (!player.capabilities.isCreativeMode) {
								stack.shrink(1);
							}
							this.setKillsPlayers(true);
							return true;
						}
						else {
							player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
							return true;
						}
					}
				}
				else if (stack.getItem() == ModItems.PEACE_TREATY) {
					if (this.isTamed()) {
						ItemPeaceTreaty contract = (ItemPeaceTreaty) stack.getItem();
						if (this.isOwnerId(contract.getOwner(stack))) {
							if (contract.getOwner(stack).equals(this.getOwnerId())) {
								if (this.leader.equals(this.getOwnerId())) {
									this.setLeader(player);
								}
								this.getOwner().sendMessage(new TextComponentTranslation("command.kagic.peace_treaty_by", this.getName(), player.getName()));
							}
							else {
								for (UUID ownerId : this.jointOwners) {
									if (contract.getOwner(stack).equals(ownerId)) {
										if (this.leader.equals(this.getOwnerId())) {
											this.setLeader(player);
										}
										try {
											this.world.getPlayerEntityByUUID(EntityPlayer.getUUID(player.getGameProfile())).sendMessage(new TextComponentTranslation("command.kagic.peace_treaty_by", this.getName(), player.getName()));
										}
										catch (Exception e) {
											// Failed.
										}
										ownerId = EntityPlayer.getUUID(player.getGameProfile());
									}
								}
							}
							this.playObeySound();
							player.sendMessage(new TextComponentTranslation("command.kagic.peace_treaty", this.getName()));
							if (!player.capabilities.isCreativeMode) {
								stack.shrink(1);
							}
							this.setKillsPlayers(false);
							return true;
						}
						else {
							player.sendMessage(new TextComponentTranslation("command.kagic.does_not_serve_signee", this.getName()));
							return true;
						}
					}
				}
				else if (DyeUtils.isDye(stack) && this.canChangeUniformColorByDefault() && player.isSneaking()) {
					if (this.isTamed() && this.isOwner(player)) {
						this.setUniformColor(DyeUtils.metaFromStack(stack).orElse(0));
						this.uniformColorChanged = true;
						return true;
					}
				}
				else if (DyeUtils.isDye(stack) && this.canChangeInsigniaColorByDefault()) {
					if (this.isTamed()) {
						if (this.isOwner(player)) {
							this.setInsigniaColor(DyeUtils.metaFromStack(stack).orElse(0));
							return true;
						}
					}
				}
				else if (this.isSoldier) {
					return super.processInteract(player, hand) || this.setAttackWeapon(player, hand, stack);
				}
			}
			else {
				ItemStack stack = player.getHeldItemOffhand();
				if (stack.getItem() == ModItems.COMMANDER_STAFF) {
					if (this.isTamed()) {
						if (this.isOwner(player) && this.getHeldItemMainhand().isEmpty()) {
							this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack.copy());
							if (!player.capabilities.isCreativeMode) {
								stack.shrink(1);
							}
						}
					}
				}
				else if (stack.getItem() == ModItems.GEM_STAFF) {
					if (this.isTamed()) {
						if (this.isOwner(player) && player.isSneaking()) {
							this.setSelected(!this.isSelected());
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
	}
	
	public boolean alternateInteract(EntityPlayer player) {
		KAGIC.instance.chatInfoMessage("This gem is " + this.getCachedUniqueIdString());
		KAGIC.instance.chatInfoMessage("Owner is " + this.getOwnerId());
		KAGIC.instance.chatInfoMessage("Max health is " + this.getMaxHealth() + ", defective is " + this.isDefective() + ", and prime is " + this.isPrimary());
		KAGIC.instance.chatInfoMessage("Cut is " + this.getGemCut() + " and Placement is " + this.getGemPlacement());
		KAGIC.instance.chatInfoMessage("skinColor is " + this.getSkinColor());
		KAGIC.instance.chatInfoMessage("hairColor is " + this.getHairColor());
		KAGIC.instance.chatInfoMessage("Hair style is " + this.getHairStyle());
		KAGIC.instance.chatInfoMessage("Insignia color is " + this.getInsigniaColor());
		KAGIC.instance.chatInfoMessage("Uniform color is " + this.getUniformColor());
		KAGIC.instance.chatInfoMessage("Size is " + this.width + ", " + this.height);
		KAGIC.instance.chatInfoMessage("Pitch is " + this.pitch);
		return false;
	}
	
	public boolean setAttackWeapon(EntityPlayer player, EnumHand hand, ItemStack stack) {
		if (this.isFusion()) return false;
		if (this.isTamed()) {
			if (this.isOwner(player)) {
				boolean toolChanged = true;
				if (!this.isCoreItem(stack) && (stack.isEmpty() || (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemBow))) {
					if (!this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isItemEqualIgnoreDurability(stack)) {
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND), 0.0F);
					}
					else {
						toolChanged = false;
					}
					if (toolChanged) {
						ItemStack heldItem = stack.copy();
						this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
						this.playObeySound();
						this.setAttackAI();
						if (!player.capabilities.isCreativeMode) {
							stack.shrink(1);
						}
					}
					return true;
				}
				else if (stack.getItem() instanceof ItemArrow || stack.getItem() instanceof ItemShield) {
					if (!this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isItemEqualIgnoreDurability(stack)) {
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND), 0.0F);
					}
					else {
						toolChanged = false;
					}
					if (toolChanged) {
						ItemStack heldItem = stack.copy();
						this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem);
						this.playObeySound();
						this.setAttackAI();
						if (!player.capabilities.isCreativeMode) {
							stack.shrink(1);
						}
					}
				}
			}
		}
		return false;
	}
	
	public void setFusionWeapon(ItemStack weapon) {
		if (!this.isFusion()) return;
		if (weapon.getItem() instanceof ItemSword || weapon.getItem() instanceof ItemTool || weapon.getItem() instanceof ItemBow) {
			ItemStack heldItem = weapon.copy();
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
		}
		else if (weapon.getItem() instanceof ItemArrow || weapon.getItem() instanceof ItemShield) {
			ItemStack heldItem = weapon.copy();
			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem);
		}
		this.setAttackAI();
	}
	
	public boolean isPlayerLookingAt(EntityPlayer player) {
		Vec3d raycast = player.getLook(1.0F).normalize();
		Vec3d view = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + (double) this.getEyeHeight() - (player.posY + (double) player.getEyeHeight()), this.posZ - player.posZ);
		double length = view.lengthVector();
		view = view.normalize();
		double product = raycast.dotProduct(view);
		return product > 1.0D - 0.025D / length ? player.canEntityBeSeen(this) : false;
	}
	
	public void warp(EntityPlayer player, String destination) {
		TileEntityWarpPadCore pad = TileEntityWarpPadCore.getEntityPad(this);
		if (pad != null && pad.isValidPad() && !pad.isWarping()) {
			if (!pad.isValid()) {
				this.talkTo(player, new TextComponentTranslation("notify.kagic.padnotvalid").getUnformattedComponentText());
				return;
			}
			if (!pad.isClear()) {
				this.talkTo(player, new TextComponentTranslation("notify.kagic.padnotclear").getUnformattedComponentText());
				return;
			}
			if (pad.name.toLowerCase() == destination) {
				this.talkTo(player, new TextComponentTranslation("notify.kagic.alreadyhere").getUnformattedComponentText());
				return;
			}
			
			if (pad instanceof TileEntityGalaxyPadCore) {
				Map<GalaxyPadLocation, WarpPadDataEntry> padData = WorldDataGalaxyPad.get(this.world).getGalaxyPadData();
				SortedMap<Double, GalaxyPadLocation> sortedPoses = WorldDataGalaxyPad.getSortedPositions(padData, pad.getPos());
				Iterator<GalaxyPadLocation> it = sortedPoses.values().iterator();
				while (it.hasNext()) {
					GalaxyPadLocation dest = (GalaxyPadLocation) it.next();
					WarpPadDataEntry data = padData.get(dest);
					if (data.name.toLowerCase().equals(destination)) {
						if (!data.valid) {
							this.talkTo(player, new TextComponentTranslation("notify.kagic.destnotvalid").getUnformattedComponentText());
							return;
						}
						if (!data.clear) {
							this.talkTo(player, new TextComponentTranslation("notify.kagic.destnotclear").getUnformattedComponentText());
							return;
						}
						this.talkTo(player, new TextComponentTranslation("notify.kagic.warping", data.name).getUnformattedComponentText());
						this.playObeySound();
						((TileEntityGalaxyPadCore) pad).beginWarp(dest);
						return;
					}
				}
			} else {
				Map<BlockPos, WarpPadDataEntry> padData = WorldDataWarpPad.get(this.world).getWarpPadData();
				SortedMap<Double, BlockPos> sortedPoses = WorldDataWarpPad.getSortedPositions(padData, pad.getPos());
				Iterator<BlockPos> it = sortedPoses.values().iterator();
				while (it.hasNext()) {
					BlockPos pos = (BlockPos) it.next();
					WarpPadDataEntry data = padData.get(pos);
					if (data.name.toLowerCase().equals(destination)) {
						TileEntityWarpPadCore dest = (TileEntityWarpPadCore) this.getEntityWorld().getTileEntity(pos);
						if (!dest.isValid()) {
							this.talkTo(player, new TextComponentTranslation("notify.kagic.destnotvalid").getUnformattedComponentText());
							return;
						}
						if (!dest.isClear()) {
							this.talkTo(player, new TextComponentTranslation("notify.kagic.destnotclear").getUnformattedComponentText());
							return;
						}
						this.talkTo(player, new TextComponentTranslation("notify.kagic.warping", data.name).getUnformattedComponentText());
						this.playObeySound();
						pad.beginWarp(pos);
						return;
					}
				}
			}
			this.talkTo(player, new TextComponentTranslation("notify.kagic.nopad", destination).getUnformattedComponentText());
		}
	}
	
	public void talkTo(EntityPlayer player, String message) {
		player.sendMessage(new TextComponentString("<" + this.getName() + "> " + message));
	}
	
	public boolean canPickUpItem(Item itemIn) {
		return false;
	}
	
	public boolean canBeLeashedTo(EntityPlayer player) {
		return this.isTamed() && this.isOwner(player);
	}
	
	public boolean isCoreItem(ItemStack stack) {
		if (stack.getItem() == ModItems.CRACKED_BLUE_DIAMOND_GEM || stack.getItem() == ModItems.CRACKED_YELLOW_DIAMOND_GEM || stack.getItem() == ModItems.BLUE_DIAMOND_GEM || stack.getItem() == ModItems.YELLOW_DIAMOND_GEM) {
			return true;
		}
		else if (DyeUtils.isDye(stack) && (this.canChangeInsigniaColorByDefault() || this.canChangeUniformColorByDefault())) {
			return true;
		}
		else if (stack.getItem() == ModItems.GEM_STAFF) {
			return true;
		}
		else if (stack.getItem() == ModItems.COMMANDER_STAFF) {
			return true;
		}
		else if (stack.getItem() == ModItems.TRANSFER_CONTRACT) {
			return true;
		}
		else if (stack.getItem() == ModItems.JOINT_CONTRACT) {
			return true;
		}
		else if (stack.getItem() == ModItems.AUTONOMY_CONTRACT) {
			return true;
		}
		else if (stack.getItem() == ModItems.LIBERATION_CONTRACT) {
			return true;
		}
		else if (stack.getItem() == ModItems.WAR_DECLARATION) {
			return true;
		}
		else if (stack.getItem() == ModItems.PEACE_TREATY) {
			return true;
		}
		else if (stack.getItem() == Items.NAME_TAG) {
			return true;
		}
		else if (stack.getItem() == Items.LEAD) {
			return true;
		}
		else if (stack.getItem() == Items.WATER_BUCKET) {
			return true;
		}
		else if (stack.getItem() == Items.BUCKET) {
			return true;
		}
		return false;
	}
	
	public int getGemColor() {
		return this.dataManager.get(GEM_COLOR);
	}
	public void setGemColor(int color) {
		this.dataManager.set(GEM_COLOR, color);
	}
	
	public void setCutPlacement(GemCuts cut, GemPlacements placement) {
		if (this.cutPlacements.containsKey(cut)) {
			this.cutPlacements.get(cut).add(placement);
		} else {
			ArrayList<GemPlacements> placements = new ArrayList<GemPlacements>();
			placements.add(placement);
			this.cutPlacements.put(cut, placements);
		}
	}
	
	public boolean isCorrectCutPlacement() {
		if (this.cutPlacements.containsKey(this.getGemCut())) {
			if (this.cutPlacements.get(this.getGemCut()).contains(this.getGemPlacement())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isGemPlacementDefined() {
		return this.getGemPlacement() != GemPlacements.UNKNOWN;
	}

	
	public boolean isGemCutDefined() {
		return this.getGemCut() != GemCuts.UNKNOWN;
	}
	public GemCuts getGemCut() {
		//Must add 1 because initial value is UNKNOWN, which is stored in dataManager as -1
		return GemCuts.values()[this.dataManager.get(GEM_CUT) + 1];
	}
	
	public void setGemCut(int gemCut) {
		this.dataManager.set(GEM_CUT, gemCut);
	}
	
	public GemPlacements getGemPlacement() {
		//Must add 1 because initial value is UNKNOWN, which is stored in dataManager as -1
		return GemPlacements.values()[this.dataManager.get(GEM_PLACEMENT) + 1];
	}
	
	public void setGemPlacement(int gemPlacement) {
		this.dataManager.set(GEM_PLACEMENT, gemPlacement);
	}
	
	public void convertGems(int placement) {
		
	}
	
	public void itemDataToGemData(int data) {
		this.setSpecial(data);
	}
	
	@SuppressWarnings("incomplete-switch")
	public void applyGemPlacementBuffs(boolean initialSpawn) {
		switch (this.getGemPlacement()) {
		case FOREHEAD:
			this.tasks.addTask(2, new EntityAIPredictFights(this, 0.9D));
			break;
		case MOUTH:
			this.canTalk = false;
			break;
		case LEFT_HAND:
			if (initialSpawn) {
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() * 2.0D);
			}
			break;
		case RIGHT_HAND:
			if (initialSpawn) {
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() * 2.0D);
			}
			break;
		case BACK:
			this.tasks.addTask(2, new EntityAIPanic(this, 0.9D));
			break;
		case CHEST:
			if (initialSpawn) {
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 1.25D);
				this.setHealth(this.getMaxHealth());
			}
			break;
		case BELLY:
			if (this.fallbackServitude == -1 && this.rand.nextInt(8) == 0 && ModConfigs.canRebel) {
				this.fallbackServitude = EntityGem.SERVE_REBELLION;
			}
			else {
				this.fallbackServitude = 0;
			}
			break;
		case RIGHT_KNEE:
			this.setAIMoveSpeed(this.getAIMoveSpeed() * 1.2F);
			break;
		case LEFT_KNEE:
			this.setAIMoveSpeed(this.getAIMoveSpeed() * 1.2F);
			break;
		}
	}
	
	public boolean hasVisor() {
		return this.dataManager.get(VISOR);
	}
	
	public void setHasVisor(boolean hasVisor) {
		this.dataManager.set(VISOR, hasVisor);
	}
	
	public int getInsigniaColor() {
		return this.dataManager.get(INSIGNIA_COLOR);
	}
	
	public void setInsigniaColor(int insigniaColor) {
		this.dataManager.set(INSIGNIA_COLOR, insigniaColor);
	}
	
	public int getUniformColor() {
		return this.dataManager.get(UNIFORM_COLOR);
	}
	
	public void setUniformColor(int uniformColor) {
		this.dataManager.set(UNIFORM_COLOR, uniformColor);
	}
	
	public int getSkinColor() {
		return this.dataManager.get(SKIN_COLOR);
	}
	
	public void setSkinColor(int skinColor) {
		this.dataManager.set(SKIN_COLOR, skinColor);
	}

	public int getHairStyle() {
		return this.dataManager.get(HAIR);
	}
	
	public void setHairStyle(int hairstyle) {
		this.dataManager.set(HAIR, hairstyle);
	}
	
	public int getHairColor() {
		return this.dataManager.get(HAIR_COLOR);
	}
	
	public void setHairColor(int hairColor) {
		this.dataManager.set(HAIR_COLOR, hairColor);
	}

	public boolean isDefective() {
		return this.dataManager.get(DEFECTIVE);
	}
	
	public void setDefective(boolean defective) {
		this.dataManager.set(DEFECTIVE, defective);
		if (defective) {
			this.whenDefective();
		}
	}
	
	public void whenDefective() {
		
	}
	
	public boolean isPrimary() {
		return this.dataManager.get(PRIMARY);
	}
	
	public void setPrimary(boolean primary) {
		this.dataManager.set(PRIMARY, primary);
		if (primary) {
			this.whenPrimary();
		}
	}
	
	public void whenPrimary() {
		
	}
	
	public void seePastDoors() {
		((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
		((PathNavigateGround) this.getNavigator()).setEnterDoors(true);
	}
	
	public int getSpecial() {
		return this.dataManager.get(SPECIAL);
	}
	
	protected void setSpecial(int special) {
		this.dataManager.set(SPECIAL, special);
	}
	
	public boolean isTamed() {
		return this.getOwnerId() != null || this.servitude > SERVE_HUMAN || this.isDiamond;
	}
	
	public boolean isSitting() {
		return this.isSitting;
	}
	
	public void setSitting(EntityLivingBase player, boolean isSitting) {
		if (player instanceof EntityPlayer) {
			if (!isSitting) {
				player.sendMessage(new TextComponentTranslation("command.kagic.will_follow_you", this.getName()));
				this.isSitting = false;
				this.restPosition = null;
				this.setLeader(player);
			}
			else {
				player.sendMessage(new TextComponentTranslation("command.kagic.will_not_follow_you", this.getName()));
				this.isSitting = true;
				this.restPosition = this.getPosition();
			}
			this.followingGem = false;
		}
		else {
			this.isSitting = false;
			this.restPosition = null;
			this.followingGem = true;
		}
	}
	public void setOwnerId(UUID ownerId) {
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(ownerId));
	}
	
	public void setOwnerId(String ownerId) {
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(UUID.fromString(ownerId)));
	}
	
	public UUID getOwnerId() {
		return (UUID) ((Optional<UUID>) this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
	}
	
	public void setGemId(UUID gemId) {
		this.dataManager.set(GEUUID, Optional.fromNullable(gemId));
	}
	public void setGemId(String gemId) {
		this.dataManager.set(GEUUID, Optional.fromNullable(UUID.fromString(gemId)));
	}
	public UUID getGemId() {
		return (UUID) ((Optional<UUID>) this.dataManager.get(GEUUID)).orNull();
	}
	
	public EntityPlayer getOwner() {
		EntityPlayer owner = null;
		double distance = Double.MAX_VALUE;
		for (EntityPlayer playerIn : this.world.playerEntities) {
			if (EntityPlayer.getUUID(playerIn.getGameProfile()).equals(this.getOwnerId())) {
				if (this.getDistanceSq(playerIn.getPosition()) <= distance) {
					distance = this.getDistanceSq(playerIn.getPosition());
					owner = playerIn;
				}
			}
			else {
				for (UUID ownerId : this.jointOwners) {
					if (EntityPlayer.getUUID(playerIn.getGameProfile()).equals(ownerId)) {
						if (this.getDistanceSq(playerIn.getPosition()) <= distance) {
							distance = this.getDistanceSq(playerIn.getPosition());
							owner = playerIn;
						}
					}
				}
			}
		}
		return owner;
	}
	
	public UUID getLeader() {
		return this.leader;
	}
	
	public EntityPlayer getLeaderEntity() {
		try {
			return this.world.getPlayerEntityByUUID(this.leader);
		}
		catch (Exception e) {
			return null;
		}  
	}
	
	public void setLeader(EntityLivingBase entity) {
		try {
			EntityPlayer player = (EntityPlayer) entity;
			if (this.leader != null && !this.leader.equals(EntityPlayer.getUUID(player.getGameProfile()))) {
				EntityPlayer oldLeader = this.world.getPlayerEntityByUUID(this.leader);
				oldLeader.sendMessage(new TextComponentTranslation("command.kagic.following_someone_else", this.getName(), player.getName()));
			}
			this.leader = EntityPlayer.getUUID(player.getGameProfile());
		}
		catch (Exception e) {
			this.leader = null;
		}
	}
	
	public void setLeader(UUID newLeader) {
		this.leader = newLeader; 	
	}
	
	public void resetLeader() {
		this.leader = null;
	}
	
	public void setSelected(boolean selected) {
		this.dataManager.set(SELECTED, selected);
	}
	
	public boolean isSelected() {
		return this.dataManager.get(SELECTED);
	}
	
	public BlockPos getRestPosition() {
		return this.restPosition;
	}
	
	public void setRestPosition(BlockPos pos) {
		this.restPosition = pos;
	}
	public boolean spokenTo(EntityPlayer player, String message) {
		if (this.isTamed() && this.isOwner(player)) {
			return super.spokenTo(player, message);
		}
		return false;
	}
	public boolean isOwner(EntityLivingBase entityIn) {
		if (entityIn != null) {
			if (this.servitude == EntityGem.SERVE_HUMAN) {
				if (entityIn instanceof EntityPlayer) {
					EntityPlayer playerIn = (EntityPlayer) entityIn;
					if (playerIn.getUniqueID().equals(this.getOwnerId())) {
						return true;
					}
					else {
						for (UUID ownerId : this.jointOwners) {
							if (playerIn.getUniqueID().equals(ownerId)) {
								return true;
							}
						}
					}
				}
				if (entityIn.getHeldItemMainhand().getItem() == ModItems.COMMANDER_STAFF) {
					return this.isOwnerId(ModItems.COMMANDER_STAFF.getOwner(entityIn.getHeldItemMainhand()));
				}
			}
			else if (this.servitude == EntityGem.SERVE_BLUE_DIAMOND) {
				return entityIn instanceof EntityBlueDiamond;
			}
			else if (this.servitude == EntityGem.SERVE_YELLOW_DIAMOND) {
				return entityIn instanceof EntityYellowDiamond;
			}
		}
		return false;
	}
	
	public boolean isOwnerId(UUID uuid) {
		if (uuid != null) {
			if (uuid.equals(this.getOwnerId())) {
				return true;
			}
			else {
				for (UUID ownerId : this.jointOwners) {
					if (uuid.equals(ownerId)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public EntityAIStay getAIStay() {
		return this.stayAI;
	}
	
	public int getFusionCount() {
		return this.dataManager.get(FUSION_COUNT);
	}
	
	public void setFusionCount(int count) {
		this.dataManager.set(FUSION_COUNT, count);
	}
	
	public String getFusionPlacements() {
		return this.dataManager.get(FUSION_PLACEMENTS);
	}
	
	public void setFusionPlacements(String fusionPlacements) {
		this.dataManager.set(FUSION_PLACEMENTS, fusionPlacements);
	}
	
	public String generateFusionPlacements() {
		String fusionPlacements = "";
		for (int i = 0; i < this.fusionMembers.size(); ++i) {
			if (i > 0) {
				fusionPlacements += " " + this.fusionMembers.get(i).getInteger("gemPlacement") + "_" + this.fusionMembers.get(i).getInteger("gemCut");
			}
			else {
				fusionPlacements += this.fusionMembers.get(i).getInteger("gemPlacement") + "_" + this.fusionMembers.get(i).getInteger("gemCut");
			}
		}
		this.setFusionPlacements(fusionPlacements);
		return fusionPlacements;
	}
	
	public boolean canFuse() {
		return (this.isFusion() || (!this.isFusion() && this.ticksExisted > 40)) && !this.isDefective() && this.getFusionCount() < 10;
	}
	
	public void unfuse() {
		this.playSound(ModSounds.GEM_POOF, 3.0F, 1.0F);
	}
	
	public boolean isFusion() {
		return this.getFusionCount() > 1;
	}
	/**
	 * Creates a Kindergarten style name based on info provided from "Back to the Kindergarten"
	 * Facet is generated by adding chunk coordinates, dividing by 7 (the size of most ravines in chunks), 
	 * 		and encoding into Base36 (0-9-A-Z),
	 * Cut is generated by taking X, Z, and Y coordinates.
	 * 	x-coordinate % 10 is your first number (every gem has a number as the first part of cut.)
	 * 	z-coordinate % 26 to Base26 (every gem has a letter as the second part of cut.)
	 * 	y-coordinate % 26 to Base26 (Peridot said it starts from Z bottom to A top.)
	 * 
	 * @param pos the created position.
	 * @return the created name.
	 */
	public String generateSpecificName(BlockPos pos) {
		if (this.isFusion()) {
			return "Fusion";
		}
		else {
			int x = pos.getX(); int y = pos.getY(); int z = pos.getZ();
			String cutX = Integer.toString(Math.abs((x%10))).toUpperCase();
			String cutY = Integer.toString(Math.abs((y/4)%36),36).toUpperCase();
			String cutZ = Integer.toString(Math.abs((z%26)+10),36).toUpperCase();
			String face = Integer.toString(Math.abs(((x/16)+(z/16))/3),36).toUpperCase();
			String cut = "Cut " + (cutX+cutZ+cutY);
			return "Facet " + face + " " + cut;
		}
	}
	public String getFacet() {
		String[] s = this.getSpecificName().split(" ");
		if (s.length == 4) {
			return s[1];
		}
		return null;
	}
	public String getCut() {
		String[] s = this.getSpecificName().split(" ");
		if (s.length == 4) {
			return s[3];
		}
		return null;
	}
	
	@Override
	public void setCallibleNames(ArrayList<String> list) {
		super.setCallibleNames(list);
		list.add(this.getFacet());
		list.add(this.getCut());
	}

	/*********************************************************
	 * Methods related to entity combat.					 *
	 *********************************************************/
	public boolean shouldAttackEntity(EntityLivingBase attacker, EntityLivingBase victim) {
		return !this.isOnSameTeam(victim);
	}
	
	public void setAttackAI() {
		this.tasks.removeTask(this.rangedAttack);
		this.tasks.removeTask(this.meleeAttack);
		if (this.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow) {
			this.tasks.addTask(1, this.rangedAttack);
		}
		else {
			this.tasks.addTask(1, this.meleeAttack);
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.world.isRemote) {
			if (this.isDead || this.getHealth() <= 0.0F) {
				return false;
			}
			else if (this.isSpaceBorn) {
				return false;
			}
			else if (this.isPeaceful) {
				this.isPeaceful = false;
				return false;
			} else if (source.damageType.equals("Vacuum") || source.damageType.equals("oxygen_suffocation")) {
				// Gems don't need to breathe
				return false;
			}
			else if (source == DamageSource.IN_WALL) {
				this.jump();
				return true;
			}
			else {
				switch (this.dimensionOfCreation) {
				case -1:
					if (source.isExplosion() || source.isFireDamage()) {
						return false;
					}
					break;
				case 1:
					if (source.isMagicDamage() || source.isProjectile()) {
						return false;
					}
					break;
				}
				if (source.getTrueSource() instanceof EntityLivingBase) {
					EntityLivingBase attacker = (EntityLivingBase) source.getTrueSource();
					ItemStack heldItem = attacker.getHeldItemMainhand();
					if (heldItem.isItemEnchanted()) {
						NBTTagList enchantments = heldItem.getEnchantmentTagList();
						for (int i = 0; i < enchantments.tagCount(); i++) {
							if (enchantments.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(ModEnchantments.FAIR_FIGHT)) {
								if (this.isFusion()) {
									this.unfuse();
								}
								else {
									this.attackEntityFrom(new PoofDamage(), this.getMaxHealth());
								}
							}
						}
					}
					else if (heldItem.getItem() == ModItems.GEM_STAFF || heldItem.getItem() == ModItems.COMMANDER_STAFF) {
						if (this.isOwner(attacker)) {
							if (this.isFusion()) {
								this.unfuse();
							}
							else {
								this.attackEntityFrom(new PoofDamage(), this.getMaxHealth());
							}
						}
					}
				}
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	
	public boolean attackEntityAsMob(Entity entityIn) {
		float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int i = 0;
		if (entityIn instanceof EntityLivingBase) {
			f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entityIn).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		this.swingArm(EnumHand.MAIN_HAND);
		if (flag) {
			if (i > 0 && entityIn instanceof EntityLivingBase) {
				((EntityLivingBase) entityIn).knockBack(this, (float) i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}
			int j = EnchantmentHelper.getFireAspectModifier(this);
			if (j > 0) {
				entityIn.setFire(j * 4);
			}
			if (entityIn instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer)entityIn;
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;
				if (itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD) {
					float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
					if (this.rand.nextFloat() < f1) {
						entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.world.setEntityState(entityplayer, (byte)30);
					}
				}
			}
			this.applyEnchantments(this, entityIn);
		}
		return flag;
	}
	
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityTippedArrow arrow = new EntityTippedArrow(this.world, this);
		double distanceFromTargetX = target.posX - this.posX;
		double distanceFromTargetY = target.getEntityBoundingBox().minY + (double)(target.height) - arrow.posY;
		double distanceFromTargetZ = target.posZ - this.posZ;
		double distanceFromTargetS = (double) MathHelper.sqrt(distanceFromTargetX * distanceFromTargetX + distanceFromTargetY * distanceFromTargetY);
		arrow.shoot(distanceFromTargetX, distanceFromTargetY + distanceFromTargetS * 0.20000000298023224D, distanceFromTargetZ, 1.6F, 0.0F);
		arrow.setDamage(distanceFactor * 2.0D + this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + this.rand.nextGaussian() * 0.25D);
		int power = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
		int punch = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
		boolean flame = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0;
		if (power > 0) {
			arrow.setDamage(arrow.getDamage() + (double) power * 0.5D + 0.5D);
		}
		if (punch > 0) {
			arrow.setKnockbackStrength(punch);
		}
		if (flame) {
			arrow.setFire(100);
		}
		ItemStack itemstack = this.getHeldItem(EnumHand.OFF_HAND);
		if (itemstack.getItem() == Items.TIPPED_ARROW) {
			arrow.setPotionEffect(itemstack);
		}
		this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.world.spawnEntity(arrow);
	}
	
	//Was moveEntityWithHeading in 1.11
	public void travel(float strafe, float up, float forward) {
		if (this.getGemPlacement() == GemPlacements.NOSE) {
			if (this.getAttackTarget() != null || this.getRevengeTarget() != null) {
				forward *= 2.0;
				strafe *= 2.0;
			}
		}
		if (this.dimensionOfCreation == 1) {
			if (up > 0) {
				up *= 2.0;
			}
			else {
				up *= 0.5;
			}
		}
		super.travel(strafe, up, forward);
	}
	
	public void fall(float distance, float damageMultiplier) {
		if (this.isSpaceBorn) {
			if (this.rand.nextBoolean() && ModConfigs.canRebel) {
				this.fallbackServitude = EntityGem.SERVE_YELLOW_DIAMOND;
			}
			if (this.world.getGameRules().getBoolean("mobGriefing")) {
				this.world.newExplosion(this, this.posX, this.posY, this.posZ, this.height + this.width, true, true);
				this.isSpaceBorn = false;
			}
			else if (this.isSpaceBorn) {
				for (int i = 0; i < this.rand.nextInt(7) + 7; ++i) {
					this.createFireworks();
				}
				this.isSpaceBorn = false;
			}
		}
	}
	
 	@SideOnly(Side.CLIENT)
	public boolean isSwingingArms() {
 		return this.dataManager.get(SWINGING_ARMS);
	}
	public void setSwingingArms(boolean swingingArms) {
		this.dataManager.set(SWINGING_ARMS, swingingArms);
	}
	
	public boolean isOwnedBySamePeople(EntityGem gem) {
		if (gem != null) {
			return this.isOwnedById(gem.getOwnerId()) && gem.isOwnedById(this.getOwnerId());
		}
		return false;
	}
	
	public boolean isOwnedBy(EntityLivingBase possibleOwner) {
		if (possibleOwner != null) {
			return this.isOwnedById(possibleOwner.getUniqueID());
		}
		return false;
	}
	
	public boolean isOwnedById(UUID possibleOwner) {
		UUID owner = this.getOwnerId();
		if (possibleOwner != null && owner != null) {
			boolean match = owner.equals(possibleOwner);
			for (int i = 0; i < this.jointOwners.size() && match; ++i) {
				match = this.getOwnerId().equals(this.jointOwners.get(i));
			}
			return match;
		}
		return false;
	}
	
	public Team getTeam() {
		if (this.isTamed()) {
			EntityLivingBase entitylivingbase = this.getOwner();
			if (entitylivingbase != null) {
				return entitylivingbase.getTeam();
			}
		}
		return super.getTeam();
	}
	
	public boolean isOnSameTeam(Entity entity) {
		if (entity != null) {
			if (entity instanceof EntityGem) {
				EntityGem gem = (EntityGem) entity;
				if (gem.getServitude() == this.getServitude()) {
					if (gem.getServitude() == SERVE_HUMAN) {
						if (gem.getOwnerId().equals(this.getOwnerId())) {
							return true;
						}
						return false;
					}
					return true;
				}
			}
			else if (this.isTamed()) {
				EntityLivingBase entitylivingbase = this.getOwner();
				if (entity.equals(entitylivingbase)) {
					return true;
				}
				if (entitylivingbase != null) {
					return entitylivingbase.isOnSameTeam(entity);
				}
			}
			return super.isOnSameTeam(entity);
		}
		return false;
	}
	
	public int getServitude() {
		return this.servitude;
	}
	
	public void setServitude(int servitude) {
		this.servitude = servitude;
		if (!this.isDiamond) {
			switch (this.servitude) {
			case EntityGem.SERVE_YELLOW_DIAMOND:
				this.targetTasks.addTask(2, this.diamondAttackAI);
				this.setInsigniaColor(4);
				this.setUniformColor(14);
				break;
			case EntityGem.SERVE_BLUE_DIAMOND:
				this.targetTasks.addTask(2, this.diamondAttackAI);
				this.setInsigniaColor(11);
				this.setUniformColor(11);
				break;
			case EntityGem.SERVE_WHITE_DIAMOND:
				this.targetTasks.addTask(2, this.diamondAttackAI);
				this.setInsigniaColor(0);
				this.setUniformColor(0);
				break;
			case EntityGem.SERVE_REBELLION:
				this.targetTasks.addTask(2, this.rebelAttackAI);
				this.setInsigniaColor(6);
				this.setUniformColor(6);
				break;
			}
		}
	}
	
	public boolean killsPlayers() {
		return this.killsPlayers;
	}
	
	public void setKillsPlayers(boolean killsPlayers) {
		if (killsPlayers) {
			this.targetTasks.addTask(1, this.warDeclarationAI);
			this.killsPlayers = true;
		}
		else {
			this.targetTasks.removeTask(this.warDeclarationAI);
			this.killsPlayers = false;
		}
	}
	
	public boolean isTraitor() {
		return this.fallbackServitude != this.servitude && this.fallbackServitude > 0 && ModConfigs.canRebel;
	}
	
	public int getDimensionOfCreation() {
		return this.dimensionOfCreation;
	}
	
	public void setDimensionOfCreation(int dimension) {
		this.dimensionOfCreation = dimension;
		if (dimension == -1) {
			this.isImmuneToFire = true;
		}
	}
	
	/*********************************************************
	 * Methods related to entity death.					  *
	 *********************************************************/
	public void onDeath(DamageSource cause) {
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + this.height / 2, this.posZ, 1.0D, 1.0D, 1.0D);
		if (!this.world.isRemote) {
			if (this.isFusion()) {
				this.unfuse();
				this.setDead();
			}
			else {
				ItemStack stack = new ItemStack(this.droppedGemItem);
				boolean shatterGem = cause.isExplosion() ? true : (this.rand.nextInt(20) == 0);
				boolean poofGem = true;
				if (cause.getTrueSource() instanceof EntityLivingBase) {
					if (cause.getTrueSource() instanceof EntitySlag) {
						cause = new SlagDamage();
						shatterGem = false;
						poofGem = false;
					}
					else {
						EntityLivingBase attacker = (EntityLivingBase) cause.getTrueSource();
						ItemStack heldItem = attacker.getHeldItemMainhand();
						if (heldItem.getItem() != ModItems.GEM_STAFF && heldItem.getItem() != ModItems.COMMANDER_STAFF) {
							shatterGem = this.rand.nextInt(60) == 0;
							if (heldItem.isItemEnchanted()) {
								NBTTagList enchantments = heldItem.getEnchantmentTagList();
								for (int i = 0; i < enchantments.tagCount(); i++) {
									if (enchantments.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(ModEnchantments.BREAKING_POINT)) {
										shatterGem = true;
									}
								}
							}
						}
						else {
							stack = new ItemStack(this.droppedGemItem);
							cause = new PoofDamage();
							stack.setItemDamage(0);
							shatterGem = false;
							poofGem = false;
						}
					}
				}
				else if (cause instanceof PoofDamage) {
					stack.setItemDamage(0);
					shatterGem = false;
					poofGem = false;
				}
				this.playSound(ModSounds.GEM_POOF, 3.0F, 1.0F);
				if (shatterGem) {
					this.playSound(ModSounds.GEM_SHATTER, 3.0F, 1.0F);
					stack = new ItemStack(this.droppedCrackedGemItem);
					cause = new ShatterDamage();
					stack.setItemDamage(0);
				}
				else if (poofGem) {
					stack = new ItemStack(this.droppedGemItem);
					cause = new PoofDamage();
					stack.setItemDamage(60);
				}
				if (this.world.getGameRules().getBoolean("showDeathMessages")) {
					for (EntityPlayer playerIn : this.world.playerEntities) {
						if (EntityPlayer.getUUID(playerIn.getGameProfile()).equals(this.getOwnerId())) {
							playerIn.sendMessage(cause.getDeathMessage(this));
						}
						else {
							for (UUID ownerId : this.jointOwners) {
								if (EntityPlayer.getUUID(playerIn.getGameProfile()).equals(ownerId)) {
									playerIn.sendMessage(cause.getDeathMessage(this));
								}
							}
						}
					}
				}
				if (!stack.isEmpty()) {
					((ItemGem) stack.getItem()).setData(this, stack);
					this.entityDropItem(stack, 0.0F);
				}
			}
		}
		super.onDeath(cause);
	}
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
		return;
	}
	public void sendMessage(String line, Object[] formatting) {
		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(24.0D, 16.0D, 24.0D));
		for (EntityPlayer player : list) {
			player.sendMessage(new TextComponentString("<" + this.getName() + "> " + new TextComponentTranslation(line, formatting).getUnformattedComponentText()));
		}
	}
	public void sendMessage(String line) {
		List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(24.0D, 16.0D, 24.0D));
		for (EntityPlayer player : list) {
			player.sendMessage(new TextComponentString("<" + this.getName() + "> " + new TextComponentTranslation(line).getUnformattedComponentText()));
		}
	}
	
	/*********************************************************
	 * Methods related to entity rendering.				  *
	 *********************************************************/
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 7) {
			this.playTameEffect();
		}
		else {
			super.handleStatusUpdate(id);
		}
	}
	public void playTameEffect() {
		EnumParticleTypes enumparticletypes = EnumParticleTypes.VILLAGER_HAPPY;
		for (int i = 0; i < 7; ++i) {
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.world.spawnParticle(enumparticletypes, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
		}
	}
	public void createFireworks() {
		NBTTagList explosions = new NBTTagList();
		NBTTagCompound explosion = new NBTTagCompound();
		explosion.setIntArray("Colors", new int[] {this.world.rand.nextInt(16777215)});
		explosion.setIntArray("FadeColors", new int[] {this.world.rand.nextInt(16777215)});
		explosion.setBoolean("Trail", this.world.rand.nextBoolean());
		explosion.setBoolean("Flicker", this.world.rand.nextBoolean());
		explosions.appendTag(explosion);
		ItemStack stack = new ItemStack(Items.FIREWORKS);
		stack.getOrCreateSubCompound("Fireworks").setByte("Flight", (byte) this.rand.nextInt(3));
		stack.getOrCreateSubCompound("Fireworks").setTag("Explosions", explosions);
		EntityFireworkRocket firework = new EntityFireworkRocket(this.world, (double) this.posX, (double) this.posY, (double) this.posZ, stack);
		this.world.spawnEntity(firework);
	}
	
	@Override
	public boolean getAlwaysRenderNameTag() {
		return true;
	}
	
	/*********************************************************
	 * Methods related to entity sound.					  *
	 *********************************************************/
	public float getSoundPitch() {
		return this.pitch;
	}
	public int getTalkInterval() {
		return 200;
	}
	protected SoundEvent getObeySound() {
		return null;
	}
	public void playObeySound() {
		if (this.getObeySound() != null) {
			this.playSound(this.getObeySound(), this.getSoundVolume(), this.getSoundPitch());
		}
	}
	public void playLivingSound() {
		if (ModConfigs.canGemsMakeSounds && this.canTalk) {
			super.playLivingSound();
		}
	}
	public void playHurtSound(DamageSource source) {
		if (ModConfigs.canGemsMakeSounds) {
			super.playHurtSound(source);
		}
	}
	
	/*********************************************************
	 * Methods related to entity rendering.				  *
	 *********************************************************/
	public boolean hasInsigniaVariant(GemPlacements placement) {
		return false;
	}
	
	public boolean hasUniformVariant(GemPlacements placement) {
		return false;
	}
	
	public boolean hasHairVariant(GemPlacements placement) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(/*float partialTicks*/) {
		return this.isSpaceBorn ? 15728880 : super.getBrightnessForRender();
	}
	
	public float getBrightness(/*float partialTicks*/) {
		return this.isSpaceBorn ? 1.0F : super.getBrightness();
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeFloat(this.width);
		buffer.writeFloat(this.height);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		this.setSize(buffer.readFloat(), buffer.readFloat());
	}
}
