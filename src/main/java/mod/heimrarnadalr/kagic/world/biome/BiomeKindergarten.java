package mod.heimrarnadalr.kagic.world.biome;

import java.util.List;
import java.util.Random;

import mod.akrivus.kagic.blocks.BlockIncubator;
import mod.akrivus.kagic.blocks.BlockInjector;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeKindergarten extends Biome {
	private static final BiomeProperties PROPERTIES = new BiomeProperties("Kindergarten");
	protected static final IBlockState DRAINED_BLOCK = ModBlocks.DRAINED_BLOCK.getDefaultState();
	protected static final IBlockState DRAINED_BLOCK_2 = ModBlocks.DRAINED_BLOCK_2.getDefaultState();
	protected static final IBlockState DRAINED_BANDS = ModBlocks.DRAINED_BANDS.getDefaultState(); 
	protected static final int SEALEVELOFFSET = -10;
	private long worldSeed;
	private NoiseGeneratorPerlin cliffNoise;
	private NoiseGeneratorPerlin cliffRoofNoise;
	
	static {
		PROPERTIES.setBaseHeight(0.1F);
		PROPERTIES.setHeightVariation(0F);
		PROPERTIES.setRainDisabled();
		PROPERTIES.setWaterColor(0x646400);
	}
	
	public BiomeKindergarten() {
		this(PROPERTIES);
	}
	
	public BiomeKindergarten(BiomeProperties properties) {
		super(properties);
		
		this.setRegistryName("kindergarten");
		
		this.topBlock = AIR;//this.DRAINED_BLOCK;
		this.fillerBlock = AIR;//this.DRAINED_BLOCK;
		
		//This biome is devoid of life
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.clear();
		for (List<Biome.SpawnListEntry> list : this.modSpawnableLists.values()) {
			list.clear();
		}
		
		this.decorator.bigMushroomsPerChunk = 0;
		this.decorator.cactiPerChunk = 0;
		this.decorator.clayPerChunk = 0;
		this.decorator.deadBushPerChunk = 0;
		this.decorator.flowersPerChunk = 0;
		this.decorator.grassPerChunk = 0;
		this.decorator.gravelPatchesPerChunk = 0;
		this.decorator.mushroomsPerChunk = 0;
		this.decorator.reedsPerChunk = 0;
		this.decorator.sandPatchesPerChunk = 0;
		//The 'trees' of this biome are injectors
		this.decorator.treesPerChunk = 2;
		this.decorator.waterlilyPerChunk = 0;
		
		this.decorator.generateFalls = false;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeKindergarten.Decorator();
	}
	
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noiseVal)
	{
		if (this.cliffNoise == null || this.cliffRoofNoise == null || this.worldSeed != world.getSeed()) {
			this.worldSeed = world.getSeed();
			Random peakRandom = new Random(this.worldSeed);
			this.cliffNoise = new NoiseGeneratorPerlin(peakRandom, 8);
			this.cliffRoofNoise = new NoiseGeneratorPerlin(peakRandom, 2);
		}

		double peakHeight = 0.0D;

		int i = (x & -16) + (z & 15);
		int j = (z & -16) + (x & 15);
		double d0 = Math.min(Math.abs(noiseVal), Math.abs(this.cliffNoise.getValue((double)i * 1D, (double)j * 1D)));

		if (d0 > 0.0D) {
			//0.001953125
			double d2 = Math.abs(this.cliffRoofNoise.getValue((double)i * 0.001953125D, (double)j * 0.001953125D));
			peakHeight = d0 * d0 * 8D;
			double peakOffset = Math.ceil(d2 * 25.0D) + 40.0D;

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

		IBlockState drainedBlockState = DRAINED_BLOCK;
		
		for (int height = 255; height >= 0; --height) {
			if (height % 6 == 0 || height % 6 == 1) {
				drainedBlockState = DRAINED_BLOCK_2;
			} else if (height % 5 == 0) {
				drainedBlockState = DRAINED_BANDS;
			} else {
				drainedBlockState = DRAINED_BLOCK;
			}

			if (chunkPrimer.getBlockState(chunkZ, height, chunkX).getMaterial() == Material.AIR && height < (int)peakHeight) {
				chunkPrimer.setBlockState(chunkZ, height, chunkX, STONE);
			}

			if (height <= rand.nextInt(5)) {
				chunkPrimer.setBlockState(chunkZ, height, chunkX, BEDROCK);
			}
			else {
				if (chunkPrimer.getBlockState(chunkZ, height, chunkX).getBlock() == Blocks.STONE || chunkPrimer.getBlockState(chunkZ, height, chunkX).getBlock() == WATER) {
					chunkPrimer.setBlockState(chunkZ, height, chunkX, drainedBlockState);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x382E2F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0x69555E;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0xD7D3DD;
	}
	
	class Decorator extends BiomeDecorator
	{
		private static final float HOLE_CHANCE = 0.9F;
		private static final int HOLE_HEIGHT = 3;
		private static final int HOLE_DEPTH = 4;
		private static final float INJECTOR_CHANCE = 0.05F;
		private static final float GEM_CHANCE = 0.0001F;
		private Decorator()
		{
			super();
		}
		
		private void generateInjector(World world, BlockPos pos, EnumFacing direction) {
				world.setBlockState(pos, ModBlocks.GEM_DRILL.getDefaultState());
				world.setBlockState(pos.up(1), ModBlocks.INJECTOR.getDefaultState().withProperty(BlockInjector.FACING, direction));
				world.setBlockState(pos.up(2), ModBlocks.INCUBATOR.getDefaultState().withProperty(BlockIncubator.FACING, direction));
		}
		
		private boolean canMakeHole(World world, BlockPos pos, EnumFacing direction) {
			for (int y = 0; y < Decorator.HOLE_HEIGHT + 1; ++y) {
				for (int z = 2; z < Decorator.HOLE_DEPTH + 2; ++z) {
					if (!world.isBlockFullCube(pos.up(y).offset(direction, z)) ||
						!world.isBlockFullCube(pos.up(y).offset(direction, z).offset(direction.rotateY())) ||
						!world.isBlockFullCube(pos.up(y).offset(direction, z).offset(direction.rotateYCCW()))) {
						return false;
					}
				}
			}
			return true;
		}
		
		private void generateHole(World world, BlockPos pos, EnumFacing direction, Random rand) {
			if (rand.nextFloat() < GEM_CHANCE) {
				world.setBlockState(pos.offset(direction, Decorator.HOLE_DEPTH), ModBlocks.GEM_SEED.getDefaultState());
				world.setBlockState(pos.up().offset(direction, Decorator.HOLE_DEPTH), Blocks.QUARTZ_ORE.getDefaultState());
			} else {
				for (int y = 0; y < Decorator.HOLE_HEIGHT; ++y) {
					for (int l = 0; l <= Decorator.HOLE_DEPTH; ++l) {
						world.setBlockToAir(pos.up(y).offset(direction, l));
					}
				}
			}
		}

		private EnumFacing generateExitHole(World world, BlockPos pos, Random random) {
			if (random.nextFloat() > Decorator.HOLE_CHANCE) {
				return EnumFacing.DOWN;
			}
			if (canMakeHole(world, pos, EnumFacing.SOUTH)) {
				generateHole(world, pos, EnumFacing.SOUTH, random);
				return EnumFacing.SOUTH;
			}
			if (canMakeHole(world, pos, EnumFacing.WEST)) {
				generateHole(world, pos, EnumFacing.WEST, random);
				return EnumFacing.WEST;
			}
			if (canMakeHole(world, pos, EnumFacing.NORTH)) {
				generateHole(world, pos, EnumFacing.NORTH, random);
				return EnumFacing.NORTH;
			}
			if (canMakeHole(world, pos, EnumFacing.EAST)) {
				generateHole(world, pos, EnumFacing.EAST, random);
				return EnumFacing.EAST;
			}
			return EnumFacing.DOWN;
		}
		
		@Override
		protected void genDecorations(Biome biome, World world, Random random) {
			for (int x = 8; x < 24; x += 2) {
				for (int z = 8; z < 24; z += 2) {
					BlockPos potentialHolePos = world.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z));
					if (potentialHolePos.getY() <= 120) {
						EnumFacing holeDirection = EnumFacing.DOWN;
						for (int y = 0; y < 60; y += 5) {
							EnumFacing dir = generateExitHole(world, potentialHolePos.up(y), random);
							if (dir != EnumFacing.DOWN) {
								holeDirection = dir;								
							}
						}
						if (holeDirection != EnumFacing.DOWN && random.nextFloat() < Decorator.INJECTOR_CHANCE) {
							generateInjector(world, world.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z).up().offset(holeDirection, HOLE_DEPTH)), holeDirection);
						}
					}
				}
			}
			
			//super.genDecorations(biome, world, random);
		}
		
		@Override
		protected void generateOres(World worldIn, Random random)
		{
			//All minerals have been drained from this biome
			return;
		}
	}
}
