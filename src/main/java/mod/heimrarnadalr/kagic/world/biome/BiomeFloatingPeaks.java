package mod.heimrarnadalr.kagic.world.biome;

import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityLlama;
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
	private int fillerDepth = 2;
	
	static {
		PROPERTIES.setBaseHeight(0.5F);
		PROPERTIES.setHeightVariation(0.25F);
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
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int chunkX, int chunkZ, double noiseVal) {

		if (this.peakNoise == null || this.peakRoofNoise == null || this.worldSeed != world.getSeed()) {
			Random peakRandom = new Random(this.worldSeed);
			this.peakNoise = new NoiseGeneratorPerlin(peakRandom, 4);
			this.peakRoofNoise = new NoiseGeneratorPerlin(peakRandom, 1);
		}

		this.worldSeed = world.getSeed();
		double peakHeight = 0.0D;

		int i = (chunkX & -128) + (chunkZ & 127);
		int j = (chunkZ & -128) + (chunkX & 127);
		double d0 = Math.min(Math.abs(noiseVal) * .9, this.peakNoise.getValue((double)i * 0.5D, (double)j * 0.5D));

		if (d0 > 0.0D) {
			//0.001953125
			double d2 = Math.abs(this.peakRoofNoise.getValue((double)i * 0.001953125D, (double)j * 0.001953125D));
			peakHeight = d0 * d0 * 2.5D;
			//Additive factor was 14.0
			double peakOffset = Math.ceil(d2 * 50.0D) + 14.0D;

			if (peakHeight > peakOffset)
			{
				peakHeight = peakOffset;
			}

			peakHeight = peakHeight + 64.0D;
		}

		int x = chunkX & 15;
		int z = chunkZ & 15;
		int seaLevel = world.getSeaLevel();
		IBlockState fillerState = this.fillerBlock;
		int l = -1;

		for (int height = 255; height >= 0; --height) {
			if (chunkPrimer.getBlockState(x, height, z).getMaterial() == Material.AIR && height < (int)peakHeight) {
				chunkPrimer.setBlockState(x, height, z, STONE);
			}

			if (height <= rand.nextInt(5)) {
				chunkPrimer.setBlockState(x, height, z, BEDROCK);
			}
			else {
				IBlockState iblockstate1 = chunkPrimer.getBlockState(x, height, z);

				if (iblockstate1.getMaterial() == Material.AIR) {
					l = -1 - this.fillerDepth;
				}
				else if (iblockstate1.getBlock() == Blocks.STONE) {
					if (l == -1 - this.fillerDepth) {
						chunkPrimer.setBlockState(x, height, z, this.topBlock);
						++l;
					} else if (l < 0) {
						chunkPrimer.setBlockState(x, height, z, this.fillerBlock);
						++l;
					} else if (l == 0) {
						l = 1;//k + Math.max(0, height - seaLevel);
						chunkPrimer.setBlockState(x, height, z, this.fillerBlock);
					}
					else if (l > 0) {
						//--l;
						chunkPrimer.setBlockState(x, height, z, STONE);
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
		private static final int FLOATCHANCERECIPROCAL = 8;
		private static final float MINRADIUS = 5F;
		private static final float MAXRADIUS = 9F;
		private static final float MINHEIGHT = 10F;
		private static final float MAXHEIGHT = 18F;
		
		private static final int VERTICALOFFSET = 20;
		private static final int OFFSETVARIANCE = 10;
		
		private void generateFloats(World world, BlockPos pos, Random random) {
			float radius = Decorator.MINRADIUS + random.nextInt((int) (Decorator.MAXRADIUS - Decorator.MINRADIUS));
			float height = Decorator.MINHEIGHT + random.nextInt((int) (Decorator.MAXHEIGHT - Decorator.MINHEIGHT));
			
			for (float x = -radius; x <= radius; ++x) {
				for (float z = -radius; z <= radius; ++z) {
					float a = (x * x) / (radius * radius);
					float c = (z * z) / (radius * radius);
					
					for (float y = 0; y >= -height; --y) {
						float b = (y * y) / (height * height);
						
						if (a + b + c <= 1) {
							world.setBlockState(pos.add(x, y, z), STONE);
						}
					}
					
					if (a + c <= 1) {
						world.setBlockState(pos.add(x, 1, z), Blocks.GRASS.getDefaultState());
					}
				}
			}
		}
		
		@Override
		protected void genDecorations(Biome biome, World world, Random random) {
			if (random.nextInt(Decorator.FLOATCHANCERECIPROCAL) == 0) {
				int j = random.nextInt(16) + 8;
				int k = random.nextInt(16) + 8;
				int offset = random.nextInt(OFFSETVARIANCE) + (int) (Decorator.MAXHEIGHT + Decorator.VERTICALOFFSET);
				BlockPos floatPos = world.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)).up(offset);
				if (floatPos.getY() < 175) {
					KAGIC.instance.chatInfoMessage("Generating float");
					this.generateFloats(world, floatPos, random);
				}
			}
			super.genDecorations(biome, world, random);
		}
	}
}