package mod.akrivus.kagic.init;

import mod.akrivus.kagic.blocks.BlockGemDrill;
import mod.akrivus.kagic.blocks.BlockGemSeed;
import mod.akrivus.kagic.blocks.BlockIncubator;
import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.blocks.BlockRockMelt;
import mod.akrivus.kagic.blocks.BlockVarying;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
	public static void register() {
		registerBlock(GEM_SEED, new ResourceLocation("kagic:gem_seed"));
		registerBlock(GEM_DRILL, new ResourceLocation("kagic:gem_drill"));
		registerBlock(INJECTOR, new ResourceLocation("kagic:injector"));
		registerBlock(EQUIPPED_INJECTOR, new ResourceLocation("kagic:equipped_injector"));
		registerBlock(INCUBATOR, new ResourceLocation("kagic:incubator"));
		registerBlock(DRAINED_BLOCK, new ResourceLocation("kagic:drained_block"));
		registerBlock(SMOOTH_CARBONITE, new ResourceLocation("kagic:smooth_carbonite"));
		registerBlock(CHISELED_CARBONITE, new ResourceLocation("kagic:chiseled_carbonite"));
		registerBlock(ROCK_MELT, new ResourceLocation("kagic:rock_melt"));
		registerBlock(RUTILE_TRAIL, new ResourceLocation("kagic:rutile_trail"));
	}
	public static void registerBlock(Block block, ResourceLocation location) {
		ItemBlock item = new ItemBlock(block);
		GameRegistry.register(block, location);
		GameRegistry.register(item, location);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
