package mod.akrivus.kagic.client.render.layers;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerHair implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<EntityGem> gemRenderer;
	private final ModelBase gemModel;
	
	public LayerHair(RenderLivingBase gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getHairStyle(gem, gem.getHairStyle()));
		int hairColor = gem.getHairColor();
        float r = (float) ((hairColor & 16711680) >> 16) / 255f;
        float g = (float) ((hairColor & 65280) >> 8) / 255f;
        float b = (float) ((hairColor & 255) >> 0) / 255f;
		GlStateManager.color(r, g, b, 0.99f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	
	public ResourceLocation getHairStyle(EntityGem gem, int hairstyle) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (gem.getGemPlacement() == GemPlacements.FOREHEAD) {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/hair_forehead.png");
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
	public boolean shouldCombineTextures() {
		return false;
	}
}
