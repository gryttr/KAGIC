package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBismuth extends ModelGem {
	public ModelBismuth() {
		super(0.0F, 0.0F, 64, 64, false, -4F);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}
		
	    // Hair.
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4F, -12F, -4F, 8, 8, 8, 1.1F);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Body.
		this.bipedBody = new ModelRenderer(this, 0, 16);
		this.bipedBody.addBox(-8F, -4F, -3F, 16, 16, 6);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		// Right arm.
		this.bipedRightArm = new ModelRenderer(this, 16, 38);
		this.bipedRightArm.addBox(-7F, -4F, -2F, 4, 14, 4);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
	    // Left arm.
		this.bipedLeftArm = new ModelRenderer(this, 0, 38);
		this.bipedLeftArm.addBox(3F, -4F, -2F, 4, 14, 4);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
	    // Right leg.
		this.bipedRightLeg = new ModelRenderer(this, 48, 38);
		this.bipedRightLeg.addBox(-7F, 0F, -2F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(0F, 12F, 0F);
	  	// Left leg.
		this.bipedLeftLeg = new ModelRenderer(this, 32, 38);
		this.bipedLeftLeg.addBox(3F, 0F, -2F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(0F, 12F, 0F);
	}
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}
