package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelSapphire;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityPadparadscha;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderPadparadscha extends RenderLivingBase<EntityPadparadscha> {
	public RenderPadparadscha() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSapphire(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityPadparadscha entitylivingbaseIn, float partialTickTime) {
	}
	protected ResourceLocation getEntityTexture(EntityPadparadscha entity) {
		return new ResourceLocation("kagic:textures/entities/padparadscha/padparadscha.png");
	}
}
