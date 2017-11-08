package mod.akrivus.kagic.client.model.fusions;

import mod.akrivus.kagic.client.model.ModelGem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRainbowQuartz extends ModelGem {
    public ModelRenderer Body;
    public ModelRenderer leg_left_top;
    public ModelRenderer leg_left_top_1;
    public ModelRenderer shoulder_left;
    public ModelRenderer shoulder_right;
    public ModelRenderer neck;
    public ModelRenderer left_leg_bottom;
    public ModelRenderer left_leg_bottom_1;
    public ModelRenderer head;
    public ModelRenderer hair;

	public ModelRainbowQuartz() {
		this(0.0F);
	}
	
	public ModelRainbowQuartz(float modelSize) {
		super(modelSize, 0.0F, 64, 64, false, -4F);

		this.hair = new ModelRenderer(this, 18, 26);
		this.hair.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.hair.addBox(-5.0F, -10.0F, 0.0F, 10, 20, 6, 0.0F);
		this.shoulder_right = new ModelRenderer(this, 0, 14);
		this.shoulder_right.setRotationPoint(-3.0F, -5.0F, 0.0F);
		this.shoulder_right.addBox(-2.0F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
		this.setRotateAngle(shoulder_right, 0.0F, 0.0F, 0.08726646259971647F);
		this.left_leg_bottom = new ModelRenderer(this, 50, 26);
		this.left_leg_bottom.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.left_leg_bottom.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.Body = new ModelRenderer(this, 0, 0);
		this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Body.addBox(-3.0F, -5.0F, -2.0F, 6, 10, 4, 0.0F);
		this.neck = new ModelRenderer(this, 0, 29);
		this.neck.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.neck.addBox(-1.0F, -2.0F, -1.0F, 2, 1, 2, 0.0F);
		this.head = new ModelRenderer(this, 20, 0);
		this.head.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.head.addBox(-3.0F, -8.0F, -3.5F, 6, 8, 6, 0.0F);
		this.shoulder_left = new ModelRenderer(this, 12, 14);
		this.shoulder_left.setRotationPoint(3.0F, -5.0F, 0.0F);
		this.shoulder_left.addBox(-1.0F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
		this.setRotateAngle(shoulder_left, 0.0F, 0.0F, -0.08726646259971647F);
		this.leg_left_top_1 = new ModelRenderer(this, 44, 0);
		this.leg_left_top_1.setRotationPoint(-1.9F, 3.5F, 0.02F);
		this.leg_left_top_1.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
		this.leg_left_top = new ModelRenderer(this, 40, 13);
		this.leg_left_top.setRotationPoint(2.0F, 3.5F, 0.02F);
		this.leg_left_top.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
		this.left_leg_bottom_1 = new ModelRenderer(this, 24, 14);
		this.left_leg_bottom_1.setRotationPoint(0.0F, 9.0F, 0.0F);
		this.left_leg_bottom_1.addBox(-1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F);
		this.head.addChild(this.hair);
		this.Body.addChild(this.shoulder_right);
		this.leg_left_top.addChild(this.left_leg_bottom);
		this.Body.addChild(this.neck);
		this.neck.addChild(this.head);
		this.Body.addChild(this.shoulder_left);
		this.Body.addChild(this.leg_left_top_1);
		this.Body.addChild(this.leg_left_top);
		this.leg_left_top_1.addChild(this.left_leg_bottom_1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
		this.Body.render(f5);
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
