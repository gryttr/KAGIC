package mod.akrivus.kagic.entity.gem.corrupted;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.ai.EntityAIGemFlyToTarget;
import mod.akrivus.kagic.entity.ai.EntityAIGemRandomFlight;
import mod.akrivus.kagic.entity.ai.EntityAITourmalineBlowAttack;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCorruptedWatermelonTourmaline extends EntityCorruptedGem {
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityCorruptedWatermelonTourmaline.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> MOUTH_OPEN = EntityDataManager.<Integer>createKey(EntityCorruptedWatermelonTourmaline.class, DataSerializers.VARINT);
	private static final int MOUTH_OPEN_DURATION = 30;
	
	public EntityCorruptedWatermelonTourmaline(World world) {
		super(world);
		this.setSize(2.9F, 2.9F);
		
		this.setCutPlacement(GemCuts.TRIANGULAR, GemPlacements.BACK_OF_HEAD);
		
		this.moveHelper = new EntityCorruptedWatermelonTourmaline.TourmalineMoveHelper(this);
		this.tasks.taskEntries.clear();
		this.tasks.addTask(1, new EntityAITourmalineBlowAttack(this, 2D, 10D, 4D));
		this.tasks.addTask(2, new EntityAIGemFlyToTarget(this, 8F, 3F));
		this.tasks.addTask(3, new EntityAIGemRandomFlight(this));
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		this.dataManager.register(ATTACKING, false);
		this.dataManager.register(MOUTH_OPEN, 0);
		 
		this.droppedGemItem = ModItems.CORRUPTED_WATERMELON_TOURMALINE_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_CORRUPTED_WATERMELON_TOURMALINE_GEM;
	}

	public boolean isAttacking() {
		return this.dataManager.get(ATTACKING);
	}

	public void setAttacking(boolean attacking) {
		if (attacking) {
			this.setMouthOpen(EntityCorruptedWatermelonTourmaline.MOUTH_OPEN_DURATION);
		}
		this.dataManager.set(ATTACKING, attacking);
	}

	public void setMouthOpen(int time) {
		this.dataManager.set(MOUTH_OPEN, time);
	}

	public int getMouthOpen() {
		return this.dataManager.get(MOUTH_OPEN);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.getMouthOpen() > 0) {
			this.setMouthOpen(this.getMouthOpen() - 1);
		}

		if (this.getMouthOpen() <= 0 && this.isAttacking()) {
			this.setAttacking(false);
		}
	}
	
	@Override
	public void setAttackAI() {}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();

			if (!source.isExplosion()) {
				entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void travel(float strafe, float up, float forward) {
		if (this.isInWater()) {
			this.moveRelative(strafe, up, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		}
		else if (this.isInLava()) {
			this.moveRelative(strafe, up, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				IBlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			float f1 = 0.16277136F / (f * f * f);
			this.moveRelative(strafe, up, forward, this.onGround ? 0.1F * f1 : 0.02F);
			f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				IBlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)f;
			this.motionY *= (double)f;
			this.motionZ *= (double)f;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	@Override
	public boolean isOnLadder() {
		return false;
	}
	
	@Override
	public float getEyeHeight() {
		return 2F;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		World world = this.world;
		BlockPos pos = this.getPosition();
		Biome biome = world.getBiome(pos);
		
		boolean isWater = BiomeDictionary.hasType(biome, Type.OCEAN) || BiomeDictionary.hasType(biome, Type.BEACH);
		
		return isWater && world.canSeeSky(pos) && super.getCanSpawnHere();
	}
	
	static class TourmalineMoveHelper extends EntityMoveHelper {
		private final EntityCorruptedWatermelonTourmaline parentEntity;
		private int courseChangeCooldown;

		public TourmalineMoveHelper(EntityCorruptedWatermelonTourmaline tourmaline) {
			super(tourmaline);
			this.parentEntity = tourmaline;
		}

		@Override
		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				double xDist = this.posX - this.parentEntity.posX;
				double yDist = this.posY - this.parentEntity.posY;
				double zDist = this.posZ - this.parentEntity.posZ;
				double euclideanDistance = xDist * xDist + yDist * yDist + zDist * zDist;

				//KAGIC.instance.chatInfoMessage("Cooldown is " + this.courseChangeCooldown);
				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					euclideanDistance = (double)MathHelper.sqrt(euclideanDistance);

					if (this.isNotColliding(this.posX, this.posY, this.posZ, euclideanDistance)) {
						this.parentEntity.motionX += xDist / euclideanDistance * 0.1D;
						this.parentEntity.motionY += yDist / euclideanDistance * 0.1D;
						this.parentEntity.motionZ += zDist / euclideanDistance * 0.1D;
					} else {
						this.action = EntityMoveHelper.Action.WAIT;
					}
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
			double d0 = (x - this.parentEntity.posX) / p_179926_7_;
			double d1 = (y - this.parentEntity.posY) / p_179926_7_;
			double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double)i < 3F; ++i) {
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()) {
					return false;
				}
			}

			return true;
		}
	}
}