package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.BlockPos;

public class Mine extends Speak<EntityGem> {
	private BlockPos blockLocation = null;
	private int goal = 64;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	private boolean cut = false;
	public Mine() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] { 
			"mine",
			"gather",
			"find",
			"get"
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
					this.goal = 64;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityGem gem) {
		boolean hasPearl = gem instanceof EntityPearl;
		if (this.blockLocation != null && (this.amountBeforeGoal < this.goal) && gem.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
			if (!hasPearl) {
				List<EntityPearl> list = gem.world.<EntityPearl>getEntitiesWithinAABB(EntityPearl.class, gem.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
				for (EntityPearl entity : list) {
					if (entity.isOwnedBySamePeople(gem)) {
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
		ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
		for (int x = -16; x < 16; ++x) {
			for (int y = -16; y < 16; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(state)) {
						blocks.add(gem.getPosition().add(x, y, z));
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < blocks.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(blocks.get(i));
			if (distance < minDistance) {
				this.blockLocation = blocks.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.blockLocation != null) {
			gem.getLookHelper().setLookPosition(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ(), 30.0F, 30.0F);
			if (gem.getDistanceSq(this.blockLocation) < 5) {
				if (this.lastBlockBreak > 20) {	
					boolean cut = gem.breakBlock(this.blockLocation);
					if (cut) {
						++this.amountBeforeGoal;
					}
					this.cut = cut;
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.blockLocation);
			}
			if (this.amountBeforeGoal < this.goal) {
				this.blockLocation = null;
				this.cut = false;
				this.init(gem);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.blockLocation = null;
		this.amountBeforeGoal = 0;
		this.goal = 0;
		this.cut = false;
	}
	public boolean isCorrectBlock(IBlockState state) {
		if (state.getBlock().getLocalizedName().toLowerCase().contains(this.selectedNoun)) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.blockLocation;
	}
}
