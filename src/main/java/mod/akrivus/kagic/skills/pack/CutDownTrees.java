package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.util.math.BlockPos;

public class CutDownTrees extends Speak<EntityGem> {
	private BlockPos treeLocation = null;
	private boolean cuttingDownTrees = true;
	private boolean countTreesNotWood = true;
	private int goal = 1;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	private boolean cut = false;
	public CutDownTrees() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"cut",
			"chop",
			"find",
			"get"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"trees",
			"tree",
			"wood",
			"log",
			"logs"
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
					this.goal = 2;
				}
			}
			if (this.selectedNoun.startsWith("tree")) {
				this.countTreesNotWood = true;
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityGem gem) {
		boolean hasPearl = gem instanceof EntityPearl;
		if (this.treeLocation != null && (this.cuttingDownTrees || this.amountBeforeGoal < this.goal) && gem.getHeldItemMainhand().getItem() instanceof ItemAxe) {
			if (!hasPearl) {
				List<EntityPearl> list = gem.world.<EntityPearl>getEntitiesWithinAABB(EntityPearl.class, gem.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
				for (EntityPearl entity : list) {
					if (entity.isOwnedBySamePeople(gem) && !entity.isSitting()) {
						hasPearl = true;
					}
		        }
			}
			return hasPearl && !this.cut;
		}
		return false;
	}
	@Override
	public void init(EntityGem gem) {
		ArrayList<BlockPos> trees = new ArrayList<BlockPos>();
		ArrayList<float[]> points = new ArrayList<float[]>();
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(state)) {
						for (int i = 0; i < 64; ++i) {
							Block top = gem.world.getBlockState(gem.getPosition().add(x, y + i, z)).getBlock();
							if (top instanceof BlockLeaves) {
								BlockPos tree = gem.getPosition().add(x, y - 1, z);
								if (!trees.contains(new float[] { tree.getX(), tree.getZ() })) {
									points.add(new float[] { tree.getX(), tree.getZ() });
									trees.add(tree);
								}
							}
						}
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < trees.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(trees.get(i));
			if (distance < minDistance) {
				this.treeLocation = trees.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.treeLocation != null) {
			BlockPos nextPos = this.treeLocation.add(0, 1, 0);
			gem.getLookHelper().setLookPosition(nextPos.getX(), nextPos.getY(), nextPos.getZ(), 30.0F, 30.0F);
			if (gem.getDistanceSqToCenter(this.treeLocation.down((int)(this.treeLocation.getY() - gem.posY))) < 5) {
				if (this.lastBlockBreak > 20) {	
					while (gem.world.getBlockState(nextPos).getBlock() instanceof BlockLog) {
						boolean cut = gem.breakBlock(nextPos);
						this.cut = cut;
						nextPos = nextPos.up(1);
					}
					++this.amountBeforeGoal;
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.treeLocation);
			}
			if (this.amountBeforeGoal < this.goal && this.cut) {
				this.cuttingDownTrees = true;
				this.treeLocation = null;
				this.cut = false;
				this.init(gem);
			}
		}
		else {
			this.cuttingDownTrees = false;
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.cuttingDownTrees = false;
		this.treeLocation = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
		this.cut = false;
	}
	public boolean isCorrectBlock(IBlockState state) {
		Block block = state.getBlock();
		if (block instanceof BlockLog) {
			String modifier = this.selectedPhrase.get(Math.max(0, this.selectedNounIndex - 2));
			switch (block.getMetaFromState(state)) {
			case 0:
				if (modifier.equals("oak")) {
					return true;
				}
			case 1:
				if (modifier.equals("spruce")) {
					return true;
				}
			case 2:
				if (modifier.equals("birch")) {
					return true;
				}
			case 3:
				if (modifier.equals("jungle")) {
					return true;
				}
			case 4:
				if (modifier.equals("acacia")) {
					return true;
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.treeLocation;
	}
}
