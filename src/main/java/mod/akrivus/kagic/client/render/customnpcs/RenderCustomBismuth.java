package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelBismuth;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomBismuth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomBismuth extends RenderLiving<EntityCustomBismuth> {
	public RenderCustomBismuth() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBismuth(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomBismuth entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
