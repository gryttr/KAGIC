package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelLapisLazuli;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomLapisLazuli;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomLapisLazuli extends RenderLiving<EntityCustomLapisLazuli> {
	public RenderCustomLapisLazuli() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelLapisLazuli(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomLapisLazuli entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
