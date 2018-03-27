package mod.akrivus.kagic.entity.gem.corrupted;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import mod.heimrarnadalr.kagic.world.structure.LootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCorruptedMoissanite extends EntityCorruptedGem {

	public EntityCorruptedMoissanite(World world) {
		super(world);
		this.setSize(1F, 5.8F);
		
		this.tasks.taskEntries.clear();
		
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.CHEST);
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(Double.MAX_VALUE);
		this.setNoGravity(true);
		
		this.droppedGemItem = ModItems.CORRUPTED_MOISSANITE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_MOISSANITE_GEM;
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setPosition(this.posX + 0.5, this.posY, this.posZ + 0.5);
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	@Override
	protected void collideWithEntity(Entity entity) {
		KAGIC.instance.chatInfoMessage("Collided");
		entity.applyEntityCollision(this);
	}
	
	@Override
	protected void collideWithNearbyEntities() {}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.rotationYaw = 0;
	}

	@Override
	public void setAttackAI() {}
	
	@Override
	public boolean getAlwaysRenderNameTag() {
		return false;
	}
	
	@Override
	public ResourceLocation getLootTable() {
		return LootTables.OBELISK;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return false;
	}
}
