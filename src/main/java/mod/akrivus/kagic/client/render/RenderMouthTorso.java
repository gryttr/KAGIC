package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.shardfusions.ModelMouthTorso;
import mod.akrivus.kagic.client.render.layers.LayerShardPlacement;
import mod.akrivus.kagic.entity.shardfusion.EntityMouthTorso;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderMouthTorso extends RenderLivingBase<EntityMouthTorso> {

	public RenderMouthTorso() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelMouthTorso(), 0.25F);
        this.addLayer(new LayerShardPlacement(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMouthTorso entity) {
		return new ResourceLocation("kagic:textures/entities/shardfusions/mouthtorso/mouthtorso.png");
	}
}
