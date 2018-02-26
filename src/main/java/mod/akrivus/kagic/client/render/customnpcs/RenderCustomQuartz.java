package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomQuartz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomQuartz extends RenderLiving<EntityCustomQuartz> {
	public RenderCustomQuartz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomQuartz entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
