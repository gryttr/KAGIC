package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedMoissanite;
import mod.akrivus.kagic.init.ModBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PyramidTemple extends BuriedRuinStructure {

	public PyramidTemple(String type) {
		super(type, 44, 44, false);
		
		this.structures.add("/assets/kagic/structures/pyramid_temple.schematic");
		this.allowedBiomes.add(ModBiomes.STRAWBERRYBATTLEFIELD);
		this.entities.put(new BlockPos(24, 6, 24), EntityCorruptedMoissanite.class);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(10) != 0) {
			return false;
		}
		
		return super.generate(world, rand, pos);
	}
}
