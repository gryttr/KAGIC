package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.fusions.ModelGarnet;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomGarnet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomGarnet extends RenderLiving<EntityCustomGarnet> {
	public RenderCustomGarnet() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelGarnet(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomGarnet entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
