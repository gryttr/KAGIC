package mod.akrivus.kagic.biomes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class BiomeLion extends Biome {
	public BiomeLion() {
		super(new BiomeProperties("lion").setWaterColor(0xF385A8));
		this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
	}
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0xF385A8;
	}
	public int getGrassColorAtPos(BlockPos pos) {
		return 0xF6B3C9;
	}
}
