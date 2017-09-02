package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPeridot;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerPeridotItem;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderPeridot extends RenderLivingBase<EntityPeridot> {
	public RenderPeridot() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPeridot(), 0.25F);
        this.addLayer(new LayerPeridotItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityPeridot entitylivingbaseIn, float partialTickTime) {
		
	}
	protected ResourceLocation getEntityTexture(EntityPeridot entity) {
		return new ResourceLocation("kagic:textures/entities/peridot/peridot.png");
	}
}
