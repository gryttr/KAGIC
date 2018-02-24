package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.client.model.ModelNothing;
import mod.akrivus.kagic.client.model.ModelRutile;
import mod.akrivus.kagic.client.model.ModelTwinRutile;
import mod.akrivus.kagic.client.render.layers.LayerBirthdayHat;
import mod.akrivus.kagic.client.render.layers.LayerRutileGemPlacement;
import mod.akrivus.kagic.client.render.layers.LayerRutileHair;
import mod.akrivus.kagic.client.render.layers.LayerRutileInsignia;
import mod.akrivus.kagic.client.render.layers.LayerRutileItem;
import mod.akrivus.kagic.client.render.layers.LayerRutileModel;
import mod.akrivus.kagic.client.render.layers.LayerRutileSkin;
import mod.akrivus.kagic.client.render.layers.LayerRutileUniform;
import mod.akrivus.kagic.client.render.layers.LayerRutileVisor;
import mod.akrivus.kagic.client.render.layers.LayerSantaHat;
import mod.akrivus.kagic.client.render.layers.LayerWitchHat;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderRutile extends RenderGemBase<EntityRutile> {
	private ModelGem normalRutile = new ModelRutile();
	private ModelGem twinRutile = new ModelTwinRutile();
	public RenderRutile() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelNothing(), 0.25F);
        this.addLayer(new LayerRutileModel(this));
        this.addLayer(new LayerRutileItem(this));
        this.addLayer(new LayerRutileSkin(this));
        this.addLayer(new LayerRutileUniform(this));
        this.addLayer(new LayerRutileInsignia(this));
        this.addLayer(new LayerRutileHair(this));
        this.addLayer(new LayerRutileVisor(this));
        this.addLayer(new LayerRutileGemPlacement(this));
		if (KAGIC.isBirthday()) {
			this.addLayer(new LayerBirthdayHat(this));
		} else if (KAGIC.isHalloween()) {
			this.addLayer(new LayerWitchHat(this));
		} else if (KAGIC.isChristmas()) {
			this.addLayer(new LayerSantaHat(this));
		}
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityRutile entity) {
		return new ResourceLocation("kagic:textures/entities/rutile/rutile.png");
	}
	
	public ModelGem getModel(boolean defective) {
		if (defective) {
			return this.twinRutile;
		}
		return this.normalRutile;
	}
}
