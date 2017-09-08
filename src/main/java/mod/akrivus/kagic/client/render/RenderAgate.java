package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelAgate;
import mod.akrivus.kagic.client.render.layers.LayerAgateBand;
import mod.akrivus.kagic.client.render.layers.LayerAgateHair;
import mod.akrivus.kagic.client.render.layers.LayerAgateItem;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderAgate extends RenderLivingBase<EntityAgate> {
	private static final float OFFSET = .0f;

	public RenderAgate() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelAgate(), 0.25F);
        this.addLayer(new LayerAgateItem(this));
        this.addLayer(new LayerAgateHair(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerAgateBand(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerQuartzCape(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityAgate agate, float partialTickTime) {
		if (!agate.isHolly()) {
			float[] afloat = EntityAgate.AGATECOLORS[agate.getColor()];
			GlStateManager.color(afloat[0] + OFFSET, afloat[1] + OFFSET, afloat[2] + OFFSET);
		}
	}
	protected ResourceLocation getEntityTexture(EntityAgate entity) {
		if (entity.isHolly()) {
			return new ResourceLocation("kagic:textures/entities/agate/holly_blue_agate.png");
		}
		return new ResourceLocation("kagic:textures/entities/agate/agate.png");
	}
}
