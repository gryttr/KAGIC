package mod.akrivus.kagic.entity.gem.fusion;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySameTypeFusion extends EntityFusionGem {

	public EntitySameTypeFusion(World world, EntityGem gem) {
		super(world);

		for (EntityAITasks.EntityAITaskEntry task : gem.tasks.taskEntries) {
			this.tasks.taskEntries.add(task);
		}
		
		for (EntityAITasks.EntityAITaskEntry task : gem.targetTasks.taskEntries) {
			this.targetTasks.taskEntries.add(task);
		}
		
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(gem.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(gem.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
		this.isImmuneToFire = gem.isImmuneToFire();
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setAdjustedSize();
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	@Override
	public boolean addGem(EntityGem gem) {
		if (this.getFusionCount() == 0) {
			this.setSkinColor(gem.getSkinColor());
			this.setHairColor(gem.getHairColor());
			this.setHairStyle(gem.getHairStyle());
			this.setGemColor(gem.getGemColor());
		}
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() + 
			gem.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue()
		);
		
		return super.addGem(gem);
	}
	
	@Override
	protected int generateSkinColor() {
		return this.getSkinColor();
	}
	
	@Override
	protected int generateHairStyle() {
		return this.getHairStyle();
	}
	
	@Override
	protected int generateHairColor() {
		return this.getHairColor();
	}
	
	@Override
	protected int generateGemColor() {
		return this.getGemColor();
	}
}
