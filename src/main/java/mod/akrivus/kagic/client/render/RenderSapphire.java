package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelSapphire;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderSapphire extends RenderLivingBase<EntitySapphire> {
	public RenderSapphire() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSapphire(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntitySapphire entitylivingbaseIn, float partialTickTime) {
	}
	protected ResourceLocation getEntityTexture(EntitySapphire entity) {
		return new ResourceLocation("kagic:textures/entities/sapphire/sapphire.png");
	}
}
