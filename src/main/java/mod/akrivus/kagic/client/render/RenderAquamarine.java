package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelAquamarine;
import mod.akrivus.kagic.client.render.layers.LayerAquamarineItem;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderAquamarine extends RenderGemBase<EntityAquamarine> {
	public RenderAquamarine() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelAquamarine(), 0.25F);
        this.addLayer(new LayerAquamarineItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	
	@Override
	protected void preRenderCallback(EntityAquamarine entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(0.65F, 0.65F, 0.65F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityAquamarine entity) {
		return new ResourceLocation("kagic:textures/entities/aquamarine/aquamarine.png");
	}
}
