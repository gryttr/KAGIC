package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.fusions.ModelOpal;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomOpal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomOpal extends RenderLiving<EntityCustomOpal> {
	public RenderCustomOpal() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelOpal(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomOpal entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
