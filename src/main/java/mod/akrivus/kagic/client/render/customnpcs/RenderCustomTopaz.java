package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelTopaz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomTopaz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomTopaz extends RenderLiving<EntityCustomTopaz> {
	public RenderCustomTopaz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelTopaz(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomTopaz entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
