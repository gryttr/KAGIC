package mod.akrivus.kagic.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs extends CreativeTabs {
	public static final CreativeTabs CREATIVE_TAB_GEMS = new ModCreativeTabs("gems", 0);
	public static final CreativeTabs CREATIVE_TAB_OTHER = new ModCreativeTabs("other", 1);
	public static final CreativeTabs CREATIVE_TAB_MUSIC = new ModCreativeTabs("music", 2);
	private final int id;
	public ModCreativeTabs(String label, int id) {
		super(CreativeTabs.getNextID(), label);
		this.id = id;
	}
	public ItemStack getTabIconItem() {
		switch (this.id) {
		case 0:
			return new ItemStack(ModItems.YELLOW_DIAMOND_GEM);
		case 1:
			return new ItemStack(ModItems.GEM_STAFF);
		case 2:
			return new ItemStack(ModItems.RECORD_LAPIS_FLIGHT);
		}
		return new ItemStack(ModItems.CRACKED_YELLOW_DIAMOND_GEM);
	}
	
}
