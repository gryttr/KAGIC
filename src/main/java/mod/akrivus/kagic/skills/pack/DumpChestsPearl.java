package mod.akrivus.kagic.skills.pack;

import java.util.ArrayList;
import java.util.Arrays;

import mod.akrivus.kagic.entity.EntityCrystalSkills;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.skills.Speak;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DumpChestsPearl extends Speak<EntityPearl> {
	private BlockPos chestLocation = null;
	private boolean deposited = false;
	public DumpChestsPearl() {
		this.TRIGGER_VERBS = new ArrayList<String>(Arrays.asList(new String[] {
			"dump",
			"drop",
			"put",
			"place"
		}));
		this.canBeStopped = true;
		this.killsOnEnd = true;
		this.can(RunWith.RESTING);
		this.task(true);
	}
	@Override
	public boolean proceed(EntityPearl gem) {
		return this.chestLocation != null && !this.deposited;
	}
	@Override
	public void init(EntityPearl gem) {
		ArrayList<BlockPos> chests = new ArrayList<BlockPos>();
		this.chestLocation = null;
		for (int x = -16; x < 16; ++x) {
			for (int y = -8; y < 8; ++y) {
				for (int z = -16; z < 16; ++z) {
					IBlockState state = gem.world.getBlockState(gem.getPosition().add(x, y, z));
					if (this.isCorrectBlock(gem.world, gem.getPosition().add(x, y, z), state)) {
						chests.add(gem.getPosition().add(x, y, z));
					}
				}
			}
		}
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < chests.size(); ++i) {
			double distance = gem.getDistanceSqToCenter(chests.get(i));
			if (distance < minDistance) {
				this.chestLocation = chests.get(i);
				minDistance = distance;
			}
		}
	}
	@Override
	public void run(EntityPearl gem) {
		if (this.chestLocation != null) {
			BlockPos lookPos = this.chestLocation.down();
			gem.lookAt(lookPos);
			if (gem.getDistanceSqToCenter(this.chestLocation) < 5) {
				TileEntityLockableLoot te = (TileEntityLockableLoot) gem.world.getTileEntity(this.chestLocation);
				int y = 0;
				for (int x = 0; x < te.getSizeInventory(); ++x) {
					if (te.getStackInSlot(x).isEmpty() && !gem.gemStorage.getStackInSlot(y).isEmpty()) {
						te.setInventorySlotContents(x, gem.gemStorage.removeStackFromSlot(y));
						gem.onInventoryChanged(gem.gemStorage);
						++y;
					}
				}
				this.deposited = true;
			}
			else {
				gem.tryToMoveTo(this.chestLocation);
			}
		}
	}
	@Override
	public void reset(EntityPearl gem) {
		this.chestLocation = null;
		this.deposited = false;
	}
	public boolean isCorrectBlock(World world, BlockPos pos, IBlockState state) {
		if (state.getBlock() instanceof BlockContainer) {
			if (world.getTileEntity(pos) instanceof TileEntityLockableLoot) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.chestLocation;
	}
}
