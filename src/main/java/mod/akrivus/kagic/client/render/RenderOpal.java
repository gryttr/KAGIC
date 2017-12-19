package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.model.fusions.ModelOpal;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerCrossFusionGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerOpalItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderOpal extends RenderGemBase<EntityOpal> {

	public RenderOpal() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelOpal(), 1F);
		this.addLayer(new LayerOpalItem(this));
		this.addLayer(new LayerSkin(this, 0.4F));
		this.addLayer(new LayerHair(this));
		this.addLayer(new LayerNoDyeOverlay(this));
		this.addLayer(new LayerCrossFusionGemPlacement(this));
		
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
	}

	@Override
	protected void preRenderCallback(EntityOpal opal, float partialTickTime) {
		GlStateManager.scale(1.8F * opal.getSizeFactor(), 1.8F * opal.getSizeFactor(), 1.8F * opal.getSizeFactor());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityOpal entity) {
		return new ResourceLocation("kagic:textures/entities/opal/opal.png");
	}
}
