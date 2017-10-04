package mod.akrivus.kagic.client.model.shardfusions;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMouthTorso extends ModelBiped {
	
	public ModelMouthTorso() {
		super(0.0F, 0.0F, 64, 64);
		this.bipedBody = new ModelRenderer(this, 0, 0);
		this.bipedBody.addBox(-6.0F, 0.0F, -3.0F, 12, 12, 6);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedRightArm = new ModelRenderer(this, 16, 18);
		this.bipedRightArm.addBox(-5.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		this.bipedLeftArm = new ModelRenderer(this, 16, 18);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(1.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		this.bipedRightLeg = new ModelRenderer(this, 0, 18);
		this.bipedRightLeg.addBox(-4.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 0, 18);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(0.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
	}
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
}
