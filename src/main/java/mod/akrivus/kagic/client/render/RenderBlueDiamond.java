package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelBlueDiamond;
import mod.akrivus.kagic.client.render.layers.LayerDiamondGlow;
import mod.akrivus.kagic.entity.gem.EntityBlueDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderBlueDiamond extends RenderLivingBase<EntityBlueDiamond> {
	public RenderBlueDiamond() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelBlueDiamond(), 1.0F);
        this.addLayer(new LayerDiamondGlow(this));
    }
	protected void preRenderCallback(EntityBlueDiamond entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(3.0F, 3.0F, 3.0F);
    }
	protected ResourceLocation getEntityTexture(EntityBlueDiamond entity) {
		return new ResourceLocation("kagic:textures/entities/blue_diamond/blue_diamond_" + (entity.isHooded() ? "hooded" : "unhooded") + ".png");
	}
}
