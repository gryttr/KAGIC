package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPearl;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerPearlDress;
import mod.akrivus.kagic.client.render.layers.LayerPearlHair;
import mod.akrivus.kagic.client.render.layers.LayerPearlItem;
import mod.akrivus.kagic.client.render.layers.LayerVisor;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderPearl extends RenderLivingBase<EntityPearl> {
	public RenderPearl() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPearl(), 0.25F);
        this.addLayer(new LayerPearlItem(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerPearlHair(this));
        this.addLayer(new LayerPearlDress(this));
        this.addLayer(new LayerVisor(this));
        this.addLayer(new LayerGemPlacement(this));
    }
	protected void preRenderCallback(EntityPearl gem, float partialTickTime) {
		if (gem.getSpecialSkin().equals("_0")) {
			float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[((EntityPearl) gem).getColor()]);
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
		}
	}
	protected ResourceLocation getEntityTexture(EntityPearl entity) {
		return new ResourceLocation("kagic:textures/entities/pearl/pearl" + entity.getSpecialSkin() + ".png");
	}
}
