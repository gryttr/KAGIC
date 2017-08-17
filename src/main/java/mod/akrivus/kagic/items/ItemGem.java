package mod.akrivus.kagic.items;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModEntities;
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
		if (!name.matches(".*\\d.*")) {
			this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_GEMS);
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean info) {
		try {
			list.add(stack.getTagCompound().getString("name"));
		}
		catch (NullPointerException e) {
			list.add(this.gemName);
		}
	}
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(hand);
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
	       	return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	public boolean spawnGem(World worldIn, EntityPlayer playerIn, BlockPos blockpos, ItemStack stack) {
		if (this.isCracked) {
			return false;
		}
		else {
			EntityGem newGem = new EntityRuby(worldIn);
			for (String key : ModEntities.GEMS.keySet()) {
				try {
					if (this.getUnlocalizedName().contains(key)) {
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
	    		newGem.onInitialSpawn(worldIn.getDifficultyForLocation(blockpos), null);
	    		if (playerIn != null && !newGem.isDiamond) {
	    			newGem.setOwnerId(EntityPlayer.getUUID(playerIn.getGameProfile()));
	    			newGem.setLeader(playerIn);
	        		newGem.setServitude(EntityGem.SERVE_HUMAN);
	            	newGem.getNavigator().clearPathEntity();
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
			worldIn.spawnEntity(newGem);
			return true;
		}
	}
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.isDead = false;
		entity.setEntityInvulnerable(true);
		entity.extinguish();
		if (!this.isCracked && entity.ticksExisted > 1200 && !entity.world.isRemote) {
			ItemGem gem = (ItemGem) entity.getItem().getItem();
			boolean spawned = gem.spawnGem(entity.world, null, entity.getPosition(), entity.getItem());
			if (spawned) {
				entity.setDead();
			}
		}
		else if (this.isCracked && entity.ticksExisted > 4800) {
			entity.setNoDespawn();
		}
        return false;
    }
}
