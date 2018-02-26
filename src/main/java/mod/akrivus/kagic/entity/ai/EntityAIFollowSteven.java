package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.humans.EntityConnie;
import mod.akrivus.kagic.entity.humans.EntitySteven;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowSteven extends EntityAIBase {
	private final EntityConnie connie;
	private EntitySteven steven;
	private final double followSpeed;
	private float oldWaterCost;
	
	public EntityAIFollowSteven(EntityConnie connieIn, double followSpeedIn) {
		this.connie = connieIn;
		this.followSpeed = followSpeedIn;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntitySteven steven : list) {
			double newDistance = this.connie.getDistanceSq(steven);
			if (newDistance <= distance) {
				distance = newDistance;
				this.steven = steven;
			}
		}
		return this.steven != null;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.steven != null && !this.connie.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.oldWaterCost = this.connie.getPathPriority(PathNodeType.WATER);
		this.connie.setPathPriority(PathNodeType.WATER, 0.0F);
	}
	
	@Override
	public void resetTask() {
		this.steven = null;
		this.connie.getNavigator().clearPath();
		this.connie.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	}
	
	@Override
	public void updateTask() {
		if (this.connie.getDistanceSq(this.steven) > (this.steven.width * 3) + 3) {
			this.connie.getNavigator().tryMoveToEntityLiving(this.steven, this.followSpeed);
		}
	}
}