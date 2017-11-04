package mod.akrivus.kagic.client.render.layers;

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

public class LayerInsignia implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final ModelBase gemModel;
	private final String name;
	
	public LayerInsignia(RenderLivingBase<?> gemRenderer) {
		this(gemRenderer, null);
	}
	
	public LayerInsignia(RenderLivingBase<?> gemRenderer, String name) {
		this.gemRenderer = gemRenderer;
		this.gemModel = gemRenderer.getMainModel();
		this.name = name;
	}
	
	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getTexture(gem));
		float[] insigniaColor = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getInsigniaColor()]);
		GlStateManager.color(insigniaColor[0] * 2, insigniaColor[1] * 2, insigniaColor[2] * 2, 0.99f);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.disableBlend();
	}
	
	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (gem.hasInsigniaVariant(gem.getGemPlacement())) {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/insignia_" + gem.getGemPlacement().toString().toLowerCase() + ".png");
		} else {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/insignia.png");
		}
	}
	
	public String getName(EntityGem gem) {
		if (this.name == null) {
			ResourceLocation loc = EntityList.getKey(gem);
			if (loc.getResourceDomain().equals("kagic")) {
		        return loc.getResourcePath().replaceFirst("kagic.", "");
			}
			else {
		        return loc.getResourcePath();
			}
		} else {
			return this.name;
		}
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
