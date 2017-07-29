package mod.akrivus.kagic.util.injector;

import java.util.ArrayList;

import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExitHole {
	private final BlockPos[] blocks;
	private final boolean canCreate;
	private final boolean meltRocks;
	private final int minY;
	public ExitHole(BlockPos[] blocks, boolean canCreate, boolean meltRocks, int y) {
		this.blocks = blocks;
		this.canCreate = canCreate;
		this.meltRocks = meltRocks;
		this.minY = y;
	}
	public boolean canCreate() {
		return this.canCreate;
	}
	public boolean createRockMelt() {
		return this.meltRocks;
	}
	public void emerge(World world) {
		for (BlockPos block : this.blocks) {
			world.destroyBlock(block, false);
			if (this.meltRocks && block.getY() == minY) {
				world.setBlockState(block, ModBlocks.ROCK_MELT.getDefaultState());
			}
		}
	}
	public static ExitHole create(World world, BlockPos pos, double height, boolean meltRocks) {
		ArrayList<BlockPos> blocksToDelete = new ArrayList<BlockPos>();
		float shortestLength = Float.MAX_VALUE;
		char direction = 'o';
		int brightestLight = 0;
		for (int x = -9; x <= 9; ++x) {
			BlockPos check = pos.add(x, 0, 0);
			if (world.isAirBlock(check) && x <= shortestLength && world.getLight(check) >= brightestLight) {
				brightestLight = world.getLight(check);
				shortestLength = Math.abs(x);
				if (x == 0) {
					direction = 'o';
				}
				else if (x > 0) {
					direction = 'e';
				}
				else {
					direction = 'w';
				}
			}
		}
		for (int y = 0; y < height; ++y) {
			blocksToDelete.add(pos.up(y));
		}
		for (int z = -9; z <= 9; ++z) {
			BlockPos check = pos.add(0, 0, z);
			if (world.isAirBlock(check) && z <= shortestLength && world.getLight(check) >= brightestLight) {
				brightestLight = world.getLight(check);
				shortestLength = Math.abs(z);
				if (z == 0) {
					direction = 'o';
				}
				else if (z > 0) {
					direction = 's';
				}
				else {
					direction = 'n';
				}
			}
		}
		switch (direction) {
		case 'n':
			for (int z = 0; z <= shortestLength; ++z) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(0, y, -z));
				}
			}
			break;
		case 's':
			for (int z = 0; z <= shortestLength; ++z) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(0, y, z));
				}
			}
			break;
		case 'e':
			for (int x = 0; x <= shortestLength; ++x) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(x, y, 0));
				}
			}
			break;
		case 'w':
			for (int x = 0; x <= shortestLength; ++x) {
				for (int y = 0; y <= height; ++y) {
					blocksToDelete.add(pos.add(-x, y, 0));
				}
			}
			break;
		}
		return new ExitHole(blocksToDelete.toArray(new BlockPos[0]), blocksToDelete.size() <= height, meltRocks, pos.getY());
	}
}
