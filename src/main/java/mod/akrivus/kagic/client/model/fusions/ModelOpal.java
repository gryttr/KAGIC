package mod.akrivus.kagic.client.model.fusions;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelOpal extends ModelGem {
	//public ModelRenderer body;
	public ModelRenderer neck;
	public ModelRenderer waist;
	//public ModelRenderer shoulder_left;
	//public ModelRenderer shoulder_right;
	public ModelRenderer lower_arm_left;
	public ModelRenderer lower_arm_right;
	//public ModelRenderer head;
	public ModelRenderer nose;
	public ModelRenderer Hair;
	public ModelRenderer headout;
	//public ModelRenderer leg_left_top;
	//public ModelRenderer leg_right_top;
	public ModelRenderer skirt;
	public ModelRenderer left_leg_bottom;
	public ModelRenderer right_leg_bottom;

	public ModelOpal() {
		this(0F);
	}
	
	public ModelOpal(float modelSize) {
		super(modelSize, 0.0F, 128, 64, false, -1.5F);

		this.bipedHead = new ModelRenderer(this, 94, 0);
		this.bipedHead.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.bipedHead.addBox(-3.0F, -9.75F, -3.0F, 6, 8, 6, modelSize);
		this.headout = new ModelRenderer(this, 87, 14);
		this.headout.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.headout.addBox(-3.5F, -10.25F, -3.5F, 7, 9, 7, modelSize);
		this.nose = new ModelRenderer(this, 0, 0);
		this.nose.setRotationPoint(0.0F, -3.25F, -3.0F);
		this.nose.addBox(-0.5F, -2.5F, -2.0F, 1, 1, 2, modelSize);
		this.Hair = new ModelRenderer(this, 0, 13);
		this.Hair.setRotationPoint(0.0F, -10.4F, 0.5F);
		this.Hair.addBox(-5.0F, -1.75F, 0.0F, 10, 30, 7, modelSize);
		this.setRotateAngle(Hair, 0.18203784098300857F, 0.0F, 0.0F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}

		this.neck = new ModelRenderer(this, 22, 0);
		this.neck.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.neck.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, modelSize);
		
		this.bipedBody = new ModelRenderer(this, 0, 0);
		this.bipedBody.setRotationPoint(0.0F, -6.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, -3.0F, -3.0F, 8, 7, 6, modelSize);
		this.waist = new ModelRenderer(this, 28, 0);
		this.waist.setRotationPoint(0.0F, 2.8F, 0.0F);
		this.waist.addBox(-2.5F, 0.0F, -2.0F, 5, 5, 4, modelSize);
		this.skirt = new ModelRenderer(this, 61, 29);
		this.skirt.setRotationPoint(0.0F, 2.7F, 0.0F);
		this.skirt.addBox(-4.5F, 0.0F, -2.5F, 9, 20, 5, modelSize);
		
		this.bipedLeftArm = new ModelRenderer(this, 46, 0);
		this.bipedLeftArm.setRotationPoint(-2.7F, -.75F, 0.0F);
		this.bipedLeftArm.addBox(0.5F, -1.5F, -2.0F, 3, 15, 3, modelSize);
		this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, 0.5235987755982988F);
		this.bipedRightArm = new ModelRenderer(this, 62, 0);
		this.bipedRightArm.setRotationPoint(2.7F, -.75F, 0.0F);
		this.bipedRightArm.addBox(-4.5F, -1.5F, -2.0F, 3, 15, 3, modelSize);
		this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, -0.5235987755982988F);

		this.lower_arm_left = new ModelRenderer(this, 86, 0);
		this.lower_arm_left.setRotationPoint(2.5F, -1.5F, 0.0F);
		this.lower_arm_left.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, modelSize);
		this.setRotateAngle(lower_arm_left, 0.0F, 0.0F, -0.3490658503988659F);
		this.lower_arm_right = new ModelRenderer(this, 78, 0);
		this.lower_arm_right.setRotationPoint(-2.5F, -1.5F, 0.0F);
		this.lower_arm_right.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, modelSize);
		this.setRotateAngle(lower_arm_right, 0.0F, 0.0F, 0.3490658503988659F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 50, 19);
		this.bipedLeftLeg.setRotationPoint(0.0F, 3.4F, 0.5F);
		this.bipedLeftLeg.addBox(0.0F, 0.0F, -2.5F, 4, 11, 4, modelSize);
		this.bipedRightLeg = new ModelRenderer(this, 34, 15);
		this.bipedRightLeg.setRotationPoint(0.0F, 3.5F, 0.5F);
		this.bipedRightLeg.addBox(-4.0F, 0.0F, -2.5F, 4, 11, 4, modelSize);

		this.left_leg_bottom = new ModelRenderer(this, 112, 27);
		this.left_leg_bottom.setRotationPoint(0.6F, 11.0F, -0.6F);
		this.left_leg_bottom.addBox(0.0F, 0.0F, -1.5F, 3, 13, 3, modelSize);
		this.right_leg_bottom = new ModelRenderer(this, 115, 11);
		this.right_leg_bottom.setRotationPoint(-0.7F, 11.0F, -0.6F);
		this.right_leg_bottom.addBox(-3.0F, 0.0F, -1.5F, 3, 13, 3, modelSize);
		
		this.bipedBody.addChild(this.waist);
		this.waist.addChild(this.bipedRightLeg);
		this.bipedHead.addChild(this.nose);
		this.bipedBody.addChild(this.lower_arm_right);
		this.bipedHead.addChild(this.Hair);
		this.bipedBody.addChild(this.bipedLeftArm);
		this.bipedBody.addChild(this.lower_arm_left);
		this.bipedBody.addChild(this.neck);
		this.bipedLeftLeg.addChild(this.left_leg_bottom);
		this.bipedBody.addChild(this.bipedRightArm);
		this.bipedRightLeg.addChild(this.right_leg_bottom);
		this.bipedHead.addChild(this.headout);
		this.waist.addChild(this.bipedLeftLeg);
		this.waist.addChild(this.skirt);
		//this.neck.addChild(this.bipedHead);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.bipedHead.render(f5);
		this.bipedBody.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		//super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		boolean onElytra = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

		if (onElytra)
		{
			this.bipedHead.rotateAngleX = -((float)Math.PI / 4F);
		}
		else
		{
			this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
			this.Hair.rotateAngleX = - this.bipedHead.rotateAngleX + .125F;
		}

		
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedRightArm.rotationPointX = -2.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointX = 3.0F;
		float f = 1.0F;

		if (onElytra)
		{
			f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F)
		{
			f = 1.0F;
		}

		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.lower_arm_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.lower_arm_right.rotateAngleZ = 1.0F;
		
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.lower_arm_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
		this.lower_arm_left.rotateAngleZ = -1.0F;
		
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount / f;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.5F * limbSwingAmount / f;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;

		if (this.isRiding)
		{
			this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
			this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
			this.bipedRightLeg.rotateAngleX = -1.4137167F;
			this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
			this.bipedRightLeg.rotateAngleZ = 0.07853982F;
			this.bipedLeftLeg.rotateAngleX = -1.4137167F;
			this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
			this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;

		switch (this.leftArmPose)
		{
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

		switch (this.rightArmPose)
		{
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

		if (this.swingProgress > 0.0F)
		{
			EnumHandSide mainHand = this.getMainHand(entityIn);
			ModelRenderer mainArm = this.getArmForSide(mainHand);
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			if (mainHand == EnumHandSide.LEFT)
			{
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
			mainArm.rotateAngleX = (float)((double)mainArm.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
			mainArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			mainArm.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
		}

		if (this.isSneak)
		{
			this.bipedBody.rotateAngleX = 0.5F;
			this.bipedRightArm.rotateAngleX += 0.4F;
			this.bipedLeftArm.rotateAngleX += 0.4F;
			this.bipedRightLeg.rotationPointZ = 4.0F;
			this.bipedLeftLeg.rotationPointZ = 4.0F;
			this.bipedRightLeg.rotationPointY = 9.0F;
			this.bipedLeftLeg.rotationPointY = 9.0F;
			this.bipedHead.rotationPointY = 1.0F;
		}
		else
		{
			this.bipedBody.rotateAngleX = 0.0F;
			/*this.bipedRightLeg.rotationPointZ = 0.1F;
			this.bipedLeftLeg.rotationPointZ = 0.1F;
			this.bipedRightLeg.rotationPointY = 12.0F;
			this.bipedLeftLeg.rotationPointY = 12.0F;*/
			this.bipedHead.rotationPointY = -9.0F;
		}

		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.6F;
		this.lower_arm_right.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.lower_arm_right.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.55F;
		
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.6F;
		this.lower_arm_left.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.lower_arm_left.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.55F;

		if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
		{
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}
		else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
		{
			this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
			this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
		}

		copyModelAngles(this.bipedHead, this.bipedHeadwear);
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwingAmount, float ageInTicks, float partialTickTime) {
		this.rightArmPose = ModelBiped.ArmPose.EMPTY;
		this.leftArmPose = ModelBiped.ArmPose.EMPTY;
		
		ItemStack mainItem = entity.getHeldItem(EnumHand.MAIN_HAND);
		if (mainItem != null && mainItem.getItem() == Items.BOW && ((EntityGem) entity).isSwingingArms()) {
			if (entity.getPrimaryHand() == EnumHandSide.RIGHT) {
				this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			}
			else {
				this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			}
		}
		super.setLivingAnimations(entity, limbSwingAmount, ageInTicks, partialTickTime);
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
