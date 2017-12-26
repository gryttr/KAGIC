package mod.akrivus.kagic.entity.gem.corrupted;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTongueMonster extends EntityCorruptedGem {
	private static final int SKIN_COLOR = 0x2695A4;
	
	public EntityTongueMonster(World world) {
		super(world);
		this.setSize(0.9F, 2.1F);
		
		this.setCutPlacement(GemCuts.PILLOW, GemPlacements.FOREHEAD);
		//this.setCutPlacement(GemCuts.PILLOW, GemPlacements.BACK);
		//this.setCutPlacement(GemCuts.PILLOW, GemPlacements.LEFT_THIGH);
		//this.setCutPlacement(GemCuts.PILLOW, GemPlacements.RIGHT_THIGH);

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75D);
		
		this.droppedGemItem = ModItems.CORRUPTED_TONGUE_MONSTER_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_TONGUE_MONSTER_GEM;
	}

	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		if (this.rand.nextInt(10) == 0) {
			this.setCustomNameTag(new TextComponentTranslation("entity.kagic.tongue_monster.name.alt").getUnformattedComponentText());
		}
		return super.onInitialSpawn(difficulty, livingdata);
	}

	/*********************************************************
	 * Methods related to sounds.							*
	 *********************************************************/
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.TONGUE_MONSTER_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.TONGUE_MONSTER_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.TONGUE_MONSTER_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		return this.SKIN_COLOR;
	}
}
