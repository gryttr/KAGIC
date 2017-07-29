package mod.akrivus.kagic.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfigs {
	public static File file;
	public static Configuration settings;
	public static boolean notifyOnUpdates;
	public static boolean useMetrics;
	public static boolean canGemsMakeSounds;
	public static boolean spawnMeteorRubies;
	public static int meteorRubyRate;
	public static boolean instructInjectors;
	public static void register(FMLPreInitializationEvent e) {
		ModConfigs.file = e.getSuggestedConfigurationFile();
		ModConfigs.settings = new Configuration(ModConfigs.file);
		ModConfigs.setValues();
	}
	public static void setValues() {
		ModConfigs.settings.addCustomCategoryComment("preferences", "These can be set to the user's delight and do not change mod behavior.");
		ModConfigs.notifyOnUpdates = ModConfigs.settings.getBoolean("Update notifications:", "preferences", true, "Setting to false might expose you to possible bugs.");
		ModConfigs.useMetrics = ModConfigs.settings.getBoolean("Use metrics?", "preferences", true, "This helps me fine-tune the mod and obtain crash reports.");
		ModConfigs.spawnMeteorRubies = ModConfigs.settings.getBoolean("Spawn Meteor Rubies:", "preferences", true, "Should Meteor Rubies spawn?");
		ModConfigs.canGemsMakeSounds = ModConfigs.settings.getBoolean("Make Gems talk:", "preferences", true, "Setting to true can make Gems very loud and annoying.");
		ModConfigs.meteorRubyRate = ModConfigs.settings.getInt("Meteor Ruby spawn rate:", "preferences", 10, 0, 365, "Amount of days minimum between Meteor Rubies.");
		ModConfigs.instructInjectors = ModConfigs.settings.getBoolean("Instruct Injector setup:", "preferences", true, "Instructs players on how to set up injectors.");
		ModConfigs.settings.save();
	}
	public static void syncConfiguration() {
		ModConfigs.settings.save();
	}
	public static List<IConfigElement> getCategories() {
		List<IConfigElement> returnee = new ArrayList<IConfigElement>();
		for (String title : ModConfigs.settings.getCategoryNames()) {
			if (!title.equals("server")) {	
				IConfigElement elements = new ConfigElement(ModConfigs.settings.getCategory(title));
				for (IConfigElement element : (List<IConfigElement>) elements.getChildElements()) {
					returnee.add(element);
				}
			}
		}
		return returnee;
	}
}
