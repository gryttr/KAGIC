package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.shardfusions.ModelHandBody;
import mod.akrivus.kagic.client.render.layers.LayerShardPlacement;
import mod.akrivus.kagic.entity.shardfusion.EntityHandBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHandBody extends RenderLivingBase<EntityHandBody> {

	public RenderHandBody() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelHandBody(), 0.25F);
        this.addLayer(new LayerShardPlacement(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHandBody entity) {
		return new ResourceLocation("kagic:textures/entities/shardfusions/handbody/handbody.png");
	}
}
