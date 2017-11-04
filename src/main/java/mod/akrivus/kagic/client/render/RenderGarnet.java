package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.fusions.ModelGarnet;
import mod.akrivus.kagic.client.render.layers.LayerCrossFusionGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerGarnetItem;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.fusion.EntityGarnet;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderGarnet extends RenderGemBase<EntityGarnet> {

	public RenderGarnet() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelGarnet(), 0.5F);
		
		this.addLayer(new LayerGarnetItem(this));
		this.addLayer(new LayerSkin(this));
		this.addLayer(new LayerHair(this));
		this.addLayer(new LayerNoDyeOverlay(this));
		this.addLayer(new LayerVisor(this));
		this.addLayer(new LayerCrossFusionGemPlacement(this));
	}

	@Override
	protected void preRenderCallback(EntityGarnet garnet, float partialTickTime) {
		GlStateManager.scale(1.125F * garnet.getSizeFactor(), 1.125F * garnet.getSizeFactor(), 1.125F * garnet.getSizeFactor());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGarnet entity) {
		return new ResourceLocation("kagic:textures/entities/garnet/garnet.png");
	}
}