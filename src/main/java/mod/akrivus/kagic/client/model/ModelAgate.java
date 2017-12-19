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
