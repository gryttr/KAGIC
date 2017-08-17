package mod.akrivus.kagic.dimensions.homeworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderHomeworld implements IChunkGenerator {
	private final World world;
	private final Random rand;
	private Vector2f nextChunk;
    public ChunkProviderHomeworld(World world, long seed) {
    	this.world = world;
    	this.rand = new Random(seed);
    }
	public Chunk provideChunk(int chunkX, int chunkZ) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        boolean fillEmptyKindergarten = this.nextChunk == null || !this.nextChunk.equals(new Vector2f(chunkX, chunkZ));
        for (int y = 0; y < 255; ++y) {
    	    for (int z = 0; z < 16; ++z) {
        		for (int x = 0; x < 16; ++x) {
        			if (y < 2) {
        				chunkPrimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
        			}
        			else if (y < 6) {
        				if (this.rand.nextInt(6 - y) > 0) {
        					chunkPrimer.setBlockState(x, y, z, ModBlocks.DRAINED_BLOCK.getDefaultState());
        				}
        				else {
        					chunkPrimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
        				}
        			}
        			else if (y < 8) {
        				chunkPrimer.setBlockState(x, y, z, ModBlocks.DRAINED_BLOCK.getDefaultState());
        			}
        			else if (y < 54 && fillEmptyKindergarten) {
	        			chunkPrimer.setBlockState(x, y, z, ModBlocks.DRAINED_BLOCK.getDefaultState());
        			}
    		    }
        	}
        }
        int cX = this.rand.nextBoolean() ? (this.rand.nextBoolean() ? -1 : 1) : 0;
		int cZ = this.rand.nextBoolean() ? (this.rand.nextBoolean() ? -1 : 1) : 0;
		this.nextChunk = new Vector2f(chunkX + cX, chunkZ + cZ);
        Chunk chunk = new Chunk(this.world, chunkPrimer, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
	}
	public void populate(int x, int z) {
		
	}
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return true;
	}
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return new ArrayList<SpawnListEntry>();
	}
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
		return null;
	}
	@Override
	public Chunk generateChunk(int x, int z) {
		// TODO Auto-generated method stub
		return this.provideChunk(x, z);
	}
	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}
}
