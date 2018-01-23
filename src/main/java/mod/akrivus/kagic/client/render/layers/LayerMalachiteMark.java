package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderJasper;
import mod.akrivus.kagic.client.render.RenderMalachite;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.fusion.EntityMalachite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerMalachiteMark implements LayerRenderer<EntityMalachite> {
	private final RenderMalachite gemRenderer;
	private final ModelBase gemModel;
	
	public LayerMalachiteMark(RenderMalachite renderMalachite) {
		this.gemRenderer = renderMalachite;
		this.gemModel = renderMalachite.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityMalachite malachite, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getMark(malachite));
		int mark1Color = malachite.getMarkColor();
        float r = (float) ((mark1Color & 16711680) >> 16) / 255f;
        float g = (float) ((mark1Color & 65280) >> 8) / 255f;
        float b = (float) ((mark1Color & 255) >> 0) / 255f;
		GlStateManager.color(r, g, b/*, 0.99f*/);
		//GlStateManager.enableBlend();
		//GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(malachite, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}

	public ResourceLocation getMark(EntityMalachite malachite) {
		ResourceLocation loc = EntityList.getKey(malachite);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/malachite/mark_" + malachite.getSpecial() + ".png");
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
