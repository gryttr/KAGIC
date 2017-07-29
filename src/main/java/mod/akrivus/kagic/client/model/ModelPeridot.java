package mod.akrivus.kagic.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPeridot extends ModelBiped {
	public ModelPeridot() {
		super(0.0F, 0.0F, 64, 64);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -2F, -4F, 2, 2, 2);
	    this.bipedHead.setRotationPoint(0F, 0F, 0F);
		// Hair.
		this.bipedHeadwear = new ModelRenderer(this, 0, 0);
		this.bipedHeadwear.addBox(-6F, -6F, -4F, 12, 12, 8);
	    this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
	    // Body.
	    this.bipedBody = new ModelRenderer(this, 8, 20);
	    this.bipedBody.addBox(-3F, 8F, -2F, 6, 8, 4);
	    this.bipedBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
	    this.bipedRightArm = new ModelRenderer(this, 28, 20);
	    this.bipedRightArm.addBox(0F, 0F, -1F, 2, 8, 2);
	    this.bipedRightArm.setRotationPoint(0F, 8F, 0F);
	    // Left arm.
	    this.bipedLeftArm = new ModelRenderer(this, 36, 20);
	    this.bipedLeftArm.addBox(-2F, 0F, -1F, 2, 8, 2);
	    this.bipedLeftArm.setRotationPoint(0F, 8F, 0F);
	    // Right leg.
	    this.bipedRightLeg = new ModelRenderer(this, 0, 20);
	    this.bipedRightLeg.addBox(-3F, 4F, -1F, 2, 8, 2);
	    this.bipedRightLeg.setRotationPoint(0F, 8F, 0F);
	    // Left leg.
	    this.bipedLeftLeg = new ModelRenderer(this, 0, 20);
	    this.bipedLeftLeg.addBox(1F, 4F, -1F, 2, 8, 2);
	    this.bipedLeftLeg.setRotationPoint(0F, 8F, 0F);
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
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleY * 0.6875F;
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleX * -1;
		this.bipedHeadwear.rotateAngleZ = 0.7853982F;
	}
}