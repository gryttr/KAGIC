package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelTopaz;
import mod.akrivus.kagic.client.render.layers.LayerFusionPlacement;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerTopazItem;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderTopaz extends RenderLivingBase<EntityTopaz> {
	public RenderTopaz() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTopaz(), 0.25F);
        this.addLayer(new LayerTopazItem(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
        this.addLayer(new LayerFusionPlacement(this));
    }
	protected void preRenderCallback(EntityTopaz gem, float partialTickTime) {
		if (gem.isFusion()) {
			GlStateManager.scale(gem.getFusionCount(), gem.getFusionCount(), gem.getFusionCount());
		}
	}
	protected ResourceLocation getEntityTexture(EntityTopaz entity) {
		return new ResourceLocation("kagic:textures/entities/topaz/" + entity.getColor() + "topaz.png");
	}
}
