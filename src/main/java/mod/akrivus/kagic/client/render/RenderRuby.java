package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelRuby;
import mod.akrivus.kagic.client.render.layers.LayerFusionPlacement;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerRubyItem;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderRuby extends RenderLivingBase<EntityRuby> {
	public RenderRuby() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelRuby(), 0.25F);
        this.addLayer(new LayerRubyItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
        this.addLayer(new LayerFusionPlacement(this));
    }
	protected void preRenderCallback(EntityRuby gem, float partialTickTime) {
		if (gem.isFusion()) {
			GlStateManager.scale(gem.getFusionCount(), gem.getFusionCount(), gem.getFusionCount());
		}
		else if (gem.isDefective()) {
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
		}
	}
	protected ResourceLocation getEntityTexture(EntityRuby entity) {
		return new ResourceLocation("kagic:textures/entities/ruby/ruby.png");
	}
}
