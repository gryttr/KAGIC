package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class CollectLiquids extends Speak<EntityGem> {
	private BlockPos liquidLocation = null;
	private int goal = 1;
	private int amountBeforeGoal = 0;
	private int lastBlockBreak = 0;
	public CollectLiquids() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"collect",
			"gather",
			"get",
			"grab"
		}));
		this.TRIGGER_NOUNS = new ArrayList<String>(Arrays.asList(new String[] {
			"water",
			"lava"
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
					this.goal = 1;
				}
			}
		}
		return previous;
	}
	@Override
	public boolean proceed(EntityGem gem) {
		return this.liquidLocation != null && gem.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.BUCKET && gem.isSoldier;
	}
	@Override
	public void init(EntityGem gem) {
		ArrayList<BlockPos> liquids = new ArrayList<BlockPos>();
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(state)) {
						BlockPos liquid = gem.getPosition().add(x, y, z);
						liquids.add(liquid);
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < liquids.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(liquids.get(i));
			if (distance < minDistance) {
				this.liquidLocation = liquids.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityGem gem) {
		if (this.liquidLocation != null) {
			BlockPos lookPos = this.liquidLocation.down();
			gem.getLookHelper().setLookPosition(lookPos.getX(), lookPos.getY(), lookPos.getZ(), 30.0F, 30.0F);
			if (gem.getDistanceSqToCenter(this.liquidLocation) < 5) {
				if (this.lastBlockBreak > 20) {
					if (this.selectedNoun.equals("water")) {
						gem.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
					}
					else {
						gem.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.LAVA_BUCKET));
					}
					gem.world.setBlockToAir(this.liquidLocation);
					++this.amountBeforeGoal;
					this.lastBlockBreak = 0;
				}
				++this.lastBlockBreak;
			}
			else {
				gem.tryToMoveTo(this.liquidLocation);
			}
			if (this.amountBeforeGoal < this.goal) {
				this.liquidLocation = null;
				this.init(gem);
			}
		}
	}
	@Override
	public void reset(EntityGem gem) {
		this.liquidLocation = null;
	}
	public boolean isCorrectBlock(IBlockState state) {
		Block block = state.getBlock();
		if (this.selectedNoun.equals("water")) {
			return block.equals(Blocks.WATER);
		}
		else {
			return block.equals(Blocks.LAVA);
		}
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.liquidLocation;
	}
}
