package mod.akrivus.kagic.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowPlayer extends EntityAIBase {
	private final EntityLiving human;
	private EntityPlayer player;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowPlayer(EntityLiving humanIn, double followSpeedIn) {
        this.human = humanIn;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    public boolean shouldExecute() {
        List<EntityPlayer> list = this.human.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.human.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityPlayer player : list) {
        	if (!player.isSpectator() || !player.isInvisible()) {
	            double newDistance = player.getDistanceSq(this.human);
	            if (newDistance <= maxDistance) {
	                maxDistance = newDistance;
	                this.player = player;
	            }
        	}
        }
        return this.player != null;
    }
    public boolean shouldContinueExecuting() {
        return this.player != null && !this.human.getNavigator().noPath();
    }
    public void startExecuting() {
        this.oldWaterCost = this.human.getPathPriority(PathNodeType.WATER);
        this.human.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    public void resetTask() {
        this.player = null;
        this.human.getNavigator().clearPath();
        this.human.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    public void updateTask() {
        if (this.human.getDistanceSq(this.player) > (this.player.width * 3) + 3) {
        	this.human.getNavigator().tryMoveToEntityLiving(this.player, this.followSpeed);
        }
    }
}