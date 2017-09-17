package mod.heimrarnadalr.kagic.world.biome;

import java.util.List;
import java.util.Random;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeKindergarten extends Biome {
	protected static final IBlockState DRAINED_BLOCK = ModBlocks.DRAINED_BLOCK.getDefaultState();
	protected static final int SEALEVELOFFSET = -10;
	
	public BiomeKindergarten() {
		this(getBiomeProperties());
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
		this.decorator.gravelPatchesPerChunk = 1;
		this.decorator.mushroomsPerChunk = 0;
		this.decorator.reedsPerChunk = 0;
		this.decorator.sandPatchesPerChunk = 0;
		//The 'trees' of this biome are injectors
		this.decorator.treesPerChunk = 2;
		this.decorator.waterlilyPerChunk = 0;
		
		this.decorator.generateFalls = false;
	}

	private static BiomeProperties getBiomeProperties() {
		BiomeProperties properties = new BiomeProperties("Kindergarten");
		properties.setBaseHeight(-1F);
		properties.setHeightVariation(0F);
		properties.setRainDisabled();
		return properties;
	}
	
	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeKindergarten.Decorator();
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		//TODO replace with injector tree
		return TREE_FEATURE;
	}
	
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double noiseVal)
	{
        int seaLevel = world.getSeaLevel() + this.SEALEVELOFFSET;
        IBlockState topState = this.topBlock;
        IBlockState fillerState = this.fillerBlock;
        int j = -1;
        int k = 0;//(int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int chunkX = x & 15;
        int chunkZ = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int height = 255; height >= 0; --height)
        {
            if (height <= rand.nextInt(5))
            {
                chunkPrimer.setBlockState(chunkZ, height, chunkX, BEDROCK);
            }
            else
            {
                IBlockState iblockstate2 = chunkPrimer.getBlockState(chunkZ, height, chunkX);

                if (iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                }
                else if (iblockstate2.getBlock() == Blocks.STONE)
                {
                    if (j == -1)
                    {
                        if (k <= 0)
                        {
                        	topState = AIR;
                        	fillerState = DRAINED_BLOCK;
                        }
                        else if (height >= seaLevel - 4 && height <= seaLevel + 1)
                        {
                        	topState = this.topBlock;
                        	fillerState = this.fillerBlock;
                        }

                        j = k;

                        if (height >= seaLevel - 1)
                        {
                            chunkPrimer.setBlockState(chunkZ, height, chunkX, topState);
                        }
                        else if (height < seaLevel - 7 - k)
                        {
                        	topState = AIR;
                        	fillerState = DRAINED_BLOCK;
                            chunkPrimer.setBlockState(chunkZ, height, chunkX, GRAVEL);
                        }
                        else
                        {
                            chunkPrimer.setBlockState(chunkZ, height, chunkX, fillerState);
                        }
                    }
                    else if (j > 0)
                    {
                        --j;
                        chunkPrimer.setBlockState(chunkZ, height, chunkX, fillerState);
                    }
                }
            }
            if (chunkPrimer.getBlockState(chunkZ, height, chunkX) == STONE) {
            	//KAGIC.instance.chatInfoMessage("Detected stone");
            	chunkPrimer.setBlockState(chunkZ, height, chunkX, DRAINED_BLOCK);
            }
        }
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 10387789;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 9470285;
	}

	class Decorator extends BiomeDecorator
	{
		private Decorator()
		{
			super();
		}

		@Override
		protected void generateOres(World worldIn, Random random)
		{
			//All minerals have been drained from this biome
			return;
		}
	}
}
