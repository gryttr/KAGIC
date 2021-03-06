package mod.akrivus.kagic.init;

import java.util.HashMap;

import mod.akrivus.kagic.items.ItemActiveGemBase;
import mod.akrivus.kagic.items.ItemActiveGemShard;
import mod.akrivus.kagic.items.ItemAutonomyContract;
import mod.akrivus.kagic.items.ItemCommanderStaff;
import mod.akrivus.kagic.items.ItemGem;
import mod.akrivus.kagic.items.ItemGemStaff;
import mod.akrivus.kagic.items.ItemInactiveGemBase;
import mod.akrivus.kagic.items.ItemJointContract;
import mod.akrivus.kagic.items.ItemLiberationContract;
import mod.akrivus.kagic.items.ItemPeaceTreaty;
import mod.akrivus.kagic.items.ItemTimeGlass;
import mod.akrivus.kagic.items.ItemTransferContract;
import mod.akrivus.kagic.items.ItemVehicle;
import mod.akrivus.kagic.items.ItemWarDeclaration;
import mod.akrivus.kagic.items.ItemWarpWhistle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
	public static final HashMap<ItemGem, ItemGem> GEM_TABLE = new HashMap<ItemGem, ItemGem>();

	public static final ModRecord RECORD_LITTLE_PERIDOT = new ModRecord("little_peridot", ModSounds.RECORD_LITTLE_PERIDOT);
	public static final ModRecord RECORD_ROSES_FOUNTAIN = new ModRecord("roses_fountain", ModSounds.RECORD_ROSES_FOUNTAIN);
	public static final ModRecord RECORD_LAPIS_FLIGHT = new ModRecord("lapis_flight", ModSounds.RECORD_LAPIS_FLIGHT);
	public static final ModRecord RECORD_THE_BREAKING_POINT = new ModRecord("the_breaking_point", ModSounds.RECORD_THE_BREAKING_POINT);
	public static final ModRecord RECORD_DUEL_OF_THE_QUARTZ = new ModRecord("duel_of_the_quartz", ModSounds.RECORD_DUEL_OF_THE_QUARTZ);
	public static final ModRecord RECORD_YELLOW_DIAMOND = new ModRecord("yellow_diamond", ModSounds.RECORD_YELLOW_DIAMOND);
	public static final ModRecord RECORD_BLUE_DIAMOND = new ModRecord("blue_diamond", ModSounds.RECORD_BLUE_DIAMOND);
	public static final ModRecord RECORD_WHATS_THE_USE_OF_FEELING_BLUE = new ModRecord("whats_the_use_of_feeling_blue", ModSounds.RECORD_WHATS_THE_USE_OF_FEELING_BLUE);

	public static final ModRecord RECORD_HEAVEN_BEETLE = new ModRecord("heaven_beetle", ModSounds.RECORD_HEAVEN_BEETLE);
	public static final ModRecord RECORD_DEFECTIVE = new ModRecord("defective", ModSounds.RECORD_DEFECTIVE);
	public static final ModRecord RECORD_GEM_SHARDS = new ModRecord("gem_shards", ModSounds.RECORD_GEM_SHARDS);
	public static final ModRecord RECORD_HEART_OF_THE_PYRAMID = new ModRecord("pyramid", ModSounds.RECORD_HEART_OF_THE_PYRAMID);
	public static final ModRecord RECORD_UNDERWATER_TEMPLE = new ModRecord("underwater_temple", ModSounds.RECORD_UNDERWATER_TEMPLE);

	public static final ItemActiveGemShard ACTIVATED_GEM_SHARD = new ItemActiveGemShard();
	public static final ItemActiveGemBase ACTIVATED_GEM_BASE = new ItemActiveGemBase();
	public static final ItemInactiveGemBase INACTIVE_GEM_BASE = new ItemInactiveGemBase();
	public static final ItemTransferContract TRANSFER_CONTRACT = new ItemTransferContract();
	public static final ItemJointContract JOINT_CONTRACT = new ItemJointContract();
	public static final ItemLiberationContract LIBERATION_CONTRACT = new ItemLiberationContract();
	public static final ItemAutonomyContract AUTONOMY_CONTRACT = new ItemAutonomyContract();
	public static final ItemWarDeclaration WAR_DECLARATION = new ItemWarDeclaration();
	public static final ItemPeaceTreaty PEACE_TREATY = new ItemPeaceTreaty();
	public static final ItemGemStaff GEM_STAFF = new ItemGemStaff();
	public static final ItemCommanderStaff COMMANDER_STAFF = new ItemCommanderStaff();
	public static final ItemVehicle ROAMING_EYE = new ItemVehicle("roaming_eye");
	public static final Item LASER_BEAM = new Item().setUnlocalizedName("laser_beam");
	public static final ItemWarpWhistle WARP_WHISTLE = new ItemWarpWhistle();
	public static final ItemFood STRAWBERRY_SLICE = (ItemFood) new ItemFood(2, 0.3F, false).setUnlocalizedName("strawberry_slice").setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	public static final ItemSeeds STRAWBERRY_SEEDS = (ItemSeeds) new ItemSeeds(ModBlocks.GIANT_STRAWBERRY_STEM, Blocks.FARMLAND).setUnlocalizedName("strawberry_seeds").setCreativeTab(ModCreativeTabs.CREATIVE_TAB_OTHER);
	public static final ItemTimeGlass TIME_GLASS = new ItemTimeGlass();
	
	public static final ItemGem RUBY_GEM = new ItemGem("ruby");
	public static final ItemGem WHITE_PEARL_GEM = new ItemGem("pearl_0");
	public static final ItemGem ORANGE_PEARL_GEM = new ItemGem("pearl_1");
	public static final ItemGem MAGENTA_PEARL_GEM = new ItemGem("pearl_2");
	public static final ItemGem LIGHT_BLUE_PEARL_GEM = new ItemGem("pearl_3");
	public static final ItemGem YELLOW_PEARL_GEM = new ItemGem("pearl_4");
	public static final ItemGem LIME_PEARL_GEM = new ItemGem("pearl_5");
	public static final ItemGem PINK_PEARL_GEM = new ItemGem("pearl_6");
	public static final ItemGem GRAY_PEARL_GEM = new ItemGem("pearl_7");
	public static final ItemGem LIGHT_GRAY_PEARL_GEM = new ItemGem("pearl_8");
	public static final ItemGem CYAN_PEARL_GEM = new ItemGem("pearl_9");
	public static final ItemGem PURPLE_PEARL_GEM = new ItemGem("pearl_10");
	public static final ItemGem BLUE_PEARL_GEM = new ItemGem("pearl_11");
	public static final ItemGem BROWN_PEARL_GEM = new ItemGem("pearl_12");
	public static final ItemGem GREEN_PEARL_GEM = new ItemGem("pearl_13");
	public static final ItemGem RED_PEARL_GEM = new ItemGem("pearl_14");
	public static final ItemGem BLACK_PEARL_GEM = new ItemGem("pearl_15");
	public static final ItemGem PEARL_GEM = new ItemGem("pearl");
	public static final ItemGem BISMUTH_GEM = new ItemGem("bismuth");
	public static final ItemGem PERIDOT_GEM = new ItemGem("peridot");
	public static final ItemGem JASPER_GEM = new ItemGem("jasper");
	public static final ItemGem NOREENA_JASPER_GEM = new ItemGem("jasper_0");
	public static final ItemGem OCEAN_JASPER_GEM = new ItemGem("jasper_1");
	public static final ItemGem BIGGS_JASPER_GEM = new ItemGem("jasper_2");
	public static final ItemGem GREEN_JASPER_GEM = new ItemGem("jasper_3");
	public static final ItemGem BRUNEAU_JASPER_GEM = new ItemGem("jasper_4");
	public static final ItemGem PURPLE_JASPER_GEM = new ItemGem("jasper_5");
	public static final ItemGem FLAME_JASPER_GEM = new ItemGem("jasper_6");
	public static final ItemGem PICTURE_JASPER_GEM = new ItemGem("jasper_7");
	public static final ItemGem CANDY_CANE_JASPER_GEM = new ItemGem("jasper_8");
	public static final ItemGem AMETHYST_GEM = new ItemGem("amethyst");
	public static final ItemGem ROSE_QUARTZ_GEM = new ItemGem("rose_quartz");
	public static final ItemGem LAPIS_LAZULI_GEM = new ItemGem("lapis_lazuli");
	public static final ItemGem CARNELIAN_GEM = new ItemGem("carnelian");
	public static final ItemGem WHITE_AGATE_GEM = new ItemGem("agate_0");
	public static final ItemGem ORANGE_AGATE_GEM = new ItemGem("agate_1");
	public static final ItemGem MAGENTA_AGATE_GEM = new ItemGem("agate_2");
	public static final ItemGem LIGHT_BLUE_AGATE_GEM = new ItemGem("agate_3");
	public static final ItemGem YELLOW_AGATE_GEM = new ItemGem("agate_4");
	public static final ItemGem LIME_AGATE_GEM = new ItemGem("agate_5");
	public static final ItemGem PINK_AGATE_GEM = new ItemGem("agate_6");
	public static final ItemGem GRAY_AGATE_GEM = new ItemGem("agate_7");
	public static final ItemGem LIGHT_GRAY_AGATE_GEM = new ItemGem("agate_8");
	public static final ItemGem CYAN_AGATE_GEM = new ItemGem("agate_9");
	public static final ItemGem PURPLE_AGATE_GEM = new ItemGem("agate_10");
	public static final ItemGem BLUE_AGATE_GEM = new ItemGem("agate_11");
	public static final ItemGem BROWN_AGATE_GEM = new ItemGem("agate_12");
	public static final ItemGem GREEN_AGATE_GEM = new ItemGem("agate_13");
	public static final ItemGem RED_AGATE_GEM = new ItemGem("agate_14");
	public static final ItemGem BLACK_AGATE_GEM = new ItemGem("agate_15");
	public static final ItemGem HOLLY_BLUE_AGATE_GEM = new ItemGem("agate_16");
	public static final ItemGem AGATE_GEM = new ItemGem("agate");
	public static final ItemGem AQUAMARINE_GEM = new ItemGem("aquamarine");
	public static final ItemGem HESSONITE_GEM = new ItemGem("hessonite");

	public static final ItemGem WHITE_SAPPHIRE_GEM = new ItemGem("sapphire_0");
	public static final ItemGem ORANGE_SAPPHIRE_GEM = new ItemGem("sapphire_1");
	public static final ItemGem YELLOW_SAPPHIRE_GEM = new ItemGem("sapphire_4");
	public static final ItemGem PINK_SAPPHIRE_GEM = new ItemGem("sapphire_6");
	public static final ItemGem PURPLE_SAPPHIRE_GEM = new ItemGem("sapphire_10");
	public static final ItemGem BLUE_SAPPHIRE_GEM = new ItemGem("sapphire_11");
	public static final ItemGem GREEN_SAPPHIRE_GEM = new ItemGem("sapphire_13");
	public static final ItemGem BLACK_SAPPHIRE_GEM = new ItemGem("sapphire_15");
	public static final ItemGem PADPARADSCHA_GEM = new ItemGem("sapphire_16");
	public static final ItemGem SAPPHIRE_GEM = new ItemGem("sapphire");

	public static final ItemGem TOPAZ_GEM = new ItemGem("topaz");
	public static final ItemGem BLUE_TOPAZ_GEM = new ItemGem("topaz_1");
	
	public static final ItemGem CITRINE_GEM = new ItemGem("citrine");
	public static final ItemGem AMETRINE_GEM = new ItemGem("citrine_1");
	
	public static final ItemGem WHITE_ZIRCON_GEM = new ItemGem("zircon_0");
	public static final ItemGem ORANGE_ZIRCON_GEM = new ItemGem("zircon_1");
	public static final ItemGem MAGENTA_ZIRCON_GEM = new ItemGem("zircon_2");
	public static final ItemGem LIGHT_BLUE_ZIRCON_GEM = new ItemGem("zircon_3");
	public static final ItemGem YELLOW_ZIRCON_GEM = new ItemGem("zircon_4");
	public static final ItemGem LIME_ZIRCON_GEM = new ItemGem("zircon_5");
	public static final ItemGem PINK_ZIRCON_GEM = new ItemGem("zircon_6");
	public static final ItemGem GRAY_ZIRCON_GEM = new ItemGem("zircon_7");
	public static final ItemGem LIGHT_GRAY_ZIRCON_GEM = new ItemGem("zircon_8");
	public static final ItemGem CYAN_ZIRCON_GEM = new ItemGem("zircon_9");
	public static final ItemGem PURPLE_ZIRCON_GEM = new ItemGem("zircon_10");
	public static final ItemGem BLUE_ZIRCON_GEM = new ItemGem("zircon_11");
	public static final ItemGem BROWN_ZIRCON_GEM = new ItemGem("zircon_12");
	public static final ItemGem GREEN_ZIRCON_GEM = new ItemGem("zircon_13");
	public static final ItemGem RED_ZIRCON_GEM = new ItemGem("zircon_14");
	public static final ItemGem BLACK_ZIRCON_GEM = new ItemGem("zircon_15");
	public static final ItemGem ZIRCON_GEM = new ItemGem("zircon");
	
	public static final ItemGem RUTILE_GEM = new ItemGem("rutile");
	public static final ItemGem TWIN_RUTILE_GEM = new ItemGem("rutile_1");

	public static final ItemGem YELLOW_DIAMOND_GEM = new ItemGem("yellow_diamond");
	public static final ItemGem BLUE_DIAMOND_GEM = new ItemGem("blue_diamond");

	public static final ItemGem CRACKED_RUBY_GEM = new ItemGem("ruby", true);
	public static final ItemGem CRACKED_WHITE_PEARL_GEM = new ItemGem("pearl_0", true);
	public static final ItemGem CRACKED_ORANGE_PEARL_GEM = new ItemGem("pearl_1", true);
	public static final ItemGem CRACKED_MAGENTA_PEARL_GEM = new ItemGem("pearl_2", true);
	public static final ItemGem CRACKED_LIGHT_BLUE_PEARL_GEM = new ItemGem("pearl_3", true);
	public static final ItemGem CRACKED_YELLOW_PEARL_GEM = new ItemGem("pearl_4", true);
	public static final ItemGem CRACKED_LIME_PEARL_GEM = new ItemGem("pearl_5", true);
	public static final ItemGem CRACKED_PINK_PEARL_GEM = new ItemGem("pearl_6", true);
	public static final ItemGem CRACKED_GRAY_PEARL_GEM = new ItemGem("pearl_7", true);
	public static final ItemGem CRACKED_LIGHT_GRAY_PEARL_GEM = new ItemGem("pearl_8", true);
	public static final ItemGem CRACKED_CYAN_PEARL_GEM = new ItemGem("pearl_9", true);
	public static final ItemGem CRACKED_PURPLE_PEARL_GEM = new ItemGem("pearl_10", true);
	public static final ItemGem CRACKED_BLUE_PEARL_GEM = new ItemGem("pearl_11", true);
	public static final ItemGem CRACKED_BROWN_PEARL_GEM = new ItemGem("pearl_12", true);
	public static final ItemGem CRACKED_GREEN_PEARL_GEM = new ItemGem("pearl_13", true);
	public static final ItemGem CRACKED_RED_PEARL_GEM = new ItemGem("pearl_14", true);
	public static final ItemGem CRACKED_BLACK_PEARL_GEM = new ItemGem("pearl_15", true);
	public static final ItemGem CRACKED_PEARL_GEM = new ItemGem("pearl", true);
	public static final ItemGem CRACKED_BISMUTH_GEM = new ItemGem("bismuth", true);
	public static final ItemGem CRACKED_PERIDOT_GEM = new ItemGem("peridot", true);
	public static final ItemGem CRACKED_JASPER_GEM = new ItemGem("jasper", true);
	public static final ItemGem CRACKED_NOREENA_JASPER_GEM = new ItemGem("jasper_0", true);
	public static final ItemGem CRACKED_OCEAN_JASPER_GEM = new ItemGem("jasper_1", true);
	public static final ItemGem CRACKED_BIGGS_JASPER_GEM = new ItemGem("jasper_2", true);
	public static final ItemGem CRACKED_GREEN_JASPER_GEM = new ItemGem("jasper_3", true);
	public static final ItemGem CRACKED_BRUNEAU_JASPER_GEM = new ItemGem("jasper_4", true);
	public static final ItemGem CRACKED_PURPLE_JASPER_GEM = new ItemGem("jasper_5", true);
	public static final ItemGem CRACKED_FLAME_JASPER_GEM = new ItemGem("jasper_6", true);
	public static final ItemGem CRACKED_PICTURE_JASPER_GEM = new ItemGem("jasper_7", true);
	public static final ItemGem CRACKED_CANDY_CANE_JASPER_GEM = new ItemGem("jasper_8", true);
	public static final ItemGem CRACKED_AMETHYST_GEM = new ItemGem("amethyst", true);
	public static final ItemGem CRACKED_ROSE_QUARTZ_GEM = new ItemGem("rose_quartz", true);
	public static final ItemGem CRACKED_LAPIS_LAZULI_GEM = new ItemGem("lapis_lazuli", true);
	public static final ItemGem CRACKED_CARNELIAN_GEM = new ItemGem("carnelian", true);
	public static final ItemGem CRACKED_WHITE_AGATE_GEM = new ItemGem("agate_0", true);
	public static final ItemGem CRACKED_ORANGE_AGATE_GEM = new ItemGem("agate_1", true);
	public static final ItemGem CRACKED_MAGENTA_AGATE_GEM = new ItemGem("agate_2", true);
	public static final ItemGem CRACKED_LIGHT_BLUE_AGATE_GEM = new ItemGem("agate_3", true);
	public static final ItemGem CRACKED_YELLOW_AGATE_GEM = new ItemGem("agate_4", true);
	public static final ItemGem CRACKED_LIME_AGATE_GEM = new ItemGem("agate_5", true);
	public static final ItemGem CRACKED_PINK_AGATE_GEM = new ItemGem("agate_6", true);
	public static final ItemGem CRACKED_GRAY_AGATE_GEM = new ItemGem("agate_7", true);
	public static final ItemGem CRACKED_LIGHT_GRAY_AGATE_GEM = new ItemGem("agate_8", true);
	public static final ItemGem CRACKED_CYAN_AGATE_GEM = new ItemGem("agate_9", true);
	public static final ItemGem CRACKED_PURPLE_AGATE_GEM = new ItemGem("agate_10", true);
	public static final ItemGem CRACKED_BLUE_AGATE_GEM = new ItemGem("agate_11", true);
	public static final ItemGem CRACKED_BROWN_AGATE_GEM = new ItemGem("agate_12", true);
	public static final ItemGem CRACKED_GREEN_AGATE_GEM = new ItemGem("agate_13", true);
	public static final ItemGem CRACKED_RED_AGATE_GEM = new ItemGem("agate_14", true);
	public static final ItemGem CRACKED_BLACK_AGATE_GEM = new ItemGem("agate_15", true);
	public static final ItemGem CRACKED_HOLLY_BLUE_AGATE_GEM = new ItemGem("agate_16", true);
	public static final ItemGem CRACKED_AGATE_GEM = new ItemGem("agate", true);
	public static final ItemGem CRACKED_AQUAMARINE_GEM = new ItemGem("aquamarine", true);
	public static final ItemGem CRACKED_HESSONITE_GEM = new ItemGem("hessonite", true);
	
	public static final ItemGem CRACKED_WHITE_SAPPHIRE_GEM = new ItemGem("sapphire_0", true);
	public static final ItemGem CRACKED_ORANGE_SAPPHIRE_GEM = new ItemGem("sapphire_1", true);
	public static final ItemGem CRACKED_YELLOW_SAPPHIRE_GEM = new ItemGem("sapphire_4", true);
	public static final ItemGem CRACKED_PINK_SAPPHIRE_GEM = new ItemGem("sapphire_6", true);
	public static final ItemGem CRACKED_PURPLE_SAPPHIRE_GEM = new ItemGem("sapphire_10", true);
	public static final ItemGem CRACKED_BLUE_SAPPHIRE_GEM = new ItemGem("sapphire_11", true);
	public static final ItemGem CRACKED_GREEN_SAPPHIRE_GEM = new ItemGem("sapphire_13", true);
	public static final ItemGem CRACKED_BLACK_SAPPHIRE_GEM = new ItemGem("sapphire_15", true);
	public static final ItemGem CRACKED_PADPARADSCHA_GEM = new ItemGem("sapphire_16", true);
	public static final ItemGem CRACKED_SAPPHIRE_GEM = new ItemGem("sapphire", true);

	public static final ItemGem CRACKED_TOPAZ_GEM = new ItemGem("topaz", true);
	public static final ItemGem CRACKED_BLUE_TOPAZ_GEM = new ItemGem("topaz_1", true);
	
	public static final ItemGem CRACKED_CITRINE_GEM = new ItemGem("citrine", true);
	public static final ItemGem CRACKED_AMETRINE_GEM = new ItemGem("citrine_1", true);

	public static final ItemGem CRACKED_WHITE_ZIRCON_GEM = new ItemGem("zircon_0", true);
	public static final ItemGem CRACKED_ORANGE_ZIRCON_GEM = new ItemGem("zircon_1", true);
	public static final ItemGem CRACKED_MAGENTA_ZIRCON_GEM = new ItemGem("zircon_2", true);
	public static final ItemGem CRACKED_LIGHT_BLUE_ZIRCON_GEM = new ItemGem("zircon_3", true);
	public static final ItemGem CRACKED_YELLOW_ZIRCON_GEM = new ItemGem("zircon_4", true);
	public static final ItemGem CRACKED_LIME_ZIRCON_GEM = new ItemGem("zircon_5", true);
	public static final ItemGem CRACKED_PINK_ZIRCON_GEM = new ItemGem("zircon_6", true);
	public static final ItemGem CRACKED_GRAY_ZIRCON_GEM = new ItemGem("zircon_7", true);
	public static final ItemGem CRACKED_LIGHT_GRAY_ZIRCON_GEM = new ItemGem("zircon_8", true);
	public static final ItemGem CRACKED_CYAN_ZIRCON_GEM = new ItemGem("zircon_9", true);
	public static final ItemGem CRACKED_PURPLE_ZIRCON_GEM = new ItemGem("zircon_10", true);
	public static final ItemGem CRACKED_BLUE_ZIRCON_GEM = new ItemGem("zircon_11", true);
	public static final ItemGem CRACKED_BROWN_ZIRCON_GEM = new ItemGem("zircon_12", true);
	public static final ItemGem CRACKED_GREEN_ZIRCON_GEM = new ItemGem("zircon_13", true);
	public static final ItemGem CRACKED_RED_ZIRCON_GEM = new ItemGem("zircon_14", true);
	public static final ItemGem CRACKED_BLACK_ZIRCON_GEM = new ItemGem("zircon_15", true);
	public static final ItemGem CRACKED_ZIRCON_GEM = new ItemGem("zircon", true);
	
	public static final ItemGem CRACKED_RUTILE_GEM = new ItemGem("rutile", true);
	public static final ItemGem CRACKED_TWIN_RUTILE_GEM = new ItemGem("rutile_1", true);

	public static final ItemGem CRACKED_YELLOW_DIAMOND_GEM = new ItemGem("yellow_diamond", true);
	public static final ItemGem CRACKED_BLUE_DIAMOND_GEM = new ItemGem("blue_diamond", true);
	
	public static final ItemGem HANDBODY_GEM = new ItemGem("handbody", false);
	public static final ItemGem CRACKED_HANDBODY_GEM = new ItemGem("handbody", true);
	public static final ItemGem FOOTARM_GEM = new ItemGem("footarm", false);
	public static final ItemGem CRACKED_FOOTARM_GEM = new ItemGem("footarm", true);
	public static final ItemGem MOUTHTORSO_GEM = new ItemGem("mouthtorso", false);
	public static final ItemGem CRACKED_MOUTHTORSO_GEM = new ItemGem("mouthtorso", true);
	
	public static final ItemGem CORRUPTED_AMETHYST_GEM = new ItemGem("corrupted_amatista");
	public static final ItemGem CRACKED_CORRUPTED_AMETHYST_GEM = new ItemGem("corrupted_amatista", true);
	public static final ItemGem CORRUPTED_CARNELIAN_GEM = new ItemGem("corrupted_cornalina");
	public static final ItemGem CRACKED_CORRUPTED_CARNELIAN_GEM = new ItemGem("corrupted_cornalina", true);
	public static final ItemGem CORRUPTED_JASPER_GEM = new ItemGem("corrupted_jasper");
	public static final ItemGem CORRUPTED_NOREENA_JASPER_GEM = new ItemGem("corrupted_jasper_0");
	public static final ItemGem CORRUPTED_OCEAN_JASPER_GEM = new ItemGem("corrupted_jasper_1");
	public static final ItemGem CORRUPTED_BIGGS_JASPER_GEM = new ItemGem("corrupted_jasper_2");
	public static final ItemGem CORRUPTED_GREEN_JASPER_GEM = new ItemGem("corrupted_jasper_3");
	public static final ItemGem CORRUPTED_BRUNEAU_JASPER_GEM = new ItemGem("corrupted_jasper_4");
	public static final ItemGem CORRUPTED_PURPLE_JASPER_GEM = new ItemGem("corrupted_jasper_5");
	public static final ItemGem CORRUPTED_FLAME_JASPER_GEM = new ItemGem("corrupted_jasper_6");
	public static final ItemGem CORRUPTED_PICTURE_JASPER_GEM = new ItemGem("corrupted_jasper_7");
	public static final ItemGem CRACKED_CORRUPTED_JASPER_GEM = new ItemGem("corrupted_jasper", true);
	public static final ItemGem CRACKED_CORRUPTED_NOREENA_JASPER_GEM = new ItemGem("corrupted_jasper_0", true);
	public static final ItemGem CRACKED_CORRUPTED_OCEAN_JASPER_GEM = new ItemGem("corrupted_jasper_1", true);
	public static final ItemGem CRACKED_CORRUPTED_BIGGS_JASPER_GEM = new ItemGem("corrupted_jasper_2", true);
	public static final ItemGem CRACKED_CORRUPTED_GREEN_JASPER_GEM = new ItemGem("corrupted_jasper_3", true);
	public static final ItemGem CRACKED_CORRUPTED_BRUNEAU_JASPER_GEM = new ItemGem("corrupted_jasper_4", true);
	public static final ItemGem CRACKED_CORRUPTED_PURPLE_JASPER_GEM = new ItemGem("corrupted_jasper_5", true);
	public static final ItemGem CRACKED_CORRUPTED_FLAME_JASPER_GEM = new ItemGem("corrupted_jasper_6", true);
	public static final ItemGem CRACKED_CORRUPTED_PICTURE_JASPER_GEM = new ItemGem("corrupted_jasper_7", true);
	public static final ItemGem CORRUPTED_MOISSANITE_GEM = new ItemGem("corrupted_moissanita");
	public static final ItemGem CRACKED_CORRUPTED_MOISSANITE_GEM = new ItemGem("corrupted_moissanita", true);
	public static final ItemGem CORRUPTED_ROSE_QUARTZ_GEM = new ItemGem("corrupted_cuarzo_rosa");
	public static final ItemGem CRACKED_CORRUPTED_ROSE_QUARTZ_GEM = new ItemGem("corrupted_cuarzo_rosa", true);
	public static final ItemGem CORRUPTED_TONGUE_MONSTER_GEM = new ItemGem("tongue_monster");
	public static final ItemGem CRACKED_CORRUPTED_TONGUE_MONSTER_GEM = new ItemGem("tongue_monster", true);
	public static final ItemGem CORRUPTED_BLUE_WATER_BEAR_GEM = new ItemGem("water_bear_0");
	public static final ItemGem CRACKED_CORRUPTED_BLUE_WATER_BEAR_GEM = new ItemGem("water_bear_0", true);
	public static final ItemGem CORRUPTED_GREEN_WATER_BEAR_GEM = new ItemGem("water_bear_1");
	public static final ItemGem CRACKED_CORRUPTED_GREEN_WATER_BEAR_GEM = new ItemGem("water_bear_1", true);
	public static final ItemGem CORRUPTED_WATERMELON_TOURMALINE_GEM = new ItemGem("corrupted_watermelon_tourmaline");
	public static final ItemGem CRACKED_CORRUPTED_WATERMELON_TOURMALINE_GEM = new ItemGem("corrupted_watermelon_tourmaline", true);

	public static void registerItems(RegistryEvent.Register<Item> event) {
		registerGem(RUBY_GEM, CRACKED_RUBY_GEM, event);
		registerGem(WHITE_PEARL_GEM, CRACKED_WHITE_PEARL_GEM, event);
		registerGem(ORANGE_PEARL_GEM, CRACKED_ORANGE_PEARL_GEM, event);
		registerGem(MAGENTA_PEARL_GEM, CRACKED_MAGENTA_PEARL_GEM, event);
		registerGem(LIGHT_BLUE_PEARL_GEM, CRACKED_LIGHT_BLUE_PEARL_GEM, event);
		registerGem(YELLOW_PEARL_GEM, CRACKED_YELLOW_PEARL_GEM, event);
		registerGem(LIME_PEARL_GEM, CRACKED_LIME_PEARL_GEM, event);
		registerGem(PINK_PEARL_GEM, CRACKED_PINK_PEARL_GEM, event);
		registerGem(GRAY_PEARL_GEM, CRACKED_GRAY_PEARL_GEM, event);
		registerGem(LIGHT_GRAY_PEARL_GEM, CRACKED_LIGHT_GRAY_PEARL_GEM, event);
		registerGem(CYAN_PEARL_GEM, CRACKED_CYAN_PEARL_GEM, event);
		registerGem(PURPLE_PEARL_GEM, CRACKED_PURPLE_PEARL_GEM, event);
		registerGem(BLUE_PEARL_GEM, CRACKED_BLUE_PEARL_GEM, event);
		registerGem(BROWN_PEARL_GEM, CRACKED_BROWN_PEARL_GEM, event);
		registerGem(GREEN_PEARL_GEM, CRACKED_GREEN_PEARL_GEM, event);
		registerGem(RED_PEARL_GEM, CRACKED_RED_PEARL_GEM, event);
		registerGem(BLACK_PEARL_GEM, CRACKED_BLACK_PEARL_GEM, event);
		registerGem(PEARL_GEM, CRACKED_PEARL_GEM, event);
		registerGem(BISMUTH_GEM, CRACKED_BISMUTH_GEM, event);
		registerGem(PERIDOT_GEM, CRACKED_PERIDOT_GEM, event);
		registerGem(JASPER_GEM, CRACKED_JASPER_GEM, event);
		registerGem(NOREENA_JASPER_GEM, CRACKED_NOREENA_JASPER_GEM, event);
		registerGem(OCEAN_JASPER_GEM, CRACKED_OCEAN_JASPER_GEM, event);
		registerGem(BIGGS_JASPER_GEM, CRACKED_BIGGS_JASPER_GEM, event);
		registerGem(GREEN_JASPER_GEM, CRACKED_GREEN_JASPER_GEM, event);
		registerGem(BRUNEAU_JASPER_GEM, CRACKED_BRUNEAU_JASPER_GEM, event);
		registerGem(PURPLE_JASPER_GEM, CRACKED_PURPLE_JASPER_GEM, event);
		registerGem(FLAME_JASPER_GEM, CRACKED_FLAME_JASPER_GEM, event);
		registerGem(PICTURE_JASPER_GEM, CRACKED_PICTURE_JASPER_GEM, event);
		registerGem(CANDY_CANE_JASPER_GEM, CRACKED_CANDY_CANE_JASPER_GEM, event);
		registerGem(AMETHYST_GEM, CRACKED_AMETHYST_GEM, event);
		registerGem(ROSE_QUARTZ_GEM, CRACKED_ROSE_QUARTZ_GEM, event);
		registerGem(LAPIS_LAZULI_GEM, CRACKED_LAPIS_LAZULI_GEM, event);
		registerGem(CARNELIAN_GEM, CRACKED_CARNELIAN_GEM, event);
		registerGem(WHITE_AGATE_GEM, CRACKED_WHITE_AGATE_GEM, event);
		registerGem(ORANGE_AGATE_GEM, CRACKED_ORANGE_AGATE_GEM, event);
		registerGem(MAGENTA_AGATE_GEM, CRACKED_MAGENTA_AGATE_GEM, event);
		registerGem(LIGHT_BLUE_AGATE_GEM, CRACKED_LIGHT_BLUE_AGATE_GEM, event);
		registerGem(YELLOW_AGATE_GEM, CRACKED_YELLOW_AGATE_GEM, event);
		registerGem(LIME_AGATE_GEM, CRACKED_LIME_AGATE_GEM, event);
		registerGem(PINK_AGATE_GEM, CRACKED_PINK_AGATE_GEM, event);
		registerGem(GRAY_AGATE_GEM, CRACKED_GRAY_AGATE_GEM, event);
		registerGem(LIGHT_GRAY_AGATE_GEM, CRACKED_LIGHT_GRAY_AGATE_GEM, event);
		registerGem(CYAN_AGATE_GEM, CRACKED_CYAN_AGATE_GEM, event);
		registerGem(PURPLE_AGATE_GEM, CRACKED_PURPLE_AGATE_GEM, event);
		registerGem(BLUE_AGATE_GEM, CRACKED_BLUE_AGATE_GEM, event);
		registerGem(BROWN_AGATE_GEM, CRACKED_BROWN_AGATE_GEM, event);
		registerGem(GREEN_AGATE_GEM, CRACKED_GREEN_AGATE_GEM, event);
		registerGem(RED_AGATE_GEM, CRACKED_RED_AGATE_GEM, event);
		registerGem(BLACK_AGATE_GEM, CRACKED_BLACK_AGATE_GEM, event);
		registerGem(HOLLY_BLUE_AGATE_GEM, CRACKED_HOLLY_BLUE_AGATE_GEM, event);
		registerGem(AGATE_GEM, CRACKED_AGATE_GEM, event);
		registerGem(AQUAMARINE_GEM, CRACKED_AQUAMARINE_GEM, event);
		registerGem(HESSONITE_GEM, CRACKED_HESSONITE_GEM, event);
		
		registerGem(SAPPHIRE_GEM, CRACKED_SAPPHIRE_GEM, event);
		registerGem(WHITE_SAPPHIRE_GEM, CRACKED_WHITE_SAPPHIRE_GEM, event);
		registerGem(ORANGE_SAPPHIRE_GEM, CRACKED_ORANGE_SAPPHIRE_GEM, event);
		registerGem(YELLOW_SAPPHIRE_GEM, CRACKED_YELLOW_SAPPHIRE_GEM, event);
		registerGem(PINK_SAPPHIRE_GEM, CRACKED_PINK_SAPPHIRE_GEM, event);
		registerGem(PURPLE_SAPPHIRE_GEM, CRACKED_PURPLE_SAPPHIRE_GEM, event);
		registerGem(BLUE_SAPPHIRE_GEM, CRACKED_BLUE_SAPPHIRE_GEM, event);
		registerGem(GREEN_SAPPHIRE_GEM, CRACKED_GREEN_SAPPHIRE_GEM, event);
		registerGem(BLACK_SAPPHIRE_GEM, CRACKED_BLACK_SAPPHIRE_GEM, event);
		registerGem(PADPARADSCHA_GEM, CRACKED_PADPARADSCHA_GEM, event);
		
		registerGem(TOPAZ_GEM, CRACKED_TOPAZ_GEM, event);
		registerGem(BLUE_TOPAZ_GEM, CRACKED_BLUE_TOPAZ_GEM, event);
		
		//registerGem(CITRINE_GEM, CRACKED_CITRINE_GEM, event);
		//registerGem(AMETRINE_GEM, CRACKED_AMETRINE_GEM, event);
		
		registerGem(WHITE_ZIRCON_GEM, CRACKED_WHITE_ZIRCON_GEM, event);
		registerGem(ORANGE_ZIRCON_GEM, CRACKED_ORANGE_ZIRCON_GEM, event);
		registerGem(MAGENTA_ZIRCON_GEM, CRACKED_MAGENTA_ZIRCON_GEM, event);
		registerGem(LIGHT_BLUE_ZIRCON_GEM, CRACKED_LIGHT_BLUE_ZIRCON_GEM, event);
		registerGem(YELLOW_ZIRCON_GEM, CRACKED_YELLOW_ZIRCON_GEM, event);
		registerGem(LIME_ZIRCON_GEM, CRACKED_LIME_ZIRCON_GEM, event);
		registerGem(PINK_ZIRCON_GEM, CRACKED_PINK_ZIRCON_GEM, event);
		registerGem(GRAY_ZIRCON_GEM, CRACKED_GRAY_ZIRCON_GEM, event);
		registerGem(LIGHT_GRAY_ZIRCON_GEM, CRACKED_LIGHT_GRAY_ZIRCON_GEM, event);
		registerGem(CYAN_ZIRCON_GEM, CRACKED_CYAN_ZIRCON_GEM, event);
		registerGem(PURPLE_ZIRCON_GEM, CRACKED_PURPLE_ZIRCON_GEM, event);
		registerGem(BLUE_ZIRCON_GEM, CRACKED_BLUE_ZIRCON_GEM, event);
		registerGem(BROWN_ZIRCON_GEM, CRACKED_BROWN_ZIRCON_GEM, event);
		registerGem(GREEN_ZIRCON_GEM, CRACKED_GREEN_ZIRCON_GEM, event);
		registerGem(RED_ZIRCON_GEM, CRACKED_RED_ZIRCON_GEM, event);
		registerGem(BLACK_ZIRCON_GEM, CRACKED_BLACK_ZIRCON_GEM, event);
		registerGem(ZIRCON_GEM, CRACKED_ZIRCON_GEM, event);
		
		registerGem(RUTILE_GEM, CRACKED_RUTILE_GEM, event);
		registerGem(TWIN_RUTILE_GEM, CRACKED_TWIN_RUTILE_GEM, event);

		registerGem(YELLOW_DIAMOND_GEM, CRACKED_YELLOW_DIAMOND_GEM, event);
		registerGem(BLUE_DIAMOND_GEM, CRACKED_BLUE_DIAMOND_GEM, event);
		
		registerGem(HANDBODY_GEM, CRACKED_HANDBODY_GEM, event);
		registerGem(FOOTARM_GEM, CRACKED_FOOTARM_GEM, event);
		registerGem(MOUTHTORSO_GEM, CRACKED_MOUTHTORSO_GEM, event);

		registerGem(CORRUPTED_AMETHYST_GEM, CRACKED_CORRUPTED_AMETHYST_GEM, event);
		registerGem(CORRUPTED_CARNELIAN_GEM, CRACKED_CORRUPTED_CARNELIAN_GEM, event);
		registerGem(CORRUPTED_JASPER_GEM, CRACKED_CORRUPTED_JASPER_GEM, event);
		registerGem(CORRUPTED_NOREENA_JASPER_GEM, CRACKED_CORRUPTED_NOREENA_JASPER_GEM, event);
		registerGem(CORRUPTED_OCEAN_JASPER_GEM, CRACKED_CORRUPTED_OCEAN_JASPER_GEM, event);
		registerGem(CORRUPTED_BIGGS_JASPER_GEM, CRACKED_CORRUPTED_BIGGS_JASPER_GEM, event);
		registerGem(CORRUPTED_GREEN_JASPER_GEM, CRACKED_CORRUPTED_GREEN_JASPER_GEM, event);
		registerGem(CORRUPTED_BRUNEAU_JASPER_GEM, CRACKED_CORRUPTED_BRUNEAU_JASPER_GEM, event);
		registerGem(CORRUPTED_PURPLE_JASPER_GEM, CRACKED_CORRUPTED_PURPLE_JASPER_GEM, event);
		registerGem(CORRUPTED_FLAME_JASPER_GEM, CRACKED_CORRUPTED_FLAME_JASPER_GEM, event);
		registerGem(CORRUPTED_PICTURE_JASPER_GEM, CRACKED_CORRUPTED_PICTURE_JASPER_GEM, event);
		registerGem(CORRUPTED_MOISSANITE_GEM, CRACKED_CORRUPTED_MOISSANITE_GEM, event);
		registerGem(CORRUPTED_ROSE_QUARTZ_GEM, CRACKED_CORRUPTED_ROSE_QUARTZ_GEM, event);
		registerGem(CORRUPTED_TONGUE_MONSTER_GEM, CRACKED_CORRUPTED_TONGUE_MONSTER_GEM, event);
		registerGem(CORRUPTED_BLUE_WATER_BEAR_GEM, CRACKED_CORRUPTED_BLUE_WATER_BEAR_GEM, event);
		registerGem(CORRUPTED_GREEN_WATER_BEAR_GEM, CRACKED_CORRUPTED_GREEN_WATER_BEAR_GEM, event);
		registerGem(CORRUPTED_WATERMELON_TOURMALINE_GEM, CRACKED_CORRUPTED_WATERMELON_TOURMALINE_GEM, event);

		registerItem(RECORD_LITTLE_PERIDOT, event, "record");
		registerItem(RECORD_ROSES_FOUNTAIN, event, "record");
		registerItem(RECORD_LAPIS_FLIGHT, event, "record");
		registerItem(RECORD_THE_BREAKING_POINT, event, "record");
		registerItem(RECORD_DUEL_OF_THE_QUARTZ, event, "record");
		registerItem(RECORD_YELLOW_DIAMOND, event, "record");
		registerItem(RECORD_BLUE_DIAMOND, event, "record");
		registerItem(RECORD_WHATS_THE_USE_OF_FEELING_BLUE, event, "record");
		registerItem(RECORD_HEAVEN_BEETLE, event, "record");
		registerItem(RECORD_DEFECTIVE, event, "record");
		registerItem(RECORD_GEM_SHARDS, event, "record");
		registerItem(RECORD_HEART_OF_THE_PYRAMID, event, "record");
		registerItem(RECORD_UNDERWATER_TEMPLE, event, "record");
		registerItem(ACTIVATED_GEM_SHARD, event);
		registerItem(ACTIVATED_GEM_BASE, event);
		registerItem(INACTIVE_GEM_BASE, event);
		registerItem(TRANSFER_CONTRACT, event);
		registerItem(JOINT_CONTRACT, event);
		registerItem(LIBERATION_CONTRACT, event);
		registerItem(AUTONOMY_CONTRACT, event);
		registerItem(WAR_DECLARATION, event);
		registerItem(PEACE_TREATY, event);
		registerItem(GEM_STAFF, event);
		registerItem(COMMANDER_STAFF, event);
		registerItem(ROAMING_EYE, event);
		registerItem(LASER_BEAM, event);
		registerItem(WARP_WHISTLE, event);
		registerItem(STRAWBERRY_SLICE, event, "cropGiantStrawberry");
		OreDictionary.registerOre("cropStrawberry", STRAWBERRY_SLICE);
		registerItem(STRAWBERRY_SEEDS, event, "seedGiantStrawberry");
		registerItem(TIME_GLASS, event);
	}
	public static void registerGem(ItemGem normal, ItemGem broken, RegistryEvent.Register<Item> event) {
		GEM_TABLE.put(normal, broken);
		GEM_TABLE.put(broken, normal);
		registerItem(normal, event);
		registerItem(broken, event);
	}
	public static void registerExternalGem(ItemGem normal, ItemGem broken, String prefix, RegistryEvent.Register<Item> event) {
		GEM_TABLE.put(normal, broken);
		GEM_TABLE.put(broken, normal);
		registerExternalItem(normal, prefix, event);
		registerExternalItem(broken, prefix, event);
	}
	
	public static void registerItem(Item item, RegistryEvent.Register<Item> event) {
		registerItem(item, event, "");
	}
	
	public static void registerItem(Item item, RegistryEvent.Register<Item> event, String oredictName) {
		//GameRegistry.register(item, new ResourceLocation("kagic:" + item.getUnlocalizedName().replaceFirst("item\\.|tile\\.", "")));
		item.setRegistryName( new ResourceLocation("kagic:" + item.getUnlocalizedName().replaceFirst("item\\.|tile\\.", "")));
		event.getRegistry().register(item);
		
		if (!oredictName.isEmpty()) {
			OreDictionary.registerOre(oredictName, item);
		}
		
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	
	public static void registerExternalItem(Item item, String prefix, RegistryEvent.Register<Item> event) {
		registerExternalItem(item, prefix, event, "");
	}
	
	public static void registerExternalItem(Item item, String prefix, RegistryEvent.Register<Item> event, String oredictName) {
		//GameRegistry.register(item, new ResourceLocation(prefix + ":" + item.getUnlocalizedName().replaceFirst("item\\.|tile\\.", "")));
		item.setRegistryName(new ResourceLocation(prefix + ":" + item.getUnlocalizedName().replaceFirst("item\\.|tile\\.", "")));
		event.getRegistry().register(item);
		
		if (!oredictName.isEmpty()) {
			OreDictionary.registerOre(oredictName, item);
		}

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
