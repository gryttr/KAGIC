package mod.akrivus.kagic.init;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.EntityLaser;
import mod.akrivus.kagic.entity.EntitySlag;
import mod.akrivus.kagic.entity.EntitySteven;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityCarnelian;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPadparadscha;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import mod.akrivus.kagic.entity.pepo.EntityCactus;
import mod.akrivus.kagic.entity.pepo.EntityMelon;
import mod.akrivus.kagic.entity.pepo.EntityPumpkin;
import mod.akrivus.kagic.entity.shardfusion.EntityFootArm;
import mod.akrivus.kagic.entity.shardfusion.EntityHandBody;
import mod.akrivus.kagic.entity.shardfusion.EntityMouthTorso;
import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class ModEntities {
	public static final HashMap<String, Class<? extends EntityGem>> GEMS = new HashMap<String, Class<? extends EntityGem>>();
	public static final ArrayList<Class<? extends EntityGem>> MINERALS = new ArrayList<Class<? extends EntityGem>>();
	public static final boolean GENERATE_FACTORIES_INSTEAD_OF_INSTANCES = false;	// Switches to the new system, which doesn't work right now.
	private static int currentID = 0;
	
	public static void register() {
		registerGem("ruby", EntityRuby.class, 0xE52C5C, 0x3A0015, true);
		registerGem("sapphire", EntitySapphire.class, 0xBAF5FD, 0x7298EC, false);
		registerGem("padparadscha", EntityPadparadscha.class, 0xFF8065, 0xFFB3A4, true);
		registerGem("pearl", EntityPearl.class, 0xFCCCB1, 0x92EAD9, true);
		registerGem("bismuth", EntityBismuth.class, 0x91A8CF, 0x9C5867, false);
		registerGem("peridot", EntityPeridot.class, 0x98FF72, 0x13BA54, true);
		registerGem("jasper", EntityJasper.class, 0xF89E57, 0xE13941, true);
		registerGem("amethyst", EntityAmethyst.class, 0xEAE2FF, 0xC49EDB, true);
		registerGem("rose_quartz", EntityRoseQuartz.class, 0xFEDED3, 0xE99CBE, false);
		registerGem("lapis_lazuli", EntityLapisLazuli.class, 0x4FEEFB, 0x1B6AD6, false);
		registerGem("carnelian", EntityCarnelian.class, 0xC13178, 0x510245, true);
		registerGem("agate", EntityAgate.class, 0xB3DEFF, 0x1B50D0, true);
		registerGem("aquamarine", EntityAquamarine.class, 0x8AFFFF, 0x0487E3, false);
		registerGem("topaz", EntityTopaz.class, 0xF5FC51, 0xFDFEA4, false);
		registerGem("rutile", EntityRutile.class, 0xD2508C, 0x23020D, false);
		registerGem("zircon", EntityZircon.class, 0x458FBE, 0x57C7CF, false);
		registerDiamond("yellow_diamond", EntityYellowDiamond.class);
		registerDiamond("blue_diamond", EntityBlueDiamond.class);
		
		registerDiamond("handbody", EntityHandBody.class);
		registerDiamond("footarm", EntityFootArm.class);
		registerDiamond("mouthtorso", EntityMouthTorso.class);
		
		registerMob("melon", EntityMelon.class, 0xB5B128, 0x5A671A);
		registerMob("pumpkin", EntityPumpkin.class, 0xD58116, 0x744E03);
		registerMob("cactus", EntityCactus.class, 0x138622, 0xD9DB9F);
		registerMob("slag", EntitySlag.class, 0xFFFFFF, 0x00FF5D);
		registerMob("steven", EntitySteven.class, 0xFD6270, 0xFFD248);
		registerEntity("roaming_eye", EntityRoamingEye.class);
		registerEntity("laser", EntityLaser.class);
		registerGemYields();
		registerGemAddons();
	}
	public static void registerGemYields() {
		EntityRuby.RUBY_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.77);
		EntityRuby.RUBY_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.55);
		EntityRuby.RUBY_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.77);
		EntityRuby.RUBY_YIELDS.put(Blocks.NETHERRACK.getDefaultState(), 0.99);
		EntityRuby.RUBY_YIELDS.put(Blocks.REDSTONE_ORE.getDefaultState(), 0.99);
		EntityRuby.RUBY_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 0.99);
		EntityRuby.RUBY_YIELDS.put(Blocks.REDSTONE_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityRuby.RUBY_YIELDS, "Ruby", "Corundum", "Aluminium", "Aluminum", "Bauxite", "Cinnabar", "Chromite", "Chromium", "Garnet");
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.ICE.getDefaultState(), 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.22);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.PACKED_ICE.getDefaultState(), 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.SNOW.getDefaultState(), 0.77);
		ModEntities.registerWithOreDictionary(EntitySapphire.SAPPHIRE_YIELDS, "Sapphire", "Corundum", "Aluminium", "Aluminum", "Bauxite", "Garnet");
		EntityPearl.PEARL_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.77);
		EntityPearl.PEARL_YIELDS.put(Blocks.COAL_ORE.getDefaultState(), 0.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.55);
		EntityPearl.PEARL_YIELDS.put(Blocks.SAND.getDefaultState(), 0.77);
		EntityPearl.PEARL_YIELDS.put(Blocks.SOUL_SAND.getDefaultState(), 0.44);
		EntityPearl.PEARL_YIELDS.put(Blocks.WATER.getDefaultState(), 0.55);
		EntityPearl.PEARL_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState(), 1.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.COAL_BLOCK.getDefaultState(), 5.99);
		
		EntityPearl.PEARL_YIELDS.put(Blocks.BLACK_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.BLUE_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.RED_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.YELLOW_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.GREEN_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.LIME_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.LIGHT_BLUE_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.WHITE_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.ORANGE_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.BROWN_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.GRAY_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.SILVER_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.CYAN_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.MAGENTA_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.PINK_SHULKER_BOX.getDefaultState(), 5.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.PURPLE_SHULKER_BOX.getDefaultState(), 5.99);
		
		ModEntities.registerWithOreDictionary(EntityPearl.PEARL_YIELDS, "Pearl", "Salt", "Calcite", "Amber");
		EntityBismuth.BISMUTH_YIELDS.put(Blocks.DIAMOND_ORE.getDefaultState(), 0.99);
		EntityBismuth.BISMUTH_YIELDS.put(Blocks.GOLD_ORE.getDefaultState(), 0.99);
		EntityBismuth.BISMUTH_YIELDS.put(Blocks.GOLD_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityBismuth.BISMUTH_YIELDS, "Bismuth", "Lead", "Galena", "Platinum", "Tungsten", "Silver", "Tin", "Copper");
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.EMERALD_ORE.getDefaultState(), 0.11);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.33);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.NETHERRACK.getDefaultState(), 0.22);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.OBSIDIAN.getDefaultState(), 0.33);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 0.11);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.REDSTONE_ORE.getDefaultState(), 0.55);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.IRON_BLOCK.getDefaultState(), 5.99);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityPeridot.PERIDOT_YIELDS, "Peridot", "Olivine", "Copper", "Vanadium");
		EntityJasper.JASPER_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.22);
		EntityJasper.JASPER_YIELDS.put(Blocks.HARDENED_CLAY.getDefaultState(), 0.44);
		EntityJasper.JASPER_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.33);
		EntityJasper.JASPER_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 1.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.RED_SANDSTONE.getDefaultState(), 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.SANDSTONE.getDefaultState(), 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.STAINED_HARDENED_CLAY.getDefaultState(), 0.99);
		EntityJasper.JASPER_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 0.11);
		EntityJasper.JASPER_YIELDS.put(Blocks.QUARTZ_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityJasper.JASPER_YIELDS, "Jasper", "Quartz");
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.66);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 1.99);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 0.44);
		EntityAmethyst.AMETHYST_YIELDS.put(Blocks.QUARTZ_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityAmethyst.AMETHYST_YIELDS, "Amethyst", "Quartz");
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.11);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 1.99);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.SANDSTONE.getDefaultState(), 0.33);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 0.11);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.QUARTZ_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, "RoseQuartz", "Quartz");
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.LAPIS_ORE.getDefaultState(), 1.99);
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.PRISMARINE.getDefaultState(), 0.44);
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.WATER.getDefaultState(), 0.22);
		ModEntities.registerWithOreDictionary(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, "LapisLazuli", "Lapis", "Lazuli", "Pyrite", "Sodalite");
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.HARDENED_CLAY.getDefaultState(), 0.99);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.IRON_ORE.getDefaultState(), 0.11);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.NETHERRACK.getDefaultState(), 0.77);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 1.99);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.RED_SANDSTONE.getDefaultState(), 0.66);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.REDSTONE_ORE.getDefaultState(), 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), 0.33);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.QUARTZ_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityCarnelian.CARNELIAN_YIELDS, "Carnelian", "Quartz");
		EntityAgate.AGATE_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.82);
		EntityAgate.AGATE_YIELDS.put(Blocks.OBSIDIAN.getDefaultState(), 0.99);
		EntityAgate.AGATE_YIELDS.put(Blocks.QUARTZ_ORE.getDefaultState(), 0.99);
		EntityAgate.AGATE_YIELDS.put(Blocks.SOUL_SAND.getDefaultState(), 0.77);
		EntityAgate.AGATE_YIELDS.put(Blocks.QUARTZ_BLOCK.getDefaultState(), 5.99);
		EntityAgate.AGATE_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityAgate.AGATE_YIELDS, "Agate", "Quartz", "Chalcedony", "Onyx");
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.ICE.getDefaultState(), 0.33);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.OBSIDIAN.getDefaultState(), 0.66);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.PACKED_ICE.getDefaultState(), 0.99);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.PRISMARINE.getDefaultState(), 1.99);
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.WATER.getDefaultState(), 0.11);
		ModEntities.registerWithOreDictionary(EntityAquamarine.AQUAMARINE_YIELDS, "Aquamarine", "Beryl");
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.22);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.GLOWSTONE.getDefaultState(), 0.99);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.GOLD_ORE.getDefaultState(), 0.45);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), 0.99);
		EntityTopaz.TOPAZ_YIELDS.put(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE_SMOOTH), 1.99);
		ModEntities.registerWithOreDictionary(EntityTopaz.TOPAZ_YIELDS, "Topaz", "BlueTopaz", "Sulfur");
		EntityRutile.RUTILE_YIELDS.put(Blocks.GLOWSTONE.getDefaultState(), 0.99);
		EntityRutile.RUTILE_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.50);
		EntityRutile.RUTILE_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.75);
		EntityRutile.RUTILE_YIELDS.put(Blocks.REDSTONE_ORE.getDefaultState(), 0.99);
		EntityRutile.RUTILE_YIELDS.put(Blocks.REDSTONE_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityRutile.RUTILE_YIELDS, "Rutile", "Titanium", "Apatite");
		EntityZircon.ZIRCON_YIELDS.put(Blocks.DIAMOND_ORE.getDefaultState(), 1.99);
		EntityZircon.ZIRCON_YIELDS.put(Blocks.DIAMOND_BLOCK.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityZircon.ZIRCON_YIELDS, "Zircon");
	}
	public static void registerWithOreDictionary(HashMap<IBlockState, Double> yields, String... keys) {
		NonNullList<ItemStack> totalCruxes = NonNullList.create();
		for (String suffix : keys) {
			totalCruxes.addAll(OreDictionary.getOres("ore" + suffix));
		}
		for (ItemStack stack : totalCruxes) {
			if (stack.getItem() instanceof ItemBlock) {
				IBlockState blockState = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
				yields.put(blockState, 1.99);
			}
		}
		NonNullList<ItemStack> synthCruxes = NonNullList.create();
		for (String suffix : keys) {
			synthCruxes.addAll(OreDictionary.getOres("block" + suffix));
		}
		for (ItemStack stack : synthCruxes) {
			if (stack.getItem() instanceof ItemBlock) {
				IBlockState blockState = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
				yields.put(blockState, 5.99);
			}
		}
	}
	public static void registerGemAddons() {
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/holly.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/back.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/top.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/spikes.png"));
		EntityAgate.AGATE_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/hair/axis.png"));
 		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/holly.png"));
 		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/back.png"));
 		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/top.png"));
 		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/spikes.png"));
 		EntityAgate.AGATE_BAND_STYLES.add(new ResourceLocation("kagic:textures/entities/agate/band/axis.png"));
  		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/blue.png"));
  		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/brows.png"));
  		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/curly.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/blue.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/brows.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/curly.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/messy.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/mop.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/sides.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/swoop.png"));
		EntityPearl.PEARL_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/hair/yellow.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/dumpy.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/flower.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/jumpsuit.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/leather.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/legacy.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/maid.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/seethrough.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/servant.png"));
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/spacesuit.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/cowl.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/combover.png"));
		EntityZircon.ZIRCON_HAIR_STYLES.add(new ResourceLocation("kagic:textures/entities/zircon/hair/smooth.png"));
	}
	@SuppressWarnings({ "unchecked" })
	public static <T extends EntityGem> void registerGem(String name, Class<T> entity, int back, int fore, boolean mineral) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true, back, fore);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				if (GENERATE_FACTORIES_INSTEAD_OF_INSTANCES) {
					IRenderFactory<T> factory = null;
	                try {
	                    MethodHandles.Lookup lookup = MethodHandles.lookup();
	                    MethodHandle constructor = lookup.findConstructor(render, MethodType.methodType(void.class, String.class));
	                    MethodType type = constructor.type().changeReturnType(IRenderFactory.class);
	                    factory = (IRenderFactory<T>) LambdaMetafactory.metafactory(lookup, "getInstance", MethodType.methodType(IRenderFactory.class), type, constructor, type).getTarget().invokeExact();
	                }
	                catch (Throwable t) {
	                	CrashReport.makeCrashReport(t, "Something when wrong registering a Gem.");
	                }
	                RenderingRegistry.registerEntityRenderingHandler(entity, factory);
				}
				else {
					RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		GEMS.put(name, entity);
		if (mineral) {
			MINERALS.add(entity);
		}
		++currentID;
	}
	
	//Just like registerGem, but no spawn egg
	@SuppressWarnings({ "unchecked" })
	public static <T extends EntityGem> void registerDiamond(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true);
	 	if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				if (GENERATE_FACTORIES_INSTEAD_OF_INSTANCES) {
					IRenderFactory<T> factory = null;
	                try {
	                    MethodHandles.Lookup lookup = MethodHandles.lookup();
	                    MethodHandle constructor = lookup.findConstructor(render, MethodType.methodType(void.class, String.class));
	                    MethodType type = constructor.type().changeReturnType(IRenderFactory.class);
	                    factory = (IRenderFactory<T>) LambdaMetafactory.metafactory(lookup, "getInstance", MethodType.methodType(IRenderFactory.class), type, constructor, type).getTarget().invokeExact();
	                }
	                catch (Throwable t) {
	                	CrashReport.makeCrashReport(t, "Something went wrong registering a Gem.");
	                }
	                RenderingRegistry.registerEntityRenderingHandler(entity, factory);
				}
				else {
					RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		GEMS.put(name, entity);
		++currentID;
	}

	@SuppressWarnings({ "unchecked" })
    public static <T extends EntityGem> void registerExternalGem(String prefix, String name, Class<T> entity, String renderpath, int back, int fore, boolean mineral) {
        EntityRegistry.registerModEntity(new ResourceLocation(prefix + ":" + name), entity, name, currentID, KAGIC.instance, 256, 1, true, back, fore);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            try {
                Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass(renderpath);
                if (GENERATE_FACTORIES_INSTEAD_OF_INSTANCES) {
					IRenderFactory<T> factory = null;
	                try {
	                    MethodHandles.Lookup lookup = MethodHandles.lookup();
	                    MethodHandle constructor = lookup.findConstructor(render, MethodType.methodType(void.class, String.class));
	                    MethodType type = constructor.type().changeReturnType(IRenderFactory.class);
	                    factory = (IRenderFactory<T>) LambdaMetafactory.metafactory(lookup, "getInstance", MethodType.methodType(IRenderFactory.class), type, constructor, type).getTarget().invokeExact();
	                }
	                catch (Throwable t) {
	                	CrashReport.makeCrashReport(t, "Something went wrong registering a Gem.");
	                }
	                RenderingRegistry.registerEntityRenderingHandler(entity, factory);
				}
				else {
					RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
				}
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        GEMS.put(name, entity);
        if (mineral) {
			MINERALS.add(entity);
		}
        ++currentID;
    }
	public static <T extends EntityGem> void registerExternalGem(String prefix, String name, Class<T> entity, String renderpath, int back, int fore) {
		ModEntities.registerExternalGem(prefix, name, entity, renderpath, back, fore, false);
	}
	@SuppressWarnings({ "unchecked" })
	public static <T extends Entity> void registerMob(String name, Class<T> entity, int back, int fore) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true, back, fore);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				if (GENERATE_FACTORIES_INSTEAD_OF_INSTANCES) {
					IRenderFactory<T> factory = null;
	                try {
	                    MethodHandles.Lookup lookup = MethodHandles.lookup();
	                    MethodHandle constructor = lookup.findConstructor(render, MethodType.methodType(void.class, String.class));
	                    MethodType type = constructor.type().changeReturnType(IRenderFactory.class);
	                    factory = (IRenderFactory<T>) LambdaMetafactory.metafactory(lookup, "getInstance", MethodType.methodType(IRenderFactory.class), type, constructor, type).getTarget().invokeExact();
	                }
	                catch (Throwable t) {
	                	CrashReport.makeCrashReport(t, "Something went wrong registering a mob.");
	                }
	                RenderingRegistry.registerEntityRenderingHandler(entity, factory);
				}
				else {
					RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
	@SuppressWarnings({ "unchecked" })
	public static <T extends Entity> void registerEntity(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic." + name), entity, "kagic." + name, currentID, KAGIC.instance, 256, 1, true);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/" + entity.getName().replaceAll(".+?Entity", "Render"));
				if (GENERATE_FACTORIES_INSTEAD_OF_INSTANCES) {
					IRenderFactory<T> factory = null;
	                try {
	                    MethodHandles.Lookup lookup = MethodHandles.lookup();
	                    MethodHandle constructor = lookup.findConstructor(render, MethodType.methodType(void.class, String.class));
	                    MethodType type = constructor.type().changeReturnType(IRenderFactory.class);
	                    factory = (IRenderFactory<T>) LambdaMetafactory.metafactory(lookup, "getInstance", MethodType.methodType(IRenderFactory.class), type, constructor, type).getTarget().invokeExact();
	                }
	                catch (Throwable t) {
	                	CrashReport.makeCrashReport(t, "Something went wrong registering an entity.");
	                }
	                RenderingRegistry.registerEntityRenderingHandler(entity, factory);
				}
				else {
					RenderingRegistry.registerEntityRenderingHandler(entity, render.newInstance());
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		++currentID;
	}
}
