package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerGemPlacement implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<EntityGem> gemRenderer;
	private final ModelBase gemModel;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayerGemPlacement(RenderLivingBase gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isFusion()) {
			this.gemRenderer.bindTexture(new ResourceLocation("kagic:textures/entities/" + this.getName(gem) + "/gems/" + gem.getGemPlacement() + "_" + gem.getGemCut() + ".png"));
			float[] colors = gem.getGemColor();
			GlStateManager.color(colors[0], colors[1], colors[2]);
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        GlStateManager.disableBlend();
		}
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
