package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerWitchHat implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	
	public LayerWitchHat(RenderLivingBase<?> gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		float[] insigniaColor = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getInsigniaColor()]);
		GlStateManager.color(insigniaColor[0] * 2, insigniaColor[1] * 2, insigniaColor[2] * 2, 1F);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}

	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/other/witch_hat.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
