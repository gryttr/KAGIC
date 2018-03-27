package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModEnchantments;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.skills.SkillBase;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EntityBismuth extends EntityGem implements IInventoryChangedListener {
	public static final HashMap<IBlockState, Double> BISMUTH_YIELDS = new HashMap<IBlockState, Double>();
	public static final double BISMUTH_DEFECTIVITY_MULTIPLIER = 1;
	public static final double BISMUTH_DEPTH_THRESHOLD = 0;
	public static final HashMap<Integer, ResourceLocation> BISMUTH_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();

	private static final int SKIN_COLOR_BEGIN = 0x91A8CF; 
	private static final int SKIN_COLOR_END = 0x503243; 
	
	public InventoryBasic gemStorage;
	public InvWrapper gemStorageHandler;
	
	public EntityBismuth(World worldIn) {
		super(worldIn);
		this.nativeColor = 10;
		this.isImmuneToFire = true;
		this.initGemStorage();
		this.setSize(0.9F, 2.3F);
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.BISMUTH, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.BISMUTH, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.BISMUTH, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.BISMUTH, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(2, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0d, true));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        
        // Apply target AI.
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.droppedGemItem = ModItems.BISMUTH_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_BISMUTH_GEM;
	}


	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.BISMUTH.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return super.onInitialSpawn(difficulty, livingdata);
    }
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.gemStorage.getSizeInventory(); ++i) {
            ItemStack itemstack = this.gemStorage.getStackInSlot(i);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("slot", (byte)i);
            itemstack.writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }
        compound.setTag("items", nbttaglist);
	}
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("items", 10);
        this.initGemStorage();
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("slot") & 255;
            if (j >= 0 && j < this.gemStorage.getSizeInventory()) {
                this.gemStorage.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }
    }
	
	/*********************************************************
     * Methods related to entity interaction.                *
     *********************************************************/
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
						else if (this.isSneaking() && !this.isDefective()) {
		            		this.openGUI(player);
		            		this.playObeySound();
		            		return true;
						}
						else if (stack.isItemStackDamageable() && !this.isDefective()) {
							if (stack.isItemDamaged()) {
								int damage = stack.getItemDamage() - stack.getMaxDamage();
								stack.damageItem(damage, this);
								if (!stack.isItemEnchanted() && stack.isItemEnchantable() && this.rand.nextInt(300) == 0) {
									List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, stack, damage / 10, true);
									for (int i = 0; i < list.size(); ++i) {
				                        EnchantmentData data = (EnchantmentData) list.get(i);
				                        stack.addEnchantment(data.enchantment, data.enchantmentLevel);
				                    }
									if (list.size() > 1) {
										if (this.rand.nextInt(30) == 0) {
											if (this.rand.nextInt(90) == 0) {
												stack.addEnchantment(ModEnchantments.BREAKING_POINT, 1);
											}
											else {
												stack.addEnchantment(ModEnchantments.FAIR_FIGHT, 1);
											}
										}
									}
								}
							}
						}
						else if (!stack.isEmpty() && !this.isDefective()) {
							ItemStack result = smeltItem(stack);
							if (!result.isEmpty()) {
								if (player.inventory.getFirstEmptyStack() > -1) {
									player.inventory.addItemStackToInventory(result);
								}
								else {
									this.entityDropItem(result, 0.0F);
								}
								/*if (result.getItem() == ModItems.ACTIVATED_GEM_BASE) {
									this.getOwner().addStat(ModAchievements.GEM_FORGER);
								}*/
								if (!player.capabilities.isCreativeMode) {
									stack.shrink(1);
								}
								//this.getOwner().addStat(ModAchievements.TO_THE_FORGE);
								return true;
							}
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public void onInventoryChanged(IInventory inventory) {
		ItemStack firstItem = this.gemStorage.getStackInSlot(0);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, firstItem);
		/*if (firstItem.getItem() instanceof ItemSword) {
			if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
            	this.getOwner().addStat(ModAchievements.RENEGADE_PEARL);
            }
		}*/
	}
	protected void updateEquipmentIfNeeded(EntityItem itementity) {
        ItemStack itemstack = itementity.getItem();
        ItemStack itemstack1 = this.gemStorage.addItem(itemstack);
        if (itemstack1.isEmpty()) {
            itementity.setDead();
        }
        else {
            itemstack.setCount(itemstack1.getCount());
        }
    }
	public boolean canPickUpItem(Item itemIn) {
		return true;
	}
	public InventoryBasic getInventory() {
		return this.gemStorage;
	}
	
	public ItemStack smeltItem(ItemStack stack) {
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack).copy();
		return result;
    }
	private void initGemStorage() {
        InventoryBasic gemstorage = this.gemStorage;
        this.gemStorage = new InventoryBasic("gemStorage", false, 36);
        if (gemstorage != null) {
            gemstorage.removeInventoryChangeListener(this);
            for (int i = 0; i < this.gemStorage.getSizeInventory(); ++i) {
                ItemStack itemstack = gemstorage.getStackInSlot(i);
                this.gemStorage.setInventorySlotContents(i, itemstack.copy());
            }
        }
        this.gemStorage.addInventoryChangeListener(this);
        this.gemStorageHandler = new InvWrapper(this.gemStorage);
        this.setCanPickUpLoot(this.isTamed());
    }
	public void openGUI(EntityPlayer playerEntity) {
        if (!this.world.isRemote && this.isTamed()) {
            this.gemStorage.setCustomName(this.getName());
            playerEntity.displayGUIChest(this.gemStorage);
        }
    }
	
	@Override
	public void whenDefective() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		this.setSize(0.6F, 2.07F);
	}

	/*********************************************************
     * Methods related to entity death.                      *
     *********************************************************/
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			if (cause.getTrueSource() instanceof EntityLivingBase) {
				ItemStack heldItem = ((EntityLivingBase)cause.getTrueSource()).getHeldItemMainhand();
				if (heldItem.isItemEnchanted()) {
					NBTTagList enchantments = heldItem.getEnchantmentTagList();
					for (int i = 0; i < enchantments.tagCount(); i++) {
						if (enchantments.getCompoundTagAt(i).getInteger("id") == Enchantment.getEnchantmentID(ModEnchantments.BREAKING_POINT)) {
							this.dropItem(ModItems.RECORD_THE_BREAKING_POINT, 1);
						}
					}
				}
			}
		}
		super.onDeath(cause);
    }
	
	/*********************************************************
     * Methods related to entity sounds.                     *
     *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.BISMUTH_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.BISMUTH_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.BISMUTH_DEATH;
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityBismuth.SKIN_COLOR_BEGIN);
		skinColors.add(EntityBismuth.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
}