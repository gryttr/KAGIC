package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.init.ModBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkySpire extends FloatingRuinStructure {

	public SkySpire(String type) {
		super(type, 50, 60, true, true);
		
		this.structures.add("/assets/kagic/structures/SkySpirePristine.schematic");

		this.chestTables.put(new BlockPos(39, 70, 74), LootTables.SKY_SPIRE);

		this.allowedBiomes.add(ModBiomes.FLOATINGPEAKS);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(10) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
