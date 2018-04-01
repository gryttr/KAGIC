package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class MowGrass extends Speak<EntityGem> {
	private BlockPos grassLocation = null;
	private int lastBlockBreak = 0;
	public MowGrass() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"mow",
			"cut"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"grass",
			"lawn",
			"yard"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.grassLocation != null && gem.canEntityBeSeen(this.commandingPlayer);
	}
	@Override
	public void init(EntityGem gem) {
		ArrayList<BlockPos> flowers = new ArrayList<BlockPos>();
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(state)) {
						BlockPos flower = gem.getPosition().add(x, y, z);
						flowers.add(flower);
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < flowers.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(flowers.get(i));
			if (distance < minDistance) {
				this.grassLocation = flowers.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.grassLocation != null) {
			BlockPos lookPos = this.grassLocation.down();
			gem.lookAt(lookPos);
			if (gem.getDistanceSqToCenter(this.grassLocation) < 5) {
				if (this.lastBlockBreak > 10) {
					boolean picked = gem.breakBlock(this.grassLocation);
					if (picked) {
						this.init(gem);
					}
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.grassLocation);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.grassLocation = null;
	}
	public boolean isCorrectBlock(IBlockState state) {
		Block block = state.getBlock();
		if (block == Blocks.TALLGRASS || (block == Blocks.DOUBLE_PLANT && state.getValue(BlockDoublePlant.VARIANT) == BlockDoublePlant.EnumPlantType.GRASS)) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.grassLocation;
	}
}
