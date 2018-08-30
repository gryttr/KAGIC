package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerFusionPlacement extends GemLayer implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	public LayerFusionPlacement(RenderLivingBase<?> gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	
	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (gem.isFusion()) {
			String[] placements = gem.getFusionPlacements().split(" ");
			for (String placement : placements) {
				this.gemRenderer.bindTexture(this.getTexture(gem, placement));
				int color = gem.getGemColor();
				float r = (float) ((color & 16711680) >> 16) / 255f;
		        float g = (float) ((color & 65280) >> 8) / 255f;
		        float b = (float) ((color & 255) >> 0) / 255f;
				GlStateManager.color(r, g, b);
				this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
		        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			}
		}
	}
	public ResourceLocation getTexture(EntityGem gem, String placement) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/gems/" + placement + ".png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
