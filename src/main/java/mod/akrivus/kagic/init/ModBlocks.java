package mod.akrivus.kagic.init;

import mod.akrivus.kagic.blocks.BlockGemDrill;
import mod.akrivus.kagic.blocks.BlockGemSeed;
import mod.akrivus.kagic.blocks.BlockIncubator;
import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.blocks.BlockRockMelt;
import mod.akrivus.kagic.blocks.BlockVarying;
import mod.akrivus.kagic.blocks.BlockWarpPadCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ModBlocks {
	public static final BlockGemSeed GEM_SEED = new BlockGemSeed();
	public static final ItemBlock GEM_SEED_BLOCK = new ItemBlock(GEM_SEED);
	public static final BlockGemDrill GEM_DRILL = new BlockGemDrill();
	public static final BlockInjector INJECTOR = new BlockInjector(false);
	public static final BlockInjector EQUIPPED_INJECTOR = new BlockInjector(true);
	public static final BlockIncubator INCUBATOR = new BlockIncubator();
	public static final BlockVarying DRAINED_BLOCK = new BlockVarying("drained_block", 1, 40, 1);
	public static final BlockVarying SMOOTH_CARBONITE = new BlockVarying("smooth_carbonite", 2, 80, 1);
	public static final BlockVarying CHISELED_CARBONITE = new BlockVarying("chiseled_carbonite", 2, 80, 1);
	public static final BlockRockMelt ROCK_MELT = new BlockRockMelt(true);
	public static final BlockRockMelt RUTILE_TRAIL = new BlockRockMelt(false);
	public static final BlockWarpPadCore WARP_PAD_CORE = new BlockWarpPadCore();
	
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registerBlock(GEM_SEED, new ResourceLocation("kagic:gem_seed"), event);
		registerBlock(GEM_DRILL, new ResourceLocation("kagic:gem_drill"), event);
		registerBlock(INJECTOR, new ResourceLocation("kagic:injector"), event);
		registerBlock(EQUIPPED_INJECTOR, new ResourceLocation("kagic:equipped_injector"), event);
		registerBlock(INCUBATOR, new ResourceLocation("kagic:incubator"), event);
		registerBlock(DRAINED_BLOCK, new ResourceLocation("kagic:drained_block"), event);
		registerBlock(SMOOTH_CARBONITE, new ResourceLocation("kagic:smooth_carbonite"), event);
		registerBlock(CHISELED_CARBONITE, new ResourceLocation("kagic:chiseled_carbonite"), event);
		registerBlock(ROCK_MELT, new ResourceLocation("kagic:rock_melt"), event);
		registerBlock(RUTILE_TRAIL, new ResourceLocation("kagic:rutile_trail"), event);
		registerBlock(WARP_PAD_CORE, new ResourceLocation("kagic:warp_pad_core"), event);
	}
	
	public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
		block.setRegistryName(location);
		event.getRegistry().register(block);
	}
	
	public static void registerBlockItems(RegistryEvent.Register<Item> event) {
		registerBlockItem(GEM_SEED, new ResourceLocation("kagic:gem_seed"), event);
		registerBlockItem(GEM_DRILL, new ResourceLocation("kagic:gem_drill"), event);
		registerBlockItem(INJECTOR, new ResourceLocation("kagic:injector"), event);
		registerBlockItem(EQUIPPED_INJECTOR, new ResourceLocation("kagic:equipped_injector"), event);
		registerBlockItem(INCUBATOR, new ResourceLocation("kagic:incubator"), event);
		registerBlockItem(DRAINED_BLOCK, new ResourceLocation("kagic:drained_block"), event);
		registerBlockItem(SMOOTH_CARBONITE, new ResourceLocation("kagic:smooth_carbonite"), event);
		registerBlockItem(CHISELED_CARBONITE, new ResourceLocation("kagic:chiseled_carbonite"), event);
		registerBlockItem(ROCK_MELT, new ResourceLocation("kagic:rock_melt"), event);
		registerBlockItem(RUTILE_TRAIL, new ResourceLocation("kagic:rutile_trail"), event);
		registerBlockItem(WARP_PAD_CORE, new ResourceLocation("kagic:warp_pad_core"), event);		
	}
	
	public static void registerBlockItem(Block block, ResourceLocation location, RegistryEvent.Register<Item> event) {
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(location);
		event.getRegistry().register(item);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
