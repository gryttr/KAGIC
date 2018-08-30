package mod.akrivus.kagic.entity.ai;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIProtectionFuse<P extends EntityGem, F extends EntityFusionGem> extends EntityAIBase {
	private EntityGem initiator;
	private Class<P> passive;
	private Class<F> result;
	private P target;
	private double size;

	public <T extends EntityGem> EntityAIProtectionFuse(EntityGem initiator, Class<P> passive, Class<F> result, double size) {
		this.initiator = initiator;
		this.passive = passive;
		this.result = result;
		this.target = null;
		this.size = size;
		
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<P> list = this.initiator.world.<P>getEntitiesWithinAABB(passive, this.initiator.getEntityBoundingBox().grow(size, size, size));
		double distance = Double.MAX_VALUE;
		for (P gem : list) {
			if (this.checkInitiator() && this.checkTarget(gem) && this.initiator.getGemPlacement() != gem.getGemPlacement() && this.initiator.isOwnedBySamePeople(gem)) {
				double newDistance = this.initiator.getDistanceSq(gem);
				if (newDistance <= distance) {
					distance = newDistance;
					this.target = gem;
					KAGIC.instance.chatInfoMessage("Found target");
				}
			}
		}
		return this.checkTarget(this.target);
	}

	@Override
	public void startExecuting() {
		this.initiator.getLookHelper().setLookPositionWithEntity(this.target, 30.0F, 30.0F);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.checkInitiator() && this.checkTarget(this.target);
	}
	
	@Override
	public void updateTask() {
		if (this.initiator.getDistanceSq(this.target) > this.target.width * 3) {
			this.initiator.getNavigator().tryMoveToEntityLiving(this.target, this.initiator.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 2.0);
		} else {
			F fusion;
			try {
				fusion = result.getDeclaredConstructor(World.class).newInstance(this.initiator.world);
				fusion.addGem(this.initiator);
				fusion.addGem(this.target);
				fusion.setAdjustedSize();
				if (this.initiator.world.spawnEntity(fusion)) {
					fusion.onInitialSpawn(this.initiator.world.getDifficultyForLocation(this.initiator.getPosition()), null);
					fusion.isSitting = true;
					fusion.setRestPosition(fusion.getPosition());
					this.initiator.world.removeEntity(this.initiator);
					this.target.world.removeEntity(this.target);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.resetTask();
		}
	}

	@Override
	public void resetTask() {
		this.initiator.getNavigator().clearPath();
		this.target = null;
	}
	
	private boolean checkInitiator() {
		return this.initiator.isTamed() && !this.initiator.isFusion() && this.initiator.getHealth() > 0 && !this.initiator.isDead ;
	}

	private boolean checkTarget(P target) {
		return target != null
				&& target.isTamed()
				&& !target.isFusion()
				&& target.getHealth() > 0
				&& !target.isDead 
				&& target.getRevengeTarget() != null
				&& target.getRevengeTarget() != this.initiator
				&& !target.getRevengeTarget().isDead;
	}
}