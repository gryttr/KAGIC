package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderJasper;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerJasperMark2 implements LayerRenderer<EntityJasper> {
	private final RenderJasper gemRenderer;
	private final ModelBase gemModel;
	
	public LayerJasperMark2(RenderJasper renderJasper) {
		this.gemRenderer = renderJasper;
		this.gemModel = renderJasper.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityJasper jasper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (jasper.hasSecondMarking()) {
			this.gemRenderer.bindTexture(this.getMark2(jasper));
			int mark2Color = jasper.getMark2Color();
	        float r = (float) ((mark2Color & 16711680) >> 16) / 255f;
	        float g = (float) ((mark2Color & 65280) >> 8) / 255f;
	        float b = (float) ((mark2Color & 255) >> 0) / 255f;
			GlStateManager.color(r, g, b/*, 0.99f*/);
			//GlStateManager.enableBlend();
			//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(jasper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		} else {
			return;
		}
	}

	public ResourceLocation getMark2(EntityJasper jasper) {
		ResourceLocation loc = EntityList.getKey(jasper);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/jasper/mark2_" + jasper.getSpecial() + "_" + jasper.getMark2() + ".png");
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
