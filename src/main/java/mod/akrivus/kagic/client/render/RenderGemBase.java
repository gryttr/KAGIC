package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.ModConfigs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class RenderGemBase<T extends EntityGem> extends RenderBiped<T> {
	public RenderGemBase(RenderManager renderManager, ModelBiped modelBiped, float shadowSize) {
		super(renderManager, modelBiped, shadowSize);

		for (Iterator<LayerRenderer<T>> iter = this.layerRenderers.iterator(); iter.hasNext();) {
			LayerRenderer layer = iter.next();
			if (layer instanceof LayerHeldItem) {
				iter.remove();
			}
		}
	}
	protected void renderEntityName(T entityIn, double x, double y, double z, String name, double distanceSq) {
		if (ModConfigs.displayNames) {
			this.renderLivingLabel(entityIn, name, x, y+0.25, z, 64);
			this.renderLivingLabel(entityIn, "(" + entityIn.getSpecificName() + ")", x, y, z, 64);
		}
		else {
			this.renderLivingLabel(entityIn, name, x, y, z, 64);
		}
    }
}
