package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelPeridot;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomDefectivePeridot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomDefectivePeridot extends RenderLiving<EntityCustomDefectivePeridot> {
	public RenderCustomDefectivePeridot() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelPeridot(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomDefectivePeridot entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
