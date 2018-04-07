package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityQuartzSoldier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.BlockPos;

public class EntityAIMineOres extends EntityAIBase {
    private final EntityQuartzSoldier gem;
	private final double movementSpeed;
	private final int searchRadius;
	private boolean placed = false;
	private double posX;
	private double posY;
	private double posZ;
	private int delay;
	
	public EntityAIMineOres(EntityQuartzSoldier gemIn, double speedIn, int radius) {
		this.gem = gemIn;
		this.movementSpeed = speedIn;
		this.searchRadius = radius;
		this.delay = 0;
		this.setMutexBits(0);
	}
	
	@Override
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
			if (this.delay > 0) {
				--this.delay;
				return false;
			}
			else {
				this.delay = this.gem.getRNG().nextInt(20);
				boolean found = false;
				for (int x = -this.searchRadius; x <= this.searchRadius && !found; ++x) {
					for (int y = -2; y <= 2 && !found; ++y) {
						for (int z = -this.searchRadius; z <= this.searchRadius && !found; ++z) {
							BlockPos tempPos = this.gem.getPosition().add(new BlockPos(x, y, z));
							Block block =  this.gem.world.getBlockState(tempPos).getBlock();
							if (block instanceof BlockOre) {
								boolean canBeSeen = false;
								for (int ox = -1; ox <= 1 && !canBeSeen; ++ox) {
									if (this.gem.world.isAirBlock(tempPos.add(ox, 0, 0))) {
										canBeSeen = true;
									}
								}
								for (int oy = -1; oy <= 1 && !canBeSeen; ++oy) {
									if (this.gem.world.isAirBlock(tempPos.add(0, oy, 0))) {
										canBeSeen = true;
									}
								}
								for (int oz = -1; oz <= 1 && !canBeSeen; ++oz) {
									if (this.gem.world.isAirBlock(tempPos.add(0, 0, oz))) {
										canBeSeen = true;
									}
								}
								if (canBeSeen) {
									this.posX = tempPos.getX();
									this.posY = tempPos.getY();
									this.posZ = tempPos.getZ();
									found = true;
									break;
								}
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		if (!this.gem.isDefective()) {
			this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return !this.gem.getNavigator().noPath() && !this.gem.isDefective() && !this.placed && !this.gem.world.isAirBlock(new BlockPos(this.posX, this.posY, this.posZ));
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.placed = false;
	}
	
	@Override
	public void updateTask() {
		this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		if (this.gem.getDistanceSq(this.posX, this.posY, this.posZ) < 8) {
			this.placed = this.gem.breakBlock(new BlockPos(this.posX, this.posY, this.posZ));
		}
	}
}