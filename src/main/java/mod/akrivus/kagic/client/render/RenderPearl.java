package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelPearl;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerInsignia;
import mod.akrivus.kagic.client.render.layers.LayerNoDyeOverlay;
import mod.akrivus.kagic.client.render.layers.LayerPearlDress;
import mod.akrivus.kagic.client.render.layers.LayerPearlHair;
import mod.akrivus.kagic.client.render.layers.LayerPearlItem;
import mod.akrivus.kagic.client.render.layers.LayerPearlVisor;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderPearl extends RenderGemBase<EntityPearl> {
	private static final float OFFSET = .0f;

	public RenderPearl() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelPearl(), 0.25F);
        this.addLayer(new LayerPearlItem(this));
        this.addLayer(new LayerNoDyeOverlay(this));
        this.addLayer(new LayerInsignia(this));
        this.addLayer(new LayerPearlHair(this));
        this.addLayer(new LayerPearlDress(this));
        this.addLayer(new LayerPearlVisor(this));
        this.addLayer(new LayerGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }
	
	@Override
	protected void preRenderCallback(EntityPearl gem, float partialTickTime) {
		if (gem.getSpecialSkin().equals("_0")) {
			int skin = gem.generateSkinColor();
			float r = (float) ((skin & 16711680) >> 16) / 255f;
			float g = (float) ((skin & 65280) >> 8) / 255f;
			float b = (float) ((skin & 255) >> 0) / 255f;
			//KAGIC.instance.chatInfoMessage("Skin color is " + r + " , " + g + " , " + b);
			GlStateManager.color(r + this.OFFSET, g + this.OFFSET, b + this.OFFSET, 1f);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityPearl entity) {
		return new ResourceLocation("kagic:textures/entities/pearl/pearl" + entity.getSpecialSkin() + ".png");
	}
}
