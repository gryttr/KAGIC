package mod.akrivus.kagic.init;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.EntityLaser;
import mod.akrivus.kagic.entity.EntitySlag;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomAgate;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomAquamarine;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomBismuth;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomDefectivePeridot;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomDefectiveQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomGarnet;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomHessonite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomLapisLazuli;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomMalachite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomOpal;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomPearl;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomPeridot;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRainbowQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRhodonite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRobeDiamond;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRuby;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRutile;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRutileTwins;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomSapphire;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomShoulderDiamond;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomTopaz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomZircon;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import mod.akrivus.kagic.entity.gem.EntityCarnelian;
import mod.akrivus.kagic.entity.gem.EntityCitrine;
import mod.akrivus.kagic.entity.gem.EntityEnderPearl;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.EntityHoloPearl;
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
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedAmethyst;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedCarnelian;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedJasper;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedMoissanite;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedRoseQuartz;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWaterBear;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWatermelonTourmaline;
import mod.akrivus.kagic.entity.gem.corrupted.EntityTongueMonster;
import mod.akrivus.kagic.entity.gem.fusion.EntityGarnet;
import mod.akrivus.kagic.entity.gem.fusion.EntityMalachite;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.entity.gem.fusion.EntityRhodonite;
import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import mod.akrivus.kagic.entity.pepo.EntityCactus;
import mod.akrivus.kagic.entity.pepo.EntityMelon;
import mod.akrivus.kagic.entity.pepo.EntityPumpkin;
import mod.akrivus.kagic.entity.pepo.EntityStrawberry;
import mod.akrivus.kagic.entity.shardfusion.EntityFootArm;
import mod.akrivus.kagic.entity.shardfusion.EntityHandBody;
import mod.akrivus.kagic.entity.shardfusion.EntityMouthTorso;
import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
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
		registerGem("hessonite", EntityHessonite.class, 0xBE331C, 0xEDCC41, false);
		//registerGem("citrine", EntityCitrine.class, 0xECF404, 0xEBFD64, false);
		registerGem("ender_pearl", EntityEnderPearl.class, 0x000000, 0xFF00FF, false);
		registerDiamond("yellow_diamond", EntityYellowDiamond.class);
		registerDiamond("blue_diamond", EntityBlueDiamond.class);
		
		// removed gem, still needs to be registered though
		registerDiamond("padparadscha", EntityPadparadscha.class);
		
		registerDiamond("handbody", EntityHandBody.class);
		registerDiamond("footarm", EntityFootArm.class);
		registerDiamond("mouthtorso", EntityMouthTorso.class);
		
		registerCorruptedGem("corrupted_amatista", EntityCorruptedAmethyst.class);
		registerCorruptedGem("corrupted_cornalina", EntityCorruptedCarnelian.class);
		registerCorruptedGem("corrupted_jasper", EntityCorruptedJasper.class);
		registerDiamond("corrupted_moissanita", EntityCorruptedMoissanite.class);
		registerCorruptedGem("corrupted_cuarzo_rosa", EntityCorruptedRoseQuartz.class);
		registerCorruptedGem("tongue_monster", EntityTongueMonster.class);
		registerCorruptedGem("water_bear", EntityCorruptedWaterBear.class);
		registerCorruptedGem("corrupted_watermelon_tourmaline", EntityCorruptedWatermelonTourmaline.class);

		registerDiamond("opal", EntityOpal.class);
		registerDiamond("garnet", EntityGarnet.class);
		registerDiamond("rhodonite", EntityRhodonite.class);
		registerDiamond("holopearl", EntityHoloPearl.class);
		registerDiamond("rainbow_quartz", EntityRainbowQuartz.class);
		registerDiamond("malachite", EntityMalachite.class);
		
		registerMob("melon", EntityMelon.class, 0xB5B128, 0x5A671A);
		registerMob("pumpkin", EntityPumpkin.class, 0xD58116, 0x744E03);
		registerMob("cactus", EntityCactus.class, 0x138622, 0xD9DB9F);
		registerMob("strawberry", EntityStrawberry.class, 0xEF4B69, 0x80855A);
		registerMob("slag", EntitySlag.class, 0xFFFFFF, 0x00FF5D);
		registerMob("steven", EntitySteven.class, 0xFD6270, 0xFFD248);
		registerMob("connie", EntityConnie.class, 0x99D3CD, 0xAF4E3D);
		registerEntity("roaming_eye", EntityRoamingEye.class);
		registerEntity("laser", EntityLaser.class);
		
		// custom npcs wrapper entities
		registerCustomEntity("agate", EntityCustomAgate.class);
		registerCustomEntity("aquamarine", EntityCustomAquamarine.class);
		registerCustomEntity("bismuth", EntityCustomBismuth.class);
		registerCustomEntity("defective_peridot", EntityCustomDefectivePeridot.class);
		registerCustomEntity("defective_quartz", EntityCustomDefectiveQuartz.class);
		registerCustomEntity("garnet", EntityCustomGarnet.class);
		registerCustomEntity("hessonite", EntityCustomHessonite.class);
		registerCustomEntity("lapis_lazuli", EntityCustomLapisLazuli.class);
		registerCustomEntity("malachite", EntityCustomMalachite.class);
		registerCustomEntity("opal", EntityCustomOpal.class);
		registerCustomEntity("pearl", EntityCustomPearl.class);
		registerCustomEntity("peridot", EntityCustomPeridot.class);
		registerCustomEntity("quartz", EntityCustomQuartz.class);
		registerCustomEntity("rainbow_quartz", EntityCustomRainbowQuartz.class);
		registerCustomEntity("rhodonite", EntityCustomRhodonite.class);
		registerCustomEntity("robe_diamond", EntityCustomRobeDiamond.class);
		registerCustomEntity("ruby", EntityCustomRuby.class);
		registerCustomEntity("rutile", EntityCustomRutile.class);
		registerCustomEntity("rutile_twins", EntityCustomRutileTwins.class);
		registerCustomEntity("sapphire", EntityCustomSapphire.class);
		registerCustomEntity("shoulder_diamond", EntityCustomShoulderDiamond.class);
		registerCustomEntity("topaz", EntityCustomTopaz.class);
		registerCustomEntity("zircon", EntityCustomZircon.class);
		
		//registerGemYields();
		registerGemAddons();
	}
	
	public static void registerGemYields() {
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.77, "oreIron");
		EntityRuby.RUBY_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.55);
		EntityRuby.RUBY_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.77);
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.99, "netherrack");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.99, "oreRedstone");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.11, "stoneGranite");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.44, "stoneGranitePolished");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.11, "stoneMarble");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.44, "stoneMarblePolished");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 5.99, "blockRedstone");
		ModEntities.registerWithOreDictionary(EntityRuby.RUBY_YIELDS, "Ruby", "Corundum", "Bauxite", "Cinnabar", "Chromite", "Chromium");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.33, "oreAluminum");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.33, "oreAluminium");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 0.99, "oreGarnet");
		ModEntities.registerOreDictValue(EntityRuby.RUBY_YIELDS, 1.99, "blockGarnet");

		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.ICE.getDefaultState(), 0.99);
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.11, "stoneMarble");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.44, "stoneMarblePolished");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.22, "oreIron");
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.PACKED_ICE.getDefaultState(), 0.99);
		EntitySapphire.SAPPHIRE_YIELDS.put(Blocks.SNOW.getDefaultState(), 0.77);
		ModEntities.registerWithOreDictionary(EntitySapphire.SAPPHIRE_YIELDS, "Sapphire", "VioletSapphire", "Corundum", "Bauxite");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.33, "oreAluminum");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.33, "oreAluminium");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 0.99, "oreGarnet");
		ModEntities.registerOreDictValue(EntitySapphire.SAPPHIRE_YIELDS, 1.99, "blockGarnet");

		EntityPearl.PEARL_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.77);
		ModEntities.registerOreDictValue(EntityPearl.PEARL_YIELDS, 0.99, "oreCoal");
		ModEntities.registerOreDictValue(EntityPearl.PEARL_YIELDS, 0.55, "endstone");
		ModEntities.registerOreDictValue(EntityPearl.PEARL_YIELDS, 0.77, "sand");
		EntityPearl.PEARL_YIELDS.put(Blocks.SOUL_SAND.getDefaultState(), 0.44);
		EntityPearl.PEARL_YIELDS.put(Blocks.WATER.getDefaultState(), 0.55);
		EntityPearl.PEARL_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X), 1.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y), 1.99);
		EntityPearl.PEARL_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z), 1.99);
		ModEntities.registerOreDictValue(EntityPearl.PEARL_YIELDS, 5.99, "blockCoal");
		
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
		
		EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 2.99);
		EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.99);
		
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 0.99, "oreDiamond");
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 0.99, "oreGold");
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 5.99, "blockGold");
		ModEntities.registerWithOreDictionary(EntityBismuth.BISMUTH_YIELDS, "Bismuth", "Silver");
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 0.11, "oreCopper");
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 0.11, "oreTin");
		ModEntities.registerOreDictValue(EntityBismuth.BISMUTH_YIELDS, 0.33, "oreLead");

		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.99, "endstone");
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.99, "oreIron");
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.33);
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.99);
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.22, "netherrack");
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.33, "obsidian");
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.55, "oreRedstone");
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 5.99, "blockIron");
		EntityPeridot.PERIDOT_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityPeridot.PERIDOT_YIELDS, "Peridot", "Olivine", "Vanadium");
		ModEntities.registerOreDictValue(EntityPeridot.PERIDOT_YIELDS, 0.33, "oreCopper");
		
		EntityJasper.JASPER_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.22);
		EntityJasper.JASPER_YIELDS.put(Blocks.HARDENED_CLAY.getDefaultState(), 0.44);
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.33, "oreIron");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 1.99, "oreQuartz");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.99, "sandstone");
		EntityJasper.JASPER_YIELDS.put(Blocks.STAINED_HARDENED_CLAY.getDefaultState(), 0.99);
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.11, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.11, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityJasper.JASPER_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityJasper.JASPER_YIELDS, "Jasper");
		
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 0.66, "oreIron");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 1.99, "oreQuartz");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 0.44, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 0.44, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityAmethyst.AMETHYST_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityAmethyst.AMETHYST_YIELDS, "Amethyst");
		
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, "oreIron");
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.PURPUR_BLOCK.getDefaultState(), 0.11);
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.PURPUR_PILLAR.getDefaultState(), 0.11);
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 1.99, "oreQuartz");
		EntityRoseQuartz.ROSE_QUARTZ_YIELDS.put(Blocks.SANDSTONE.getDefaultState(), 0.33);
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityRoseQuartz.ROSE_QUARTZ_YIELDS, "RoseQuartz");
		
		ModEntities.registerOreDictValue(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, 0.44, "blockPrismarine");
		EntityLapisLazuli.LAPIS_LAZULI_YIELDS.put(Blocks.WATER.getDefaultState(), 0.22);
		ModEntities.registerWithOreDictionary(EntityLapisLazuli.LAPIS_LAZULI_YIELDS, "LapisLazuli", "Lapis", "Lazuli", "Pyrite", "Sodalite");

		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.22);
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.HARDENED_CLAY.getDefaultState(), 0.99);
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.11, "oreIron");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.77, "netherrack");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 1.99, "oreQuartz");
		EntityCarnelian.CARNELIAN_YIELDS.put(Blocks.RED_SANDSTONE.getDefaultState(), 0.66);
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.22, "oreRedstone");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.33, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.33, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityCarnelian.CARNELIAN_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityCarnelian.CARNELIAN_YIELDS, "Carnelian");
		
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 1.99, "oreQuartz");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityCitrine.CITRINE_YIELDS, "Citrine");
		
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 0.88, "endstone");
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 0.99, "obsidian");
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 1.99, "oreQuartz");
		EntityAgate.AGATE_YIELDS.put(Blocks.SOUL_SAND.getDefaultState(), 0.77);
		ModEntities.registerOreDictValue(EntityAgate.AGATE_YIELDS, 5.99, "blockQuartz");
		EntityAgate.AGATE_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 5.99);
		ModEntities.registerWithOreDictionary(EntityAgate.AGATE_YIELDS, "Agate", "Chalcedony", "Onyx");
		
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.ICE.getDefaultState(), 0.33);
		ModEntities.registerOreDictValue(EntityAquamarine.AQUAMARINE_YIELDS, 0.66, "obsidian");
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.PACKED_ICE.getDefaultState(), 0.99);
		ModEntities.registerOreDictValue(EntityAquamarine.AQUAMARINE_YIELDS, 1.99, "blockPrismarine");
		EntityAquamarine.AQUAMARINE_YIELDS.put(Blocks.WATER.getDefaultState(), 0.11);
		ModEntities.registerWithOreDictionary(EntityAquamarine.AQUAMARINE_YIELDS, "Aquamarine", "Beryl", "GoldenBeryl", "Emerald", "Heliodor", "Morganite");
		
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 0.11, "oreIron");
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 0.55, "blockIron");
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 0.99, "endstone");
		EntityHessonite.HESSONITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X), 0.99);
		EntityHessonite.HESSONITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y), 0.99);
		EntityHessonite.HESSONITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z), 0.99);
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 1.99, "oreGarnet");
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 1.99, "oreAluminum");
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 1.99, "oreAluminium");
		ModEntities.registerOreDictValue(EntityHessonite.HESSONITE_YIELDS, 5.99, "blockGarnet");
		
		ModEntities.registerOreDictValue(EntityTopaz.TOPAZ_YIELDS, 0.22, "endstone");
		ModEntities.registerOreDictValue(EntityTopaz.TOPAZ_YIELDS, 0.99, "glowstone");
		ModEntities.registerOreDictValue(EntityTopaz.TOPAZ_YIELDS, 0.45, "oreGold");
		ModEntities.registerOreDictValue(EntityTopaz.TOPAZ_YIELDS, 0.11, "stoneGranite");
		ModEntities.registerOreDictValue(EntityTopaz.TOPAZ_YIELDS, 0.22, "stoneGranitePolished");
		ModEntities.registerWithOreDictionary(EntityTopaz.TOPAZ_YIELDS, "Topaz", "BlueTopaz", "Sulfur");
		
		ModEntities.registerOreDictValue(EntityRutile.RUTILE_YIELDS, 0.99, "glowstone");
		EntityRutile.RUTILE_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.50);
		EntityRutile.RUTILE_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.75);
		ModEntities.registerOreDictValue(EntityRutile.RUTILE_YIELDS, 0.99, "oreRedstone");
		ModEntities.registerOreDictValue(EntityRutile.RUTILE_YIELDS, 5.99, "blockRedstone");
		ModEntities.registerWithOreDictionary(EntityRutile.RUTILE_YIELDS, "Rutile", "Apatite");
		ModEntities.registerOreDictValue(EntityRutile.RUTILE_YIELDS, 0.99, "oreTitanium");
		
		ModEntities.registerOreDictValue(EntityZircon.ZIRCON_YIELDS, 2.99, "oreDiamond");
		ModEntities.registerOreDictValue(EntityZircon.ZIRCON_YIELDS, 5.99, "blockDiamond");
		ModEntities.registerOreDictValue(EntityZircon.ZIRCON_YIELDS, 0.22, "stoneGranite");
		ModEntities.registerOreDictValue(EntityZircon.ZIRCON_YIELDS, 0.66, "stoneGranitePolished");
		EntityZircon.ZIRCON_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.66);
		EntityZircon.ZIRCON_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.44);
		ModEntities.registerWithOreDictionary(EntityZircon.ZIRCON_YIELDS, "Zircon", "Uranium");
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
	
	public static void registerOreDictValue(HashMap<IBlockState, Double> yields, double value, String key) {
		NonNullList<ItemStack> cruxes = OreDictionary.getOres(key);
		for (ItemStack stack : cruxes) {
			if (stack.getItem() instanceof ItemBlock) {
				IBlockState blockState = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
				yields.put(blockState, value);
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
		EntityPearl.PEARL_DRESS_STYLES.add(new ResourceLocation("kagic:textures/entities/pearl/dress/hijab.png"));
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

	public static <T extends EntityCorruptedGem> void registerCorruptedGem(String name, Class<T> gem) {
		ModEntities.registerDiamond(name, gem);
		for (BiomeType type : BiomeType.values()) {
			for (BiomeEntry entry : BiomeManager.getBiomes(type)) {
				EntityRegistry.addSpawn(gem, 1, 1, 2, EnumCreatureType.MONSTER, entry.biome);
			}
		}
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
	@SuppressWarnings({ "unchecked" })
	public static <T extends Entity> void registerCustomEntity(String name, Class<T> entity) {
		EntityRegistry.registerModEntity(new ResourceLocation("kagic:kagic.custom_" + name), entity, "kagic.custom_" + name, currentID, KAGIC.instance, 256, 1, true);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			try {
				Class<Render<? extends T>> render = (Class<Render<? extends T>>) KAGIC.class.getClassLoader().loadClass("mod/akrivus/kagic/client/render/customnpcs/" + entity.getName().replaceAll(".+?Entity", "Render"));
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
