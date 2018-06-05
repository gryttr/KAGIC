package mod.akrivus.kagic.util.injector;

import java.util.ArrayList;
import java.util.PriorityQueue;

import mod.akrivus.kagic.event.ExitHoleEvent;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ExitHole {
	private final BlockPos[] blocks;
	private final boolean canCreate;
	private final boolean meltRocks;
	private final boolean xDirection;
	private final int minY;
	public ExitHole(BlockPos[] blocks, boolean canCreate, boolean meltRocks, boolean direction, int y) {
		this.blocks = blocks;
		this.canCreate = canCreate;
		this.meltRocks = meltRocks;
		this.xDirection = direction;
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
			ExitHoleEvent e1 = new ExitHoleEvent(world, block);
			if (MinecraftForge.EVENT_BUS.post(e1)) return;
			world.destroyBlock(block, false);
			InjectorResult.drainBlock(world, this.xDirection ? block.north() : block.east());
			InjectorResult.drainBlock(world, this.xDirection ? block.south() : block.west());
			if (block.getY() == minY) {
				InjectorResult.drainBlock(world, block.down());
				if (this.meltRocks && !world.isAirBlock(block.down())) {
					world.setBlockState(block, ModBlocks.ROCK_MELT.getDefaultState());
				}
			} else {
				InjectorResult.drainBlock(world, block.up());
			}
		}
	}
	public static ExitHole create(World world, BlockPos pos, double height, boolean meltRocks) {
		ArrayList<BlockPos> blocksToDelete = new ArrayList<BlockPos>();
		PriorityQueue<ExitPotential> exitQueue = new PriorityQueue<ExitPotential>(4, new ExitPotential());
		exitQueue.add(new ExitPotential(false, 0, 10, 'o'));
		
		for (int x = -1; x >= -9; --x) {
			BlockPos check = pos.add(x, 0, 0);
			if (world.isAirBlock(check)) {
				boolean seesSky = world.canSeeSky(check);
				int light = world.getLight(check);
				exitQueue.add(new ExitPotential(seesSky, light, -x, 'w'));
				break;
			}
		}

		for (int x = 1; x <= 9; ++x) {
			BlockPos check = pos.add(x, 0, 0);
			if (world.isAirBlock(check)) {
				boolean seesSky = world.canSeeSky(check);
				int light = world.getLight(check);
				exitQueue.add(new ExitPotential(seesSky, light, x, 'e'));
				break;
			}
		}

		for (int z = -1; z >= -9; --z) {
			BlockPos check = pos.add(0, 0, z);
			if (world.isAirBlock(check)) {
				boolean seesSky = world.canSeeSky(check);
				int light = world.getLight(check);
				exitQueue.add(new ExitPotential(seesSky, light, -z, 'n'));
				break;
			}
		}

		for (int z = 1; z <= 9; ++z) {
			BlockPos check = pos.add(0, 0, z);
			if (world.isAirBlock(check)) {
				boolean seesSky = world.canSeeSky(check);
				int light = world.getLight(check);
				exitQueue.add(new ExitPotential(seesSky, light, z, 's'));
				break;
			}
		}

		for (int y = 0; y < height; ++y) {
			blocksToDelete.add(pos.up(y));
		}
		
		ExitPotential exit = exitQueue.peek();
		
		switch (exit.direction) {
		case 'n':
			for (int z = 0; z <= exit.length; ++z) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(0, y, -z));
				}
			}
			break;
		case 's':
			for (int z = 0; z <= exit.length; ++z) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(0, y, z));
				}
			}
			break;
		case 'e':
			for (int x = 0; x <= exit.length; ++x) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(x, y, 0));
				}
			}
			break;
		case 'w':
			for (int x = 0; x <= exit.length; ++x) {
				for (int y = 0; y < height; ++y) {
					blocksToDelete.add(pos.add(-x, y, 0));
				}
			}
			break;
		}
		boolean xDirection = exit.direction == 'e' || exit.direction == 'w';
		return new ExitHole(blocksToDelete.toArray(new BlockPos[0]), blocksToDelete.size() <= height, meltRocks, xDirection, pos.getY());
	}
}
