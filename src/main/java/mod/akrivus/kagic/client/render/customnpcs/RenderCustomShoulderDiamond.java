package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelYellowDiamond;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomShoulderDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomShoulderDiamond extends RenderLiving<EntityCustomShoulderDiamond> {
	public RenderCustomShoulderDiamond() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelYellowDiamond(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomShoulderDiamond entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
