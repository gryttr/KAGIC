package mod.akrivus.kagic.biomes;

import net.minecraft.world.biome.Biome;

public class BiomeHomeworld extends Biome {
	public BiomeHomeworld() {
		super(new BiomeProperties("homeworld"));
		this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
	}
}
