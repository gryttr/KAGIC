package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.fusions.ModelMalachite;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerCrossFusionGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerMalachiteItem;
import mod.akrivus.kagic.client.render.layers.LayerMalachiteMark;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.fusion.EntityMalachite;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderMalachite extends RenderGemBase<EntityMalachite> {

	public RenderMalachite() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelMalachite(), 3F);
		
		this.addLayer(new LayerMalachiteItem(this));
		this.addLayer(new LayerSkin(this));
		this.addLayer(new LayerMalachiteMark(this));
		this.addLayer(new LayerUniform(this));
		this.addLayer(new LayerInsignia(this));
		this.addLayer(new LayerHair(this));
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
	protected void preRenderCallback(EntityMalachite malachite, float partialTickTime) {
		GlStateManager.scale(4F * malachite.getSizeFactor(), 4F * malachite.getSizeFactor(), 4F * malachite.getSizeFactor());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMalachite malachite) {
		return new ResourceLocation("kagic:textures/entities/malachite/malachite.png");
	}
}