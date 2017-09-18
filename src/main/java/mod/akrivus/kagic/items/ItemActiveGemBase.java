package mod.akrivus.kagic.items;

import mod.akrivus.kagic.init.ModCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemActiveGemBase extends Item {
	public ItemActiveGemBase() {
		super();
		this.setUnlocalizedName("active_gem_base");
		this.setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
		this.setMaxStackSize(16);
	}
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		//playerIn.addStat(ModAchievements.GEM_FORGER);
	}
}
