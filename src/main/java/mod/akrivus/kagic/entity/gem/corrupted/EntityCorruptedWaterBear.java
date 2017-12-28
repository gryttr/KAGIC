package mod.akrivus.kagic.entity.gem.corrupted;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCorruptedWaterBear extends EntityCorruptedGem {

	public EntityCorruptedWaterBear(World world) {
		super(world);
		this.setSize(0.9F, 1.3F);
		
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.NOSE);
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}

	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setSpecial(this.rand.nextInt(2));
		this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.water_bear_%1$d.name", this.getSpecial())).getUnformattedComponentText());
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	public void itemDataToGemData(int data) {
		this.setSpecial(data);
		this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.water_bear_%1$d.name", data)).getUnformattedComponentText());
	}

	/*********************************************************
	 * Methods related to entity death.					  *
	 *********************************************************/
	public void onDeath(DamageSource cause) {
		switch (this.getSpecial()) {
		case 0:
			this.droppedGemItem = ModItems.CORRUPTED_BLUE_WATER_BEAR_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_BLUE_WATER_BEAR_GEM;
			break;
		default:
			this.droppedGemItem = ModItems.CORRUPTED_GREEN_WATER_BEAR_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_GREEN_WATER_BEAR_GEM;
			break;
		}
		super.onDeath(cause);
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return SoundEvents.ENTITY_SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SLIME_DEATH;
	}
}
