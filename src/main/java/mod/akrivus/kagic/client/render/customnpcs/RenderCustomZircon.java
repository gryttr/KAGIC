package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelZircon;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomZircon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomZircon extends RenderLiving<EntityCustomZircon> {
	public RenderCustomZircon() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelZircon(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomZircon entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
