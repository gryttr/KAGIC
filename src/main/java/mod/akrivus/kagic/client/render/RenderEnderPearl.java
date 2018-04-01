package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPearl;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerDiamondGlow;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerPearlDress;
import mod.akrivus.kagic.client.render.layers.LayerPearlHair;
import mod.akrivus.kagic.client.render.layers.LayerPearlItem;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityEnderPearl;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class RenderEnderPearl extends RenderGemBase<EntityEnderPearl> {
	private static final float OFFSET = .75f;

	public RenderEnderPearl() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPearl(), 0.25F);
        this.addLayer(new LayerPearlItem(this));
        this.addLayer(new LayerPearlHair(this));
        this.addLayer(new LayerPearlDress(this));
        this.addLayer(new LayerGemPlacement(this, "pearl", true));
        this.addLayer(new LayerDiamondGlow(this, "pearl"));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	
	@Override
	protected void preRenderCallback(EntityEnderPearl gem, float partialTickTime) {
		if (gem.getSpecialSkin().equals("_0")) {
			float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[((EntityPearl) gem).getColor()]);
			GlStateManager.color(afloat[0] + OFFSET, afloat[1] + OFFSET, afloat[2] + OFFSET);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityEnderPearl entity) {
		return new ResourceLocation("kagic:textures/entities/pearl/ender_pearl.png");
	}
}
