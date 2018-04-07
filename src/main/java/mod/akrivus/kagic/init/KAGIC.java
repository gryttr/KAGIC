package mod.akrivus.kagic.init;

import java.io.InputStream;
import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import mod.akrivus.kagic.client.gui.KTGUIProxy;
import mod.akrivus.kagic.command.CommandMeteorRuby;
import mod.akrivus.kagic.command.CommandScanGems;
import mod.akrivus.kagic.command.CommandSpawnGems;
import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.gem.fusion.FusionSpawnHandler;
import mod.akrivus.kagic.server.SpaceStuff;
import mod.akrivus.kagic.skills.SkillBase;
import mod.akrivus.kagic.skills.pack.AlignGems;
import mod.akrivus.kagic.skills.pack.BreedLivestock;
import mod.akrivus.kagic.skills.pack.BuildBox;
import mod.akrivus.kagic.skills.pack.BuildBridge;
import mod.akrivus.kagic.skills.pack.BuildRamp;
import mod.akrivus.kagic.skills.pack.BuildStairs;
import mod.akrivus.kagic.skills.pack.BuildTower;
import mod.akrivus.kagic.skills.pack.BuildWall;
import mod.akrivus.kagic.skills.pack.CollectLiquids;
import mod.akrivus.kagic.skills.pack.Come;
import mod.akrivus.kagic.skills.pack.CutDownTrees;
import mod.akrivus.kagic.skills.pack.Defend;
import mod.akrivus.kagic.skills.pack.DumpChestsBismuth;
import mod.akrivus.kagic.skills.pack.DumpChestsLapis;
import mod.akrivus.kagic.skills.pack.DumpChestsPearl;
import mod.akrivus.kagic.skills.pack.DumpChestsPeridot;
import mod.akrivus.kagic.skills.pack.EnderPearlWarp;
import mod.akrivus.kagic.skills.pack.FetchChestsBismuth;
import mod.akrivus.kagic.skills.pack.FetchChestsPearl;
import mod.akrivus.kagic.skills.pack.Follow;
import mod.akrivus.kagic.skills.pack.FuseTopaz;
import mod.akrivus.kagic.skills.pack.Harvest;
import mod.akrivus.kagic.skills.pack.HarvestAndReplant;
import mod.akrivus.kagic.skills.pack.HarvestAndReplantNetherWart;
import mod.akrivus.kagic.skills.pack.HarvestCacti;
import mod.akrivus.kagic.skills.pack.HarvestMelons;
import mod.akrivus.kagic.skills.pack.HarvestNetherWart;
import mod.akrivus.kagic.skills.pack.HarvestPumpkins;
import mod.akrivus.kagic.skills.pack.HarvestReeds;
import mod.akrivus.kagic.skills.pack.KillOtherEntities;
import mod.akrivus.kagic.skills.pack.Look;
import mod.akrivus.kagic.skills.pack.MilkCows;
import mod.akrivus.kagic.skills.pack.Mine;
import mod.akrivus.kagic.skills.pack.MowGrass;
import mod.akrivus.kagic.skills.pack.PickFlowers;
import mod.akrivus.kagic.skills.pack.PlantSaplingsBismuth;
import mod.akrivus.kagic.skills.pack.PlantSaplingsPearl;
import mod.akrivus.kagic.skills.pack.PlantSaplingsPeridot;
import mod.akrivus.kagic.skills.pack.SingSong;
import mod.akrivus.kagic.skills.pack.Stop;
import mod.akrivus.kagic.skills.pack.TameCats;
import mod.akrivus.kagic.skills.pack.TameDogs;
import mod.akrivus.kagic.skills.pack.TameParrots;
import mod.akrivus.kagic.skills.pack.TellFuture;
import mod.akrivus.kagic.skills.pack.TellFutureGarnet;
import mod.akrivus.kagic.skills.pack.TellReport;
import mod.akrivus.kagic.skills.pack.UnfuseTopaz;
import mod.akrivus.kagic.skills.pack.Warp;
import mod.heimrarnadalr.kagic.chunk.KAGICChunkCallback;
import mod.heimrarnadalr.kagic.crafting.KAGICSmeltingRecipes;
import mod.heimrarnadalr.kagic.dispenser.DispenserBehaviors;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.proxies.CommonProxy;
import mod.heimrarnadalr.kagic.util.GemPlayerLoot;
import mod.heimrarnadalr.kagic.world.CorruptedGemSpawner;
import mod.heimrarnadalr.kagic.world.Fogger;
import mod.heimrarnadalr.kagic.world.GenEventCanceller;
import mod.heimrarnadalr.kagic.world.KAGICWorldGenerator;
import mod.heimrarnadalr.kagic.world.structure.LootTables;
import net.minecraft.crash.CrashReport;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

