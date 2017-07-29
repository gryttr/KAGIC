package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.model.ModelPearl;
import mod.akrivus.kagic.client.render.RenderPearl;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

public class LayerPearlHair implements LayerRenderer<EntityPearl> {
	private final RenderPearl pearlRenderer;
	private final ModelPearl pearlModel = new ModelPearl();
	public LayerPearlHair(RenderPearl pearlRendererIn) {
		this.pearlRenderer = pearlRendererIn;
	}
	public void doRenderLayer(EntityPearl gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (gem.getSpecialSkin().equals("_0")) {
			this.pearlRenderer.bindTexture(EntityPearl.PEARL_HAIR_STYLES.get(gem.getHairStyle()));
			float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getHairColor()]);
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			this.pearlModel.setModelAttributes(this.pearlRenderer.getMainModel());
			this.pearlModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
	public boolean shouldCombineTextures() {
		return false;
	}
}
