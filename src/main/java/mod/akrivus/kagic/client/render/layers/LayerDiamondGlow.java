package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerDiamondGlow implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	private final String name;
	public LayerDiamondGlow(RenderLivingBase<?> gemRendererIn) {
		this(gemRendererIn, null);
	}
	public LayerDiamondGlow(RenderLivingBase<?> gemRendererIn, String name) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
		this.name = name;
	}
	public void doRenderLayer(EntityGem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(entitylivingbaseIn));
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
	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/glow.png");
	}
	public String getName(EntityGem gem) {
		if (this.name == null) {
			ResourceLocation loc = EntityList.getKey(gem);
			if (loc.getResourceDomain().equals("kagic")) {
		        return loc.getResourcePath().replaceFirst("kagic.", "");
			}
			else {
		        return loc.getResourcePath();
			}
		}
		return this.name;
	}
	public boolean shouldCombineTextures() {
		return false;
	}
}
