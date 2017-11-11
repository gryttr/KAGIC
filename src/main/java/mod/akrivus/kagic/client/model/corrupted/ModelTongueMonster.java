package mod.akrivus.kagic.client.model.corrupted;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelTongueMonster extends ModelGem {
	//public ModelRenderer Body;
	//public ModelRenderer leg_1_left;
	//public ModelRenderer leg_1_right;
	public ModelRenderer leg_2_left;
	public ModelRenderer leg_2_right;
	public ModelRenderer foot_left;
	public ModelRenderer foot_right;

	public ModelTongueMonster() {
		super(0F, 0.0F, 64, 64, false, -4F);

		this.bipedBody = new ModelRenderer(this, 0, 0);
		this.bipedBody.setRotationPoint(0.0F, 5.5F, -3.0F);
		this.bipedBody.addBox(-6.0F, -5.0F, -8.0F, 12, 12, 18, 0.0F);
		this.setRotateAngle(bipedBody, 0.12217304763960307F, 0.0F, 0.0F);

		this.bipedLeftLeg = new ModelRenderer(this, 0, 30);
		this.bipedLeftLeg.setRotationPoint(4.2F, 2.0F, -1.5F);
		this.bipedLeftLeg.addBox(0.0F, 0.0F, -2.5F, 5, 9, 8, 0.0F);
		this.setRotateAngle(bipedLeftLeg, 0.7853981633974483F, 0.0F, 0.0F);
		this.leg_2_left = new ModelRenderer(this, 42, 0);
		this.leg_2_left.setRotationPoint(2.4F, 5.8F, -0.3F);
		this.leg_2_left.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(leg_2_left, -1.0471975511965976F, 0.0F, 0.0F);
		this.foot_left = new ModelRenderer(this, -11, 0);
		this.foot_left.setRotationPoint(0.0F, 12.1F, 0.0F);
		this.foot_left.addBox(-3.5F, 0.0F, -5.0F, 7, 0, 11, 0.0F);
		this.setRotateAngle(foot_left, 0.22759093446006054F, -0.005235987755982988F, 0.0F);

		this.bipedRightLeg = new ModelRenderer(this, 26, 30);
		this.bipedRightLeg.setRotationPoint(-4.2F, 2.1F, -1.5F);
		this.bipedRightLeg.addBox(-5.0F, 0.0F, 0.0F, 5, 9, 8, 0.0F);
		this.setRotateAngle(bipedRightLeg, 0.7853981633974483F, 0.0F, 0.0F);
		this.leg_2_right = new ModelRenderer(this, 42, 0);
		this.leg_2_right.setRotationPoint(-2.4F, 5.8F, -0.3F);
		this.leg_2_right.addBox(-2.0F, 0.0F, 0.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(leg_2_right, -1.0471975511965976F, 0.0F, 0.0F);
		this.foot_right = new ModelRenderer(this, -11, 0);
		this.foot_right.setRotationPoint(0.0F, 12.1F, 0.0F);
		this.foot_right.addBox(-3.5F, 0.0F, -5.0F, 7, 0, 11, 0.0F);
		this.setRotateAngle(foot_right, 0.22759093446006054F, 0.0F, 0.0F);

		this.bipedBody.addChild(this.bipedLeftLeg);
		this.bipedBody.addChild(this.bipedRightLeg);
		this.bipedLeftLeg.addChild(this.leg_2_left);
		this.bipedRightLeg.addChild(this.leg_2_right);
		this.leg_2_left.addChild(this.foot_left);
		this.leg_2_right.addChild(this.foot_right);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.bipedBody.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		boolean onElytra = entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getTicksElytraFlying() > 4;

		this.bipedBody.rotateAngleY = 0.0F;
		float f = 1.0F;

		if (onElytra) {
			f = (float)(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount / f + (float)Math.PI / 4;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount / f + (float)Math.PI / 4;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;

		if (this.isRiding) {
			this.bipedRightLeg.rotateAngleX = -1.4137167F;
			this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
			this.bipedRightLeg.rotateAngleZ = 0.07853982F;
			this.bipedLeftLeg.rotateAngleX = -1.4137167F;
			this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
			this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		}

		if (this.swingProgress > 0.0F) {
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;
		}

		if (this.isSneak) {
			this.bipedBody.rotateAngleX = 0.5F;
			this.bipedRightLeg.rotationPointZ = 4.0F;
			this.bipedLeftLeg.rotationPointZ = 4.0F;
			this.bipedRightLeg.rotationPointY = 9.0F;
			this.bipedLeftLeg.rotationPointY = 9.0F;
		} else {
			this.bipedBody.rotateAngleX = 0.0F;
		}
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