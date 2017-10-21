package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelSapphire;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityPadparadscha;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderPadparadscha extends RenderGemBase<EntityPadparadscha> {
	public RenderPadparadscha() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSapphire(), 0.25F);
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		}
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityPadparadscha entity) {
		return new ResourceLocation("kagic:textures/entities/padparadscha/padparadscha.png");
	}
}
