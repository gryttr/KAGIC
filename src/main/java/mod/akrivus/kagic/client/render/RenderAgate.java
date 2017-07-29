package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelAgate;
import mod.akrivus.kagic.client.render.layers.LayerAgateHair;
import mod.akrivus.kagic.client.render.layers.LayerAgateItem;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderAgate extends RenderLivingBase<EntityAgate> {
	public RenderAgate() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelAgate(), 0.25F);
        this.addLayer(new LayerAgateItem(this));
        this.addLayer(new LayerAgateHair(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityAgate entitylivingbaseIn, float partialTickTime) {
		if (!entitylivingbaseIn.isHolly()) {
			float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[((EntityAgate) entitylivingbaseIn).getColor()]);
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
		}
	}
	protected ResourceLocation getEntityTexture(EntityAgate entity) {
		if (entity.isHolly()) {
			return new ResourceLocation("kagic:textures/entities/agate/holly_blue_agate.png");
		}
		return new ResourceLocation("kagic:textures/entities/agate/agate.png");
	}
}
