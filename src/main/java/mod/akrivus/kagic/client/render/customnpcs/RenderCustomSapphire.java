package mod.akrivus.kagic.client.render.customnpcs;

import mod.akrivus.kagic.client.model.ModelSapphire;
import mod.akrivus.kagic.entity.customnpcs.EntityCustomSapphire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

public class RenderCustomSapphire extends RenderLiving<EntityCustomSapphire> {
	public RenderCustomSapphire() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelSapphire(), 0.5F);
	}
	protected ResourceLocation getEntityTexture(EntityCustomSapphire entity) {
		return new ResourceLocation("kagic:textures/entities/custom-default.png");
	}
}
