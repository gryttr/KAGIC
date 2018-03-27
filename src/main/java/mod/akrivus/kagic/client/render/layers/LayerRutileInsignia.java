package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderRutile;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerRutileInsignia implements LayerRenderer<EntityRutile> {
	private final RenderRutile gemRenderer;
	
	public LayerRutileInsignia(RenderRutile gemRenderer) {
		this.gemRenderer = gemRenderer;
	}
	
	@Override
	public void doRenderLayer(EntityRutile gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		float[] insigniaColor = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getInsigniaColor()]);
		GlStateManager.color(insigniaColor[0], insigniaColor[1], insigniaColor[2]/*, 0.99f*/);
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemRenderer.getModel(gem.isDefective()).setModelAttributes(this.gemRenderer.getMainModel());
        this.gemRenderer.getModel(gem.isDefective()).render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	
	public ResourceLocation getTexture(EntityGem gem) {
		return new ResourceLocation("kagic:textures/entities/rutile/insignia.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
