package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelGem extends ModelBiped {
	protected final ModelRenderer witchHat;

	public ModelGem(float modelSize, float p_i1149_2_, int textureWidth, int textureHeight, boolean isArmor, float hatHeight) {
		super(modelSize, p_i1149_2_, textureWidth, textureHeight);

		if (!isArmor && (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas())) {
			this.witchHat = (new ModelRenderer(this)).setTextureSize(128, 128);
			this.witchHat.setRotationPoint(-6.0F, -10.03125F, -6.0F);
			this.witchHat.setTextureOffset(64, 114).addBox(0.0F, hatHeight, 0.0F, 12, 2, 12);
			//this.bipedHead.addChild(this.witchHat);
			ModelRenderer middle = (new ModelRenderer(this)).setTextureSize(128, 128);
			middle.setRotationPoint(2.75F, -4.0F, 3.0F);
			middle.setTextureOffset(100, 114).addBox(0.0F, hatHeight, 0.0F, 7, 4, 7);
			middle.rotateAngleX = -0.05235988F;
			middle.rotateAngleZ = 0.02617994F;
			this.witchHat.addChild(middle);
			ModelRenderer top = (new ModelRenderer(this)).setTextureSize(128, 128);
			top.setRotationPoint(1.75F, -4.0F, 2.0F);
			top.setTextureOffset(112, 106).addBox(0.0F, hatHeight, 0.0F, 4, 4, 4);
			top.rotateAngleX = -0.10471976F;
			top.rotateAngleZ = 0.05235988F;
			middle.addChild(top);
			ModelRenderer tip = (new ModelRenderer(this)).setTextureSize(128, 128);
			tip.setRotationPoint(1.75F, -2.0F, 2.0F);
			tip.setTextureOffset(124, 106).addBox(0.0F, hatHeight, 0.0F, 1, 2, 1, 0.25F);
			tip.rotateAngleX = -0.20943952F;
			tip.rotateAngleZ = 0.10471976F;
			top.addChild(tip);
		} else {
			this.witchHat = null;
		}
	}
	
	public void renderWitchHat(float scale) {
		this.bipedHead.render(scale);
	}

	public void renderCape(float scale)	{}
}
