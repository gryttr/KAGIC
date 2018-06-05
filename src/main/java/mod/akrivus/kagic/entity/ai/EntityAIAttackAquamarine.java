package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBow;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class EntityAIAttackAquamarine extends EntityAIBase {
	private final EntityAquamarine gem;
	private double speedTowardsTarget;
	private Path path;
	public EntityAIAttackAquamarine(EntityAquamarine gem, double speedIn) {
		this.gem = gem;
		this.speedTowardsTarget = speedIn;
		this.setMutexBits(7);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.gem.isDefective()) {
			return false;
		}
		else {
			EntityLivingBase target = this.gem.getAttackTarget();
	        if (target == null) {
	            return false;
	        }
	        else if (!target.isEntityAlive()) {
	            return false;
	        }
	        else if (target.isGlowing()) {
	        	return false;
	        }
	        else {
	            this.path = this.gem.getNavigator().getPathToEntityLiving(target);
	            if (this.path != null) {
	                return true;
	            }
	            else {
	                return this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ) < 32;
	            }
	        }
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		EntityLivingBase target = this.gem.getAttackTarget();
        if (target == null) {
            return false;
        }
        else if (!target.isEntityAlive()) {
            return false;
        }
        else if (target.isGlowing()) {
        	return false;
        }
        return true;
	}
	
	@Override
	public void startExecuting() {
		this.gem.getNavigator().setPath(this.path, this.speedTowardsTarget);
	}
	
	@Override
	public void updateTask() {
		EntityLivingBase target = this.gem.getAttackTarget();
		if (target != null) {
			double distance = this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
			boolean flag = this.gem.getEntitySenses().canSee(target);
			if (distance < 32 && flag && !this.gem.isDefective()) {
				target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 8));
		    	target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 600));
		    	if (this.gem.isPrimary()) {
		    		target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 600));
		    	}
			}
		}
	}
}