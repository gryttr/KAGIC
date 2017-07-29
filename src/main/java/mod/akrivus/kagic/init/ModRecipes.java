package mod.akrivus.kagic.init;

import mod.akrivus.kagic.items.crafting.RepairGemsRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ModRecipes {
	public static void register() {
		registerTypes();
		registerRecipes();
	}
	public static void registerTypes() {
		RecipeSorter.register("repair_gems", RepairGemsRecipes.class, Category.SHAPELESS, "");
	}
	public static void registerRecipes() {
		GameRegistry.addRecipe(new RepairGemsRecipes());
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.TRANSFER_CONTRACT), Items.DIAMOND, Items.WRITABLE_BOOK);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.JOINT_CONTRACT), ModItems.TRANSFER_CONTRACT, Items.REDSTONE);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.LIBERATION_CONTRACT), ModItems.TRANSFER_CONTRACT, Items.GUNPOWDER);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.AUTONOMY_CONTRACT), ModItems.LIBERATION_CONTRACT, Items.REDSTONE);
		GameRegistry.addRecipe(new ItemStack(ModItems.GEM_STAFF), "D  ", " B ", "  B", 'D', Items.DIAMOND, 'B', Items.BLAZE_ROD);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.INJECTOR), "IHI", "GCG", "IPI", 'I', Items.IRON_INGOT, 'H', new ItemStack(Blocks.HOPPER), 'G', new ItemStack(Blocks.STAINED_GLASS, 1, 2), 'C', Items.COMPARATOR, 'P', new ItemStack(Blocks.PISTON));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.GEM_DRILL), "III", "IHI", " D ", 'I', Items.IRON_INGOT, 'H', new ItemStack(Blocks.HOPPER), 'D', Items.DIAMOND);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.INCUBATOR), "GGG", "GCG", "GHG", 'G', new ItemStack(Blocks.STAINED_GLASS, 1, 2), 'C', new ItemStack(Blocks.DISPENSER), 'H', new ItemStack(Blocks.HOPPER));
		GameRegistry.addRecipe(new ItemStack(ModItems.ACTIVATED_GEM_BASE), "###", "###", "###", '#', ModItems.ACTIVATED_GEM_SHARD);
		GameRegistry.addRecipe(new ItemStack(ModItems.INACTIVE_GEM_BASE), "PSP", "GRG", "PEP", 'P', Items.BLAZE_POWDER, 'S', new ItemStack(Blocks.SOUL_SAND), 'G', Items.GLOWSTONE_DUST, 'R', Items.REDSTONE, 'E', Items.EMERALD);
		GameRegistry.addSmelting(new ItemStack(ModItems.INACTIVE_GEM_BASE), new ItemStack(ModItems.ACTIVATED_GEM_BASE), 2000);
		GameRegistry.addSmelting(new ItemStack(ModItems.TRANSFER_CONTRACT), new ItemStack(Items.DIAMOND), 200);
		GameRegistry.addSmelting(new ItemStack(ModItems.JOINT_CONTRACT), new ItemStack(Items.DIAMOND), 200);
		GameRegistry.addSmelting(new ItemStack(ModItems.LIBERATION_CONTRACT), new ItemStack(Items.DIAMOND), 200);
		GameRegistry.addSmelting(new ItemStack(ModItems.AUTONOMY_CONTRACT), new ItemStack(Items.DIAMOND), 200);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.DRAINED_BLOCK, 3), new ItemStack(ModBlocks.SMOOTH_CARBONITE), 0);
	}
}
