package mod.akrivus.kagic.entity.ai;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.util.CharsTrie.Iterator;

import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.fusion.EntityRubyFusion;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIRubyFuse extends EntityAIBase {
	private final EntityRuby initiator;
	private final double movementSpeed;
	private final double size;
	private List<EntityRuby> targetRubies = new ArrayList<EntityRuby>();
	
	public EntityAIRubyFuse(EntityRuby ruby, double speed, double size) {
		this.initiator = ruby;
		this.movementSpeed = speed;
		this.size = size;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.initiator.getFusionTarget() != null) {
			return true;
		} else if (this.initiator.canFuse() && this.initiator.getAnger() > 0) {
			List<EntityRuby> nearbyRubies = this.initiator.world.<EntityRuby>getEntitiesWithinAABB(EntityRuby.class, this.initiator.getEntityBoundingBox().grow(size, size, size));
			
			next:
			for (EntityRuby nearby : nearbyRubies) {
				if (!this.initiator.canFuseWith(nearby)) {
					continue;
				}
				
				for (EntityRuby otherNearby : targetRubies) {
					if (!nearby.canFuseWith(otherNearby)) {
						continue next;
					}
				}
				
				if (this.checkTarget(nearby)) {
					nearby.setFusionTarget(this.initiator);
					this.targetRubies.add(nearby);
				}
			}
			return !targetRubies.isEmpty();
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		if (this.initiator.getFusionTarget() != null) {
			this.initiator.getLookHelper().setLookPositionWithEntity(this.initiator.getFusionTarget(), 30F, 30F);
		} else if (!this.targetRubies.isEmpty()) {
			this.initiator.getLookHelper().setLookPositionWithEntity(this.targetRubies.get(0), 30.0F, 30.0F);
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if (this.initiator.getFusionTarget() != null) {
			return this.initiator.canFuseWith(this.initiator.getFusionTarget());
		} else {
			for (final java.util.Iterator<EntityRuby> i = this.targetRubies.iterator(); i.hasNext();) {
				EntityRuby target = i.next();
				if (!this.checkTarget(target)) {
					i.remove();
					target.setFusionTarget(null);
				}
			}
			return checkInitiator() && !this.targetRubies.isEmpty();
		}
	}
	
	@Override
	public void resetTask() {
		this.initiator.getNavigator().clearPath();
		this.targetRubies.clear();
	}
	
	@Override
	public void updateTask() {
		EntityRuby target = this.initiator.getFusionTarget();
		if (target != null && this.initiator.getDistanceSq(target) > target.width * 3) {
			this.initiator.getNavigator().tryMoveToEntityLiving(target, this.movementSpeed);
		} else if (target == null && !this.targetRubies.isEmpty()) {
			for (EntityRuby targetRuby : this.targetRubies) {
				if (this.initiator.getDistanceSq(targetRuby) > targetRuby.width * 3) {
					return;
				}
			}
			
			this.initiator.playSound(ModSounds.RUBY_COMBINE, this.initiator.getSoundVolume(), this.initiator.getSoundPitch());
			EntityRubyFusion rubyFusion;
			try {
				rubyFusion = EntityRubyFusion.class.getDeclaredConstructor(World.class).newInstance(this.initiator.world);
				rubyFusion.addGem(this.initiator);
				for (EntityRuby targetRuby : this.targetRubies) {
					targetRuby.setFusionTarget(null);
					rubyFusion.addGem(targetRuby);
				}
				
				rubyFusion.setAdjustedSize();
				if (this.initiator.world.spawnEntity(rubyFusion)) {
					rubyFusion.onInitialSpawn(this.initiator.world.getDifficultyForLocation(this.initiator.getPosition()), null);
					rubyFusion.isSitting = true;
					rubyFusion.setRestPosition(rubyFusion.getPosition());
					this.initiator.world.removeEntity(this.initiator);
					for (EntityRuby targetRuby : this.targetRubies) {
						targetRuby.setFusionTarget(null);
						targetRuby.world.removeEntity(targetRuby);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.resetTask();
		}
	}

	private boolean checkInitiator() {
		return this.initiator.isTamed() && this.initiator.getHealth() > 0 && !this.initiator.isDead ;
	}

	private boolean checkTarget(EntityRuby target) {
		return target != null
				&& target.isTamed()
				&& !target.isFusion()
				&& target.getHealth() > 0
				&& !target.isDead 
				&& target.getRevengeTarget() != this.initiator;
	}
}