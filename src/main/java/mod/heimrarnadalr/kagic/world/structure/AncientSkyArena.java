package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary.Type;

public class AncientSkyArena extends FloatingRuinStructure {

	public AncientSkyArena(String type) {
		super(type, 40, 60, true, true);
		this.structures.add("/assets/kagic/structures/LargeArenaDamaged.schematic");

		this.chestTables.put(new BlockPos(25, 27, 19), LootTables.LARGE_ARENA_UPPER);
		this.chestTables.put(new BlockPos(25, 27, 25), LootTables.LARGE_ARENA_UPPER);
		this.chestTables.put(new BlockPos(25, 27, 31), LootTables.LARGE_ARENA_UPPER);
		this.chestTables.put(new BlockPos(25, 27, 55), LootTables.LARGE_ARENA_UPPER);
		this.chestTables.put(new BlockPos(25, 27, 61), LootTables.LARGE_ARENA_UPPER);
		this.chestTables.put(new BlockPos(25, 27, 67), LootTables.LARGE_ARENA_UPPER);

		this.chestTables.put(new BlockPos(28, 11, 28), LootTables.LARGE_ARENA_LOWER);
		this.chestTables.put(new BlockPos(28, 11, 34), LootTables.LARGE_ARENA_LOWER);
		this.chestTables.put(new BlockPos(28, 11, 40), LootTables.LARGE_ARENA_LOWER);
		this.chestTables.put(new BlockPos(28, 11, 46), LootTables.LARGE_ARENA_LOWER);
		this.chestTables.put(new BlockPos(28, 11, 52), LootTables.LARGE_ARENA_LOWER);
		this.chestTables.put(new BlockPos(28, 11, 58), LootTables.LARGE_ARENA_LOWER);

		this.allowedBiomeTypes.add(Type.MOUNTAIN);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(1000) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
