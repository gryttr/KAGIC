package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.layers.LayerAmetrineHalf;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityCitrine;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderCitrine extends RenderGemBase<EntityCitrine> {
	public RenderCitrine() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);

		this.addLayer(new LayerQuartzItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerAmetrineHalf(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerQuartzCape(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}

		LayerBipedArmor citrineArmor = new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelQuartz(0.5F, true);
				this.modelArmor = new ModelQuartz(1F, true);
			}
		};
		this.addLayer(citrineArmor);
    }
	
	@Override
	protected void preRenderCallback(EntityCitrine citrine, float partialTickTime) {
		if (citrine.isPrimary()) {
			GlStateManager.scale(1.1F, 1.1F, 1.1F);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCitrine entity) {
		if (entity.isDefective()) {
			return new ResourceLocation("kagic:textures/entities/citrine/ametrine_" + entity.getSpecial() + ".png");
		}
		return new ResourceLocation("kagic:textures/entities/citrine/citrine.png");
	}
}