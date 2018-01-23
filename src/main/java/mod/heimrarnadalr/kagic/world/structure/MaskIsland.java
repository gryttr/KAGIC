package mod.heimrarnadalr.kagic.world.structure;

import java.util.Random;

import mod.akrivus.kagic.entity.pepo.EntityMelon;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MaskIsland extends SunkenRuinStructure {

	public MaskIsland(String type) {
		super(type, 35, Blocks.STONE.getDefaultState(), true, true);

		this.structures.add("/assets/kagic/structures/MaskIsland.schematic");

		this.allowedBiomes.add(Biomes.DEEP_OCEAN);
		
		this.chestTables.put(new BlockPos(49, 40, 117), LootTables.MASK_ISLAND);
		this.chestTables.put(new BlockPos(51, 40, 118), LootTables.MASK_ISLAND);
		this.chestTables.put(new BlockPos(43, 39, 120), LootTables.MASK_ISLAND);
		this.chestTables.put(new BlockPos(50, 40, 123), LootTables.MASK_ISLAND);
		
		for (int x = 0; x < 3; ++x) {
			for (int z = 0; z < 3; ++z) {
				this.entities.put(new BlockPos(57 + x, 41, 113 + z), EntityMelon.class);
			}
		}
		this.entities.put(new BlockPos(42, 40, 117), EntityMelon.class);
		this.entities.put(new BlockPos(43, 40, 117), EntityMelon.class);
		this.entities.put(new BlockPos(42, 40, 118), EntityMelon.class);
		this.entities.put(new BlockPos(43, 40, 118), EntityMelon.class);
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
		if (rand.nextInt(5000) != 0) {
			return false;
		}

		return super.generate(world, rand, pos);
	}
}
