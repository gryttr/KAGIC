package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelAquamarine;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderAquamarine extends RenderGemBase<EntityAquamarine> {
	public RenderAquamarine() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelAquamarine(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		}
    }
	
	@Override
	protected void preRenderCallback(EntityAquamarine entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(0.65F, 0.65F, 0.65F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityAquamarine entity) {
		return new ResourceLocation("kagic:textures/entities/aquamarine/aquamarine.png");
	}
}
