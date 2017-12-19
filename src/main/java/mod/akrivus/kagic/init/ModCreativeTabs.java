package mod.akrivus.kagic.init;

import java.util.Comparator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabs extends CreativeTabs {
	public static final CreativeTabs CREATIVE_TAB_GEMS = new ModCreativeTabs("gems", 0);
	public static final CreativeTabs CREATIVE_TAB_OTHER = new ModCreativeTabs("other", 1);
	public static final CreativeTabs CREATIVE_TAB_MUSIC = new ModCreativeTabs("music", 2);
	private final int id;
	
	public ModCreativeTabs(String label, int id) {
		super(CreativeTabs.getNextID(), label);
		this.id = id;
	}
	
	@Override
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		super.displayAllRelevantItems(items);
		if (this == ModCreativeTabs.CREATIVE_TAB_OTHER) {
			items.add(FluidUtil.getFilledBucket(new FluidStack(ModBlocks.FLUID_ROSE_TEARS, 1000)));
		} else if (this == ModCreativeTabs.CREATIVE_TAB_GEMS) {
			ItemStack candyCaneGem = null, crackedCandyCaneGem = null;
			for (ItemStack stack : items) {
				if (stack.getItem() == ModItems.CANDY_CANE_JASPER_GEM) {
					candyCaneGem = stack;
				} else if (stack.getItem() == ModItems.CRACKED_CANDY_CANE_JASPER_GEM) {
					crackedCandyCaneGem = stack;
				}
			}
			items.remove(candyCaneGem);
			items.remove(crackedCandyCaneGem);
		}
		items.sort(new GemItemStackComparator());
	}

	class GemItemStackComparator implements Comparator<ItemStack> {

		@Override
		public int compare(ItemStack arg0, ItemStack arg1) {
			if (arg0.equals(arg1)) {
				return 0;
			}
			
			Item item0 = arg0.getItem();
			Item item1 = arg1.getItem();
			if (item0.equals(item1)) {
				return 0;
			}
			
			String name0 = item0.getUnlocalizedName().substring(5);
			String name1 = item1.getUnlocalizedName().substring(5);
			
			if (name0.startsWith("cracked_")) {
				if (name1.startsWith("cracked_")) {
					return name0.compareToIgnoreCase(name1);
				} else {
					if (name0.substring(8).equalsIgnoreCase(name1)) {
						return 1;
					} else {
						return name0.substring(8).compareToIgnoreCase(name1);
					}
				}
			}
			
			if (name1.startsWith("cracked_")) {
				if (name0.startsWith("cracked_")) {
					return name0.compareToIgnoreCase(name1);
				} else {
					if (name1.substring(8).equalsIgnoreCase(name0)) {
						return -1;
					} else {
						return name0.compareToIgnoreCase(name1.substring(8));
					}
				}
			}
			
			return name0.compareToIgnoreCase(name1);
		}
		
	}
}
