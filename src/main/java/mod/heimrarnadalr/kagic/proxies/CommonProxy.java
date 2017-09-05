package mod.heimrarnadalr.kagic.proxies;

import java.util.LinkedHashMap;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModEnchantments;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

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
	
	public void openWarpPadSelectionGUI(LinkedHashMap<BlockPos, WarpPadDataEntry> padData, int x, int y, int z) {

	}
}
