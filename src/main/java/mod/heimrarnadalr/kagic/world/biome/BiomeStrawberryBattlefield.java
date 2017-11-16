package mod.heimrarnadalr.kagic.world.biome;

import java.util.Random;

import mod.heimrarnadalr.kagic.world.biome.BiomeFloatingPeaks.Decorator;
import mod.heimrarnadalr.kagic.world.structure.WorldGenFloatingIslands;
import mod.heimrarnadalr.kagic.world.structure.WorldGenStrawberryBush;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeStrawberryBattlefield extends Biome {
	private static final BiomeProperties PROPERTIES = new BiomeProperties("Gem Battlefield");

	static {
		PROPERTIES.setBaseHeight(0.4F);
		PROPERTIES.setHeightVariation(0.05F);
		PROPERTIES.setTemperature(0.9F);
		PROPERTIES.setRainfall(0.5F);
		PROPERTIES.setWaterColor(0xEAFFFC);
	}

	public BiomeStrawberryBattlefield() {
		super(BiomeStrawberryBattlefield.PROPERTIES);
		this.setRegistryName("strawberry_battlefield");

		this.topBlock = Blocks.GRASS.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();

		this.decorator.generateFalls = false;
		this.decorator.gravelPatchesPerChunk = 0;
		this.decorator.sandPatchesPerChunk = 0;
		this.decorator.treesPerChunk = 0;
		this.decorator.extraTreeChance = 0F;
		this.decorator.flowersPerChunk = 0;
	}

	
	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeStrawberryBattlefield.Decorator();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x8C8A60;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0xE3E66F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0xE9FDF0;
	}
	
	class Decorator extends BiomeDecorator
	{
		private final WorldGenFloatingIslands floatGenerator = new WorldGenFloatingIslands(4F, 7F, 7F, 16F, true);
		private final WorldGenStrawberryBush strawberryGenerator = new WorldGenStrawberryBush();
		private static final int FLOATCHANCERECIPROCAL = 12;	
		private static final int VERTICALOFFSET = 16;
		private static final int OFFSETVARIANCE = 5;
		
		@Override
		protected void genDecorations(Biome biome, World world, Random random) {
			int j = random.nextInt(16) + 8;
			int k = random.nextInt(16) + 8;
			
			if (random.nextInt(Decorator.FLOATCHANCERECIPROCAL) == 0) {
				int offset = random.nextInt(OFFSETVARIANCE) + (int) (16F + Decorator.VERTICALOFFSET);
				BlockPos floatPos = world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)).up(offset);
				if (!this.floatGenerator.generate(world, random, floatPos)) {
					this.strawberryGenerator.generate(world, random, floatPos.down(offset));
				}
			} else {
				BlockPos strawberryPos = world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k));
				this.strawberryGenerator.generate(world, random, strawberryPos);
			}
			super.genDecorations(biome, world, random);
		}
	}
}
