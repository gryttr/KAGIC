package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.client.render.RenderGemBase;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerSantaHat implements LayerRenderer<EntityGem> {
	private final RenderGemBase<?> gemRenderer;
	private final ModelGem gemModel;
	
	public LayerSantaHat(RenderGemBase<?> gemRenderer) {
		this.gemRenderer = gemRenderer;
		this.gemModel = (ModelGem) gemRenderer.getMainModel();
	}

	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		GlStateManager.color(1, 1, 1, 1F);
		GlStateManager.disableBlend();
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.renderWitchHat(scale);
	}

	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/other/santa_hat.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