@Mod(modid = KAGIC.MODID, version = KAGIC.VERSION, acceptedMinecraftVersions = KAGIC.MCVERSION, guiFactory = "mod.akrivus.kagic.client.gui.GuiFactory")
public class KAGIC {
    public static final String MODID = "kagic";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
    public static final boolean DEVELOPER = false;
 
	public static Logger logger;

    @Instance
    public static KAGIC instance;
    public static SpaceStuff spaceStuff;
    public static KAGICWorldGenerator worldGen;
    
    public static SentenceModel sentModel;
    public static SentenceDetectorME sentDetector;
    public static POSModel posModel;
    public static POSTaggerME posTagger;

    static {
    	FluidRegistry.enableUniversalBucket();
    }
    
    @SidedProxy(clientSide = "mod.heimrarnadalr.kagic.proxies.ClientProxy", serverSide = "mod.heimrarnadalr.kagic.proxies.ServerProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	logger = e.getModLog();
        //ModBiomes.register();
        ModConfigs.register(e);
        //ModDimensions.register();
        KAGICSmeltingRecipes.register();
		KTPacketHandler.registerMessages(KAGIC.MODID);
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new KAGICChunkCallback());
		LootTables.register();
		KAGIC.worldGen = new KAGICWorldGenerator();
		try {
    		InputStream input = null;
    		input = this.getClass().getClassLoader().getResourceAsStream("assets/kagic/lang/processing/en-sent.bin");
    		KAGIC.sentModel = new SentenceModel(input);
    		KAGIC.sentDetector = new SentenceDetectorME(KAGIC.sentModel);
    		input = this.getClass().getClassLoader().getResourceAsStream("assets/kagic/lang/processing/en-pos-perceptron.bin");
    		KAGIC.posModel = new POSModel(input);
    		KAGIC.posTagger = new POSTaggerME(KAGIC.posModel);
    	}
    	catch (Exception ex) {
    		CrashReport.makeCrashReport(ex, "Something went wrong loading OpenNLP.");
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	ModEntities.register();
    	ModEvents.register();
    	ModTileEntities.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new KTGUIProxy());
		if (e.getSide() == Side.CLIENT) {
			ModTESRs.register();
			MinecraftForge.EVENT_BUS.register(new Fogger());
		}
		GameRegistry.registerWorldGenerator(worldGen, 50);
		GenEventCanceller genCanceller = new GenEventCanceller(); 
		MinecraftForge.EVENT_BUS.register(genCanceller);
		MinecraftForge.TERRAIN_GEN_BUS.register(genCanceller);
		MinecraftForge.EVENT_BUS.register(new FusionSpawnHandler());
		MinecraftForge.EVENT_BUS.register(new CorruptedGemSpawner());
		MinecraftForge.EVENT_BUS.register(new OreDictListener());
		MinecraftForge.EVENT_BUS.register(new GemPlayerLoot());
		DispenserBehaviors.register();
		KAGIC.proxy.registerBlockColors();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	ModEntities.registerGemYields();
		KAGIC.addSkill(AlignGems.class);
    	KAGIC.addSkill(BreedLivestock.class);
    	KAGIC.addSkill(BuildBox.class);
		KAGIC.addSkill(BuildBridge.class);
		KAGIC.addSkill(BuildStairs.class);
		KAGIC.addSkill(BuildTower.class);
		KAGIC.addSkill(BuildWall.class);
		KAGIC.addSkill(BuildRamp.class);
		KAGIC.addSkill(CollectLiquids.class);
		KAGIC.addSkill(Come.class);
		KAGIC.addSkill(CutDownTrees.class);
		KAGIC.addSkill(Defend.class);
		KAGIC.addSkill(DumpChestsBismuth.class);
		KAGIC.addSkill(DumpChestsLapis.class);
		KAGIC.addSkill(DumpChestsPearl.class);
		KAGIC.addSkill(DumpChestsPeridot.class);
		KAGIC.addSkill(EnderPearlWarp.class);
		KAGIC.addSkill(FetchChestsBismuth.class);
		KAGIC.addSkill(FetchChestsPearl.class);
		KAGIC.addSkill(Follow.class);
		KAGIC.addSkill(FuseTopaz.class);
		KAGIC.addSkill(Harvest.class);
		KAGIC.addSkill(HarvestAndReplant.class);
		KAGIC.addSkill(HarvestAndReplantNetherWart.class);
		KAGIC.addSkill(HarvestCacti.class);
		KAGIC.addSkill(HarvestMelons.class);
		KAGIC.addSkill(HarvestNetherWart.class);
		KAGIC.addSkill(HarvestPumpkins.class);
		KAGIC.addSkill(HarvestReeds.class);
		KAGIC.addSkill(KillOtherEntities.class);
		KAGIC.addSkill(Look.class);
		KAGIC.addSkill(MilkCows.class);
		KAGIC.addSkill(Mine.class);
		KAGIC.addSkill(MowGrass.class);
		KAGIC.addSkill(PickFlowers.class);
		KAGIC.addSkill(PlantSaplingsBismuth.class);
		KAGIC.addSkill(PlantSaplingsPearl.class);
		KAGIC.addSkill(PlantSaplingsPeridot.class);
		KAGIC.addSkill(SingSong.class);
		KAGIC.addSkill(Stop.class);
		KAGIC.addSkill(TameCats.class);
		KAGIC.addSkill(TameDogs.class);
		KAGIC.addSkill(TameParrots.class);
		KAGIC.addSkill(TellFuture.class);
		KAGIC.addSkill(TellFutureGarnet.class);
		KAGIC.addSkill(TellReport.class);
		KAGIC.addSkill(UnfuseTopaz.class);
		KAGIC.addSkill(Warp.class);
    }
    
    @EventHandler
	public void serverStarting(FMLServerStartingEvent e) {
    	/*if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
	    	try {
	    		ModMetrics.sendMetrics();
	    	}
	    	catch (Exception x) {
	    		x.printStackTrace();
	    	}
    	}*/
    	e.registerServerCommand(new CommandMeteorRuby());
		e.registerServerCommand(new CommandSpawnGems());
		e.registerServerCommand(new CommandScanGems());
	}
    
    //Used for debugging
	public void chatInfoMessage(String message) {
		if (DEVELOPER /*&& FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER*/) {
			PlayerList list = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
			logger.info(message);
			list.sendMessage(new TextComponentString(message));
		}
	}
	
    public static boolean isDayToday(int month, int day) {
    	boolean sameMonth = Calendar.getInstance().get(Calendar.MONTH) == month - 1;
    	boolean sameDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == day;
    	return sameMonth && sameDay;
    }
    
    public static boolean isFireworksDay() {
    	return KAGIC.isDayToday(1, 1) || KAGIC.isDayToday(3, 21) || KAGIC.isDayToday(7, 4);
    }
    
    public static boolean isHalloween() {
    	for (int day = 25; day <= 31; ++day) {
    		if (KAGIC.isDayToday(10, day)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isAprilFools() {
    	return KAGIC.isDayToday(4, 1);
    }
    
    public static boolean isChristmas() {
    	for (int day = 24; day <= 31; ++day) {
    		if (KAGIC.isDayToday(12, day)) {
    			return true;
    		}
    	}
    	for (int day = 1; day <= 5; ++day) {
    		if (KAGIC.isDayToday(1, day)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public static boolean isBirthday() {
    	return KAGIC.isDayToday(1, 17) || KAGIC.isDayToday(10, 24) || KAGIC.isDayToday(10, 22);
    }

    public static boolean isBirthdayTomorrow() {
    	return KAGIC.isDayToday(1, 16) || KAGIC.isDayToday(10, 23) || KAGIC.isDayToday(10, 21);
    }
	public static void addSkill(Class<? extends SkillBase> skillToAdd) {
		EntityCrystalSkills.SKILLS.add(skillToAdd);
	}
}