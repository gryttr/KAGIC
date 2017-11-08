package mod.akrivus.kagic.entity.gem.fusion;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityMalachite extends EntityFusionGem {
	private static final int SKIN_COLOR_BEGIN = 0x00FFC5;
	private static final int SKIN_COLOR_END = 0x00FFC5;
	
	private static final int HAIR_COLOR_BEGIN = 0xECFDE8;
	private static final int HAIR_COLOR_END = 0xECFDE8;
	
	private static final DataParameter<Integer> MARK_COLOR = EntityDataManager.<Integer>createKey(EntityJasper.class, DataSerializers.VARINT);
	private static final int MARK_COLOR_BEGIN = 0x00735E;
	private static final int MARK_COLOR_END = 0x00735E;
	
	private static final int NUM_HAIRSTYLES = 1;

	public EntityMalachite(World world) {
		super(world);
		this.setSize(1.9F, 8.3F);
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(25D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0D);
		
        this.dataManager.register(MARK_COLOR, 0);
	}

	@Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setSpecial(0);
		this.setMarkColor(this.generateMarkColor());
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	//=========================================================================
	//=== Methods for entity NBTing ===========================================
	//=========================================================================
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp) {
		comp.setInteger("markColor", this.getMarkColor());
		return super.writeToNBT(comp);
	}

	@Override
	public void readFromNBT(NBTTagCompound comp) {
		super.readFromNBT(comp);
		this.setMarkColor(comp.getInteger("markColor"));
	}
	
	/*********************************************************
	 * Methods related to entity living.                     *
	 *********************************************************/
	public void onLivingUpdate() {
		super.onLivingUpdate();
		/*
		if (this.inWater) {
			this.motionX *= 2F;
			this.motionZ *= 2F;
		}*/
	}

	/*********************************************************
	 * Methods related to entity rendering.                  *
	 *********************************************************/
	@Override
	public float[] getGemColor() {
		return new float[] { 54F / 255F, 193F / 255F, 110F / 255F };
    }

	@Override
	public int getSpecial() {
		return this.dataManager.get(SPECIAL);
	}

	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityMalachite.SKIN_COLOR_BEGIN);
		skinColors.add(EntityMalachite.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}

	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityMalachite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityMalachite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}

	protected int generateMarkColor() {
		ArrayList<Integer> markColors = new ArrayList<Integer>();
		markColors.add(EntityMalachite.MARK_COLOR_BEGIN);
		markColors.add(EntityMalachite.MARK_COLOR_END);
		return Colors.arbiLerp(markColors);
	}
	
	public void setMarkColor(int markColor) {
		this.dataManager.set(MARK_COLOR, markColor);
	}
	
	public int getMarkColor() {
		return this.dataManager.get(MARK_COLOR);
	}
	
	@Override
	public float getSizeFactor() {
		return super.getSizeFactor() + (1F - super.getSizeFactor()) / 1.25F;
	}
	
	@Override
	public void setAdjustedSize() {
		float sizeModifier = this.getPrimeCount() - this.getDefectiveCount();
		this.setSize(1.9F + sizeModifier * 0.125F, 8.3F + sizeModifier * 0.75F);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D + sizeModifier * 50D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(25D + sizeModifier * 2.5D);
	}
}