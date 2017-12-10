package mod.akrivus.kagic.entity.ai;

import java.util.Random;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

public class EntityAIGemRandomFlight extends EntityAIBase {
	private final EntityGem gem;

	public EntityAIGemRandomFlight(EntityGem gem) {
		this.gem = gem;
		this.setMutexBits(1);
	}

	public boolean shouldExecute() {
		EntityMoveHelper entitymovehelper = this.gem.getMoveHelper();

		if (!entitymovehelper.isUpdating()) {
			return true;
		} else {
			double d0 = entitymovehelper.getX() - this.gem.posX;
			double d1 = entitymovehelper.getY() - this.gem.posY;
			double d2 = entitymovehelper.getZ() - this.gem.posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			return d3 < 1.0D || d3 > 3600.0D;
		}
	}

	public boolean shouldContinueExecuting() {
		return false;
	}

	public void startExecuting() {
		Random random = this.gem.getRNG();
		double nextPosX = this.gem.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		double nextPosY = this.gem.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
		if (nextPosY >= 255) {
			nextPosY = 250;
		}
		double nextPosZ = this.gem.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		this.gem.getMoveHelper().setMoveTo(nextPosX, nextPosY, nextPosZ, 1.0D);
	}
}