package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomDefectiveQuartz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomDefectiveQuartz extends RenderLiving<EntityCustomDefectiveQuartz> {
	public RenderCustomDefectiveQuartz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);
	}
	protected void preRenderCallback(EntityCustomDefectiveQuartz gem, float partialTickTime) {
		GlStateManager.scale(0.9F, 0.72F, 0.9F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomDefectiveQuartz entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
