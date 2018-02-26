package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelPearl;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomPearl extends RenderLiving<EntityCustomPearl> {
	public RenderCustomPearl() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelPearl(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomPearl entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
