package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.corrupted.ModelCorruptedQuartz;
import mod.akrivus.kagic.client.model.corrupted.ModelCorruptedWatermelonTourmaline;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedAmethyst;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWatermelonTourmaline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderCorruptedWatermelonTourmaline extends RenderGemBase<EntityCorruptedWatermelonTourmaline> {

	public RenderCorruptedWatermelonTourmaline() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelCorruptedWatermelonTourmaline(), 2F);
	}

	@Override
	protected void preRenderCallback(EntityCorruptedWatermelonTourmaline tourmaline, float partialTickTime) {
		//this.renderManager.setDebugBoundingBox(false);
		GlStateManager.scale(3F, 3F, 3F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCorruptedWatermelonTourmaline tourmaline) {
		if (tourmaline.isAttacking()) {
			return new ResourceLocation("kagic:textures/entities/corrupted/watermelon_tourmaline/watermelon_tourmaline_attack.png");
		} else {
			return new ResourceLocation("kagic:textures/entities/corrupted/watermelon_tourmaline/watermelon_tourmaline.png");
		}
	}
}