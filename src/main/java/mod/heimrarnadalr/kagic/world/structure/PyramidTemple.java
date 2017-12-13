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
		if (rand.nextInt(10) != 0) {
			return false;
		}
		
		return super.generate(world, rand, pos);
	}
}
