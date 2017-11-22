package mod.akrivus.kagic.client.model.corrupted;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelMoissanite extends ModelGem {
	public ModelRenderer body;
	public ModelRenderer orbit;
	public ModelRenderer bottom1;
	public ModelRenderer top1;
	public ModelRenderer gem;
	public ModelRenderer gem_1;
	public ModelRenderer bottom2;
	public ModelRenderer top2;
	public ModelRenderer top3;

	public ModelMoissanite() {
		super(0F, 0.0F, 64, 64, false, -4F);
		this.bottom1 = new ModelRenderer(this, 32, 0);
		this.bottom1.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.bottom1.addBox(-3.0F, -2.0F, -3.0F, 6, 4, 6, 0.0F);
		this.gem = new ModelRenderer(this, 0, 0);
		this.gem.setRotationPoint(0.0F, -6.0F, -4.5F);
		this.gem.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(gem, 0.0F, 0.0F, 0.7853981633974483F);
		this.gem_1 = new ModelRenderer(this, 0, 0);
		this.gem_1.setRotationPoint(0.0F, -6.0F, 4.5F);
		this.gem_1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 1, 0.0F);
		this.setRotateAngle(gem_1, 0.0F, 0.0F, 0.7853981633974483F);
		this.bottom2 = new ModelRenderer(this, 32, 21);
		this.bottom2.setRotationPoint(0.0F, 5.0F, 0.0F);
		this.bottom2.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
		this.top1 = new ModelRenderer(this, 32, 10);
		this.top1.setRotationPoint(0.0F, -13.0F, 0.0F);
		this.top1.addBox(-3.5F, -2.0F, -3.5F, 7, 4, 7, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, -10.0F, -4.0F, 8, 20, 8, 0.0F);
		this.top2 = new ModelRenderer(this, 43, 24);
		this.top2.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.top2.addBox(-2.5F, -3.0F, -2.5F, 5, 3, 5, 0.0F);
		this.top3 = new ModelRenderer(this, 24, 0);
		this.top3.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.top3.addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, 0.0F);
		this.orbit = new ModelRenderer(this, 0, 33);
		this.orbit.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.orbit.addBox(-7.0F, -1.5F, -7.0F, 14, 3, 14, 0.0F);
		this.setRotateAngle(orbit, 0.0F, -2.4586453172844123F, 0.0F);
		this.body.addChild(this.bottom1);
		this.body.addChild(this.gem);
		this.body.addChild(this.gem_1);
		this.bottom1.addChild(this.bottom2);
		this.body.addChild(this.top1);
		this.top1.addChild(this.top2);
		this.top2.addChild(this.top3);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
		this.body.render(scale);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.orbit.offsetX, this.orbit.offsetY, this.orbit.offsetZ);
		GlStateManager.translate(this.orbit.rotationPointX * scale, this.orbit.rotationPointY * scale, this.orbit.rotationPointZ * scale);
		GlStateManager.scale(1.3D, 1.0D, 1.3D);
		GlStateManager.translate(-this.orbit.offsetX, -this.orbit.offsetY, -this.orbit.offsetZ);
		GlStateManager.translate(-this.orbit.rotationPointX * scale, -this.orbit.rotationPointY * scale, -this.orbit.rotationPointZ * scale);
		GlStateManager.rotate(ageInTicks % 360 * 4, 0F, 1F, 0F);
		this.orbit.render(scale);
		GlStateManager.popMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		
	}
	
	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
