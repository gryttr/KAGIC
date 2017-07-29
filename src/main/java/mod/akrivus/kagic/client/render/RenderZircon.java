package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelZircon;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerZirconHair;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderZircon extends RenderLivingBase<EntityZircon> {
	public RenderZircon() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelZircon(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerZirconHair(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityZircon gem, float partialTickTime) {
		float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[((EntityZircon) gem).getInsigniaColor()]);
		GlStateManager.color(afloat[0], afloat[1], afloat[2]);
	}
	protected ResourceLocation getEntityTexture(EntityZircon entity) {
		return new ResourceLocation("kagic:textures/entities/zircon/zircon_" + entity.getSpecialSkin() + ".png");
	}
}
