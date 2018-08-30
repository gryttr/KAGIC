package mod.akrivus.kagic.entity.gem.fusion;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRubyFusion extends EntitySameTypeFusion {
	private static final DataParameter<Integer> ANGER = EntityDataManager.<Integer>createKey(EntityRubyFusion.class, DataSerializers.VARINT);
	private int angerTicks = 0;

	public EntityRubyFusion(World world) {
		super(world, new EntityRuby(world));
	}

	@Override
	public boolean addGem(EntityGem gem) {
		if(!(gem instanceof EntityRuby)) {
			return false;
		}
		((EntityRuby)gem).setAnger(0);
		return super.addGem(gem);
	}
	
	@Override
	public float getSizeFactor() {
		if (this.getFusionCount() == 0) {
			return 0;
		}
		
		float sizeMultiplier = this.getFusionCount() - this.getDefectiveCount() * 0.5F + this.getPrimeCount() * 0.5F;
		return sizeMultiplier / this.getFusionCount();
	}
	
	@Override
	public void setAdjustedSize() {
		this.setSize(.7F, 1.1F * this.getFusionCount() * this.getSizeFactor());
	}

	/*********************************************************
	 * Methods related to sounds.                            *
	 *********************************************************/

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.RUBY_LIVING;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.RUBY_HURT;
	}
	
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.RUBY_OBEY;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.RUBY_DEATH;
	}
}
