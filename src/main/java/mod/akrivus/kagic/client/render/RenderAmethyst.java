package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderAmethyst extends RenderBiped<EntityAmethyst> {
	public RenderAmethyst() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.5F);
		for (Iterator<LayerRenderer<EntityAmethyst>> iter = this.layerRenderers.iterator(); iter.hasNext();) {
			LayerRenderer layer = iter.next();
			if (layer instanceof LayerHeldItem) {
				iter.remove();
			}
		}

		this.addLayer(new LayerQuartzItem(this));
        this.addLayer(new LayerSkin(this));
        this.addLayer(new LayerUniform(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerHair(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerQuartzCape(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		}

		LayerBipedArmor amethystArmor = new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelQuartz(0.5F, true);
				this.modelArmor = new ModelQuartz(1F, true);
			}
		};
		this.addLayer(amethystArmor);
    }
	
	@Override
	protected void preRenderCallback(EntityAmethyst entitylivingbaseIn, float partialTickTime) {
		if (entitylivingbaseIn.isDefective()) {
			GlStateManager.scale(0.8F, 0.6F, 0.8F);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityAmethyst entity) {
		return new ResourceLocation("kagic:textures/entities/amethyst/amethyst.png");
	}
}
