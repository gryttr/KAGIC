package mod.akrivus.kagic.client.render.layers;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class LayerHair implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<EntityGem> gemRenderer;
	private final ModelBase gemModel;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayerHair(RenderLivingBase gemRendererIn) {
		this.gemRenderer = gemRendererIn;
		this.gemModel = gemRendererIn.getMainModel();
	}
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.gemRenderer.bindTexture(this.getHairStyle(gem, gem.getHairStyle()));
		this.gemModel.setModelAttributes(this.gemRenderer.getMainModel());
        this.gemModel.render(gem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
	@SuppressWarnings("unchecked")
	public ResourceLocation getHairStyle(EntityGem gem, int hairstyle) {
		try {
			Class<EntityGem> gemClass = (Class<EntityGem>) ModEntities.GEMS.get(this.getName(gem));
			ArrayList<ResourceLocation> hairstyles = (ArrayList<ResourceLocation>) gemClass.getField((this.getName(gem) + "_hair_styles").toUpperCase()).get(null);
			return hairstyles.get(hairstyle);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error creating gem: " + e.getMessage());
		}
		return null;
	}
	public String getName(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
        if (loc != null) {
        	return loc.toString().replaceFirst("kagic:kagic.", "");
        }
        else {
        	return "gem";
        }
	}
	public boolean shouldCombineTextures() {
		return false;
	}
}
