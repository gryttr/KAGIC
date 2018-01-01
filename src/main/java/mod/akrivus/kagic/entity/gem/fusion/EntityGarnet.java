package mod.akrivus.kagic.entity.gem.fusion;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPadparadscha;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityGarnet extends EntityFusionGem {
	private static final int SKIN_COLOR_BEGIN = 0x9B4A6D;
	private static final int SKIN_COLOR_MID = 0xBE4459;
	private static final int SKIN_COLOR_END = 0xCC3281; 

	private static final int HAIR_COLOR_BEGIN = 0xF13FA6;
	private static final int HAIR_COLOR_END = 0xF13FA6; 
	
	private static final int NUM_HAIRSTYLES = 1;

	public EntityGarnet(World world) {
		super(world);
		this.setSize(0.7F, 2.1F);
		this.visorChanceReciprocal = 1;
		this.isImmuneToFire = true;
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
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
			if (gem instanceof EntitySapphire || gem instanceof EntityPadparadscha) {
				this.setFusionColor(gem.getSkinColor());
			}
			return super.addGem(gem);
		}
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public float[] getGemColor() {
		return new float[] { 255F/ 255F, 61F / 255F, 122F / 255F };
	}

	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityGarnet.SKIN_COLOR_BEGIN);
		skinColors.add(EntityGarnet.SKIN_COLOR_MID);
		skinColors.add(EntityGarnet.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityGarnet.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityGarnet.HAIR_COLOR_BEGIN);
		hairColors.add(EntityGarnet.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	
	@Override
	public float getSizeFactor() {
		return super.getSizeFactor() + (1F - super.getSizeFactor()) / 2;
	}
	
	@Override
	public void setAdjustedSize() {
		float sizeModifier = this.getPrimeCount() - this.getDefectiveCount();
		this.setSize(.7F, 2.1F + sizeModifier * 0.75F);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D + sizeModifier * 50D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.5D + sizeModifier * 2.5D);
	}

	/*********************************************************
	 * Methods related to sounds.							*
	 *********************************************************/
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.GARNET_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.GARNET_HURT;
	}
	
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.GARNET_OBEY;
	}
}