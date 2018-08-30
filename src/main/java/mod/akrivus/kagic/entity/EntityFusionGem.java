package mod.akrivus.kagic.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.google.common.base.Predicate;

import io.netty.buffer.ByteBuf;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFusionGem  extends EntityGem {
	private NBTTagList fusionGems = new NBTTagList();
	private ArrayList<Class<? extends EntityGem>> gemTypes = new ArrayList<Class<? extends EntityGem>>();
	private ArrayList<GemCuts> fusionGemCuts = new ArrayList<GemCuts>();
	private ArrayList<GemPlacements> fusionGemPlacements = new ArrayList<GemPlacements>();
	
	protected static final DataParameter<String> FUSION_TYPES = EntityDataManager.<String>createKey(EntityFusionGem.class, DataSerializers.STRING);
	protected static final DataParameter<Integer> DEFECTIVE_COUNT = EntityDataManager.<Integer>createKey(EntityFusionGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> PRIME_COUNT = EntityDataManager.<Integer>createKey(EntityFusionGem.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> FUSION_COLOR = EntityDataManager.<Integer>createKey(EntityFusionGem.class, DataSerializers.VARINT);
	
	public EntityFusionGem(World world) {
		super(world);
		
		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));

		// Apply targeting.
		this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
			@Override
			public boolean apply(EntityLiving input) {
				return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
			}
		}));

		this.isSoldier = true;
		this.dataManager.register(FUSION_TYPES, "");
		this.dataManager.register(DEFECTIVE_COUNT, 0);
		this.dataManager.register(PRIME_COUNT, 0);
		this.dataManager.register(FUSION_COLOR, 0);
		this.setFusionCount(0);
		this.pitch = 0;
	}

	//=========================================================================
	//=== Methods for entity NBTing ===========================================
	//=========================================================================
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp) {
		comp.setTag("gems", fusionGems);
		
		//comp.setInteger("typeSize", this.gemTypes.size());
		NBTTagList types = new NBTTagList();
		for (int i = 0; i < this.gemTypes.size(); ++i) {
			NBTTagString nbt = new NBTTagString(this.gemTypes.get(i).getName());
			types.appendTag(nbt);
		}
		comp.setTag("types", types);
		
		NBTTagList cuts = new NBTTagList();
		for (int i = 0; i < this.fusionGemCuts.size(); ++i) {
			int cut = this.fusionGemCuts.get(i).id;
			NBTTagInt nbt = new NBTTagInt(cut);
			cuts.appendTag(nbt);
		}
		comp.setTag("cuts", cuts);
		
		NBTTagList placements = new NBTTagList();
		for (int i = 0; i < this.fusionGemPlacements.size(); ++i) {
			int placement = this.fusionGemPlacements.get(i).id;
			NBTTagInt nbt = new NBTTagInt(placement);
			placements.appendTag(nbt);
		}
		comp.setTag("places", placements);
		
		comp.setInteger("defectiveCount", this.getDefectiveCount());
		comp.setInteger("primeCount", this.getPrimeCount());
		comp.setInteger("fusionColor", this.getFusionColor());
		
		return super.writeToNBT(comp);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);
		this.fusionGems = (NBTTagList) comp.getTag("gems");
		
		NBTTagList types = comp.getTagList("types", Constants.NBT.TAG_STRING);
		for (int i = 0; i < types.tagCount(); ++i) {
			try {
				this.gemTypes.add((Class<? extends EntityGem>) Class.forName(types.getStringTagAt(i)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		NBTTagList cuts = comp.getTagList("cuts", Constants.NBT.TAG_INT);
		for (int i = 0; i < cuts.tagCount(); ++i) {
			this.fusionGemCuts.add(GemCuts.getCut(cuts.getIntAt(i)));
		}

		NBTTagList placements = comp.getTagList("places", Constants.NBT.TAG_INT);
		for (int i = 0; i < placements.tagCount(); ++i) {
			this.fusionGemPlacements.add(GemPlacements.getPlacement(placements.getIntAt(i)));
		}

		this.setFusionColor(comp.getInteger("fusionColor"));
		
		this.setDefectiveCount(comp.getInteger("defectiveCount"));
		this.setPrimeCount(comp.getInteger("primeCount"));
		this.setAdjustedSize();
		
		this.setFusionCount(types.tagCount());
		this.setFusionPlacements(this.generateFusionPlacements());
		this.setFusionTypes(this.generateFusionTypes());
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		float pitch = this.pitch;
		IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
		this.pitch = pitch;
		this.setAdjustedSize();
		return data;
	}
	
	//=========================================================================
	//=== Methods for managing fusion==========================================
	//=========================================================================
	
	public boolean addGem(EntityGem gem) {
		NBTTagCompound comp = new NBTTagCompound();
		gem.writeToNBT(comp);
		
		this.fusionGems.appendTag(comp);
		this.gemTypes.add(gem.getClass());
		this.fusionGemCuts.add(gem.getGemCut());
		this.fusionGemPlacements.add(gem.getGemPlacement());
		
		this.setServitude(gem.getServitude());
		this.jointOwners.addAll(gem.jointOwners);
		if (gem.getServitude() == SERVE_HUMAN) {
			this.setOwnerId(gem.getOwnerId());
			this.setLeader(gem.getOwner());
		}
		
		if (this.getFusionCount() == 0) {
			this.setLocationAndAngles(gem.posX, gem.posY, gem.posZ, gem.rotationYaw, gem.rotationPitch);
		} else {
			this.setLocationAndAngles((this.posX + gem.posX) / 2, (this.posY + gem.posY) / 2, (this.posZ + gem.posZ) / 2, this.rotationYaw, this.rotationPitch);
		}
		
		if (gem.isSoldier) {
			this.setInsigniaColor(gem.getInsigniaColor());
			this.setUniformColor(gem.getUniformColor());
		}
		
		ItemStack weapon = this.getHeldItem(EnumHand.MAIN_HAND);
		if (weapon.getItem() == Items.AIR) {
			weapon = gem.getHeldItem(EnumHand.MAIN_HAND);
			this.setFusionWeapon(weapon);
		}
		ItemStack second = this.getHeldItem(EnumHand.OFF_HAND);
		if (second.getItem() == Items.AIR) {
			second = gem.getHeldItem(EnumHand.OFF_HAND);
			this.setFusionWeapon(second);
		}
		
		if (gem.isDefective()) {
			this.setDefectiveCount(this.getDefectiveCount() + 1);
		}
		if (gem.isPrimary()) {
			this.setPrimeCount(this.getPrimeCount() + 1);
		}
		
		this.setFusionCount(this.getFusionCount() + 1);
		this.setFusionPlacements(this.generateFusionPlacements());
		this.setFusionTypes(this.generateFusionTypes());
		this.pitch = (this.pitch + gem.pitch) / this.getFusionCount();
		return true;
	}
	
	@Override
	public void unfuse() {
		super.unfuse();
		KAGIC.instance.chatInfoMessage("Fusion poofed");
		if (this.fusionGems == null) {
			KAGIC.instance.chatInfoMessage("ERROR: no fusion data found!");
			KAGIC.instance.chatInfoMessage("Did you use the /summon command to get this fusion?");
			KAGIC.instance.chatInfoMessage("Fusions can only be properly obtained by combining two existing gems.");
		} else {
			for (int i = 0; i < this.fusionGems.tagCount(); ++i) {
				try {
					EntityGem gem = this.gemTypes.get(i).getDeclaredConstructor(World.class).newInstance(this.world);
					gem.readFromNBT(this.fusionGems.getCompoundTagAt(i));
					gem.setPosition(this.posX + (this.rand.nextDouble() - 0.5D) * this.width / 2, this.posY + this.rand.nextDouble() * (this.height - gem.height), this.posZ + (this.rand.nextDouble() - 0.5D) * this.width / 2);
					gem.setServitude(this.getServitude());
					this.world.spawnEntity(gem);
				} catch (Exception e) {
					KAGIC.instance.chatInfoMessage("ERROR: could not properly unfuse gem. See game log for details.");
					e.printStackTrace();
				}
			}
		}
		this.world.removeEntity(this);
	}
	
	@Override
	public String generateFusionPlacements() {
		String fusionPlacements = "";
		for (int i = 0; i < this.getFusionCount(); ++i) {
			if (i > 0) {
				fusionPlacements += " " + this.fusionGemPlacements.get(i).id + "_" + this.fusionGemCuts.get(i).id;
			}
			else {
				fusionPlacements += this.fusionGemPlacements.get(i).id + "_" + this.fusionGemCuts.get(i).id;
			}
		}
		
		return fusionPlacements;
	}
	
	public String getFusionTypes() {
		return this.dataManager.get(FUSION_TYPES);
	}
	
	public void setFusionTypes(String fusionTypes) {
		this.dataManager.set(FUSION_TYPES, fusionTypes);
	}
	
	public int getDefectiveCount() {
		return this.dataManager.get(DEFECTIVE_COUNT);
	}
	
	public void setDefectiveCount(int defectiveCount) {
		this.dataManager.set(DEFECTIVE_COUNT, defectiveCount);
	}
	
	public int getPrimeCount() {
		return this.dataManager.get(PRIME_COUNT);
	}
	
	public void setPrimeCount(int primeCount) {
		this.dataManager.set(PRIME_COUNT, primeCount);
	}
	
	public int getFusionColor() {
		return this.dataManager.get(FUSION_COLOR);
	}
	
	public void setFusionColor(int fusionColor) {
		this.dataManager.set(FUSION_COLOR, fusionColor);
	}
	
	public String generateFusionTypes() {
		String fusionTypes = "";
		
		for (int i = 0; i < this.getFusionCount(); ++i) {
			if (i > 0) {
				fusionTypes += " " + this.gemTypes.get(i).getSimpleName();
			}
			else {
				fusionTypes += this.gemTypes.get(i).getSimpleName();
			}
		}
		
		return fusionTypes;
	}

	@Override
	public boolean isFusion() {
		return true;
	}
	
	@Override
	public void setNewCutPlacement() {
		return;
	}
	
	@Override
	public boolean isCorrectCutPlacement() {
		return true;
	}

	@Override
	public boolean isGemPlacementDefined() {
		return !this.fusionGemPlacements.isEmpty();
	}

	@Override
	public boolean isGemCutDefined() {
		return !this.fusionGemCuts.isEmpty();
	}
	
	public ArrayList<Class<? extends EntityGem>> getGemTypes() {
		return this.gemTypes;
	}
	
	public ArrayList<GemCuts> getGemCuts() {
		return this.fusionGemCuts;
	}
	
	public ArrayList<GemPlacements> getGemPlacements() {
		return this.fusionGemPlacements;
	}
	
	public float getSizeFactor() {
		if (this.getFusionCount() == 0) {
			return 0;
		}
		
		float sizeMultiplier = this.getFusionCount() - this.getDefectiveCount() + this.getPrimeCount();
		return sizeMultiplier / this.getFusionCount();
	}
	
	public void setAdjustedSize() {
		
	}
}
