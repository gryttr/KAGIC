package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary.Type;

public class SmallArena extends FloatingRuinStructure {

	public SmallArena(String type) {
		super(type, 50, 60, true, true);
		this.structures.add("/assets/kagic/structures/SmallArenaDamaged.schematic");
		this.structures.add("/assets/kagic/structures/SmallArenaPristine.schematic");
		
		this.chestTables.put(new BlockPos(31, 12, 8), LootTables.SMALL_ARENA);
		this.chestTables.put(new BlockPos(31, 12, 16), LootTables.SMALL_ARENA);

		this.allowedBiomeTypes.add(Type.MOUNTAIN);
		this.allowedBiomeTypes.add(Type.HILLS);
		this.canContainAnyType = true;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(1000) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
