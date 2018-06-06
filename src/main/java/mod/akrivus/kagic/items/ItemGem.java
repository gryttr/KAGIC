package mod.akrivus.kagic.items;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.shardfusion.EntityShardFusion;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModConfigs;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModEntities;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGem extends Item {
	private final String gemName;
	public boolean isCracked;
	
	public ItemGem(String name) {
		this(name, false);
	}
	
	public ItemGem(String name, boolean cracked) {
		this.setUnlocalizedName((cracked ? "cracked_" : "") + name + "_gem");
		this.setMaxStackSize(1);
		this.setMaxDamage(60);
		if (name.contains("corrupted") || name.contains("tongue_monster") || name.contains("water_bear") || name.contains("handbody") || name.contains("footarm") || name.contains("mouthtorso")) {
			this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_BAD_GEMS);
		}
		else {
			this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_GOOD_GEMS);
		}
		this.isCracked = cracked;
		this.gemName = new TextComponentTranslation("entity.kagic." + name + ".name").getUnformattedComponentText();
	}
	
	public void setData(EntityGem host, ItemStack stack) {
		stack.setTagCompound(host.writeToNBT(new NBTTagCompound()));
		stack.getTagCompound().setString("name", host.getName());
	}
	
	public void clearData(ItemStack itemStackIn) {
		itemStackIn.setTagCompound(new NBTTagCompound());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		try {
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey("name")) {
				String name = tag.getString("name");
				if (!name.isEmpty()) {
					tooltip.add(name);
				}
			}
			if (tag.hasKey("specificName") && ModConfigs.displayNames) {
				String name = tag.getString("specificName");
				if (!name.isEmpty()) {
					tooltip.add(name);
				}
			}
		}
		catch (NullPointerException e) {
			tooltip.add(this.gemName);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(hand);
			if (stack.getItemDamage() == 0) {
				if (!this.isCracked) {
					RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
			        if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
			            BlockPos blockpos = raytraceresult.getBlockPos();
			            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, stack)) {
			            	boolean spawned = spawnGem(worldIn, playerIn, blockpos, stack);
							if (!playerIn.capabilities.isCreativeMode && spawned) {
								stack.shrink(1);
							}
		            	}
		            }
		        }
			}
	       	return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	public boolean spawnGem(World worldIn, EntityPlayer playerIn, BlockPos blockpos, ItemStack stack) {
		if (!worldIn.isRemote) {
			if (this.isCracked) {
				return false;
			}
			else {
				EntityGem newGem = new EntityRuby(worldIn);
				for (String key : ModEntities.GEMS.keySet()) {
					try {
						String name = this.getUnlocalizedName().replaceAll("^item\\.(cracked_)*", "").replaceAll("_(\\d*_)*gem$", "");
						if (name.equals(key)) {
							newGem = (EntityGem) ModEntities.GEMS.get(key).getConstructors()[0].newInstance(worldIn);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error creating gem: " + e.getMessage());
					}
				}
		    	try {
					newGem.readFromNBT(stack.getTagCompound());
					newGem.setUniqueId(MathHelper.getRandomUUID(worldIn.rand));
		    	}
		    	catch (Exception e) {
		    		Matcher matcher = Pattern.compile("_(\\d+)_").matcher(this.getUnlocalizedName());
					newGem.setPosition(blockpos.getX() + 0.5, blockpos.getY() + 1.0, blockpos.getZ() + 0.5);
	            	newGem.onInitialSpawn(worldIn.getDifficultyForLocation(blockpos), null);
	            	if (matcher.find()) {
	            		newGem.itemDataToGemData(Integer.parseInt(matcher.group(1)));
	            	}
		    		if (playerIn != null && !newGem.isDiamond && !(newGem instanceof EntityShardFusion) && !(newGem instanceof EntityCorruptedGem)) {
		    			newGem.setOwnerId(EntityPlayer.getUUID(playerIn.getGameProfile()));
		    			newGem.setLeader(playerIn);
		        		newGem.setServitude(EntityGem.SERVE_HUMAN);
		            	newGem.getNavigator().clearPath();
		            	newGem.setAttackTarget(null);
		            	newGem.setHealth(newGem.getMaxHealth());
		            	newGem.playTameEffect();
		            	newGem.world.setEntityState(newGem, (byte) 7);
		            	newGem.playObeySound();
		    		}
		    	}
				newGem.setPosition(blockpos.getX() + 0.5, blockpos.getY() + 1.0, blockpos.getZ() + 0.5);
				newGem.setRestPosition(newGem.getPosition());
				newGem.setHealth(newGem.getMaxHealth());
				newGem.setAttackTarget(null);
				newGem.extinguish();
				newGem.clearActivePotions();
				worldIn.spawnEntity(newGem);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.isDead = false;
		entity.setEntityInvulnerable(true);
		entity.extinguish();
		if (entity.ticksExisted > 600) {
			entity.setNoDespawn();
		}
		if (!this.isCracked && !entity.world.isRemote && entity.ticksExisted > 600) {
			ItemStack stack = entity.getItem();
			if (entity.getEntityWorld().getWorldTime() % 20 == 0 && stack.getItemDamage() > 0) {
				stack.setItemDamage(Math.max(stack.getItemDamage() - 2, 0));
			}
			if (stack.getItemDamage() == 0) {
				ItemGem gem = (ItemGem) stack.getItem();
				boolean spawned = gem.spawnGem(entity.world, null, entity.getPosition(), entity.getItem());
				if (spawned) {
					entity.setDead();
				}
			}
		}
		else if (this.isCracked) { 
			if (!entity.world.isRemote && entity.world.getBlockState(entity.getPosition()).getBlock() == ModBlocks.ROSE_TEARS) {
				ItemStack crackedGemStack = entity.getItem();
				ItemStack healedGemStack = new ItemStack(ModItems.GEM_TABLE.get(crackedGemStack.getItem()));
				healedGemStack.setTagCompound(crackedGemStack.getTagCompound());
				healedGemStack.setItemDamage(0);
				EntityItem healedGem = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, healedGemStack);
				entity.world.spawnEntity(healedGem);
				entity.setDead();
			}
		}
        return false;
    }
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.getWorldTime() % 20 == 0 && stack.getItemDamage() > 0) {
			stack.setItemDamage(Math.max(stack.getItemDamage() - (1 * (isSelected ? 2 : 1)), 0));
		}
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
		if (stack.getTagCompound() != null) {
			NBTTagCompound tag = stack.getTagCompound();
			return tag.getBoolean("primary");
		}
		return false;
    }
}
