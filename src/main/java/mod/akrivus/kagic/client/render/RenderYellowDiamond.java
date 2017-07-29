package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelYellowDiamond;
import mod.akrivus.kagic.client.render.layers.LayerDiamondGlow;
import mod.akrivus.kagic.entity.gem.EntityYellowDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderYellowDiamond extends RenderLivingBase<EntityYellowDiamond> {
	public RenderYellowDiamond() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelYellowDiamond(), 1.0F);
        this.addLayer(new LayerDiamondGlow(this));
    }
	protected void preRenderCallback(EntityYellowDiamond entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(3.0F, 3.0F, 3.0F);
    }
	protected ResourceLocation getEntityTexture(EntityYellowDiamond entity) {
		return new ResourceLocation("kagic:textures/entities/yellow_diamond/yellow_diamond.png");
	}
}
