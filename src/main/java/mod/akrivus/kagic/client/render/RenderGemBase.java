package mod.akrivus.kagic.client.render;

import java.util.Iterator;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import net.minecraft.client.model.ModelBiped;
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
}
