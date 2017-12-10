package mod.akrivus.kagic.client.model.corrupted;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCorruptedWatermelonTourmaline extends ModelGem {
	public ModelRenderer spike2;
	public ModelRenderer spike1;
	public ModelRenderer spike3;
	public ModelRenderer spike4;
	public ModelRenderer spike5;

	public ModelCorruptedWatermelonTourmaline() {
		super(0F, 0F, 64, 64, false, 0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.bipedHead.addBox(-8.0F, 0.0F, -8.0F, 16, 16, 16, 0.0F);

		this.spike1 = new ModelRenderer(this, 0, 32);
		this.spike1.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.spike1.addBox(-10.0F, -2.0F, 0.0F, 20, 20, 0, 0.0F);
		
		this.spike2 = new ModelRenderer(this, 0, 32);
		this.spike2.mirror = true;
		this.spike2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.spike2.addBox(-10.0F, -2.0F, 0.0F, 20, 20, 0, 0.0F);
		
		this.spike3 = new ModelRenderer(this, 0, 32);
		this.spike3.mirror = true;
		this.spike3.setRotationPoint(0.0F, 0.0F, 6.0F);
		this.spike3.addBox(-10.0F, -2.0F, 0.0F, 20, 20, 0, 0.0F);
		
		this.spike4 = new ModelRenderer(this, 0, 32);
		this.spike4.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.spike4.addBox(-10.0F, -2.0F, 0.0F, 20, 20, 0, 0.0F);
		
		this.spike5 = new ModelRenderer(this, 0, 32);
		this.spike5.mirror = true;
		this.spike5.setRotationPoint(0.0F, 0.0F, -6.0F);
		this.spike5.addBox(-10.0F, -2.0F, 0.0F, 20, 20, 0, 0.0F);
		
		this.bipedHead.addChild(spike1);
		this.bipedHead.addChild(spike2);
		this.bipedHead.addChild(spike3);
		this.bipedHead.addChild(spike4);
		this.bipedHead.addChild(spike5);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.bipedHead.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
