package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAgate extends ModelQuartz {
	private ModelRenderer bipedSideBuns = null;
	private ModelRenderer bipedTopBun = null;
	private ModelRenderer bipedBackBun = null;

    public ModelAgate() {
    	this(0F, false);
    }
    
    public ModelAgate(float modelSize, boolean isArmor) {
		super(modelSize, isArmor);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		if (!isArmor) {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize);
		} else {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize + 0.5F);
		}
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (!isArmor && (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas())) {
			this.bipedHead.addChild(this.witchHat);
		}
		
	    // Hair.
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4F, -12F, -4F, 8, 8, 8, 1.1F);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Buns.
		if (!isArmor) {
			this.bipedSideBuns = new ModelRenderer(this, 0, 56);
			this.bipedSideBuns.addBox(-9F, -10F, -2F, 18, 4, 4);
			this.bipedSideBuns.setRotationPoint(0F, 0F, 0F);
			// Buns.
			this.bipedTopBun = new ModelRenderer(this, 48, 54);
			this.bipedTopBun.addBox(-2F, -17F, -2F, 4, 4, 4);
			this.bipedTopBun.setRotationPoint(0F, 0F, 0F);
			// Buns.
			this.bipedBackBun = new ModelRenderer(this, 16, 38);
			this.bipedBackBun.addBox(-2F, -10F, 5F, 4, 4, 4);
			this.bipedBackBun.setRotationPoint(0F, 0F, 0F);
		}
		// Body.
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-5F, -4F, -3F, 10, 16, 6);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
		this.bipedRightArm = new ModelRenderer(this, 48, 16);
		this.bipedRightArm.addBox(-4F, -4F, -2F, 4, 14, 4);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
	    // Left arm.
		this.bipedLeftArm = new ModelRenderer(this, 48, 34);
		this.bipedLeftArm.addBox(0F, -4F, -2F, 4, 14, 4);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
	    // Right leg.
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(1F, 0F, -2F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
	  	// Left leg.
		this.bipedLeftLeg = new ModelRenderer(this, 0, 32);
		this.bipedLeftLeg.addBox(-5F, 0F, -2F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
    }
    
    @Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		if (this.bipedTopBun != null) {
			this.bipedSideBuns.render(scale);
			this.bipedTopBun.render(scale);
			this.bipedBackBun.render(scale);
		}
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
    
    @Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		if (this.bipedTopBun != null) {
			super.copyModelAngles(this.bipedHead, this.bipedSideBuns);
			super.copyModelAngles(this.bipedHead, this.bipedTopBun);
			super.copyModelAngles(this.bipedHead, this.bipedBackBun);
		}
	}
}
