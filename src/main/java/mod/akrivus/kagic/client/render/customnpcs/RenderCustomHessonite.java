package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelHessonite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomHessonite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomHessonite extends RenderLiving<EntityCustomHessonite> {
	public RenderCustomHessonite() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelHessonite(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomHessonite entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
