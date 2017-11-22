package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.client.model.ModelAgate;
import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.client.model.corrupted.ModelMoissanite;
import mod.akrivus.kagic.client.render.layers.LayerAgateBand;
import mod.akrivus.kagic.client.render.layers.LayerAgateHair;
import mod.akrivus.kagic.client.render.layers.LayerAgateItem;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerQuartzCape;
import mod.akrivus.kagic.client.render.layers.LayerUniform;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedMoissanite;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderCorruptedMoissanite extends RenderGemBase<EntityCorruptedMoissanite> {

	public RenderCorruptedMoissanite() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelMoissanite(), 0.5F);
	}
	
	@Override
	protected void preRenderCallback(EntityCorruptedMoissanite moissanite, float partialTickTime) {
		GlStateManager.scale(2F, 2F, 2F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCorruptedMoissanite moissanite) {
		return new ResourceLocation("kagic:textures/entities/corrupted/moissanite/moissanite.png");
	}
}
