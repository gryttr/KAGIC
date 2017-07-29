package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelRutile;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderRutile extends RenderLivingBase<EntityRutile> {
	public RenderRutile() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelRutile(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityRutile entitylivingbaseIn, float partialTickTime) {
		
	}
	protected ResourceLocation getEntityTexture(EntityRutile entity) {
		return new ResourceLocation("kagic:textures/entities/rutile/rutile.png");
	}
}
