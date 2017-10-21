package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerHair implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	private float offset;

	public LayerHair(RenderLivingBase<?> gemRenderer) {
		this(gemRenderer, 0F);
	}

	public LayerHair(RenderLivingBase<?> gemRenderer, float offset) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.offset = offset;
	}
	
	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getHairStyle(gem, gem.getHairStyle()));
		int hairColor = gem.getHairColor();
        float r = (float) ((hairColor & 16711680) >> 16) / 255f;
        float g = (float) ((hairColor & 65280) >> 8) / 255f;
        float b = (float) ((hairColor & 255) >> 0) / 255f;
		GlStateManager.color(r + this.offset, g + this.offset, b + this.offset, 0.99f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	
	public ResourceLocation getHairStyle(EntityGem gem, int hairstyle) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (gem.hasHairVariant(gem.getGemPlacement())) {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/hair_" + gem.getGemPlacement().toString().toLowerCase() + ".png");
		} else {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/hair_" + gem.getHairStyle() + ".png");
		}
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
		return false;
	}
}
