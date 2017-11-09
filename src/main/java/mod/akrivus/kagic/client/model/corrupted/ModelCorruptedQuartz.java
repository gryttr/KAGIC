package mod.akrivus.kagic.client.model.corrupted;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelCorruptedQuartz extends ModelQuadruped {
	//public ModelRenderer body1;
	public ModelRenderer body2;
	//public ModelRenderer front_leg_left;
	//public ModelRenderer front_leg_right;
	//public ModelRenderer back_leg_top_left;
	//public ModelRenderer back_leg_top_right;
	public ModelRenderer tail;
	public ModelRenderer back_leg_bottom;
	public ModelRenderer back_leg_bottom_1;
	public ModelRenderer head_extra;
	public ModelRenderer head_buns;

	public ModelCorruptedQuartz() {
		super(0, 0F);
		this.textureWidth = 96;
		this.textureHeight = 96;
		
		this.head = new ModelRenderer(this, 52, 39);
		this.head.setRotationPoint(0.3F, -0.5F, -8.8F);
		this.head.addBox(-5.0F, -3.0F, -6.0F, 10, 10, 9, 0.0F);
		this.setRotateAngle(head, 0.31869712141416456F, 0.0F, 0.0F);
		this.head_buns = new ModelRenderer(this, 52, 71);
		this.head_buns.setRotationPoint(0.0F, 0.6F, -2.0F);
		this.head_buns.addBox(-9.0F, -2.0F, -2.0F, 18, 4, 4, 0.0F);
		this.head_extra = new ModelRenderer(this, 74, 15);
		this.head_extra.setRotationPoint(0.0F, -3.0F, -6.5F);
		this.head_extra.addBox(-5.0F, -2.0F, 0.0F, 10, 7, 0, 0.0F);
		this.setRotateAngle(head_extra, 0.136659280431156F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 5.5F, 0.0F);
		this.body.addBox(-6.0F, -6.0F, -7.5F, 12, 14, 15, 0.0F);
		this.setRotateAngle(body, -0.12217304763960307F, 0.0F, 0.0F);
		this.body2 = new ModelRenderer(this, 40, 15);
		this.body2.setRotationPoint(0.0F, 0.0F, 5.0F);
		this.body2.addBox(-5.0F, -5.0F, 0.0F, 10, 10, 14, 0.0F);
		this.setRotateAngle(body2, 0.0F, 0.017453292519943295F, 0.0F);
		this.tail = new ModelRenderer(this, 0, 58);
		this.tail.setRotationPoint(0.0F, 0.0F, 10.0F);
		this.tail.addBox(-2.0F, -1.0F, 0.0F, 4, 4, 14, 0.0F);
		this.setRotateAngle(tail, 0.4553564018453205F, 0.017453292519943295F, 0.0F);

		this.leg1 = new ModelRenderer(this, 26, 39);
		this.leg1.setRotationPoint(6.0F, -4.0F, -1.4F);
		this.leg1.addBox(0.0F, 0.0F, -2.5F, 7, 23, 6, 0.0F);
		this.leg2 = new ModelRenderer(this, 0, 29);
		this.leg2.mirror = true;
		this.leg2.setRotationPoint(-6.0F, -4.0F, -1.4F);
		this.leg2.addBox(-7.0F, 0.0F, -2.5F, 7, 23, 6, 0.0F);
		this.leg3 = new ModelRenderer(this, 39, 2);
		this.leg3.setRotationPoint(5.0F, 0.8F, 10.0F);
		this.leg3.addBox(0.0F, 0.0F, 0.0F, 4, 6, 7, 0.0F);
		this.setRotateAngle(leg3, -0.7853981633974483F, 0.0F, 0.0F);
		this.leg4 = new ModelRenderer(this, 61, 2);
		this.leg4.setRotationPoint(-5.0F, 0.8F, 10.0F);
		this.leg4.addBox(-4.0F, 0.0F, 0.0F, 4, 6, 7, 0.0F);
		this.setRotateAngle(leg4, -0.7853981633974483F, 0.0F, 0.0F);

		this.back_leg_bottom_1 = new ModelRenderer(this, 68, 58);
		this.back_leg_bottom_1.setRotationPoint(0.0F, -0.1F, 8.0F);
		this.back_leg_bottom_1.addBox(-3.6F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
		this.setRotateAngle(back_leg_bottom_1, -0.7853981633974483F, 0.0F, 0.0F);
		this.back_leg_bottom = new ModelRenderer(this, 42, 58);
		this.back_leg_bottom.setRotationPoint(0.0F, -0.1F, 8.0F);
		this.back_leg_bottom.addBox(0.6F, 0.0F, 0.0F, 3, 3, 10, 0.0F);
		this.setRotateAngle(back_leg_bottom, -0.7853981633974483F, 0.0F, 0.0F);
		
		this.head.addChild(this.head_extra);
		this.head.addChild(this.head_buns);
		this.body.addChild(this.head);
		this.body.addChild(this.body2);
		this.body.addChild(this.leg1);
		this.body.addChild(this.leg2);
		this.body2.addChild(this.leg3);
		this.body2.addChild(this.leg4);
		this.body2.addChild(this.tail);
		this.leg3.addChild(this.back_leg_bottom);
		this.leg4.addChild(this.back_leg_bottom_1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.body.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * .5F * limbSwingAmount;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * .5F * limbSwingAmount;
		this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * .5F * limbSwingAmount - (float)Math.PI / 4;
		this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * .5F * limbSwingAmount - (float)Math.PI / 4;
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