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

public class LayerJasperMark1 implements LayerRenderer<EntityJasper> {
	private final RenderJasper gemRenderer;
	private final ModelBase gemModel;
	
	public LayerJasperMark1(RenderJasper renderJasper) {
		this.gemRenderer = renderJasper;
		this.gemModel = renderJasper.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityJasper jasper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getMark1(jasper));
		int mark1Color = jasper.getMark1Color();
        float r = (float) ((mark1Color & 16711680) >> 16) / 255f;
        float g = (float) ((mark1Color & 65280) >> 8) / 255f;
        float b = (float) ((mark1Color & 255) >> 0) / 255f;
		GlStateManager.color(r, g, b/*, 0.99f*/);
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(jasper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	public ResourceLocation getMark1(EntityJasper jasper) {
		ResourceLocation loc = EntityList.getKey(jasper);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/jasper/mark1_" + jasper.getSpecial() + "_" + jasper.getMark1() + ".png");
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
