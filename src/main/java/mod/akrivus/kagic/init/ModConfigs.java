package mod.akrivus.kagic.init;

import net.minecraftforge.common.config.Config;

@Config(modid = KAGIC.MODID)
@Config.LangKey("kagic.config.title")
public class ModConfigs {
	public static boolean notifyOnUpdates = true;
	public static boolean useMetrics = true;
	public static boolean canGemsMakeSounds = true;
	public static boolean spawnMeteorRubies = true;
	public static int meteorRubyRate = 10;
	public static boolean instructInjectors = true;
	public static boolean canRebel = false;
	public static boolean displayNames = false;
	public static int kindergartenWeight = 1;
	public static int floatingPeaksWeight = 1;
	public static int strawberryBattlefieldWeight = 1;
	public static String ruinDimensions = "0";
}
