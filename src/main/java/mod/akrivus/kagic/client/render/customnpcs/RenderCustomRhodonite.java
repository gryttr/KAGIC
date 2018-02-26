package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.fusions.ModelRhodonite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRhodonite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRhodonite extends RenderLiving<EntityCustomRhodonite> {
	public RenderCustomRhodonite() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelRhodonite(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRhodonite entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
