package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPepo;
import mod.akrivus.kagic.entity.pepo.EntityCactus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderCactus extends RenderLivingBase<EntityCactus> {
	public RenderCactus() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerArrow(this));
    }
	protected ResourceLocation getEntityTexture(EntityCactus entity) {
		return new ResourceLocation("kagic:textures/entities/pepo/cactus.png");
	}
}
