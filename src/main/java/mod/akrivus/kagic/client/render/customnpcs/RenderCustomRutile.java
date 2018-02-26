package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelRutile;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRutile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRutile extends RenderLiving<EntityCustomRutile> {
	public RenderCustomRutile() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelRutile(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRutile entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
