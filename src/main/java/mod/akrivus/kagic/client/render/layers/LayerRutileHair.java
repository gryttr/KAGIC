package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderRutile;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerRutileHair implements LayerRenderer<EntityGem> {
	private final RenderRutile gemRenderer;

	public LayerRutileHair(RenderRutile gemRenderer) {
		this.gemRenderer = gemRenderer;
	}
	
	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getHairStyle(gem, gem.getHairStyle()));
		int hairColor = gem.getHairColor();
        float r = (float) ((hairColor & 16711680) >> 16) / 255f;
        float g = (float) ((hairColor & 65280) >> 8) / 255f;
        float b = (float) ((hairColor & 255) >> 0) / 255f;
		GlStateManager.color(r, g, b/*, 0.99f*/);
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemRenderer.getModel(gem.isDefective()).setModelAttributes(this.gemRenderer.getMainModel());
        this.gemRenderer.getModel(gem.isDefective()).render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	
	public ResourceLocation getHairStyle(EntityGem gem, int hairstyle) {
		return new ResourceLocation("kagic:textures/entities/rutile/hair_" + gem.getHairStyle() + ".png");
	}
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
