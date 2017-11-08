package mod.akrivus.kagic.entity.gem.fusion;

import mod.akrivus.kagic.entity.EntityFusionGem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityRainbowQuartz extends EntityFusionGem {

	public EntityRainbowQuartz(World world) {
		super(world);
		this.setSize(0.7F, 2.5F);

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0D);
	}

}
