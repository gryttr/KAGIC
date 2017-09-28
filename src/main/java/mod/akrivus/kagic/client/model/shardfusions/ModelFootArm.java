package mod.akrivus.kagic.client.model.shardfusions;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFootArm extends ModelBiped {

	public ModelRenderer rightFoot;
	
	public ModelFootArm() {
		super(0.0F, 0.0F, 64, 64);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-3.5F, -9.0F, -3.5F, 7, 9, 7);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		this.bipedRightArm = new ModelRenderer(this, 0, 32);
		this.bipedRightArm.addBox(-5.0F, -2.0F, -2.0F, 6, 4, 4);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		this.rightFoot = new ModelRenderer(this, 20, 32);
		this.rightFoot.addBox(-2.0F, -12.0F, -4.0F, 4, 12, 8);
		this.rightFoot.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-4.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-0.0F, 0.0F, -2.0F, 4, 12, 4);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

		this.bipedRightArm.addChild(this.rightFoot);
	}
}
