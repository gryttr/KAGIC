package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.humans.EntitySteven;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerGlowBracelet implements LayerRenderer<EntitySteven> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	
	public LayerGlowBracelet(RenderLivingBase<?> gemRenderer) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
	}
	
	@Override
	public void doRenderLayer(EntitySteven gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isInvisible() && gem.hasWristband()) {
			GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	        GlStateManager.disableLighting();
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
	        GlStateManager.enableLighting();
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			this.gemRenderer.bindTexture(this.getTexture(gem));
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
		}
	}
	
	public ResourceLocation getTexture(EntitySteven gem) {
		return new ResourceLocation("kagic:textures/entities/steven/jewelry.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
