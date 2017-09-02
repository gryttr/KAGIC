package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityCarnelian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderCarnelian extends RenderLivingBase<EntityCarnelian> {
	public RenderCarnelian() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);
        this.addLayer(new LayerQuartzItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerQuartzCape(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityCarnelian entitylivingbaseIn, float partialTickTime) {
		if (entitylivingbaseIn.isDefective()) {
			GlStateManager.scale(0.8F, 0.6F, 0.8F);
		}
	}
	protected ResourceLocation getEntityTexture(EntityCarnelian entity) {
		return new ResourceLocation("kagic:textures/entities/carnelian/carnelian.png");
	}
}
