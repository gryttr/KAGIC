package mod.akrivus.kagic.util.flying;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class EntityFlyHelper extends EntityMoveHelper {
    public EntityFlyHelper(EntityLiving entity) {
        super(entity);
    }
    public void onUpdateMoveHelper() {
        if (this.action == EntityMoveHelper.Action.MOVE_TO) {
        	double y = this.posY - this.entity.posY;
            float angle = (float)(MathHelper.atan2(this.posY, this.entity.posY) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, angle, 90.0F);
            if (Math.abs(y) >= 1) {
	            if (y > 0.0D) {
	            	this.entity.setNoGravity(true);
	            	this.entity.motionY = 0.2D;
	            }
	            else {
	            	this.entity.setNoGravity(false);
	            	this.entity.motionY = -0.2D;
	            }
            }
            else {
            	this.entity.motionY = 0.0D;
            }
        }
        super.onUpdateMoveHelper();
    }
}