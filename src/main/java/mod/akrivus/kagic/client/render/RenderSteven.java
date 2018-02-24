package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelSteven;
import mod.akrivus.kagic.client.render.layers.LayerCheeseburgerBackpack;
import mod.akrivus.kagic.entity.EntitySteven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderSteven extends RenderLivingBase<EntitySteven> {
	public RenderSteven() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSteven(), 0.25F);
        this.addLayer(new LayerCheeseburgerBackpack(this));
        this.addLayer(new LayerArrow(this));
    }
	protected ResourceLocation getEntityTexture(EntitySteven entity) {
		return new ResourceLocation("kagic:textures/entities/steven/steven.png");
	}
}
