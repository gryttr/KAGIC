package mod.akrivus.kagic.items;

import mod.akrivus.kagic.entity.humans.EntitySteven;
import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTimeGlass extends Item {
	public ItemTimeGlass() {
		this.setUnlocalizedName("time_glass");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setMaxStackSize(1);
		this.setMaxDamage(1);
	}
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(hand);
			Entity spawningEntity = new EntitySteven(worldIn);
			spawningEntity.setPosition(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
			worldIn.spawnEntity(spawningEntity);
			if (!playerIn.capabilities.isCreativeMode) {
				stack.damageItem(1, playerIn);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
