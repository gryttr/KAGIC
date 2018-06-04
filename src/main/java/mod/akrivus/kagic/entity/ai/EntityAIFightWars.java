package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIFightWars extends EntityAITarget {
	private final EntityGem gem;
	private EntityLivingBase target;
    public EntityAIFightWars(EntityGem gem) {
        super(gem, true, true);
        this.gem = gem;
        this.setMutexBits(1);
    }
    public boolean shouldExecute() {
    	if (this.gem.killsPlayers) {
	    	List<EntityLivingBase> list = this.gem.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.gem.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
	        double distance = Double.MAX_VALUE;
	        for (EntityLivingBase possibleTarget : list) {
	        	boolean canFight = false;
	        	if (this.gem != possibleTarget) {
		        	if (possibleTarget instanceof EntityGem) {
		        		canFight = !this.gem.isOwnedBySamePeople(((EntityGem) possibleTarget)) && ((EntityGem) possibleTarget).isTamed();
		        	}
		        	else if (possibleTarget instanceof EntityPlayer) {
		        		canFight = !this.gem.isOwnedBy(possibleTarget);
		        	}
		        	canFight = canFight && this.gem != possibleTarget;
		            if (canFight) {
		                double newDistance = this.gem.getDistanceSq(possibleTarget);
		                if (newDistance <= distance) {
		                    distance = newDistance;
		                    this.target = possibleTarget;
		                }
		            }
	        	}
	        }
	    	return this.target != null;
    	}
    	return false;
    }
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.target);
        super.startExecuting();
    }
}