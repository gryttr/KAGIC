package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTopaz extends ModelGem {
	public ModelTopaz() {
		this(0F, false);
	}
	public ModelTopaz(float modelSize, boolean isArmor) {
		super(0.0F, 0.0F, 64, isArmor ? 32 : 64, isArmor, -3.5F);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		if (!isArmor) {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 10, 8, modelSize);
		} else {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize);
		}
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (!isArmor &&(KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas())) {
			this.bipedHead.addChild(this.witchHat);
		}
		
		if (!isArmor) {
		    // Hair.
			this.bipedHeadwear = new ModelRenderer(this, 32, 0);
			this.bipedHeadwear.addBox(-4F, -12F, -4F, 8, 10, 8, 1.1F);
			this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		} else {
			this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		}
		// Body.
		this.bipedBody = new ModelRenderer(this, 0, 18);
		this.bipedBody.addBox(-8F, -4F, -6F, 16, 16, 12, modelSize);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
		this.bipedRightArm = new ModelRenderer(this, 16, 46);
		this.bipedRightArm.addBox(-7F, -4F, -2F, 4, 14, 4, modelSize);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
	    // Left arm.
		this.bipedLeftArm = new ModelRenderer(this, 0, 46);
		this.bipedLeftArm.addBox(3F, -4F, -2F, 4, 14, 4, modelSize);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
	    // Right leg.
		this.bipedRightLeg = new ModelRenderer(this, 32, 46);
		this.bipedRightLeg.addBox(-7F, 0F, -2F, 4, 12, 4, modelSize);
		this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
	  	// Left leg.
		this.bipedLeftLeg = new ModelRenderer(this, 48, 46);
		this.bipedLeftLeg.addBox(3F, 0F, -2F, 4, 12, 4, modelSize);
		this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
	}
	
	@Override
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
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		if (entityIn instanceof EntityTopaz) {
			EntityTopaz topaz = (EntityTopaz) entityIn;
			if (topaz.isHolding()) {
				this.bipedLeftArm.rotationPointY = -4F;
				this.bipedLeftArm.rotateAngleX = 3.15F;
				//this.bipedLeftArm.rotateAngleZ = 0.15F;
				this.bipedRightArm.rotationPointY = -4F;
				this.bipedRightArm.rotateAngleX = 3.15F;
				//this.bipedRightArm.rotateAngleZ = -0.15F;
			}
			else {
				this.bipedLeftArm.rotationPointY = 0F;
				this.bipedRightArm.rotationPointY = 0F;
			}
		}
	}
}
