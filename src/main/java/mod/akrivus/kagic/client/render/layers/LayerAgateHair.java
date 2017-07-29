package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.model.ModelAgate;
import mod.akrivus.kagic.client.render.RenderAgate;
import mod.akrivus.kagic.entity.gem.EntityAgate;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

public class LayerAgateHair implements LayerRenderer<EntityAgate> {
	private final RenderAgate agateRenderer;
	private final ModelAgate agateModel = new ModelAgate();
	public LayerAgateHair(RenderAgate agateRendererIn) {
		this.agateRenderer = agateRendererIn;
	}
	public void doRenderLayer(EntityAgate gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!gem.isHolly()) {
			this.agateRenderer.bindTexture(EntityAgate.AGATE_HAIR_STYLES.get(gem.getHairStyle()));
			float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getColor()]);
			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			this.agateModel.setModelAttributes(this.agateRenderer.getMainModel());
			this.agateModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
	public boolean shouldCombineTextures() {
		return false;
	}
}
