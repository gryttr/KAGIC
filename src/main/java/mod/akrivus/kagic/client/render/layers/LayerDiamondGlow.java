package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerDiamondGlow implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<EntityAgate> gemRenderer;
	private final ModelBase gemModel;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayerDiamondGlow(RenderLivingBase gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityGem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(new ResourceLocation("kagic:textures/entities/" + this.getName(entitylivingbaseIn) + "/glow.png"));
    	GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
	}
	public String getName(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
        if (loc != null) {
        	return loc.toString().replaceFirst("kagic:kagic.", "");
        }
        else {
        	return "gem";
        }
	}
	public boolean shouldCombineTextures() {
		return false;
	}
}
