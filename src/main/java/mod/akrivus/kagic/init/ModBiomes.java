package mod.akrivus.kagic.init;

import mod.akrivus.kagic.biomes.BiomeHomeworld;
import mod.akrivus.kagic.biomes.BiomeLion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class ModBiomes {
	public static final Biome HOMEWORLD = new BiomeHomeworld();
	public static final Biome LION = new BiomeLion();
	public static void register() {
		BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(HOMEWORLD, 0));
		BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(LION, 0));
	}
}
