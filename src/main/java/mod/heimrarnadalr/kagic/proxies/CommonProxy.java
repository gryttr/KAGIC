package mod.heimrarnadalr.kagic.proxies;

import java.util.LinkedHashMap;

import mod.akrivus.kagic.crafting.RepairGemsRecipes;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModEnchantments;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.worlddata.GalaxyPadLocation;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModBlocks.registerBlockItems(event);
		ModItems.registerItems(event);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		ModSounds.registerSounds(event);
	}
	
	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		ModEnchantments.registerEnchantments(event);
	}
	
	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		ModBiomes.register(event);
	}
	
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		final IForgeRegistry<IRecipe> registry = ForgeRegistries.RECIPES;
		registry.register(new RepairGemsRecipes());
	}
	
	public void registerStateMappers() {

	}
	
	public void registerBlockColors() {
		
	}
	
	@SubscribeEvent
	public static void changeConfigs(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(KAGIC.MODID)) {
			ConfigManager.sync(KAGIC.MODID, Config.Type.INSTANCE);
		}
	}
	
	public void openWarpPadSelectionGUI(LinkedHashMap<BlockPos, WarpPadDataEntry> padData, int x, int y, int z) {

	}

	public void openGalaxyPadSelectionGUI(LinkedHashMap<GalaxyPadLocation, WarpPadDataEntry> padData, int x, int y, int z) {

	}
}
