package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.EntityGem;
import mod.heimrarnadalr.kagic.modcompat.CompatTConstruct;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;

public class EntityAIAttackRangedBow extends EntityAIBase {
	private final EntityGem gem;
	private final double movementSpeed;
	private int attackCooldown;
	private final float maxAttackDistance;
	private int attackTime = -1;
	private int seeTime;
	private int strafingTime = -1;
	private boolean strafingBackwards;
	private boolean strafingClockwise;

	public EntityAIAttackRangedBow(EntityGem gem, double speed, int delay, float maxDistance) {
		this.gem = gem;
		this.movementSpeed = speed;
		this.attackCooldown = delay;
		this.maxAttackDistance = maxDistance * maxDistance;
		this.setMutexBits(3);
	}
	
	public void setAttackCooldown(int newAttackCooldown) {
		this.attackCooldown = newAttackCooldown;
	}
	
	public boolean isBowInMainhand() {
		return !this.gem.getHeldItemMainhand().isEmpty() 
				&& (this.gem.getHeldItemMainhand().getItem() == Items.BOW 
				|| CompatTConstruct.isTinkersRangedWeapon(this.gem.getHeldItemMainhand().getItem()));
	}
	
	@Override
	public boolean shouldExecute() {
		return this.gem.getAttackTarget() != null && !this.gem.getAttackTarget().isDead && this.isBowInMainhand();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.shouldExecute();
	}
	
	@Override
	public void startExecuting() {
		this.gem.setSwingingArms(true);
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.gem.setSwingingArms(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.gem.resetActiveHand();
	}
	
	@Override
	public void updateTask() {
		EntityLivingBase target = this.gem.getAttackTarget();
		if (target != null) {
			double d0 = this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
			boolean flag = this.gem.getEntitySenses().canSee(target);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}
			if (flag) {
				++this.seeTime;
			}
			else {
				--this.seeTime;
			}
			if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20) {
				this.gem.getNavigator().clearPath();
				++this.strafingTime;
			}
			else {
				this.gem.getNavigator().tryMoveToEntityLiving(target, this.movementSpeed);
				this.strafingTime = -1;
			}
			if (this.strafingTime >= 20) {
				if ((double)this.gem.getRNG().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}
				if ((double)this.gem.getRNG().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}
				this.strafingTime = 0;
			}
			if (this.strafingTime > -1) {
				if (d0 > (double)(this.maxAttackDistance * 0.75F)) {
					this.strafingBackwards = false;
				}
				else if (d0 < (double)(this.maxAttackDistance * 0.25F)) {
					this.strafingBackwards = true;
				}
				this.gem.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.gem.faceEntity(target, 30.0F, 30.0F);
			}
			else {
				this.gem.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
			}
			if (this.gem.isHandActive()) {
				if (!flag && this.seeTime < -60) {
					this.gem.resetActiveHand();
				}
				else if (flag) {
					int i = this.gem.getItemInUseMaxCount();
					if (i >= 20) {
						this.gem.resetActiveHand();
						this.gem.attackEntityWithRangedAttack(target, ItemBow.getArrowVelocity(i));
						this.attackTime = this.attackCooldown;
					}
				}
			}
			else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.gem.setActiveHand(EnumHand.MAIN_HAND);
			}
		}
	}
}