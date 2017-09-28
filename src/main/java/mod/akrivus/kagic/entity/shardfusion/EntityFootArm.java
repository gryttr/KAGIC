package mod.akrivus.kagic.entity.shardfusion;

import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityFootArm extends EntityShardFusion {

	public EntityFootArm(World world) {
		super(world);

		this.setCutPlacement(GemCuts.SHARD, GemPlacements.BACK);
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		
		this.droppedGemItem = ModItems.FOOTARM_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_FOOTARM_GEM;
	}
}
