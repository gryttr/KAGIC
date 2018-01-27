package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelTopaz;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerFusionPlacement;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerTopazItem;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderTopaz extends RenderGemBase<EntityTopaz> {
	public RenderTopaz() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelTopaz(), 0.625F);
        this.addLayer(new LayerTopazItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerGemPlacement(this));
        this.addLayer(new LayerFusionPlacement(this));
        this.addLayer(new LayerVisor(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}

		LayerBipedArmor topazArmor = new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelTopaz(0.5F, true);
				this.modelArmor = new ModelTopaz(1F, true);
			}
		};
		this.addLayer(topazArmor);
	}
	
	@Override
	protected void preRenderCallback(EntityTopaz gem, float partialTickTime) {
		GlStateManager.scale(gem.getFusionCount(), gem.getFusionCount(), gem.getFusionCount());
		this.shadowSize = 0.625F * gem.getFusionCount();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityTopaz entity) {
		return new ResourceLocation("kagic:textures/entities/topaz/topaz.png");
	}
}
