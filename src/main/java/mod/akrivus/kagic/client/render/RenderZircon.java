package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelZircon;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.client.render.layers.LayerZirconHair;
import mod.akrivus.kagic.client.render.layers.LayerZirconItem;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderZircon extends RenderGemBase<EntityZircon> {
	public RenderZircon() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelZircon(), 0.25F);
        this.addLayer(new LayerZirconItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerZirconHair(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
        this.addLayer(new LayerVisor(this));
        
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	
	@Override
	protected void preRenderCallback(EntityZircon gem, float partialTickTime) {
		//float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[((EntityZircon) gem).getInsigniaColor()]);
		//GlStateManager.color(afloat[0], afloat[1], afloat[2]);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityZircon entity) {
		return new ResourceLocation("kagic:textures/entities/zircon/zircon_" + entity.getSpecialSkin() + ".png");
	}
}
