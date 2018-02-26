package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelAquamarine;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomAquamarine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomAquamarine extends RenderLiving<EntityCustomAquamarine> {
	public RenderCustomAquamarine() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelAquamarine(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomAquamarine entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
