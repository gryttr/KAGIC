package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIPredictFights extends EntityAITarget {
	private final EntityGem gem;
    private double movementSpeed;
	private Path flightPath;
    public EntityAIPredictFights(EntityGem gem, double movementSpeed) {
        super(gem, true);
        this.gem = gem;
        this.movementSpeed = movementSpeed;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        return this.gem.getAttackTarget() != null;
    }
    public void startExecuting() {
    	if (this.gem.getAttackTarget() != null) {
	    	if (this.gem.isSoldier) {
	    		EntityLivingBase target = this.gem.getAttackTarget();
	    		double healthRatio = this.gem.getHealth() / target.getHealth();
	        	if (healthRatio >= 1.0D && healthRatio <= 2.0D) {
	        		this.callForHelp(true);
	        	}
	        	else if (healthRatio >= 2.0D) {
	        		this.flee(true);
	        	}
	        	else {
	        		this.fight();
	        	}
	    	}
	    	else {
	        	this.flee(true);
	    	}
    	}
    }
    private void flee(boolean callForHelp) {
        List<EntityGem> nearbygems = this.gem.world.getEntitiesWithinAABB(EntityGem.class, this.gem.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
    	EntityGem destination = null;
    	double distance = 0;
        for (EntityGem fighter : nearbygems) {
        	if (!this.gem.equals(fighter) && fighter.isSoldier) {
        		if (this.gem.getDistanceSq(fighter) > distance) {
        			distance = this.gem.getDistanceSq(fighter);
        			destination = fighter;
        		}
            }
        }
        if (destination == null) {
        	Vec3d randomPosition = RandomPositionGenerator.findRandomTarget(this.gem, 24, 8);
        	if (randomPosition == null) { 
        		int nextX = this.gem.world.rand.nextInt(24) - 12;
        		int nextZ = this.gem.world.rand.nextInt(24) - 12;
        		BlockPos pos = this.gem.world.getTopSolidOrLiquidBlock(this.gem.getPosition().add(nextX, 0, nextZ));
        		randomPosition = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        	}
        	this.flightPath = this.gem.getNavigator().getPathToXYZ(randomPosition.x, randomPosition.y, randomPosition.z);
        }
        else {
        	this.flightPath = this.gem.getNavigator().getPathToEntityLiving(destination);
        }
        if (callForHelp) {
        	this.callForHelp(false);
        }
        this.gem.getNavigator().setPath(this.flightPath, this.movementSpeed);
    }
    private void callForHelp(boolean victimWillFight) {
    	List<EntityGem> nearbygems = this.gem.world.getEntitiesWithinAABB(EntityGem.class, this.gem.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D));
    	for (EntityGem fighter : nearbygems) {
        	if (fighter.isSoldier) {
        		fighter.setAttackTarget(this.gem.getAttackTarget());
            }
        }
        if (victimWillFight) {
        	this.fight();
        }
    }
    private void fight() {
    	this.gem.setAttackTarget(this.gem.getAttackTarget());
        this.flightPath = null;
    }
}
