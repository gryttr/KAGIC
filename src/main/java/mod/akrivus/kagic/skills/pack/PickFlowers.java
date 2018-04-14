package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class PickFlowers extends Speak<EntityGem> {
	private BlockPos flowerLocation = null;
	private int goal = 3;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	public PickFlowers() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"collect",
			"pick",
			"get",
			"cut"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"bouquet",
			"flower",
			"flowers"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean triggered(EntityGem gem) {
		boolean previous = this.isAllowedToRun;
		if (previous) {
			if (!this.collectedNumbers.isEmpty()) {
				try {
					this.goal = Integer.parseInt(this.collectedNumbers.get(0));
				}
				catch (Exception ex) {
					this.goal = 3;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.flowerLocation != null && this.amountBeforeGoal < this.goal;
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
				this.flowerLocation = flowers.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.flowerLocation != null) {
			BlockPos lookPos = this.flowerLocation.down();
			gem.lookAt(lookPos);
			if (gem.getDistanceSqToCenter(this.flowerLocation) < 5) {
				if (this.lastBlockBreak > 10) {
					boolean picked = gem.breakBlock(this.flowerLocation);
					if (picked) {
						this.init(gem);
					}
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.flowerLocation);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.flowerLocation = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
	}
	public boolean isCorrectBlock(IBlockState state) {
		Block block = state.getBlock();
		if (block instanceof BlockFlower) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "picking flowers";
	}
}
