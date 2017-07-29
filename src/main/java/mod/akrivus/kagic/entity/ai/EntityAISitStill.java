package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISitStill extends EntityAIBase {
	private final EntityGem gem;
	private final double movementSpeed;	
	public EntityAISitStill(EntityGem gem, double movementSpeed) {
		this.gem = gem;
		this.movementSpeed = movementSpeed;
		this.setMutexBits(5);
	}
	public boolean shouldExecute() {
		return this.gem.isSitting() && this.gem.getRestPosition() != null && !this.gem.getRestPosition().equals(this.gem.getPosition());
	}
	public void startExecuting() {
		double x = this.gem.getRestPosition().getX();
		double y = this.gem.getRestPosition().getY();
		double z = this.gem.getRestPosition().getZ();
		this.gem.getNavigator().tryMoveToXYZ(x, y, z, this.movementSpeed);
	}
}
