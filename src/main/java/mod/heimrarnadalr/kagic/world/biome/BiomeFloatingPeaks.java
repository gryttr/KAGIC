package mod.heimrarnadalr.kagic.world.biome;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.heimrarnadalr.kagic.world.structure.WorldGenFloatingIslands;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeFloatingPeaks  extends Biome {
	private static final BiomeProperties PROPERTIES = new BiomeProperties("Floating Peaks");
	private final WorldGenSavannaTree tree = new WorldGenSavannaTree(false);
	private long worldSeed;
	private NoiseGeneratorPerlin peakNoise;
	private NoiseGeneratorPerlin peakRoofNoise;
	private static final int FILLERDEPTH = 1;
	
	static {
		PROPERTIES.setBaseHeight(0.4F);
		PROPERTIES.setHeightVariation(0.05F);
		PROPERTIES.setTemperature(0.9F);
		PROPERTIES.setRainfall(0.5F);
		PROPERTIES.setWaterColor(0xEAFFFC);
	}

	public BiomeFloatingPeaks() {
		super(BiomeFloatingPeaks.PROPERTIES);

		this.setRegistryName("floatingpeaks");
		
		this.topBlock = Blocks.GRASS.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		
		this.decorator.generateFalls = false;
		this.decorator.gravelPatchesPerChunk = 0;
		this.decorator.sandPatchesPerChunk = 0;
		this.decorator.treesPerChunk = 1;
		
		this.spawnableCreatureList.clear();
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityLlama.class, 12, 2, 3));
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noiseVal) {
		if (this.peakNoise == null || this.peakRoofNoise == null || this.worldSeed != world.getSeed()) {
			this.worldSeed = world.getSeed();
			Random peakRandom = new Random(this.worldSeed);
			this.peakNoise = new NoiseGeneratorPerlin(peakRandom, 12);
			this.peakRoofNoise = new NoiseGeneratorPerlin(peakRandom, 16);
		}

		double peakHeight = 0.0D;

		int i = (x & -16) + (z & 15);
		int j = (z & -16) + (x & 15);
		double d0 = Math.min(Math.abs(noiseVal), this.peakNoise.getValue((double)i * 0.5D, (double)j * 0.5D));

		if (d0 > 0.0D) {
			//0.001953125
			double d2 = Math.abs(this.peakRoofNoise.getValue((double)i * 0.05D, (double)j * 0.05D));
			peakHeight = d0 * d0 * 4D;
			double peakOffset = Math.ceil(d2 * 50.0D) + 50.0D;

			if (peakHeight > peakOffset)
			{
				peakHeight = peakOffset;
			}

			peakHeight = peakHeight + 64.0D;
		}

		int chunkX = x & 15;
		int chunkZ = z & 15;
		int seaLevel = world.getSeaLevel();
		int l = -1;

		for (int height = 255; height >= 0; --height) {
			if (chunkPrimer.getBlockState(chunkZ, height, chunkX).getMaterial() == Material.AIR && height < (int)peakHeight) {
				chunkPrimer.setBlockState(chunkZ, height, chunkX, STONE);
			}

			if (height <= rand.nextInt(5)) {
				chunkPrimer.setBlockState(chunkZ, height, chunkX, BEDROCK);
			}
			else {
				IBlockState iblockstate1 = chunkPrimer.getBlockState(chunkZ, height, chunkX);

				if (iblockstate1.getMaterial() == Material.AIR) {
					l = -this.FILLERDEPTH;
				}
				else if (iblockstate1.getBlock() == Blocks.STONE) {
					if (l == -this.FILLERDEPTH) {
						chunkPrimer.setBlockState(chunkZ, height, chunkX, this.topBlock);
						++l;
					} else if (l < 0) {
						chunkPrimer.setBlockState(chunkZ, height, chunkX, this.fillerBlock);
						++l;
					} else if (l == 0) {
						l = 1;
						chunkPrimer.setBlockState(chunkZ, height, chunkX, this.fillerBlock);
					}
					else if (l > 0) {
						chunkPrimer.setBlockState(chunkZ, height, chunkX, STONE);
					}
				}
			}
		}
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		return this.tree;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeFloatingPeaks.Decorator();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x7B4762;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0xE4F08C;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0xF8E8E4;
	}
	
	class Decorator extends BiomeDecorator
	{
		private final WorldGenFloatingIslands floatGenerator = new WorldGenFloatingIslands(5F, 9F, 10F, 18F, false);
		private static final int FLOATCHANCERECIPROCAL = 8;	
		private static final int VERTICALOFFSET = 20;
		private static final int OFFSETVARIANCE = 10;
		
		@Override
		protected void genDecorations(Biome biome, World world, Random random) {
			if (random.nextInt(Decorator.FLOATCHANCERECIPROCAL) == 0) {
				int j = random.nextInt(16) + 8;
				int k = random.nextInt(16) + 8;
				int offset = random.nextInt(OFFSETVARIANCE) + (int) (18F + Decorator.VERTICALOFFSET);
				BlockPos floatPos = world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)).up(offset);
				if (floatPos.getY() < 175) {
					this.floatGenerator.generate(world, random, floatPos);
				}
			}
			super.genDecorations(biome, world, random);
		}
	}
}