package mod.akrivus.kagic.entity.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIPickUpItems;
import mod.akrivus.kagic.entity.ai.EntityAISitStill;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.oredict.DyeUtils;

public class EntityPearl extends EntityGem implements IInventoryChangedListener, INpc {
	public static final HashMap<IBlockState, Double> PEARL_YIELDS = new HashMap<IBlockState, Double>();
	public static final double PEARL_DEFECTIVITY_MULTIPLIER = 1;
	public static final double PEARL_DEPTH_THRESHOLD = 0;
	public static final ArrayList<ResourceLocation> PEARL_HAIR_STYLES = new ArrayList<ResourceLocation>();
	public static final ArrayList<ResourceLocation> PEARL_DRESS_STYLES = new ArrayList<ResourceLocation>();
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> HAIR_COLOR = EntityDataManager.<Integer>createKey(EntityPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> VISOR_COLOR = EntityDataManager.<Integer>createKey(EntityPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DRESS_STYLE = EntityDataManager.<Integer>createKey(EntityPearl.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> NAKED = EntityDataManager.<Boolean>createKey(EntityPearl.class, DataSerializers.BOOLEAN);
	public String soulSong = "392935392935392939293929395";
	public InventoryBasic gemStorage;
	public InvWrapper gemStorageHandler;
	
	public EntityPearl(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.9F);
		this.initGemStorage();
		this.seePastDoors();
		this.soulSong = Integer.toString(this.rand.nextInt(999998999) + 1000);
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BELLY);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper)input).getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(1, new EntityAIPickUpItems(this, 0.9D));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(2, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAISitStill(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.droppedGemItem = ModItems.PEARL_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_PEARL_GEM;
        
        // Register entity data.
        this.dataManager.register(COLOR, this.rand.nextInt(16));
        this.dataManager.register(HAIR_COLOR, this.dataManager.get(COLOR));
        this.dataManager.register(VISOR_COLOR, this.dataManager.get(COLOR));
        this.dataManager.register(DRESS_STYLE, 1);
        this.dataManager.register(NAKED, false);
	}

	@Override
	public int generateGemColor() {
		switch (this.getColor()) {
    	case 0:
    		return 0xFFFFFF;
    	case 1:
    		return 0xC6A68B;
    	case 2:
    		return 0xBF92D1;
    	case 3:
    		return 0x657991;
    	case 4:
    		return 0xFCFCB0;
    	case 5:
    		return 0x7A9165;
    	case 6:
    		return 0xE5A0B7;
    	case 7:
    		return 0xB2B2B2;
    	case 8:
    		return 0x545454;
    	case 9:
    		return 0x7CA0B2;
    	case 10:
    		return 0x9579AD;
    	case 11:
    		return 0x808BB7;
    	case 12:
    		return 0xB2763E;
    	case 13:
    		return 0x88B22C;
    	case 14:
    		return 0xB23939;
    	case 15:
    		return 0x4C4C4C;
    	}
		return 0xFFFFFF;
    }
	public int generateSkinColor() {
		switch (this.getColor()) {
    	case 0:
    		return 0xE9ECEC;
    	case 1:
    		return 0xED975A;
    	case 2:
    		return 0xBC7CB8;
    	case 3:
    		return 0x7BC1D8;
    	case 4:
    		return 0xF7D571;
    	case 5:
    		return 0x89B750;
    	case 6:
    		return 0xEAD3DA;
    	case 7:
    		return 0x7F8B91;
    	case 8:
    		return 0xD8D8CD;
    	case 9:
    		return 0x63D2D8;
    	case 10:
    		return 0x8C5DAA;
    	case 11:
    		return 0x63669B;
    	case 12:
    		return 0xBC7643;
    	case 13:
    		return 0x8EB72D;
    	case 14:
    		return 0xED9693;
    	case 15:
    		return 0x333333;
    	}
		return 0xFFFFFF;
	}
	public int generateHairColor() {
		switch (this.getHairColor()) {
    	case 0:
    		return 0xFFFFFF;
    	case 1:
    		return 0xFF7200;
    	case 2:
    		return 0xAE00FF;
    	case 3:
    		return 0xB2D6FF;
    	case 4:
    		return 0xF9F900;
    	case 5:
    		return 0xD7FFB2;
    	case 6:
    		return 0xFFB2CB;
    	case 7:
    		return 0xD8D8D8;
    	case 8:
    		return 0xAFAFAF;
    	case 9:
    		return 0xB2E6FF;
    	case 10:
    		return 0xC07CFF;
    	case 11:
    		return 0xB2C1FF;
    	case 12:
    		return 0xCC8547;
    	case 13:
    		return 0xC5FF3F;
    	case 14:
    		return 0xFF5454;
    	case 15:
    		return 0x000000;
    	}
		return 0xFFFFFF;
	}
	public void convertGems(int placement) {
    	this.setGemCut(GemCuts.CABOCHON.id);
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
	
	@Override
	public boolean canChangeInsigniaColorByDefault() {
		return false;
	}
	
	@Override
	public boolean canChangeUniformColorByDefault() {
		return false;
	}
	
	/*********************************************************
	 * Methods related to loading.                           *
	 *********************************************************/
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("color", this.getColor());
        compound.setInteger("hairColor", this.getHairColor());
        compound.setInteger("visorColor", this.getVisorColor());
        compound.setInteger("dressStyle", this.getDressStyle());
        compound.setBoolean("naked", this.isNaked());
        compound.setString("song", this.soulSong);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.gemStorage.getSizeInventory(); ++i) {
            ItemStack itemstack = this.gemStorage.getStackInSlot(i);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("slot", (byte)i);
            itemstack.writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }
        compound.setTag("items", nbttaglist);
        super.writeEntityToNBT(compound);
	}
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setColor(compound.getInteger("color"));
        if (compound.hasKey("hairColor")) {
        	this.setHairColor(compound.getInteger("hairColor"));
        }
        else {
        	this.setHairColor(this.getColor());
        }
        if (compound.hasKey("visorColor")) {
        	this.setVisorColor(compound.getInteger("visorColor"));
        }
        else {
        	this.setVisorColor(this.getColor());
        }
        this.setDressStyle(compound.getInteger("dressStyle"));
        this.setNaked(compound.getBoolean("naked"));
        this.soulSong = compound.getString("song");
        NBTTagList nbttaglist = compound.getTagList("items", 10);
        this.initGemStorage();
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("slot") & 255;
            if (j >= 0 && j < this.gemStorage.getSizeInventory()) {
                this.gemStorage.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }
        super.readEntityFromNBT(compound);
    }
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    	livingdata = super.onInitialSpawn(difficulty, livingdata);
    	this.setGemCut(GemCuts.CABOCHON.id);
    	this.itemDataToGemData(this.getColor());
    	this.setDressStyle(this.rand.nextInt(EntityPearl.PEARL_DRESS_STYLES.size()));
    	if (this.isDefective()) {
    		this.setHairColor(15 - this.getColor());
    	}
    	else {
    		this.setHairColor(this.getColor());
    	}
    	this.nativeColor = this.getColor();
        return livingdata;
    }
    public void itemDataToGemData(int data) {
		this.setColor(data);
		this.setHairColor(data);
		this.setVisorColor(data);
		this.setInsigniaColor(data);
		this.setUniformColor(data);
		this.setGemColor(this.generateGemColor());
		this.nativeColor = this.getColor();
	}
    protected int generateHairStyle() {
    	return this.rand.nextInt(EntityPearl.PEARL_HAIR_STYLES.size());
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
						//player.addStat(ModAchievements.THAT_WILL_BE_ALL);
		    			if (stack.getItem() == Items.SHEARS) {
		    				if (player.isSneaking()) {
		    					if (!this.isNaked()) {
		    						this.playSound(this.getWeirdSound(), this.getSoundVolume(), this.getSoundPitch());
		    						this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, this.getSoundVolume(), this.getSoundPitch());
		    						this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getInsigniaColor()), 0.0F);
		    						this.setNaked(true);
		    					}
		    				}
		    				else {
			    				int hair = this.getHairStyle() + 1;
			    				if (hair >= EntityPearl.PEARL_HAIR_STYLES.size() || hair < 0) {
			    					hair = 0;
			    				}
				        		this.setHairStyle(hair);
		    				}
		    				this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, this.getSoundVolume(), this.getSoundPitch());
			        		if (!player.capabilities.isCreativeMode) {
			        			stack.damageItem(1, this);
			        		}
			        		return true;
		        		}
		    			else if (DyeUtils.isDye(stack)) {
		    				if (player.isSneaking()) {
		    					int oldColor = this.getHairColor();
				        		this.setHairColor(DyeUtils.metaFromStack(stack).orElse(0));
				        		if (!player.capabilities.isCreativeMode && oldColor != this.getHairColor()) {
				        			stack.shrink(1);
				        		}
		    				}
		    				else {
			    				int oldColor = this.getColor();
				        		this.setColor(15 - stack.getItemDamage());
				        		if (!player.capabilities.isCreativeMode && oldColor != this.getColor()) {
				        			stack.shrink(1);
				        		}
		    				}
			        		return true;
		    			}
		    			else if (stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
		    				if (player.isSneaking()) {
		    					if (this.hasVisor()) {
			    					ItemStack newstack = new ItemStack(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE));
			    					Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).setDamage(newstack, this.getVisorColor());
			    					this.entityDropItem(newstack, 0.0F);
			    					this.setHasVisor(false);
		    					}
		    				}
		    				else {
			    				if (this.hasVisor()) {
			    					ItemStack newstack = new ItemStack(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE));
			    					Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).setDamage(newstack, this.getVisorColor());
			    					this.entityDropItem(newstack, 0.0F);
			    				}
			    				else {
			    					this.setHasVisor(true);
			    				}
			    				this.setVisorColor(Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE).getMetadata(stack));
			    				stack.shrink(1);
		    				}
		    			}
		    			else if (stack.getItem() == Item.getItemFromBlock(Blocks.WOOL)) {
		    				if (this.isNaked()) {
		    					this.setInsigniaColor(stack.getItemDamage());
		    					this.setNaked(false);
		    					stack.shrink(1);
		    				}
		    				if (stack.getCount() > 0) {
			    				if (player.isSneaking()) {
			    					int dress = this.getDressStyle() + 1;
				    				if (dress >= EntityPearl.PEARL_DRESS_STYLES.size() || dress < 0) {
				    					dress = 0;
				    				}
					        		this.setDressStyle(dress);
					        		this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, this.getSoundVolume(), this.getSoundPitch());
					        		return true;
			    				}
			    				else {
				    				int oldColor = this.getInsigniaColor();
					        		this.setInsigniaColor(stack.getItemDamage());
					        		if (!player.capabilities.isCreativeMode) {
					        			if (oldColor != this.getInsigniaColor()) {
					        				stack.shrink(1);
					        			}
					        		}
			    				}
		    				}
			        		return true;
		    			}
		    			else if (this.isCoreItem(stack)) {
		    				return super.processInteract(player, hand);
		    			}
		    			else {
		            		this.openGUI(player);
		            		this.playObeySound();
		            		return true;
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
	public void setDefective(boolean defective) {
		if (defective) {
			this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
	        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
	        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
	        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
	            public boolean apply(EntityLiving input) {
	                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
	            }
	        }));
	        this.setAttackAI();
		}
		super.setDefective(defective);
	}
	
	public void setColor(int color) {
		this.dataManager.set(COLOR, color);
		this.setGemColor(this.generateGemColor());
	}
	public int getColor() {
		return this.dataManager.get(COLOR);
	}
	public void setVisorColor(int color) {
		this.dataManager.set(VISOR_COLOR, color);
	}
	public int getVisorColor() {
		return this.dataManager.get(VISOR_COLOR);
	}
	
	@Override
	public void setHairColor(int color) {
		this.dataManager.set(HAIR_COLOR, color);
	}
	
	@Override
	public int getHairColor() {
		return this.dataManager.get(HAIR_COLOR);
	}
	public void setDressStyle(int dress) {
		this.dataManager.set(DRESS_STYLE, dress);
	}
	public int getDressStyle() {
		return this.dataManager.get(DRESS_STYLE);
	}
	public void setNaked(boolean naked) {
		this.dataManager.set(NAKED, naked);
	}
	public boolean isNaked() {
		return this.dataManager.get(NAKED);
	}
	public String getSpecialSkin() {
		return "_" + this.getSpecial();
	}
	public void setSpecialSkin(int special) {
		super.setSpecial(special);
		switch (special) {
		case 1:
        	this.setCustomNameTag("Blue Pearl");
        	this.setGemPlacement(GemPlacements.CHEST.id);
        	this.setSpecial(1);
        	this.setColor(3);
	        break;
		case 2:
        	this.setCustomNameTag("Yellow Pearl");
        	this.setGemPlacement(GemPlacements.CHEST.id);
        	this.setSpecial(2);
        	this.setColor(4);
        	break;
		case 3:
			this.setCustomNameTag("Yellow Pearl");
        	this.setGemPlacement(GemPlacements.FOREHEAD.id);
        	this.setSpecial(3);
        	this.setColor(0);
        	break;
		}
	}
	public InventoryBasic getInventory() {
		return this.gemStorage;
	}
	
	/*********************************************************
	 * Methods related to living.                            *
	 *********************************************************/
	public void onLivingUpdate() {
		if (!this.canPickUpLoot()) {
			this.setCanPickUpLoot(this.isTamed());
		}
		if (!this.world.isRemote && this.ticksExisted % 20 == 0) {
			List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(Math.cos(this.rotationYaw), 2.0D, Math.sin(this.rotationYaw)));
			for (EntityPlayer entity : list) {
				if (this.isOwnedBy(entity)) {
					if (entity.isSneaking()) {
						this.playSound(this.getWeirdSound(), this.getSoundVolume(), this.getSoundPitch());
					}
				}
	        }
		}
		super.onLivingUpdate();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return super.attackEntityFrom(source, amount);
	}
	
	public void spawnHoloPearl() {
		EntityHoloPearl holoPearl = new EntityHoloPearl(this.world, this);
		holoPearl.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), null);
		this.world.spawnEntity(holoPearl);
	}
	
	/*********************************************************
     * Methods related to death.                             *
     *********************************************************/
    public void onDeath(DamageSource cause) {
    	this.setCanPickUpLoot(false);
    	switch (this.getColor()) {
    	case 0:
    		this.droppedGemItem = ModItems.WHITE_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_WHITE_PEARL_GEM;
    		break;
    	case 1:
    		this.droppedGemItem = ModItems.ORANGE_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_ORANGE_PEARL_GEM;
    		break;
    	case 2:
    		this.droppedGemItem = ModItems.MAGENTA_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_MAGENTA_PEARL_GEM;
    		break;
    	case 3:
    		this.droppedGemItem = ModItems.LIGHT_BLUE_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIGHT_BLUE_PEARL_GEM;
    		break;
    	case 4:
    		this.droppedGemItem = ModItems.YELLOW_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_YELLOW_PEARL_GEM;
    		break;
    	case 5:
    		this.droppedGemItem = ModItems.LIME_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIME_PEARL_GEM;
    		break;
    	case 6:
    		this.droppedGemItem = ModItems.PINK_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_PINK_PEARL_GEM;
    		break;
    	case 7:
    		this.droppedGemItem = ModItems.GRAY_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_GRAY_PEARL_GEM;
    		break;
    	case 8:
    		this.droppedGemItem = ModItems.LIGHT_GRAY_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_LIGHT_GRAY_PEARL_GEM;
    		break;
    	case 9:
    		this.droppedGemItem = ModItems.CYAN_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_CYAN_PEARL_GEM;
    		break;
    	case 10:
    		this.droppedGemItem = ModItems.PURPLE_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_PURPLE_PEARL_GEM;
    		break;
    	case 11:
    		this.droppedGemItem = ModItems.BLUE_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BLUE_PEARL_GEM;
    		break;
    	case 12:
    		this.droppedGemItem = ModItems.BROWN_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BROWN_PEARL_GEM;
    		break;
    	case 13:
    		this.droppedGemItem = ModItems.GREEN_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_GREEN_PEARL_GEM;
    		break;
    	case 14:
    		this.droppedGemItem = ModItems.RED_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_RED_PEARL_GEM;
    		break;
    	case 15:
    		this.droppedGemItem = ModItems.BLACK_PEARL_GEM;
    		this.droppedCrackedGemItem = ModItems.CRACKED_BLACK_PEARL_GEM;
    		break;
    	}
    	super.onDeath(cause);
    }
	
	/*********************************************************
	 * Methods related to storage.                           *
	 *********************************************************/
	private void initGemStorage() {
        InventoryBasic gemstorage = this.gemStorage;
        this.gemStorage = new InventoryBasic("gemStorage", false, this.getMaxInventorySlots());
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
	public int getMaxInventorySlots() {
		int slots = 36;
		if (this.getGemPlacement() == GemPlacements.FOREHEAD) {
			slots += 9;
		}
		if (this.isDefective()) {
			slots -= 9;
		}
		else if (this.isPrimary()) {
			slots += 9;
		}
		return slots;
	}
	public int playNote(int tone) {
		return this.playNote(tone, ModSounds.PEARL_SING);
	}
	public int playNote(int tone, SoundEvent sound) {
		// 2 = half note
		// 7 = half rest
		// 0 = whole note
		// 5 = whole rest
		switch (tone) {
		case 0:
			this.playSound(sound, 20.0F, this.pitch * 1.0F);
		case 5:
			return 5;
		case 1:
			this.playSound(sound, 20.0F, this.pitch * 1.5F);
		case 6:
			return 4;
		case 2:
			this.playSound(sound, 20.0F, this.pitch * 2.0F);
		case 7:
			return 3;
		case 3:
			this.playSound(sound, 20.0F, this.pitch * 2.5F);
		case 8:
			return 2;
		case 4:
			this.playSound(sound, 2.0F, this.pitch * 3.0F);
		case 9:
			return 1;
		}
		return 6;
	}
	
	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.PEARL_HURT;
	}
	protected SoundEvent getObeySound() {
		return ModSounds.PEARL_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return ModSounds.PEARL_DEATH;
	}
	protected SoundEvent getWeirdSound() {
		return ModSounds.PEARL_WEIRD;
	}
}
