package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerGemPlacement extends GemLayer implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	private final String name;
	private final boolean glow;

	public LayerGemPlacement(RenderLivingBase<?> gemRenderer) {
		this(gemRenderer, null);
	}
	public LayerGemPlacement(RenderLivingBase<?> gemRenderer, boolean glow) {
		this(gemRenderer, null, glow);
	}
	public LayerGemPlacement(RenderLivingBase<?> gemRenderer, String name) {
		this(gemRenderer, name, false);
	}
	public LayerGemPlacement(RenderLivingBase<?> gemRenderer, String name, boolean glow) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.name = name;
		this.glow = glow;
	}
	
	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isFusion()) {
			if (this.glow) {
				GlStateManager.enableBlend();
		        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		        GlStateManager.disableLighting();
		        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
		        GlStateManager.enableLighting();
			}
			this.gemRenderer.bindTexture(this.getTexture(gem));
			int color = gem.getGemColor();
			float r = (float) ((color & 16711680) >> 16) / 255f;
	        float g = (float) ((color & 65280) >> 8) / 255f;
	        float b = (float) ((color & 255) >> 0) / 255f;
			GlStateManager.color(r, g, b);
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        GlStateManager.disableBlend();
		}
	}
	
	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/gems/" + gem.getGemPlacement().id + "_" + gem.getGemCut().id + ".png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
