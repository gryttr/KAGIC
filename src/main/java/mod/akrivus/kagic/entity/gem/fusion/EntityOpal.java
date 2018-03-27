package mod.akrivus.kagic.entity.gem.fusion;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityOpal extends EntityFusionGem {
	private static final int OPAL_SKIN_COLOR = 0xD3D9EE;
	private static final int OPAL_HAIR_COLOR = 0xF9FEEE;
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityOpal(World world) {
		super(world);
		this.setSize(.9F, 4.75F);

        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
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
			if (gem instanceof EntityAmethyst) {
				this.setSkinColor(gem.getSkinColor());
			}
			return super.addGem(gem);
		}
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	
	@Override
	protected int generateSkinColor() {
		return this.getSkinColor();
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityOpal.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityOpal.OPAL_HAIR_COLOR);
		return Colors.arbiLerp(hairColors);
	}
	
	@Override
	public float getSizeFactor() {
		return super.getSizeFactor() + (1F - super.getSizeFactor()) / 2;
	}
	
	@Override
	public void setAdjustedSize() {
		float sizeModifier = this.getPrimeCount() - this.getDefectiveCount();
		this.setSize(.9F, 4.75F + sizeModifier);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D + sizeModifier * 50D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15D + sizeModifier * 2.5D);
	}
}