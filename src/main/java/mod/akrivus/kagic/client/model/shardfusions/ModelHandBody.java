package mod.akrivus.kagic.client.model.shardfusions;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelHandBody extends ModelBiped {
	public ModelRenderer secondRightArm;
	
	public ModelHandBody() {
		super(0.0F, 0.0F, 64, 64);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -7.0F, -4.0F, 8, 7, 8);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 17, 4);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		this.secondRightArm = new ModelRenderer(this, 40, 16);
		this.secondRightArm.addBox(-2.0F, -4.0F, -2.0F, 4, 15, 4);
		this.secondRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 13, 4);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

		this.bipedRightArm.addChild(this.secondRightArm);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;

		this.secondRightArm.rotationPointZ = 0.0F;
		this.secondRightArm.rotationPointX = -5.0F;

		float f = 1.0F;

		if (flag)
		{
			f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F)
		{
			f = 1.0F;
		}

		this.secondRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.secondRightArm.rotateAngleZ = 0.0F;

		if (this.isRiding)
		{
			this.secondRightArm.rotateAngleX += -((float)Math.PI / 5F);
		}

		this.secondRightArm.rotateAngleY = 0.0F;
		this.secondRightArm.rotateAngleZ = 0.0F;

		switch (this.rightArmPose)
		{
			case EMPTY:
				this.secondRightArm.rotateAngleY = 0.0F;
				break;
			case BLOCK:
				this.secondRightArm.rotateAngleX = this.secondRightArm.rotateAngleX * 0.5F - 0.9424779F;
				this.secondRightArm.rotateAngleY = -0.5235988F;
				break;
			case ITEM:
				this.secondRightArm.rotateAngleX = this.secondRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
				this.secondRightArm.rotateAngleY = 0.0F;
			case BOW_AND_ARROW:
				this.secondRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
				this.secondRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}

		if (this.swingProgress > 0.0F)
		{
			this.secondRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.secondRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.secondRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
		}

		this.secondRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.secondRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
