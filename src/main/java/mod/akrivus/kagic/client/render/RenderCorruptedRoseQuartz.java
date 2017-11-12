package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.model.corrupted.ModelCorruptedQuartz;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerHair;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerSkin;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedRoseQuartz;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCorruptedRoseQuartz extends RenderLiving<EntityCorruptedRoseQuartz> {
	
	public RenderCorruptedRoseQuartz() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelCorruptedQuartz(), 2F);

		this.addLayer(new LayerSkin(this, 0F, "corrupted/rose_quartz"));
		this.addLayer(new LayerHair(this, 0F, "corrupted/rose_quartz"));
		this.addLayer(new LayerGemPlacement(this, "corrupted/rose_quartz"));
		/*
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		}*/
	}
			
	@Override
	protected void preRenderCallback(EntityCorruptedRoseQuartz rose, float partialTickTime) {
		GlStateManager.scale(2F, 2F, 2F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCorruptedRoseQuartz rose) {
		return new ResourceLocation("kagic:textures/entities/corrupted/rose_quartz/rose_quartz.png");
	}
}
