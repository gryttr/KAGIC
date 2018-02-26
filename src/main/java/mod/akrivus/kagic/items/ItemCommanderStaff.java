package mod.akrivus.kagic.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCommanderStaff extends Item {
	public ItemCommanderStaff() {
		super();
		this.setUnlocalizedName("commander_staff");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setMaxStackSize(1);
	}
	
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        stack.setTagCompound(new NBTTagCompound());
        this.setOwner(EntityPlayer.getUUID(playerIn.getGameProfile()), stack);
        stack.setStackDisplayName(new TextComponentTranslation("item.commander_staff.signedname", playerIn.getName()).getUnformattedComponentText());
    }
	
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this.getOwner(playerIn.getHeldItem(hand)) == null) {
			this.setOwner(EntityPlayer.getUUID(playerIn.getGameProfile()), playerIn.getHeldItem(hand));
			playerIn.getHeldItem(hand).setStackDisplayName(new TextComponentTranslation("item.commander_staff.signedname", playerIn.getName()).getUnformattedComponentText());
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00a7a" + (this.getOwner(stack) == null ? new TextComponentTranslation("command.kagic.right_click_to_bind").getUnformattedComponentText() : new TextComponentTranslation("command.kagic.bound").getUnformattedComponentText()));
	}
	
	public UUID getOwner(ItemStack stack) {
        return (stack.getTagCompound() != null && stack.getTagCompound().hasKey("ownerId")) ? UUID.fromString(stack.getTagCompound().getString("ownerId")) : null;
	}
	
	public void setOwner(UUID ownerId, ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setString("ownerId", ownerId.toString());
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return this.getOwner(stack) != null;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
