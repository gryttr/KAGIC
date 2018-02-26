package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelTwinRutile;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRutileTwins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRutileTwins extends RenderLiving<EntityCustomRutileTwins> {
	public RenderCustomRutileTwins() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelTwinRutile(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRutileTwins entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
