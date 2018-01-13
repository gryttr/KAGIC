package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeaShrine extends SunkenRuinStructure {

	public SeaShrine(String type) {
		super(type, 40, Blocks.STONE.getDefaultState(), false, true);

		this.structures.add("/assets/kagic/structures/SeaShrine.schematic");

		this.allowedBiomes.add(Biomes.DEEP_OCEAN);

		this.chestTables.put(new BlockPos(54, 1, 11), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(58, 1, 11), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(62, 1, 24), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(63, 1, 24), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(74, 1, 34), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(74, 1, 35), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(74, 1, 39), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(74, 1, 40), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(62, 1, 50), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(63, 1, 50), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(53, 1, 63), LootTables.SEA_SHRINE);
		this.chestTables.put(new BlockPos(59, 1, 63), LootTables.SEA_SHRINE);
	}

	@Override
	protected boolean checkBiome(World world, BlockPos corner1) {
		int xFar = corner1.getX() + ((this.rotation % 2 == 0) ? this.width : this.length) - 1;
		int zFar = corner1.getZ() + ((this.rotation % 2 == 0) ? this.length : this.width) - 1;
		BlockPos corner2 = new BlockPos(xFar, 255, corner1.getZ());
		BlockPos corner3 = new BlockPos(corner1.getX(), 255, zFar);
		BlockPos corner4 = new BlockPos(xFar, 255, zFar);
		
		if (allowedBiomes.contains(world.getBiome(corner1))
			&& allowedBiomes.contains(world.getBiome(corner2))
			&& allowedBiomes.contains(world.getBiome(corner3))
			&& allowedBiomes.contains(world.getBiome(corner4))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(2000) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
