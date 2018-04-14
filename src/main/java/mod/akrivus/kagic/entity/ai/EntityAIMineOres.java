package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityQuartzSoldier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMineOres extends EntityAIMoveToBlock {
	private final EntityQuartzSoldier gem;
	private int delay = 0;
	public EntityAIMineOres(EntityQuartzSoldier gemIn, double speedIn) {
		super(gemIn, speedIn, 16);
		this.gem = gemIn;
	}
	public boolean shouldExecute() {
		boolean canExecute = false;
		List<EntityPearl> list = this.gem.world.<EntityPearl>getEntitiesWithinAABB(EntityPearl.class, this.gem.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
		for (EntityPearl entity : list) {
			if (entity.isOwnedBySamePeople(this.gem)) {
				canExecute = this.gem.getHeldItemMainhand().getItem() instanceof ItemPickaxe;
				break;
			}
        }
		if (canExecute) {
			if (delay > 20 + this.gem.getRNG().nextInt(20)) {
				this.runDelay = 0;
				return super.shouldExecute();
			}
			else {
				++this.delay;
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		super.startExecuting();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && !this.gem.getNavigator().noPath();
	}
	
	@Override
	public void resetTask() {
		super.resetTask();
	}
	
	@Override
	public void updateTask() {
		if (this.gem.getDistanceSq(this.destinationBlock) < 5) {
			this.gem.breakBlock(this.destinationBlock);
		}
	}
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		Block block =  this.gem.world.getBlockState(pos).getBlock();
		if (block instanceof BlockOre) {
			boolean canBeSeen = false;
			for (int ox = -1; ox <= 1 && !canBeSeen; ++ox) {
				if (this.gem.world.isAirBlock(pos.add(ox, 0, 0))) {
					canBeSeen = true;
				}
			}
			for (int oy = -1; oy <= 1 && !canBeSeen; ++oy) {
				if (this.gem.world.isAirBlock(pos.add(0, oy, 0))) {
					canBeSeen = true;
				}
			}
			for (int oz = -1; oz <= 1 && !canBeSeen; ++oz) {
				if (this.gem.world.isAirBlock(pos.add(0, 0, oz))) {
					canBeSeen = true;
				}
			}
			if (canBeSeen) {
				return true;
			}
		}
		return false;
	}
}