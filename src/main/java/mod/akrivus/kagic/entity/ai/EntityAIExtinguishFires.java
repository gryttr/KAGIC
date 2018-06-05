package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class EntityAIExtinguishFires extends EntityAIBase {
    private final EntityLapisLazuli gem;
	private final double movementSpeed;
	private final int searchRadius;
	private boolean placed = false;
	private double posX;
	private double posY;
	private double posZ;
	private int delay;
	
	public EntityAIExtinguishFires(EntityLapisLazuli gemIn, double speedIn, int radius) {
		this.gem = gemIn;
		this.movementSpeed = speedIn;
		this.searchRadius = radius;
		this.delay = 0;
		this.setMutexBits(0);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.delay > 0) {
			--this.delay;
			return false;
		}
		else {
			this.delay = this.gem.getRNG().nextInt(20);
			return true;
		}
	}
	
	@Override
	public void startExecuting() {
		boolean found = false;
		for (int x = -this.searchRadius; x <= this.searchRadius && !found; ++x) {
			for (int y = -2; y <= 2 && !found; ++y) {
				for (int z = -this.searchRadius; z <= this.searchRadius && !found; ++z) {
					BlockPos tempPos = this.gem.getPosition().add(new BlockPos(x, y, z));
					if (this.gem.world.getBlockState(tempPos).getBlock() == Blocks.FIRE) {
						this.posX = tempPos.getX();
						this.posY = tempPos.getY();
						this.posZ = tempPos.getZ();
						found = true;
						break;
					}
				}
			}
		}
		if (found) {
			this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return !this.gem.getNavigator().noPath();
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.placed = false;
	}
	
	@Override
	public void updateTask() {
		this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		if (this.gem.getDistanceSq(this.posX, this.posY, this.posZ) < 8 && !this.placed) {
			this.gem.world.setBlockToAir(new BlockPos(this.posX, this.posY, this.posZ));
			this.placed = true;
		}
	}
}