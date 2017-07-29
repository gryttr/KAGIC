package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelRoamingEye;
import mod.akrivus.kagic.entity.vehicles.EntityRoamingEye;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderRoamingEye extends RenderLivingBase<EntityRoamingEye> {
	public RenderRoamingEye() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelRoamingEye(), 2.0F);
    }
	protected void preRenderCallback(EntityRoamingEye entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(4.0F, 4.0F, 4.0F);
    }
	protected boolean canRenderName(EntityRoamingEye entity) {
		return entity.hasCustomName();
	}
	protected ResourceLocation getEntityTexture(EntityRoamingEye entity) {
		if (entity.isBeingRidden()) {
			return new ResourceLocation("kagic:textures/entities/roaming_eye/roaming_eye.png");
		}
		return new ResourceLocation("kagic:textures/entities/roaming_eye/roaming_eye_empty.png");
	}
}
