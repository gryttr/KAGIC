package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelAgate;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomAgate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomAgate extends RenderLiving<EntityCustomAgate> {
	public RenderCustomAgate() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelAgate(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomAgate entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
