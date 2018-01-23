package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderAgate;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayerAgateBand implements LayerRenderer<EntityAgate> {
	private final RenderAgate gemRenderer;
	private final ModelBase gemModel;
	
	public LayerAgateBand(RenderAgate renderAgate) {
		this.gemRenderer = renderAgate;
		this.gemModel = renderAgate.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityAgate agate, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (agate.hasBands()) {
			this.gemRenderer.bindTexture(EntityAgate.AGATE_BAND_STYLES.get(agate.getHairStyle()));
			float[] afloat = EntityAgate.BANDCOLORS[agate.getColor()];
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			//GlStateManager.enableBlend();
			//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(agate, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
