package mod.akrivus.kagic.init;

import mod.akrivus.kagic.blocks.BlockGalaxyPadCore;
import mod.akrivus.kagic.blocks.BlockGemDrill;
import mod.akrivus.kagic.blocks.BlockGemSeed;
import mod.akrivus.kagic.blocks.BlockIncubator;
import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.blocks.BlockPinkSandstone;
import mod.akrivus.kagic.blocks.BlockPinkSandstoneDoubleSlab;
import mod.akrivus.kagic.blocks.BlockPinkSandstoneSlab;
import mod.akrivus.kagic.blocks.BlockPinkSandstoneStairs;
import mod.akrivus.kagic.blocks.BlockRockMelt;
import mod.akrivus.kagic.blocks.BlockRoseTears;
import mod.akrivus.kagic.blocks.BlockVarying;
import mod.akrivus.kagic.blocks.BlockWarpPadCore;
import mod.akrivus.kagic.client.model.FluidModelMapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
	public static final BlockVarying DRAINED_BLOCK_2 = new BlockVarying("drained_block_2", 1, 40, 1);
	public static final BlockVarying DRAINED_BANDS = new BlockVarying("drained_bands", 1, 40, 1);
	public static final BlockVarying SMOOTH_CARBONITE = new BlockVarying("smooth_carbonite", 2, 80, 1);
	public static final BlockVarying CHISELED_CARBONITE = new BlockVarying("chiseled_carbonite", 2, 80, 1);
	public static final BlockRockMelt ROCK_MELT = new BlockRockMelt(true);
	public static final BlockRockMelt RUTILE_TRAIL = new BlockRockMelt(false);
	public static final BlockWarpPadCore WARP_PAD_CORE = new BlockWarpPadCore();
	public static final BlockGalaxyPadCore GALAXY_PAD_CORE = new BlockGalaxyPadCore();
	public static final BlockPinkSandstone PINK_SANDSTONE = new BlockPinkSandstone();
	public static final BlockPinkSandstoneStairs PINK_SANDSTONE_STAIRS = new BlockPinkSandstoneStairs();
	public static final BlockPinkSandstoneSlab PINK_SANDSTONE_SLAB = new BlockPinkSandstoneSlab();
	public static final BlockPinkSandstoneDoubleSlab PINK_SANDSTONE_DOUBLE_SLAB = new BlockPinkSandstoneDoubleSlab();
	
	public static final Fluid FLUID_ROSE_TEARS = new Fluid("rose_tears", new ResourceLocation("kagic:blocks/rose_tears_still"), new ResourceLocation("kagic:blocks/rose_tears_flowing"));
	public static BlockRoseTears ROSE_TEARS;
	
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		registerBlock(GEM_SEED, new ResourceLocation("kagic:gem_seed"), event);
		registerBlock(GEM_DRILL, new ResourceLocation("kagic:gem_drill"), event);
		registerBlock(INJECTOR, new ResourceLocation("kagic:injector"), event);
		registerBlock(EQUIPPED_INJECTOR, new ResourceLocation("kagic:equipped_injector"), event);
		registerBlock(INCUBATOR, new ResourceLocation("kagic:incubator"), event);
		registerBlock(DRAINED_BLOCK, new ResourceLocation("kagic:drained_block"), event);
		registerBlock(DRAINED_BLOCK_2, new ResourceLocation("kagic:drained_block_2"), event);
		registerBlock(DRAINED_BANDS, new ResourceLocation("kagic:drained_bands"), event);
		registerBlock(SMOOTH_CARBONITE, new ResourceLocation("kagic:smooth_carbonite"), event);
		registerBlock(CHISELED_CARBONITE, new ResourceLocation("kagic:chiseled_carbonite"), event);
		registerBlock(ROCK_MELT, new ResourceLocation("kagic:rock_melt"), event);
		registerBlock(RUTILE_TRAIL, new ResourceLocation("kagic:rutile_trail"), event);
		registerBlock(WARP_PAD_CORE, new ResourceLocation("kagic:warp_pad_core"), event);
		registerBlock(GALAXY_PAD_CORE, new ResourceLocation("kagic:galaxy_pad_core"), event);
		registerBlock(PINK_SANDSTONE, new ResourceLocation("kagic:pink_sandstone"), event);
		registerBlock(PINK_SANDSTONE_STAIRS, new ResourceLocation("kagic:pink_sandstone_stairs"), event);
		registerBlock(PINK_SANDSTONE_SLAB, new ResourceLocation("kagic:pink_sandstone_slab"), event);
		registerBlock(PINK_SANDSTONE_DOUBLE_SLAB, new ResourceLocation("kagic:pink_sandstone_double_slab"), event);

		registerFluid(FLUID_ROSE_TEARS);
		ROSE_TEARS = new BlockRoseTears(FLUID_ROSE_TEARS, Material.WATER);
		registerBlock(ROSE_TEARS, new ResourceLocation("kagic:rose_tears"), event);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomStateMapper(ModBlocks.ROSE_TEARS, new FluidModelMapper(ModBlocks.FLUID_ROSE_TEARS));
		}
	}
	
	public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
		block.setRegistryName(location);
		event.getRegistry().register(block);
	}
	
	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
	
	public static void registerBlockItems(RegistryEvent.Register<Item> event) {
		registerBlockItem(GEM_SEED, new ResourceLocation("kagic:gem_seed"), event);
		registerBlockItem(GEM_DRILL, new ResourceLocation("kagic:gem_drill"), event);
		registerBlockItem(INJECTOR, new ResourceLocation("kagic:injector"), event);
		registerBlockItem(EQUIPPED_INJECTOR, new ResourceLocation("kagic:equipped_injector"), event);
		registerBlockItem(INCUBATOR, new ResourceLocation("kagic:incubator"), event);
		registerBlockItem(DRAINED_BLOCK, new ResourceLocation("kagic:drained_block"), event);
		registerBlockItem(DRAINED_BLOCK_2, new ResourceLocation("kagic:drained_block_2"), event);
		registerBlockItem(DRAINED_BANDS, new ResourceLocation("kagic:drained_bands"), event);
		registerBlockItem(SMOOTH_CARBONITE, new ResourceLocation("kagic:smooth_carbonite"), event);
		registerBlockItem(CHISELED_CARBONITE, new ResourceLocation("kagic:chiseled_carbonite"), event);
		registerBlockItem(ROCK_MELT, new ResourceLocation("kagic:rock_melt"), event);
		registerBlockItem(RUTILE_TRAIL, new ResourceLocation("kagic:rutile_trail"), event);
		registerBlockItem(WARP_PAD_CORE, new ResourceLocation("kagic:warp_pad_core"), event);		
		registerBlockItem(GALAXY_PAD_CORE, new ResourceLocation("kagic:galaxy_pad_core"), event);		
		registerPinkSandstoneItems(event);
		registerBlockItem(PINK_SANDSTONE_STAIRS, new ResourceLocation("kagic:pink_sandstone_stairs"), event);		
		registerBlockItemSlab(PINK_SANDSTONE_SLAB, PINK_SANDSTONE_DOUBLE_SLAB, new ResourceLocation("kagic:pink_sandstone_slab"), event);		
	}
	
	public static void registerBlockItem(Block block, ResourceLocation location, RegistryEvent.Register<Item> event) {
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(location);
		event.getRegistry().register(item);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	public static void registerBlockItemSlab(BlockSlab singleSlab, BlockSlab doubleSlab, ResourceLocation location, RegistryEvent.Register<Item> event) {
		ItemSlab item = new ItemSlab(singleSlab, singleSlab, doubleSlab);
		item.setRegistryName(location);
		event.getRegistry().register(item);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	public static void registerPinkSandstoneItems(RegistryEvent.Register<Item> event) {
		ItemMultiTexture item = (ItemMultiTexture) (new ItemMultiTexture(PINK_SANDSTONE, PINK_SANDSTONE, new ItemMultiTexture.Mapper()
        {
            public String apply(ItemStack p_apply_1_)
            {
                return BlockPinkSandstone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
            }
        })).setUnlocalizedName(PINK_SANDSTONE.getUnlocalizedName());
		item.setRegistryName(new ResourceLocation("kagic:pink_sandstone"));
		event.getRegistry().register(item);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("kagic:pink_sandstone", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation("kagic:chiseled_pink_sandstone", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation("kagic:smooth_pink_sandstone", "inventory"));
		}
	}
}
