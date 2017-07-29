package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelBismuth;
import mod.akrivus.kagic.client.render.layers.LayerBismuthItem;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderBismuth extends RenderLivingBase<EntityBismuth> {
	public RenderBismuth() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelBismuth(), 0.25F);
        this.addLayer(new LayerBismuthItem(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityBismuth entitylivingbaseIn, float partialTickTime) {
		
	}
	protected ResourceLocation getEntityTexture(EntityBismuth entity) {
		return new ResourceLocation("kagic:textures/entities/bismuth/bismuth.png");
	}
}
