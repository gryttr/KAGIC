package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderJasper extends RenderLivingBase<EntityJasper> {
	public RenderJasper() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.25F);
        this.addLayer(new LayerQuartzItem(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityJasper entitylivingbaseIn, float partialTickTime) {
		if (entitylivingbaseIn.isDefective()) {
			GlStateManager.scale(0.7F, 1.0F, 0.7F);
		}
	}
	protected ResourceLocation getEntityTexture(EntityJasper entity) {
		return new ResourceLocation("kagic:textures/entities/jasper/" + entity.getSpecialSkin() + "jasper.png");
	}
}
