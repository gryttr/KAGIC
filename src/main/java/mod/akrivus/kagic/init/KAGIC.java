package mod.akrivus.kagic.init;

import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import mod.akrivus.kagic.client.gui.KTGUIProxy;
import mod.akrivus.kagic.command.CommandMeteorRuby;
import mod.akrivus.kagic.command.CommandSpawnGems;
import mod.akrivus.kagic.server.SpaceStuff;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.akrivus.kagic.tileentity.WarpRenderer;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.proxies.CommonProxy;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = KAGIC.MODID, version = KAGIC.VERSION, guiFactory = "mod.akrivus.kagic.client.gui.GuiFactory")
public class KAGIC {
    public static final String MODID = "kagic";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
    public static final boolean DEVELOPER = false;
 
	public static Logger logger;

    @Instance
    public static KAGIC instance;
    public static SpaceStuff spaceStuff;

    @SidedProxy(clientSide = "mod.heimrarnadalr.kagic.proxies.ClientProxy", serverSide = "mod.heimrarnadalr.kagic.proxies.ServerProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    	logger = e.getModLog();
        ModAchievements.register();
        ModBiomes.register();
        ModBlocks.register();
        ModConfigs.register(e);
        ModDimensions.register();
    	ModEnchantments.register();
    	ModItems.register();
    	ModRecipes.register();
    	ModSounds.register();
		KTPacketHandler.registerMessages(KAGIC.MODID);
    }
    @EventHandler
    public void init(FMLInitializationEvent e) {
    	ModEntities.register();
    	ModEvents.register();
    	ModTileEntities.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new KTGUIProxy());
		if (e.getSide() == Side.CLIENT) {
			ModTESRs.register();
		}
    }
    
    @EventHandler
	public void serverStarting(FMLServerStartingEvent e) {
    	if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
	    	try {
	    		ModMetrics.setMetrics(null);
	    	}
	    	catch (Exception x) {
	    		x.printStackTrace();
	    	}
    	}
    	e.registerServerCommand(new CommandMeteorRuby());
		e.registerServerCommand(new CommandSpawnGems());
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
    	return KAGIC.isDayToday(10, 31);
    }
    public static boolean isAprilFools() {
    	return KAGIC.isDayToday(4, 1);
    }
    public static boolean isChristmas() {
    	return KAGIC.isDayToday(12, 25);
    }
    public static boolean isBirthday() {
    	return KAGIC.isDayToday(1, 17) || KAGIC.isDayToday(10, 24);
    }
}