package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWatermelonTourmaline;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class EntityAITourmalineBlowAttack extends EntityAIBase {
	private final EntityCorruptedWatermelonTourmaline tourmaline;
	private final double blowStrength;
	private final double distance;
	private final double areaAffected;

	public EntityAITourmalineBlowAttack(EntityCorruptedWatermelonTourmaline tourmaline, double blowStrength, double distance, double areaAffected) {
		this.tourmaline = tourmaline;
		this.blowStrength = blowStrength;
		this.distance = distance;
		this.areaAffected = areaAffected;
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase target = this.tourmaline.getAttackTarget();
		return target != null && this.tourmaline.getDistance(target) <= this.distance;
	}
	
	@Override
	public void updateTask() {
		EntityLivingBase target = this.tourmaline.getAttackTarget();
		if (target != null && this.tourmaline.canEntityBeSeen(target)) {
			this.tourmaline.setAttacking(true);
			this.tourmaline.faceEntity(target, 10F, 10F);
			Vec3d direction = target.getPositionVector().subtract(this.tourmaline.getPositionVector());
			Vec3d blowVector = direction.normalize().scale(this.blowStrength);
			for (Entity entity : target.world.getEntitiesInAABBexcluding(this.tourmaline, target.getEntityBoundingBox().grow(areaAffected), null)) {
				entity.addVelocity(blowVector.x, blowVector.y, blowVector.z);
			}
		}
	}
}
