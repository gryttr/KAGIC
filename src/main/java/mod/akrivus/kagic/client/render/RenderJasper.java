package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerJasperMark1;
import mod.akrivus.kagic.client.render.layers.LayerJasperMark2;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerQuartzItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderJasper extends RenderGemBase<EntityJasper> {
	public RenderJasper() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelQuartz(), 0.25F);
		for (Iterator<LayerRenderer<EntityJasper>> iter = this.layerRenderers.iterator(); iter.hasNext();) {
			LayerRenderer layer = iter.next();
			if (layer instanceof LayerHeldItem) {
				iter.remove();
			}
		}
		
		this.addLayer(new LayerQuartzItem(this));
		this.addLayer(new LayerSkin(this));
		this.addLayer(new LayerJasperMark1(this));
		this.addLayer(new LayerJasperMark2(this));
		this.addLayer(new LayerUniform(this));
		this.addLayer(new LayerInsignia(this));
		this.addLayer(new LayerHair(this));
		this.addLayer(new LayerVisor(this));
		this.addLayer(new LayerQuartzCape(this));
		this.addLayer(new LayerGemPlacement(this));
		LayerBipedArmor jasperArmor = new LayerBipedArmor(this) {
			@Override
			protected void initArmor() {
				this.modelLeggings = new ModelQuartz(0.5F, true);
				this.modelArmor = new ModelQuartz(1F, true);
			}
		};
		this.addLayer(jasperArmor);
		
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
	}
		
	@Override
	protected void preRenderCallback(EntityJasper jasper, float partialTickTime) {
		if (jasper.isDefective()) {
			GlStateManager.scale(0.7F, 1.0F, 0.7F);
		} else if (jasper.isPrimary()) {
			GlStateManager.scale(1.1F, 1.1F, 1.1F);
		}
	}	
	
	@Override
	protected ResourceLocation getEntityTexture(EntityJasper entity) {
		return new ResourceLocation("kagic:textures/entities/jasper/" + entity.getSpecialSkin() + "jasper.png");
	}
}
