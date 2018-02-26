package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelRuby;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomRuby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomRuby extends RenderLiving<EntityCustomRuby> {
	public RenderCustomRuby() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelRuby(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomRuby entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
