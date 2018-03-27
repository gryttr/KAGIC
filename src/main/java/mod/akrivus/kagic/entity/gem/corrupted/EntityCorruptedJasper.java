package mod.akrivus.kagic.entity.gem.corrupted;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
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
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class EntityCorruptedJasper extends EntityCorruptedGem {

	public EntityCorruptedJasper(World world) {
		super(world);
		this.setSize(1.9F, 2.8F);

		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);

		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		//0 - normal jasper
		//1 - ocean jasper
		//2 - biggs jasper
		//3 - green jasper
		//4 - bruneau jasper
		//5 - purple jasper
		//6 - flame jasper
		//7 - picture jasper
		int special = this.rand.nextInt(8);

		this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.corrupted_jasper_%1$d.name", special)).getUnformattedComponentText());
		this.setSpecial(special);
		/*this.setMark1(this.generateMark1());
		this.setMark1Color(this.generateMark1Color());
		if (this.hasSecondMarking()) {
			this.setMark2(this.generateMark2());
			this.setMark2Color(this.generateMark2Color());
		}*/
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
		public void itemDataToGemData(int data) {
			this.setCustomNameTag(new TextComponentTranslation(String.format("entity.kagic.corrupted_jasper_%1$d.name", data)).getUnformattedComponentText());
			this.setSpecial(data);
			//Might need this later
			//this.setSkinColor(this.generateSkinColor());
		}
	 
	 @Override
	public void setNewCutPlacement() {
		GemCuts cut = this.getSpecial() == 0 ? GemCuts.CABOCHON : GemCuts.FACETED;
		
		ArrayList<GemPlacements> placements = this.cutPlacements.get(cut);
		int placementIndex = this.rand.nextInt(placements.size());
		GemPlacements placement = placements.get(placementIndex);
		
		this.setGemCut(cut.id);
		this.setGemPlacement(placement.id);		
	}

	public String getSpecialSkin() {
		switch (this.getSpecial()) {
		case 0:
			return "";
		case 1:
			return "ocean_";
		case 2:
			return "biggs_";
		case 3:
			return "green_";
		case 4:
			return "bruneau_";
		case 5:
			return "purple_";
		case 6:
			return "flame_";
		case 7:
			return "picture_";
		}
		return "";
	}

	@Override
	protected int generateGemColor() {
		switch (this.getSpecial()) {
		case 1:
			return 0x58D3CF;
		case 2:
			return 0xD48768;
		case 3:
			return 0xBAD1B5;
		case 4:
			return 0xFFC583;
		case 5:
			return 0xD7A3E6;
		case 6:
			return 0xC78873;
		case 7:
			return 0xF3F2F9;
		case 8:
			return 0xB02E26;
		default:
			return 0xFF3F01;
		}
	}

	/*********************************************************
	 * Methods related to entity death.					  *
	 *********************************************************/
	@Override
	public void onDeath(DamageSource cause) {
		switch (this.getSpecial()) {
		case 0:
			this.droppedGemItem = ModItems.CORRUPTED_NOREENA_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_NOREENA_JASPER_GEM;
			break;
		case 1:
			this.droppedGemItem = ModItems.CORRUPTED_OCEAN_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_OCEAN_JASPER_GEM;
			break;
		case 2:
			this.droppedGemItem = ModItems.CORRUPTED_BIGGS_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_BIGGS_JASPER_GEM;
			break;
		case 3:
			this.droppedGemItem = ModItems.CORRUPTED_GREEN_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_GREEN_JASPER_GEM;
			break;
		case 4:
			this.droppedGemItem = ModItems.CORRUPTED_BRUNEAU_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_BRUNEAU_JASPER_GEM;
			break;
		case 5:
			this.droppedGemItem = ModItems.CORRUPTED_PURPLE_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_PURPLE_JASPER_GEM;
			break;
		case 6:
			this.droppedGemItem = ModItems.CORRUPTED_FLAME_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_FLAME_JASPER_GEM;
			break;
		case 7:
			this.droppedGemItem = ModItems.CORRUPTED_PICTURE_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_PICTURE_JASPER_GEM;
			break;
		default:
			this.droppedGemItem = ModItems.CORRUPTED_JASPER_GEM;
			this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_JASPER_GEM;
			break;
		}
		super.onDeath(cause);
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
}