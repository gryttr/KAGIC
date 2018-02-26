package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderRutile;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerRutileGemPlacement implements LayerRenderer<EntityRutile> {
	private final RenderRutile gemRenderer;
	private final String name;

	public LayerRutileGemPlacement(RenderRutile gemRenderer) {
		this(gemRenderer, null);
	}
	public LayerRutileGemPlacement(RenderRutile gemRenderer, String name) {
		this.gemRenderer = gemRenderer;
		this.name = name;
	}
	
	@Override
	public void doRenderLayer(EntityRutile gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isFusion()) {
			this.gemRenderer.bindTexture(this.getTexture(gem));
			float[] colors = gem.getGemColor();
			GlStateManager.color(colors[0], colors[1], colors[2]);
			this.gemRenderer.getModel(gem.isDefective()).setModelAttributes(this.gemRenderer.getMainModel());
			this.gemRenderer.getModel(gem.isDefective()).render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
	
	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/gems/" + gem.getGemPlacement().id + "_" + gem.getGemCut().id + ".png");
	}
	
	public String getName(EntityGem gem) {
		if (this.name == null) {
			ResourceLocation loc = EntityList.getKey(gem);
			if (loc.getResourceDomain().equals("kagic")) {
		        return loc.getResourcePath().replaceFirst("kagic.", "");
			}
			else {
		        return loc.getResourcePath();
			}
		} else {
			return this.name;
		}
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
