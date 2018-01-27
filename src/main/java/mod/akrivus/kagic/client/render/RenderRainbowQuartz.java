package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.fusions.ModelRainbowQuartz;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerCrossFusionGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerRainbowQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerRainbowQuartzShawl;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderRainbowQuartz extends RenderGemBase<EntityRainbowQuartz> {

	public RenderRainbowQuartz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelRainbowQuartz(), 0.75F);

		this.addLayer(new LayerRainbowQuartzItem(this));
		this.addLayer(new LayerSkin(this));
		this.addLayer(new LayerNoDyeOverlay(this));
		this.addLayer(new LayerCrossFusionGemPlacement(this));
		this.addLayer(new LayerRainbowQuartzShawl(this));

		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
	}

	@Override
	protected void preRenderCallback(EntityRainbowQuartz rainbowQuartz, float partialTickTime) {
		GlStateManager.scale(1.8F * rainbowQuartz.getSizeFactor(), 1.8F * rainbowQuartz.getSizeFactor(), 1.8F * rainbowQuartz.getSizeFactor());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRainbowQuartz rainbowQuartz) {
		return new ResourceLocation("kagic:textures/entities/rainbow_quartz/rainbow_quartz.png");
	}
}
