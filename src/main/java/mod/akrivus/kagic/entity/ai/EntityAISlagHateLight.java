package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntitySlag;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public class EntityAISlagHateLight extends EntityAIBase {
    private final EntitySlag slag;
	private final double movementSpeed;
	private final int searchRadius;
	private int currentLight;
	private double posX;
	private double posY;
	private double posZ;
	private int delay;
	
	public EntityAISlagHateLight(EntitySlag slagIn, double speedIn, int radius) {
		this.slag = slagIn;
		this.movementSpeed = speedIn;
		this.searchRadius = radius;
		this.delay = 0;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.delay > 0) {
			--this.delay;
			return false;
		}
		else {
			this.delay = this.slag.getRNG().nextInt(20);
			return true;
		}
	}
	
	@Override
	public void startExecuting() {
		this.currentLight = this.slag.world.getLightFor(EnumSkyBlock.BLOCK, this.slag.getPosition());
		for (int x = -searchRadius; x <= searchRadius; ++x) {
			for (int y = -2; y <= 2; ++y) {
				for (int z = -searchRadius; z <= searchRadius; ++z) {
					if ((x > 2 || x < -2) && (z > 2 || z < -2)) {
						BlockPos tempPos = this.slag.getPosition().add(new BlockPos(x, y, z));
						int tempLight = this.slag.world.getLightFor(EnumSkyBlock.BLOCK, tempPos);
						if (tempLight < this.currentLight) {
							this.posX = tempPos.getX();
							this.posY = tempPos.getY();
							this.posZ = tempPos.getZ();
							this.currentLight = tempLight;
						}
					}
				}
			}
		}
		this.slag.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return !this.slag.getNavigator().noPath();
	}
	
	@Override
	public void resetTask() {
		this.slag.getNavigator().clearPathEntity();
	}
	
	@Override
	public void updateTask() {
		this.slag.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
	}
}