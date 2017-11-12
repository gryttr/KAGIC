package mod.akrivus.kagic.entity.gem.corrupted;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
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
		this.droppedCrackedGemItem = ModItems.CORRUPTED_TONGUE_MONSTER_GEM;
	}

	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		return this.SKIN_COLOR;
	}
}
