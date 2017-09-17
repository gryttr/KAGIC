package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmallArena extends FloatingRuinStructure {

	public SmallArena(String type) {
		super(type, 50, 60, true, true);
		this.structures.add("/assets/kagic/structures/SmallArenaDamaged.schematic");
		this.structures.add("/assets/kagic/structures/SmallArenaPristine.schematic");
		
		this.chestTables.put(new BlockPos(31, 12, 8), LootTables.SMALL_ARENA);
		this.chestTables.put(new BlockPos(31, 12, 16), LootTables.SMALL_ARENA);

		this.allowedBiomes.add(Biomes.EXTREME_HILLS);
		this.allowedBiomes.add(Biomes.EXTREME_HILLS_WITH_TREES);
		this.allowedBiomes.add(Biomes.MUTATED_EXTREME_HILLS);
		this.allowedBiomes.add(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES);
		this.allowedBiomes.add(Biomes.EXTREME_HILLS_EDGE);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(10) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
