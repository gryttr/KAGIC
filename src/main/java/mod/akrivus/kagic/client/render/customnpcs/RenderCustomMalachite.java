package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.fusions.ModelMalachite;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomMalachite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomMalachite extends RenderLiving<EntityCustomMalachite> {
	public RenderCustomMalachite() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelMalachite(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomMalachite entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
