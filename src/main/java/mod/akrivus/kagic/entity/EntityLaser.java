package mod.akrivus.kagic.entity;

import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import mod.akrivus.kagic.util.LaserDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLaser extends Entity {
    public EntityLivingBase shootingEntity;
    public int explosionPower;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    public EntityLaser(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }
        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }
    public EntityLaser(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ, int explosionPower) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        this.accelerationX = accelX;
        this.accelerationY = accelY;
        this.accelerationZ = accelZ;
        this.explosionPower = explosionPower;
    }
    public EntityLaser(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ, int explosionPower) {
        super(worldIn);
        this.shootingEntity = shooter;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.accelerationX = accelX;
        this.accelerationY = accelY;
        this.accelerationZ = accelZ;
        this.explosionPower = explosionPower;
    }
    public void onUpdate() {
        if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this))) {
            super.onUpdate();
            RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, false, this.shootingEntity);
            if (raytraceresult != null) {
                this.onImpact(raytraceresult);
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= this.getMotionFactor();
            this.motionY *= this.getMotionFactor();
            this.motionZ *= this.getMotionFactor();
            this.world.spawnParticle(this.getParticleType(), this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
        else {
            this.setDead();
        }
    }
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SMOKE_NORMAL;
    }
    protected double getMotionFactor() {
        return 0.75D;
    }
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
        	boolean destroy = true;
            if (result.entityHit != null) {
            	if (result.entityHit instanceof EntityRoamingEye) {
            		EntityRoamingEye roaming_eye = (EntityRoamingEye) result.entityHit;
            		for (Entity passenger : roaming_eye.getPassengers()) {
            			if (passenger.equals(this.shootingEntity)) {
            				destroy = false;
            			}
            		}
            	}
            	if (destroy) {
            		result.entityHit.attackEntityFrom(new LaserDamage(), 100.0F);
            	}
            }
            if (destroy) {
            	this.world.newExplosion(null, this.posX, this.posY, this.posZ, (float) this.explosionPower, true, true);
            	this.setDead();
            }
        }
    }
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
        compound.setTag("power", this.newDoubleNBTList(new double[] {this.accelerationX, this.accelerationY, this.accelerationZ}));
        compound.setInteger("explosionPower", this.explosionPower);
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("power", 9)) {
            NBTTagList nbttaglist = compound.getTagList("power", 6);
            if (nbttaglist.tagCount() == 3) {
                this.accelerationX = nbttaglist.getDoubleAt(0);
                this.accelerationY = nbttaglist.getDoubleAt(1);
                this.accelerationZ = nbttaglist.getDoubleAt(2);
            }
        }
        this.explosionPower = compound.getInteger("explosionPower");
        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
            NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
            this.motionX = nbttaglist1.getDoubleAt(0);
            this.motionY = nbttaglist1.getDoubleAt(1);
            this.motionZ = nbttaglist1.getDoubleAt(2);
        }
        else {
            this.setDead();
        }
    }
    public boolean canBeCollidedWith() {
        return true;
    }
    public float getCollisionBorderSize() {
        return 1.0F;
    }
    public float getBrightness(float partialTicks) {
        return 1.0F;
    }
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks) {
        return 15728880;
    }
	protected void entityInit() { }
}
