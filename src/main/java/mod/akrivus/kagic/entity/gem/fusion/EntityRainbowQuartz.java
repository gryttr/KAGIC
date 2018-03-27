package mod.akrivus.kagic.entity.gem.fusion;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityRainbowQuartz extends EntityFusionGem {
	private static final int RAINBOW_QUARTZ_SKIN_COLOR = 0xFFC5C8;
	private static final int RAINBOW_QUARTZ_HAIR_COLOR = 0xFFFFE8;
	
	public EntityRainbowQuartz(World world) {
		super(world);
		this.setSize(0.7F, 4.75F);

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0D);
	}

	//=========================================================================
	//=== Methods for managing fusion==========================================
	//=========================================================================
	
	@Override
	public boolean addGem(EntityGem gem) {
		if (this.getFusionCount() >= 2) {
			return false;
		} else {
			return super.addGem(gem);
		}
	}

	/*********************************************************
	 * Methods related to rendering.						 *
	 *********************************************************/
	
	@Override
	protected int generateSkinColor() {
		return EntityRainbowQuartz.RAINBOW_QUARTZ_SKIN_COLOR;
	}

	@Override
	protected int generateHairColor() {
		return EntityRainbowQuartz.RAINBOW_QUARTZ_HAIR_COLOR;
	}
	
	@Override
	public float getSizeFactor() {
		return super.getSizeFactor() + (1F - super.getSizeFactor()) / 2;
	}
	
	@Override
	public void setAdjustedSize() {
		float sizeModifier = this.getPrimeCount() - this.getDefectiveCount();
		this.setSize(.9F, 4.75F + sizeModifier);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D + sizeModifier * 50D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D + sizeModifier * 2.5D);
	}
}