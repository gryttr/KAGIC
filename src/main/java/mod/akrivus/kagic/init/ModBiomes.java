package mod.akrivus.kagic.init;

import mod.akrivus.kagic.biomes.BiomeHomeworld;
import mod.akrivus.kagic.biomes.BiomeLion;
import mod.heimrarnadalr.kagic.world.biome.BiomeKindergarten;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;

public class ModBiomes {
	public static final Biome HOMEWORLD = new BiomeHomeworld();
	public static final Biome LION = new BiomeLion();
	public static final Biome KINDERGARTEN = new BiomeKindergarten();
	
	public static void register(RegistryEvent.Register<Biome> event) {
		//BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(HOMEWORLD, 0));
		//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(LION, 0));
		event.getRegistry().register(KINDERGARTEN);
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(KINDERGARTEN, 1000000));
		BiomeManager.addSpawnBiome(KINDERGARTEN);
	}
}
