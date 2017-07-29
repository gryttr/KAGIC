package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPepo;
import mod.akrivus.kagic.entity.pepo.EntityPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.util.ResourceLocation;

public class RenderPumpkin extends RenderLivingBase<EntityPumpkin> {
	public RenderPumpkin() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPepo(), 0.25F);
        this.addLayer(new LayerArrow(this));
    }
	protected ResourceLocation getEntityTexture(EntityPumpkin entity) {
		if (entity.isLit()) {
			return new ResourceLocation("kagic:textures/entities/pepo/pumpkin_lit.png");
		}
		else {
			return new ResourceLocation("kagic:textures/entities/pepo/pumpkin.png");
		}
	}
}
