package mod.akrivus.kagic.init;

import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import mod.akrivus.kagic.blocks.BlockPinkSandstone;
import mod.akrivus.kagic.client.gui.KTGUIProxy;
import mod.akrivus.kagic.command.CommandMeteorRuby;
import mod.akrivus.kagic.command.CommandScanGems;
import mod.akrivus.kagic.command.CommandSpawnGems;
import mod.akrivus.kagic.server.SpaceStuff;
import mod.heimrarnadalr.kagic.chunk.KAGICChunkCallback;
import mod.heimrarnadalr.kagic.crafting.KAGICSmeltingRecipes;
import mod.heimrarnadalr.kagic.dispenser.DispenserBehaviors;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.proxies.CommonProxy;
import mod.heimrarnadalr.kagic.world.Fogger;
import mod.heimrarnadalr.kagic.world.GenEventCanceller;
import mod.heimrarnadalr.kagic.world.KAGICWorldGenerator;
import mod.heimrarnadalr.kagic.world.structure.LootTables;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = KAGIC.MODID, version = KAGIC.VERSION, acceptedMinecraftVersions = KAGIC.MCVERSION, guiFactory = "mod.akrivus.kagic.client.gui.GuiFactory")
public class KAGIC {
    public static final String MODID = "kagic";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
    public static final boolean DEVELOPER = true;
 
	public static Logger logger;

    @Instance
    public static KAGIC instance;
    public static SpaceStuff spaceStuff;
    public static KAGICWorldGenerator worldGen;

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
        ModDimensions.register();
        KAGICSmeltingRecipes.register();
		KTPacketHandler.registerMessages(KAGIC.MODID);
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new KAGICChunkCallback());
		LootTables.register();
		KAGIC.worldGen = new KAGICWorldGenerator();
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
		DispenserBehaviors.register();
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
		if (DEVELOPER) {
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
    	return KAGIC.isDayToday(12, 25);
    }
    
    public static boolean isBirthday() {
    	return KAGIC.isDayToday(1, 17) || KAGIC.isDayToday(10, 24) || KAGIC.isDayToday(10, 22);
    }

    public static boolean isBirthdayTomorrow() {
    	return KAGIC.isDayToday(1, 16) || KAGIC.isDayToday(10, 23) || KAGIC.isDayToday(10, 21);
    }
}