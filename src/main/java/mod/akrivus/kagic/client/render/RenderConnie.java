package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelConnie;
import mod.akrivus.kagic.client.render.layers.LayerConnieBackpack;
import mod.akrivus.kagic.client.render.layers.LayerConnieClothing;
import mod.akrivus.kagic.client.render.layers.LayerConnieHair;
import mod.akrivus.kagic.client.render.layers.LayerConnieItem;
import mod.akrivus.kagic.entity.humans.EntityConnie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderConnie extends RenderLivingBase<EntityConnie> {
	public RenderConnie() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelConnie(), 0.25F);
        this.addLayer(new LayerConnieItem(this));
        this.addLayer(new LayerConnieClothing(this));
        this.addLayer(new LayerConnieHair(this));
        this.addLayer(new LayerConnieBackpack(this));
        this.addLayer(new LayerArrow(this));
    }
	protected ResourceLocation getEntityTexture(EntityConnie entity) {
		return new ResourceLocation("kagic:textures/entities/connie/connie.png");
	}
}
