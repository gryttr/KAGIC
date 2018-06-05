package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTwinRutile extends ModelGem {
	public ModelRenderer bipedOtherHead;
	public ModelRenderer bipedOtherHeadwear;
	public ModelRenderer bipedTorso1;
	public ModelRenderer bipedTorso2;
	public ModelTwinRutile() {
		super(0.0F, 0.0F, 64, 64, false, -.5F);

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-8F, -4F, -4F, 8, 10, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
        if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}
        
        this.bipedOtherHead = new ModelRenderer(this, 0, 0);
		this.bipedOtherHead.addBox(0F, -4F, -4F, 8, 10, 8);
		this.bipedOtherHead.setRotationPoint(0F, 0F, 0F);
        
		this.bipedRightArm = new ModelRenderer(this, 0, 18);
		this.bipedRightArm.addBox(-2F, 2F, -1F, 2, 12, 2);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
		
		this.bipedLeftArm = new ModelRenderer(this, 0, 18);
		this.bipedLeftArm.addBox(0F, 2F, -1F, 2, 12, 2);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
        
		this.bipedRightLeg = new ModelRenderer(this, 28, 18);
		this.bipedRightLeg.addBox(1F, 0F, -1F, 2, 12, 2);
		this.bipedRightLeg.setRotationPoint(0F, 12F, 0F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 28, 18);
		this.bipedLeftLeg.addBox(-3F, 0F, -1F, 2, 12, 2);
		this.bipedLeftLeg.setRotationPoint(0F, 12F, 0F);
        
        this.bipedTorso1 = new ModelRenderer(this, 9, 18);
        this.bipedTorso1.mirror = true;
        this.bipedTorso1.addBox(-5.0F, 6.0F, -2.0F, 5, 10, 4, 0.0F);
        this.bipedTorso1.setRotationPoint(-1.3F, -4.2F, 0.0F);
        this.bipedTorso1.addChild(this.bipedHead);
        
        this.bipedTorso2 = new ModelRenderer(this, 9, 18);
        this.bipedTorso2.addBox(0.0F, 6.0F, -2.0F, 5, 10, 4, 0.0F);
        this.bipedTorso2.setRotationPoint(1.3F, -4.2F, 0.0F);
        this.bipedTorso2.addChild(this.bipedOtherHead);
        
        this.bipedBody = new ModelRenderer(this, 8, 28);
        this.bipedBody.addBox(-3.0F, 4.0F, -2.0F, 6, 2, 4);
        this.bipedBody.setRotationPoint(0.0F, 6.0F, 0.0F);
	}
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedOtherHead.rotateAngleX = headPitch * 0.017453292F;
		this.bipedOtherHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.bipedTorso1.rotateAngleZ = -0.1F;
		this.bipedTorso2.rotateAngleZ = 0.1F;
		//this.bipedHead.render(scale);
		//this.bipedOtherHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedTorso1.render(scale);
		this.bipedTorso2.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
}
