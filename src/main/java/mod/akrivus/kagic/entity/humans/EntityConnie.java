package mod.akrivus.kagic.entity.humans;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.ai.EntityAIFollowGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowPlayer;
import mod.akrivus.kagic.entity.ai.EntityAIFollowSteven;
import mod.akrivus.kagic.entity.humans.ai.EntityAIPickUpItemsIndiscriminately;
import mod.akrivus.kagic.entity.humans.ai.EntityAIProtectSteven;
import mod.akrivus.kagic.entity.humans.ai.EntityAIProtectVillagers;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EntityConnie extends EntityCreature implements IInventoryChangedListener, INpc {
	private static final DataParameter<Boolean> BACKPACKED = EntityDataManager.<Boolean>createKey(EntityConnie.class, DataSerializers.BOOLEAN);
	private static final DataParameter<ItemStack> SHEATHED = EntityDataManager.<ItemStack>createKey(EntityConnie.class, DataSerializers.ITEM_STACK);
	private static final DataParameter<Integer> CLOTHING = EntityDataManager.<Integer>createKey(EntityConnie.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> HAIRSTYLE = EntityDataManager.<Integer>createKey(EntityConnie.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> COAT = EntityDataManager.<Boolean>createKey(EntityConnie.class, DataSerializers.BOOLEAN);
	private static final int CLOTHING_MAX = 4;
	private static final int HAIRSTYLE_MAX = 2;
	public InventoryBasic backpack;
	public InvWrapper backpackHandler;
	public boolean changedWeapons;
	public boolean hasFood = true;
	private int lastHiltTime;
	public boolean silent;
	public EntityConnie(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.6F);
		this.dataManager.register(BACKPACKED, true);
		this.dataManager.register(SHEATHED, new ItemStack(Items.IRON_SWORD));
		this.dataManager.register(CLOTHING, this.rand.nextInt(CLOTHING_MAX));
		this.dataManager.register(HAIRSTYLE, this.rand.nextInt(HAIRSTYLE_MAX));
		this.dataManager.register(COAT, false);
		this.initStorage();
		
		// See doors and stuff.
		((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
		((PathNavigateGround) this.getNavigator()).setEnterDoors(true);

		// Other entity AIs.
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.8D, true));
        this.tasks.addTask(3, new EntityAIFollowSteven(this, 0.9D));
        this.tasks.addTask(4, new EntityAIFollowGem(this, 0.9D));
        this.tasks.addTask(4, new EntityAIFollowPlayer(this, 0.9D));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIPickUpItemsIndiscriminately(this, 0.9D));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIProtectSteven(this));
        this.targetTasks.addTask(1, new EntityAIProtectVillagers(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
                
        // Apply entity attributes.
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
            ItemStack itemstack = this.backpack.getStackInSlot(i);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("slot", (byte)i);
            itemstack.writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }
        compound.setTag("items", nbttaglist);
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.getBackStack().writeToNBT(nbttagcompound);
        compound.setTag("sheathed", nbttagcompound);
        compound.setBoolean("backpacked", this.isBackpacked());
        compound.setInteger("clothing", this.getClothing());
        compound.setInteger("hairstyle", this.getHairstyle());
        compound.setBoolean("coat", this.isWearingCoat());
	}
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("items", 10);
        this.initStorage();
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("slot") & 255;
            if (j >= 0 && j < this.backpack.getSizeInventory()) {
                this.backpack.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }
        this.setBackStack(new ItemStack(compound.getCompoundTag("sheathed")));
        this.setBackpack(compound.getBoolean("backpacked"));
        this.setClothing(compound.getInteger("clothing"));
        this.setHairstyle(compound.getInteger("hairstyle"));
        this.setIsWearingCoat(compound.getBoolean("coat"));
    }
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isBackpacked()) {
            		this.openGUI(player);
            		return true;
            	}
				else if (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
					this.setBackpack(true);
					return true;
				}
			}
			if (player.getHeldItem(hand).getItem() == Items.NAME_TAG) {
				this.sayHello();
				return true;
			}
		}
		return super.processInteract(player, hand);
    }
    public void onLivingUpdate() {
    	this.updateArmSwingProgress();
    	super.onLivingUpdate();
    	if (!this.world.isRemote) {
	    	if (this.getAttackTarget() != null && !this.changedWeapons) {
	    		this.setHeldItem(EnumHand.MAIN_HAND, this.getBackStack().copy());
		    	this.setBackStack(ItemStack.EMPTY);
	    		if (this.isBackpacked()) {
	    			ItemStack heldItem = this.getHeldItem(EnumHand.MAIN_HAND).copy();
	    			for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
	    				ItemStack stack = this.backpack.getStackInSlot(i);
	    		        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
	    		        boolean flag = true;
	    		        if (!heldItem.isEmpty())  {
	    		            if (slot.getSlotType() == EntityEquipmentSlot.Type.HAND) {
	    		                if (stack.getItem() instanceof ItemSword && !(heldItem.getItem() instanceof ItemSword)) {
	    		                    flag = true;
	    		                }
	    		                else if (stack.getItem() instanceof ItemSword && heldItem.getItem() instanceof ItemSword) {
	    		                    ItemSword sword = (ItemSword)(stack.getItem());
	    		                    ItemSword blade = (ItemSword)(heldItem.getItem());
	    		                    if (sword.getAttackDamage() == blade.getAttackDamage()) {
	    		                        flag = stack.getMetadata() > heldItem.getMetadata() || stack.hasTagCompound() && !heldItem.hasTagCompound();
	    		                    }
	    		                    else {
	    		                        flag = sword.getAttackDamage() > blade.getAttackDamage();
	    		                    }
	    		                }
	    		                else {
	    		                    flag = false;
	    		                }
	    		            }
	    		        }
	    				if (flag) {
	    					this.setHeldItem(EnumHand.MAIN_HAND, stack);
	    					this.backpack.setInventorySlotContents(i, heldItem);
	    				}
	    			}
	    		}
	    		this.changedWeapons = true;
	    		this.lastHiltTime = 0;
	    	}
	    	else if (this.changedWeapons && this.lastHiltTime > 200) {
	    		this.setBackStack(this.getHeldItem(EnumHand.MAIN_HAND).copy());
	    		this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
	    		this.changedWeapons = false;
	    	}
	    	if (this.isBackpacked() && this.getHealth() < 10.0F) {
    			this.hasFood = false;
	    		for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
    				ItemStack stack = this.backpack.getStackInSlot(i);
	    			if (stack.getItem() instanceof ItemFood) {
    					ItemFood food = (ItemFood)(stack.getItem());
    					int amount = food.getHealAmount(stack);
    					if (this.getHealth() + amount <= 20.0F) {
    						this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
    						this.heal(amount);
    					}
    					this.hasFood = true;
    				}
    			}
	    	}
	    	if (this.ticksExisted % 20 == 0) {
		    	if (this.world.canSnowAt(this.getPosition(), false)) {
		    		this.setIsWearingCoat(true);
		    	}
		    	else {
		    		this.setIsWearingCoat(false);
		    	}
	    	}
	    	if (this.ticksExisted % 168000 == 0) {
	    		this.setHairstyle(this.rand.nextInt(EntityConnie.HAIRSTYLE_MAX));
	    	}
	    	if (this.ticksExisted % 24000 == 0) {
	    		this.setClothing(this.rand.nextInt(EntityConnie.CLOTHING_MAX));
	    	}
	    	++this.lastHiltTime;
    	}
    	if (!this.getDisplayName().getUnformattedText().equals("Connie")) {
    		this.setCustomNameTag("Connie");
    	}
    }
	private void initStorage() {
        InventoryBasic gemstorage = this.backpack;
        this.backpack = new InventoryBasic("gemStorage", false, 27);
        if (gemstorage != null) {
            gemstorage.removeInventoryChangeListener(this);
            for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
                ItemStack itemstack = gemstorage.getStackInSlot(i);
                this.backpack.setInventorySlotContents(i, itemstack.copy());
            }
        }
        this.backpack.addInventoryChangeListener(this);
        this.backpackHandler = new InvWrapper(this.backpack);
        this.setCanPickUpLoot(this.isBackpacked());
    }
	public void openGUI(EntityPlayer playerEntity) {
        if (!this.world.isRemote && this.isBackpacked()) {
            this.backpack.setCustomName(new TextComponentTranslation("command.kagic.connie_backpack.name").getUnformattedComponentText());
            playerEntity.displayGUIChest(this.backpack);
        }
    }
	public void onInventoryChanged(IInventory inventory) {
		ItemStack item = this.backpack.getStackInSlot(8);
		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, item);
	}
	protected void updateEquipmentIfNeeded(EntityItem itementity) {
        ItemStack itemstack = itementity.getItem();
        ItemStack itemstack1 = this.backpack.addItem(itemstack);
        if (itemstack1.isEmpty()) {
            itementity.setDead();
        }
        else {
            itemstack.setCount(itemstack1.getCount());
        }
    }
	public boolean isBackpacked() {
		return this.dataManager.get(BACKPACKED);
	}
	public void setBackpack(boolean backpacked) {
		this.dataManager.set(BACKPACKED, backpacked);
		this.setCanPickUpLoot(backpacked);
	}
	public ItemStack getBackStack() {
		return this.dataManager.get(SHEATHED);
	}
	public void setBackStack(ItemStack stack) {
		this.dataManager.set(SHEATHED, stack);
	}
	public int getClothing() {
		return this.dataManager.get(CLOTHING);
	}
	public void setClothing(int clothing) {
		this.dataManager.set(CLOTHING, clothing);
	}
	public int getHairstyle() {
		return this.dataManager.get(HAIRSTYLE);
	}
	public void setHairstyle(int hairstyle) {
		this.dataManager.set(HAIRSTYLE, hairstyle);
	}
	public boolean isWearingCoat() {
		return this.dataManager.get(COAT);
	}
	public void setIsWearingCoat(boolean coat) {
		this.dataManager.set(COAT, coat);
	}
    public boolean canDespawn() {
		return false;
    }
    public boolean shouldAttackEntity(EntityLivingBase var1, EntityLivingBase var2) {
        return true;
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
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			this.dropItem(ModItems.CONNIE_BRACELET, 1);
			for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
				this.entityDropItem(this.backpack.getStackInSlot(i), 0.0F);
			}
		}
		super.onDeath(cause);
	}
	public void playProtectSound(float health) {
		this.playSound(ModSounds.CONNIE_PROTECT, this.getSoundVolume() * ((20 - health) / 20) + 1, this.getSoundPitch());
	}
	public void sayHello() {
		this.playSound(ModSounds.CONNIE_HELLO, this.getSoundVolume(), this.getSoundPitch());
	}
	protected SoundEvent getAmbientSound() {
		if (!this.silent) {
			return ModSounds.CONNIE_LIVING;
		}
		else {
			return null;
		}
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.CONNIE_HURT;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.CONNIE_DEATH;
	}
	protected float getSoundPitch() {
		return 1.0F;
	}
}
