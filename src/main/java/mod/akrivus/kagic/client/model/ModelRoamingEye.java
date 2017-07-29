package mod.akrivus.kagic.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRoamingEye extends ModelBase {
	public ModelRenderer middle;
	public ModelRenderer bottom;
	public ModelRenderer top;
	public ModelRoamingEye() {
		this.textureHeight = 64;
		this.textureWidth = 64;
		this.top = new ModelRenderer(this, 0, 0);
		this.top.addBox(4, 3, 4, 4, 1, 4);
		this.top.addBox(2, 4, 2, 8, 1, 8);
		this.top.addBox(1, 5, 1, 10, 1, 10);
		this.top.setRotationPoint(-6, 0, -6);
		this.middle = new ModelRenderer(this, 0, 16);
		this.middle.addBox(0, 6, 0, 12, 9, 12, -0.01F);
		this.middle.setRotationPoint(-6, 0, -6);
		this.bottom = new ModelRenderer(this, 0, 37);
		this.bottom.addBox(1, 15, 1, 10, 3, 10);
		this.bottom.addBox(2, 18, 2, 8, 2, 8);
		this.bottom.addBox(3, 20, 3, 6, 2, 6);
		this.bottom.setRotationPoint(-6, 0, -6);
	}
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.middle.render(scale);
        this.bottom.render(scale);
        this.top.render(scale);
    }
}
