package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerSkin extends GemLayer implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	private float offset;
	private String name;
	
	public LayerSkin(RenderLivingBase<?> gemRenderer) {
		this(gemRenderer, 0F);
	}

	public LayerSkin(RenderLivingBase<?> gemRenderer, float offset) {
		this(gemRenderer, offset, null);
	}
	
	public LayerSkin(RenderLivingBase<?> gemRenderer, float offset, String name) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.offset = offset;
		this.name = name;
	}

	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		int skin = gem.getSkinColor();
		float r = (float) ((skin & 16711680) >> 16) / 255f;
		float g = (float) ((skin & 65280) >> 8) / 255f;
		float b = (float) ((skin & 255) >> 0) / 255f;
		//KAGIC.instance.chatInfoMessage("Skin color is " + r + " , " + g + " , " + b);
		GlStateManager.color(r + this.offset, g + this.offset, b + this.offset, 1f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}

	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/skin.png");
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
