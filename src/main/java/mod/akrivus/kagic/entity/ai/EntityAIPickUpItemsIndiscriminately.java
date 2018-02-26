package mod.akrivus.kagic.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;

public class EntityAIPickUpItemsIndiscriminately extends EntityAIBase {
	private final EntityCreature entity;
	private final double movementSpeed;
	private EntityItem item;

	public EntityAIPickUpItemsIndiscriminately(EntityCreature entityIn, double movementSpeedIn) {
		this.entity = entityIn;
		this.movementSpeed = movementSpeedIn;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		List<EntityItem> list = this.entity.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.entity.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
		double maxDistance = Double.MAX_VALUE;
		for (EntityItem item : list) {
			double newDistance = this.entity.getDistanceSq(item);
			if (newDistance <= maxDistance && this.entity.canEntityBeSeen(item) && !item.isDead) {
				maxDistance = newDistance;
				this.item = item;
			}
		}
		return this.item != null && !this.item.isDead && this.entity.canPickUpLoot();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.item != null 
				&& !this.item.isDead 
				&& this.entity.canEntityBeSeen(this.item) 
				&& !this.entity.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.entity.getLookHelper().setLookPositionWithEntity(this.item, 30.0F, 30.0F);
	}
	
	@Override
	public void resetTask() {
		this.entity.getNavigator().clearPath();
		this.item = null;
	}
	
	@Override
	public void updateTask() {
		if (this.entity.getDistanceSq(this.item) > 1F) {
		   	this.entity.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
		}
	}
}