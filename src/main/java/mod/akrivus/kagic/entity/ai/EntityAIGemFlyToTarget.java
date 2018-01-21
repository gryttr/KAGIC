package mod.akrivus.kagic.entity.ai;

import java.util.Random;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntityMoveHelper.Action;
import net.minecraft.util.math.MathHelper;

public class EntityAIGemFlyToTarget extends EntityAIBase {
	private final EntityGem gem;
	private final float distance;
	private final float height;

	public EntityAIGemFlyToTarget(EntityGem gem, float distance, float height) {
		this.gem = gem;
		this.distance = distance;
		this.height = height;
		this.setMutexBits(1);
	}

	public boolean shouldExecute() {
		return this.gem.getAttackTarget() != null && this.gem.getDistanceSq(this.gem.getAttackTarget()) > distance;
	}

	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}

	public void startExecuting() {
		EntityLivingBase attackTarget = this.gem.getAttackTarget();
		if (attackTarget != null) {
			double xDist = attackTarget.posX - this.gem.posX;
			double yDist = attackTarget.posY - this.gem.posY;
			double zDist = attackTarget.posZ - this.gem.posZ;
						
			if ((Math.abs(xDist) + Math.abs(zDist)) > Math.abs(yDist)) {
				this.gem.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY + this.height, attackTarget.posZ, 1.0D);				
			} else {
				this.gem.getMoveHelper().setMoveTo(attackTarget.posX, this.gem.posY - 1, attackTarget.posZ, 1.0D);
			}
		}
	}
	
	@Override
	public void updateTask() {
		this.startExecuting();
	}
}