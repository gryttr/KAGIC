package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerVisor implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<EntityGem> gemRenderer;
	private final ModelBase gemModel;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayerVisor(RenderLivingBase gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityGem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.hasVisor()) {
			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.gemRenderer.bindTexture(new ResourceLocation("kagic:textures/entities/" + this.getName(entitylivingbaseIn) + "/visor.png"));
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
		}
	}
	public boolean shouldCombineTextures() {
		return false;
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
}
