package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderRutile;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerRutileVisor implements LayerRenderer<EntityRutile> {
	private final RenderRutile gemRenderer;

	public LayerRutileVisor(RenderRutile gemRendererIn) {
		this.gemRenderer = gemRendererIn;
	}
	public void doRenderLayer(EntityRutile gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (gem.hasVisor()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.gemRenderer.bindTexture(this.getTexture(gem));
			this.gemRenderer.getModel(gem.isDefective()).setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemRenderer.getModel(gem.isDefective()).render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
		}
	}
	public ResourceLocation getTexture(EntityGem gem) {
		return new ResourceLocation("kagic:textures/entities/rutile/visor.png");
	}
	public boolean shouldCombineTextures() {
		return true;
	}
}
