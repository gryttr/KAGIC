package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntitySlag;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;

public class EntityAISlagEatGems extends EntityAIBase {
    private final EntitySlag slag;
    private final double movementSpeed;
    private EntityItem item;
    public EntityAISlagEatGems(EntitySlag entityIn, double movementSpeedIn) {
        this.slag = entityIn;
        this.movementSpeed = movementSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
    	List<EntityItem> list = this.slag.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.slag.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityItem item : list) {
            double newDistance = this.slag.getDistanceSqToEntity(item);
            if (newDistance <= maxDistance && item.getItem().getItem() instanceof ItemGem && this.slag.canEntityBeSeen(item)) {
                maxDistance = newDistance;
                this.item = item;
            }
        }
        return this.item != null;
    }
    public boolean continueExecuting() {
        return this.item != null && !this.item.isDead && this.slag.canEntityBeSeen(this.item) && !this.slag.getNavigator().noPath();
    }
    public void startExecuting() {
        this.slag.getLookHelper().setLookPositionWithEntity(this.item, 30.0F, 30.0F);
    }
    public void resetTask() {
        this.slag.getNavigator().clearPathEntity();
        this.item = null;
    }
    public void updateTask() {
        if (this.slag.getDistanceSqToEntity(this.item) > 1.0D) {
        	this.slag.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
        }
        else {
        	this.slag.playSound(ModSounds.SLAG_EAT, 8.0F, 1.0F);
        	this.slag.setCount(this.slag.getCount() + 1);
        	this.slag.setHealth(this.slag.getMaxHealth());
        	this.item.setDead();
        }
    }
}