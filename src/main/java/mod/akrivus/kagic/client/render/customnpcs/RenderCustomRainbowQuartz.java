package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.fusions.ModelRainbowQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRainbowQuartz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRainbowQuartz extends RenderLiving<EntityCustomRainbowQuartz> {
	public RenderCustomRainbowQuartz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelRainbowQuartz(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRainbowQuartz entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
