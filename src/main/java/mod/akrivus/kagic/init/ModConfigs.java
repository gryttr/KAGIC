package mod.akrivus.kagic.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
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
	public static boolean canRebel;
	public static Property ruinDimensions;
	
	public static int kindergartenWeight;
	public static int floatingPeaksWeight;
	public static int strawberryBattlefieldWeight;
	
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
		ModConfigs.canRebel = ModConfigs.settings.getBoolean("Gems can rebel: ", "preferences", false, "Whether or not navel gems and meteor rubies have a chance of rebelling after a short time serving you");

		ModConfigs.settings.addCustomCategoryComment("world", "These affect the behavior of KAGIC's worldgen elements.");
		ModConfigs.kindergartenWeight = ModConfigs.settings.getInt("Kindergarten biome weight:", "world", 1, 1, 1000000, "Rarity of the Kindergarten biome. Plains = 10");
		ModConfigs.floatingPeaksWeight = ModConfigs.settings.getInt("Floating Peaks biome weight:", "world", 1, 1, 1000000, "Rarity of the Floating Peaks biome. Extreme Hills = 10");
		ModConfigs.strawberryBattlefieldWeight = ModConfigs.settings.getInt("Strawberry Battlefield biome weight:", "world", 1, 1, 1000000, "Rarity of the Strawberry Battlefield biome. Plains = 10");
		ModConfigs.ruinDimensions = ModConfigs.settings.get("world", "Ruin Dimensions", new int[] {0}, "List of dimension IDs to generate ruins in.");
		
		ModConfigs.settings.addCustomCategoryComment("steven", "These are settings for Steven.");
		//ModConfigs.notifyOnUpdates = ModConfigs.settings.getBoolean("Update notifications:", "preferences", true, "Setting to false might expose you to possible bugs.");		
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
