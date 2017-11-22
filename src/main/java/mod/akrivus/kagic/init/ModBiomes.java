package mod.akrivus.kagic.init;

import mod.akrivus.kagic.biomes.BiomeHomeworld;
import mod.akrivus.kagic.biomes.BiomeLion;
import mod.heimrarnadalr.kagic.world.biome.BiomeFloatingPeaks;
import mod.heimrarnadalr.kagic.world.biome.BiomeKindergarten;
import mod.heimrarnadalr.kagic.world.biome.BiomeStrawberryBattlefield;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;

public class ModBiomes {
	public static final Biome HOMEWORLD = new BiomeHomeworld();
	public static final Biome LION = new BiomeLion();
	public static final Biome KINDERGARTEN = new BiomeKindergarten();
	public static final Biome FLOATINGPEAKS = new BiomeFloatingPeaks();
	public static final Biome STRAWBERRYBATTLEFIELD = new BiomeStrawberryBattlefield();
	
	public static void register(RegistryEvent.Register<Biome> event) {
		//BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(HOMEWORLD, 0));
		//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(LION, 0));
		KAGIC.instance.logger.info("Setting biome weights: Kindergarten = " + ModConfigs.kindergartenWeight + ", Floating Peaks = " + ModConfigs.floatingPeaksWeight);
		event.getRegistry().register(KINDERGARTEN);
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(KINDERGARTEN, ModConfigs.kindergartenWeight));
		BiomeDictionary.addTypes(KINDERGARTEN, Type.DEAD, Type.DRY, Type.MESA, Type.SPOOKY, Type.WASTELAND, Type.RARE);
		event.getRegistry().register(FLOATINGPEAKS);
		BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(FLOATINGPEAKS, ModConfigs.floatingPeaksWeight));
		BiomeManager.addSpawnBiome(FLOATINGPEAKS);
		BiomeDictionary.addTypes(FLOATINGPEAKS, Type.MOUNTAIN, Type.SAVANNA, Type.MAGICAL, Type.DRY, Type.RARE);
		event.getRegistry().register(STRAWBERRYBATTLEFIELD);
		BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(STRAWBERRYBATTLEFIELD, ModConfigs.strawberryBattlefieldWeight));
		BiomeDictionary.addTypes(STRAWBERRYBATTLEFIELD, Type.HILLS, Type.PLAINS, Type.MAGICAL, Type.LUSH, Type.RARE);
	}
}
