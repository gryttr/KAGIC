package mod.akrivus.kagic.client.model.fusions;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelMalachite extends ModelGem {
	//public ModelRenderer body;
	public ModelRenderer neck;
	//public ModelRenderer arm_l;
	//public ModelRenderer arm_r;
	public ModelRenderer waist;
	//public ModelRenderer head;
	public ModelRenderer hair;
	//public ModelRenderer head_overlay;
	public ModelRenderer abdomen;
	public ModelRenderer leg;	//Front left
	public ModelRenderer leg_1; //Front right
	public ModelRenderer leg_2; //Back left
	public ModelRenderer leg_3; //Back right

	public ModelMalachite() {
		this(0F);
	}
	
	public ModelMalachite(float modelSize) {
		super(modelSize, 0.0F, 128, 64, false, 1F);

		this.bipedHead = new ModelRenderer(this, 76, 0);
		this.bipedHead.setRotationPoint(0.0F, -2.5F, 0.0F);
		this.bipedHead.addBox(-3.0F, -7.0F, -3.8F, 6, 7, 6, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 40, 35);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, -0.9F);
		this.bipedHeadwear.addBox(-4F, -7.9F, -4F, 8, 9, 8, -.6F);
		this.hair = new ModelRenderer(this, 64, 13);
		this.hair.setRotationPoint(0.0F, -1.7F, -3.8F);
		this.hair.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(hair, 0.7853981633974483F, -1.0927506446736497F, 1.5707963267948966F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}
		
		this.neck = new ModelRenderer(this, 22, 0);
		this.neck.setRotationPoint(0.0F, -1.5F, 0.0F);
		this.neck.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);

		this.bipedLeftArm = new ModelRenderer(this, 30, 0);
		this.bipedLeftArm.setRotationPoint(2.8F, -2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
		this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.5235987755982988F);
		this.bipedRightArm = new ModelRenderer(this, 42, 0);
		this.bipedRightArm.setRotationPoint(-2.8F, -2.0F, 0.0F);
		this.bipedRightArm.addBox(-1.5F, 0.0F, -1.5F, 3, 14, 3, 0.0F);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.5235987755982988F);

		this.bipedBody = new ModelRenderer(this, 0, 0);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, -2.0F, -3.0F, 8, 6, 6, 0.0F);
		this.waist = new ModelRenderer(this, 54, 0);
		this.waist.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.waist.addBox(-3.0F, 0.0F, -2.5F, 6, 10, 5, 0.0F);
		this.abdomen = new ModelRenderer(this, 0, 12);
		this.abdomen.setRotationPoint(0.0F, 7.4F, -2.1F);
		this.abdomen.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 12, 0.0F);
		this.setRotateAngle(abdomen, -0.136659280431156F, 0.0F, 0.0F);

		this.leg = new ModelRenderer(this, 100, 0);
		this.leg.setRotationPoint(2.4F, 4.8F, 2.6F);
		this.leg.addBox(-2.5F, 0.0F, -2.5F, 5, 13, 5, 0.0F);
		this.setRotateAngle(leg, -0.2617993877991494F, 0.0F, -0.5235987755982988F);
		this.leg_1 = new ModelRenderer(this, 40, 17);
		this.leg_1.setRotationPoint(-2.4F, 4.8F, 2.6F);
		this.leg_1.addBox(-2.5F, 0.0F, -2.5F, 5, 13, 5, 0.0F);
		this.setRotateAngle(leg_1, -0.2617993877991494F, 0.0F, 0.5235987755982988F);
		this.leg_2 = new ModelRenderer(this, 0, 32);
		this.leg_2.setRotationPoint(2.4F, 4.0F, 10.0F);
		this.leg_2.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
		this.setRotateAngle(leg_2, 0.5235987755982988F, 0.0F, -0.5235987755982988F);
		this.leg_3 = new ModelRenderer(this, 20, 32);
		this.leg_3.setRotationPoint(-2.4F, 4.0F, 10.0F);
		this.leg_3.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
		this.setRotateAngle(leg_3, 0.5235987755982988F, 0.0F, 0.5235987755982988F);

		this.bipedHead.addChild(this.bipedHeadwear);
		this.bipedHead.addChild(this.hair);
		//this.neck.addChild(this.bipedHead);
		this.bipedBody.addChild(this.neck);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.bipedBody.addChild(this.bipedRightArm);
		this.waist.addChild(this.abdomen);
		this.bipedBody.addChild(this.waist);
		this.abdomen.addChild(this.leg);
		this.abdomen.addChild(this.leg_1);
		this.abdomen.addChild(this.leg_2);
		this.abdomen.addChild(this.leg_3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.bipedHead.render(f5);
		this.bipedBody.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.bipedBody.offsetY = .125F;
		this.bipedHead.offsetY = -.125F;
		boolean onElytra = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

		if (onElytra) {
			this.bipedHead.rotateAngleX = -((float)Math.PI / 4F);
		} else {
			this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		}

		this.bipedBody.rotateAngleY = 0.0F;
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedRightArm.rotationPointX = -5.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointX = 5.0F;
		float f = 1.0F;

		if (onElytra) {
			f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;

		if (this.isRiding) {
			this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
			this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;

		switch (this.leftArmPose) {
			case EMPTY:
				this.bipedLeftArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
				this.bipedLeftArm.rotateAngleY = 0.5235988F;
				break;
			case ITEM:
				this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				this.bipedLeftArm.rotateAngleY = 0.0F;
			case BOW_AND_ARROW:
				break;
			default:
				break;
		}

		switch (this.rightArmPose) {
			case EMPTY:
				this.bipedRightArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
				this.bipedRightArm.rotateAngleY = -0.5235988F;
				break;
			case ITEM:
				this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				this.bipedRightArm.rotateAngleY = 0.0F;
			case BOW_AND_ARROW:
				break;
			default:
				break;
		}

		if (this.swingProgress > 0.0F) {
			EnumHandSide mainHand = this.getMainHand(entityIn);
			ModelRenderer mainHandModel = this.getArmForSide(mainHand);
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (mainHand == EnumHandSide.LEFT) {
				this.bipedBody.rotateAngleY *= -1.0F;
			}

			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			f1 = 1.0F - this.swingProgress;
			f1 = f1 * f1;
			f1 = f1 * f1;
			f1 = 1.0F - f1;
			float f2 = MathHelper.sin(f1 * (float)Math.PI);
			float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			mainHandModel.rotateAngleX = (float)((double)mainHandModel.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			mainHandModel.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			mainHandModel.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}

		if (this.isSneak) {
			this.bipedBody.rotateAngleX = 0.5F;
			this.bipedRightArm.rotateAngleX += 0.4F;
			this.bipedLeftArm.rotateAngleX += 0.4F;
			this.bipedHead.rotationPointY = 1.0F;
		} else {
			this.bipedBody.rotateAngleX = 0.0F;
			this.bipedHead.rotationPointY = 0.0F;
		}

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.25F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F - 0.1F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.25F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F + 0.1F;

		if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
		} else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}

		//copyModelAngles(this.bipedHead, this.bipedHeadwear);

		float degrees45 = ((float)Math.PI / 4F);
		this.leg.rotateAngleZ = -((float)Math.PI / 4F);
		this.leg_1.rotateAngleZ = ((float)Math.PI / 4F);
		//this.spiderLeg3.rotateAngleZ = -0.58119464F;
		//this.spiderLeg4.rotateAngleZ = 0.58119464F;
		//this.spiderLeg5.rotateAngleZ = -0.58119464F;
		//this.spiderLeg6.rotateAngleZ = 0.58119464F;
		this.leg_2.rotateAngleZ = -((float)Math.PI / 4F);
		this.leg_3.rotateAngleZ = ((float)Math.PI / 4F);
		float f1 = -0.0F;
		float f2 = 0.3926991F;
		this.leg.rotateAngleY = ((float)Math.PI / 4F);
		this.leg_1.rotateAngleY = -((float)Math.PI / 4F);
		//this.spiderLeg3.rotateAngleY = 0.3926991F;
		//this.spiderLeg4.rotateAngleY = -0.3926991F;
		//this.spiderLeg5.rotateAngleY = -0.3926991F;
		//this.spiderLeg6.rotateAngleY = 0.3926991F;
		this.leg_2.rotateAngleY = -((float)Math.PI / 4F);
		this.leg_3.rotateAngleY = ((float)Math.PI / 4F);
		float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
		float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
		this.leg.rotateAngleY += f3;
		this.leg_1.rotateAngleY += f3;
		//this.spiderLeg3.rotateAngleY += f4;
		//this.spiderLeg4.rotateAngleY += -f4;
		//this.spiderLeg5.rotateAngleY += f5;
		//this.spiderLeg6.rotateAngleY += -f5;
		this.leg_2.rotateAngleY += f6;
		this.leg_3.rotateAngleY += f6;
		
		this.leg.rotateAngleZ += f7;
		this.leg_1.rotateAngleZ += -f7;
		//this.spiderLeg3.rotateAngleZ += f8;
		//this.spiderLeg4.rotateAngleZ += -f8;
		//this.spiderLeg5.rotateAngleZ += f9;
		//this.spiderLeg6.rotateAngleZ += -f9;
		this.leg_2.rotateAngleZ += f10;
		this.leg_3.rotateAngleZ += -f10;
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}