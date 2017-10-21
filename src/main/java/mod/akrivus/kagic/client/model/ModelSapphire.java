package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSapphire extends ModelGem {
	private ModelRenderer bipedSkirt;

    public ModelSapphire() {
		super(0.0F, 0.0F, 64, 64, false, 7F);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
	    this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}
		
	    // Hair.
	    this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8, 1.1F);
	    this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
	    // Body.
	    this.bipedBody = new ModelRenderer(this, 8, 16);
	    this.bipedBody.addBox(-3.0F, 8.0F, -2.0F, 6, 8, 4);
	    this.bipedBody.setRotationPoint(0F, 0F, 0F);
	    // Skirt.
	    this.bipedSkirt = new ModelRenderer(this, 0, 28);
	    this.bipedSkirt.addBox(-5.0F, 15.0F, -5.0F, 10, 8, 10);
	    this.bipedSkirt.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
	    this.bipedRightArm = new ModelRenderer(this, 28, 16);
	    this.bipedRightArm.addBox(0F, 0F, -1F, 2, 6, 2);
	    this.bipedRightArm.setRotationPoint(0F, 8F, 0F);
	    // Left arm.
	    this.bipedLeftArm = new ModelRenderer(this, 36, 16);
	    this.bipedLeftArm.addBox(-2F, 0F, -1F, 2, 6, 2);
	    this.bipedLeftArm.setRotationPoint(0F, 8F, 0F);
	    // Right leg.
	    this.bipedRightLeg = new ModelRenderer(this, 0, 16);
	    this.bipedRightLeg.addBox(-3F, 8F, -1F, 2, 4, 2);
	    this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
	    // Left leg.
	    this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
	    this.bipedLeftLeg.addBox(1F, 8F, -1F, 2, 4, 2);
	    this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
    }
    
    @Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedSkirt.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
    
    @Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
	    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	    super.copyModelAngles(this.bipedBody, this.bipedSkirt);
	}
}