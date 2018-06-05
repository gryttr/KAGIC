package mod.akrivus.kagic.entity.gem.corrupted;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCorruptedRoseQuartz extends EntityCorruptedGem {

	public EntityCorruptedRoseQuartz(World world) {
		super(world);
		this.setSize(1.9F, 2.8F);

		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		
		this.droppedGemItem = ModItems.CORRUPTED_ROSE_QUARTZ_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_ROSE_QUARTZ_GEM;
	}

	/*********************************************************
	 * Methods related to sounds.							*
	 *********************************************************/
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.CORRUPTED_QUARTZ_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.CORRUPTED_QUARTZ_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.CORRUPTED_QUARTZ_DEATH;
	}

	/*********************************************************
	 * Methods related to rendering.						 *
	 *********************************************************/
	@Override
	protected int generateGemColor() {
		return 0xFFA2E6;
	}

	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_BEGIN);
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_BEGIN);
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}

}
