package mod.akrivus.kagic.entity.ai;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;

public class EntityAIScareMobs extends EntityAIBase {
	private final EntityGem agate;
	public EntityAIScareMobs(EntityGem agate) {
		this.agate = agate;
		this.setMutexBits(0);
	}
	public boolean shouldExecute() {
    	if (this.agate.ticksExisted % 20 == 0) {
	    	List<EntityMob> list = this.agate.world.<EntityMob>getEntitiesWithinAABB(EntityMob.class, this.agate.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
	        for (EntityMob mob : list) {
	            if (this.agate.isTamed()) {
	                mob.setAttackTarget(null);
	            }
	        }
    	}
    	return false;
    }
}
