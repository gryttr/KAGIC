package mod.heimrarnadalr.kagic.world;

import java.util.ArrayList;
import java.util.Random;

import mod.heimrarnadalr.kagic.world.structure.CommunicationHub;
import mod.heimrarnadalr.kagic.world.structure.DesertWarpPad;
import mod.heimrarnadalr.kagic.world.structure.RuinStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class KAGICWorldGenerator implements IWorldGenerator {
	private ArrayList<RuinStructure> ruins = new ArrayList<RuinStructure>();
	
	public KAGICWorldGenerator() {
		ruins.add(new CommunicationHub("/assets/kagic/structures/CommHub2.schematic"));
		ruins.add(new DesertWarpPad("/assets/kagic/structures/DesertWarpPad.schematic"));
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case 0:
				for (RuinStructure ruin : ruins) {
					runGenerator(ruin, world, rand, chunkX, chunkZ, 1);
				}
				break;
			default: //Only generate ruins in the Overworld for now 
				break;
		}
	}

	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn) {
	    for (int i = 0; i < chancesToSpawn; ++i) {
	        int x = chunk_X * 16 + rand.nextInt(16);// + 8;
	        int z = chunk_Z * 16 + rand.nextInt(16);// + 8;
	        int y = world.getHeight(x, z);
	        generator.generate(world, rand, world.getTopSolidOrLiquidBlock(new BlockPos(x, y, z)));
	    }
	}
}
