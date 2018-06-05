package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerCrossFusionGemPlacement implements LayerRenderer<EntityFusionGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	public LayerCrossFusionGemPlacement(RenderLivingBase<?> gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	
	@Override
	public void doRenderLayer(EntityFusionGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		int color = gem.getGemColor();
		float r = (float) ((color & 16711680) >> 16) / 255f;
        float g = (float) ((color & 65280) >> 8) / 255f;
        float b = (float) ((color & 255) >> 0) / 255f;
		GlStateManager.color(r, g, b);

		String[] types = gem.getFusionTypes().split(" ");
		String[] cutPlacements = gem.getFusionPlacements().split(" ");
		for (int i = 0; i < gem.getFusionCount(); ++i) {
			this.gemRenderer.bindTexture(this.getTexture(gem, types[i], cutPlacements[i]));
			this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
	        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
	
	public ResourceLocation getTexture(EntityGem gem, String type, String cutPlacement) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/gems/" + type + "/" + cutPlacement + ".png");
	}
	
	public String getName(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (loc.getResourceDomain().equals("kagic")) {
	        return loc.getResourcePath().replaceFirst("kagic.", "");
		}
		else {
	        return loc.getResourcePath();
		}
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
