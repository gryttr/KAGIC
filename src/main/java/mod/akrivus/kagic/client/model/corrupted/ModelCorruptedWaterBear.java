package mod.akrivus.kagic.client.model.corrupted;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCorruptedWaterBear extends ModelGem {
	public ModelRenderer body_inside;
	public ModelRenderer head_inside;
	public ModelRenderer tail_inside;
	public ModelRenderer body_outside;
	public ModelRenderer leg_in1l;
	public ModelRenderer leg_in1r;
	public ModelRenderer leg_in2l;
	public ModelRenderer leg_in2r;
	public ModelRenderer leg_in3l;
	public ModelRenderer leg_in3r;
	public ModelRenderer head_outside;
	public ModelRenderer tail_outside;
	public ModelRenderer leg_out1l;
	public ModelRenderer leg_out1r;
	public ModelRenderer leg_out2l;
	public ModelRenderer leg_out2r;
	public ModelRenderer leg_out3l;
	public ModelRenderer leg_out3r;

	public ModelCorruptedWaterBear() {
		super(0F, 0.0F, 128, 128, false, -4F);
		this.leg_in1r = new ModelRenderer(this, 28, 32);
		this.leg_in1r.setRotationPoint(-6.5F, 4.1F, 2.5F);
		this.leg_in1r.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in1r, 0.0F, 0.0F, 0.7853981633974483F);
		this.head_inside = new ModelRenderer(this, 64, 0);
		this.head_inside.setRotationPoint(0.0F, 0.0F, -8.8F);
		this.head_inside.addBox(-6.0F, -6.0F, -10.0F, 12, 12, 14, 0.0F);
		this.setRotateAngle(head_inside, 0.27314402793711257F, 0.0F, 0.0F);
		this.head_outside = new ModelRenderer(this, 57, 75);
		this.head_outside.setRotationPoint(0.0F, 0.0F, -8.9F);
		this.head_outside.addBox(-7.0F, -8.0F, -12.0F, 14, 16, 15, 0.0F);
		this.setRotateAngle(head_outside, 0.27314402793711257F, 0.0F, 0.0F);
		this.tail_inside = new ModelRenderer(this, 50, 26);
		this.tail_inside.setRotationPoint(0.0F, 0.0F, 12.0F);
		this.tail_inside.addBox(-6.0F, -6.0F, -6.0F, 12, 14, 14, 0.0F);
		this.setRotateAngle(tail_inside, -0.136659280431156F, 0.0F, 0.0F);
		this.leg_in1l = new ModelRenderer(this, 28, 32);
		this.leg_in1l.setRotationPoint(6.5F, 4.1F, 2.5F);
		this.leg_in1l.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in1l, 0.0F, 0.0F, -0.7853981633974483F);
		this.leg_out2r = new ModelRenderer(this, 0, 32);
		this.leg_out2r.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out2r.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.body_outside = new ModelRenderer(this, 0, 54);
		this.body_outside.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body_outside.addBox(-9.0F, -9.0F, -9.0F, 18, 18, 18, 0.0F);
		this.leg_out1l = new ModelRenderer(this, 0, 32);
		this.leg_out1l.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out1l.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.leg_out3r = new ModelRenderer(this, 0, 32);
		this.leg_out3r.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out3r.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.leg_in3r = new ModelRenderer(this, 28, 32);
		this.leg_in3r.setRotationPoint(-6.6F, 4.3F, -8.3F);
		this.leg_in3r.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in3r, -0.5235987755982988F, 0.0F, 0.5235987755982988F);
		this.leg_out2l = new ModelRenderer(this, 0, 32);
		this.leg_out2l.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out2l.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.tail_outside = new ModelRenderer(this, 0, 90);
		this.tail_outside.setRotationPoint(0.0F, 0.0F, 12.0F);
		this.tail_outside.addBox(-7.0F, -8.0F, -6.0F, 14, 16, 17, 0.0F);
		this.setRotateAngle(tail_outside, -0.136659280431156F, 0.0F, 0.0F);
		this.leg_out1r = new ModelRenderer(this, 0, 32);
		this.leg_out1r.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out1r.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.leg_in3l = new ModelRenderer(this, 28, 32);
		this.leg_in3l.setRotationPoint(6.6F, 4.3F, -8.3F);
		this.leg_in3l.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in3l, -0.5235987755982988F, 0.0F, -0.5235987755982988F);
		this.leg_in2l = new ModelRenderer(this, 28, 32);
		this.leg_in2l.setRotationPoint(4.5F, 4.1F, 14.0F);
		this.leg_in2l.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in2l, 0.5235987755982988F, 0.0F, -0.7853981633974483F);
		this.leg_out3l = new ModelRenderer(this, 0, 32);
		this.leg_out3l.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leg_out3l.addBox(-3.5F, -1.0F, -3.5F, 7, 12, 7, 0.0F);
		this.body_inside = new ModelRenderer(this, 0, 0);
		this.body_inside.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.body_inside.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
		this.leg_in2r = new ModelRenderer(this, 28, 32);
		this.leg_in2r.setRotationPoint(-4.5F, 4.1F, 14.0F);
		this.leg_in2r.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leg_in2r, 0.5235987755982988F, 0.0F, 0.7853981633974483F);
		
		this.body_inside.addChild(this.leg_in1r);
		this.body_inside.addChild(this.head_inside);
		this.body_outside.addChild(this.head_outside);
		this.body_inside.addChild(this.tail_inside);
		this.body_inside.addChild(this.leg_in1l);
		this.leg_in2r.addChild(this.leg_out2r);
		this.body_inside.addChild(this.body_outside);
		this.leg_in1l.addChild(this.leg_out1l);
		this.leg_in3r.addChild(this.leg_out3r);
		this.body_inside.addChild(this.leg_in3r);
		this.leg_in2l.addChild(this.leg_out2l);
		this.body_outside.addChild(this.tail_outside);
		this.leg_in1r.addChild(this.leg_out1r);
		this.body_inside.addChild(this.leg_in3l);
		this.body_inside.addChild(this.leg_in2l);
		this.leg_in3l.addChild(this.leg_out3l);
		this.body_inside.addChild(this.leg_in2r);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.body_inside.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		//this.spiderHead.rotateAngleY = netHeadYaw * 0.017453292F;
		//this.spiderHead.rotateAngleX = headPitch * 0.017453292F;
		float f = ((float)Math.PI / 4F);
		this.leg_in1l.rotateAngleZ = -((float)Math.PI / 4F);
		this.leg_in1r.rotateAngleZ = ((float)Math.PI / 4F);
		this.leg_in2l.rotateAngleZ = -0.58119464F;
		this.leg_in2r.rotateAngleZ = 0.58119464F;
		this.leg_in3l.rotateAngleZ = -0.58119464F;
		this.leg_in3r.rotateAngleZ = 0.58119464F;
		//this.spiderLeg7.rotateAngleZ = -((float)Math.PI / 4F);
		//this.spiderLeg8.rotateAngleZ = ((float)Math.PI / 4F);
		float f1 = -0.0F;
		float f2 = 0.3926991F;
		this.leg_in1l.rotateAngleY = ((float)Math.PI / 4F);
		this.leg_in1r.rotateAngleY = -((float)Math.PI / 4F);
		this.leg_in2l.rotateAngleY = 0.3926991F;
		this.leg_in2r.rotateAngleY = -0.3926991F;
		this.leg_in3l.rotateAngleY = -0.3926991F;
		this.leg_in3r.rotateAngleY = 0.3926991F;
		//this.spiderLeg7.rotateAngleY = -((float)Math.PI / 4F);
		//this.spiderLeg8.rotateAngleY = ((float)Math.PI / 4F);
		float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
		float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float)Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
		float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
		float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float)Math.PI) * 0.4F) * limbSwingAmount;
		float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * limbSwingAmount;
		float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float)Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
		this.leg_in1l.rotateAngleY += f3;
		this.leg_in1r.rotateAngleY += -f3;
		this.leg_in2l.rotateAngleY += f4;
		this.leg_in2r.rotateAngleY += -f4;
		this.leg_in3l.rotateAngleY += f5;
		this.leg_in3r.rotateAngleY += -f5;
		//this.spiderLeg7.rotateAngleY += f6;
		//this.spiderLeg8.rotateAngleY += -f6;
		this.leg_in1l.rotateAngleZ += f7;
		this.leg_in1r.rotateAngleZ += -f7;
		this.leg_in2l.rotateAngleZ += f8;
		this.leg_in2r.rotateAngleZ += -f8;
		this.leg_in3l.rotateAngleZ += f9;
		this.leg_in3r.rotateAngleZ += -f9;
		//this.spiderLeg7.rotateAngleZ += f10;
		//this.spiderLeg8.rotateAngleZ += -f10;
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