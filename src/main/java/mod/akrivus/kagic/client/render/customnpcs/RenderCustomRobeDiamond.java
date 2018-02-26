package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelBlueDiamond;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRobeDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRobeDiamond extends RenderLiving<EntityCustomRobeDiamond> {
	public RenderCustomRobeDiamond() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBlueDiamond(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRobeDiamond entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
