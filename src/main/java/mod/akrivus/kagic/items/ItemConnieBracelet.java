package mod.akrivus.kagic.items;

import java.util.List;

import javax.annotation.Nullable;

import mod.akrivus.kagic.init.ModCreativeTabs;
import mod.akrivus.kagic.init.ModRecord;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConnieBracelet extends ModRecord {
	public ItemConnieBracelet() {
		super("connie_bracelet", ModSounds.RECORD_LOVE_LIKE_YOU, false);
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_GEMS);
	}
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EnumActionResult result = super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		if (result == EnumActionResult.PASS && !worldIn.isRemote) {
			ITextComponent message = new TextComponentTranslation(String.format("command.kagic.connie_doesnt_spawn"));
			message.getStyle().setColor(TextFormatting.GRAY);
			playerIn.sendMessage(message);
	       	return EnumActionResult.SUCCESS;
		}
		return result;
	}
	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.isDead = false;
		entity.setEntityInvulnerable(true);
		entity.extinguish();
        return false;
    }
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
    }
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }
}
