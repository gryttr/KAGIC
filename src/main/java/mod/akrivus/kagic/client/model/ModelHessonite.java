package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class ModelHessonite extends ModelGem {
	private final ModelRenderer bipedCape;
	private final ModelRenderer bipedCapeBack;
	private final ModelRenderer bipedRightLowerLeg;
	private final ModelRenderer bipedLeftLowerLeg;
	private final ModelRenderer bipedHips;
	
	public ModelHessonite() {
		this(0, false);
	}

	public ModelHessonite(float modelSize, boolean isArmor) {
		super(modelSize, 0.0F, 64, isArmor ? 32 : 64, isArmor, -10F);
		// Head
		this.bipedHead = new ModelRenderer(this, 0, 0);
		if (!isArmor) {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize);
		} else {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize + 0.5F);
		}
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (!isArmor && (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas())) {
			this.bipedHead.addChild(this.witchHat);
		}
		
		// Hair
		this.bipedHeadwear = new ModelRenderer(this, 20, 42);
		this.bipedHeadwear.addBox(-7F, -19F, -3.5F, 14, 14, 8, modelSize);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Body
		this.bipedBody = new ModelRenderer(this, 10, 21);
		this.bipedBody.addBox(-4F, -4F, -3F, 8, 10, 6, modelSize);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		// Hips
		this.bipedHips = new ModelRenderer(this, 32, 0);
		this.bipedHips.addBox(-5F, 6F, -3F, 10, 4, 6, modelSize);
		this.bipedHips.setRotationPoint(0F, 0F, 0F);
		this.bipedBody.addChild(this.bipedHips);
		// Right arm
		this.bipedRightArm = new ModelRenderer(this, 44, 10);
		this.bipedRightArm.addBox(-2F, -4F, -1.5F, 3, 14, 3, modelSize);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
		// Left arm
		this.bipedLeftArm = new ModelRenderer(this, 32, 10);
		this.bipedLeftArm.addBox(-1F, -4F, -1.5F, 3, 14, 3, modelSize);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
		// Right leg
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(0.5F, -2F, -2F, 4, 4, 4, modelSize);
		this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
	  	// Left leg
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.addBox(-4.5F, -2F, -2F, 4, 4, 4, modelSize);
		this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);

		// Right lower leg
		this.bipedRightLowerLeg = new ModelRenderer(this, 56, 10);
		this.bipedRightLowerLeg.addBox(1.5F, 2F, -1F, 2, 10, 2, modelSize);
		this.bipedRightLowerLeg.setRotationPoint(0F, 0F, 0F);
		this.bipedRightLeg.addChild(this.bipedRightLowerLeg);
	  	// Left lower leg
		this.bipedLeftLowerLeg = new ModelRenderer(this, 56, 10);
		this.bipedLeftLowerLeg.addBox(-3.5F, 2F, -1F, 2, 10, 2, modelSize);
		this.bipedLeftLowerLeg.setRotationPoint(0F, 0F, 0F);
		this.bipedLeftLeg.addChild(this.bipedLeftLowerLeg);

		this.bipedCape = new ModelRenderer(this, 0, 0);
		this.bipedCape.setTextureSize(64, 64);
		this.bipedCape.addBox(-15.0F, -4.0F, -1.5f, 30, 20, 1, modelSize);
		
		this.bipedCapeBack = new ModelRenderer(this, 0, 22);
		this.bipedCapeBack.setTextureSize(64, 64);
		this.bipedCapeBack.addBox(-15.0F, -4.0F, -1.525f, 30, 20, 1, modelSize);
		this.bipedCape.addChild(this.bipedCapeBack);
	}
	
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
		this.rightArmPose = ModelBiped.ArmPose.EMPTY;
		this.leftArmPose = ModelBiped.ArmPose.EMPTY;
		if (entitylivingbaseIn instanceof EntityGem) {
			ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
			EntityGem gem = (EntityGem) entitylivingbaseIn;
			if (itemstack != null && itemstack.getItem() == Items.BOW && gem.isSwingingArms()) {
				if (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT) {
					this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
				}
				else {
					this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
				}
			}
		}
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
	}

	@Override
	public void renderCape(float scale)
	{
		this.bipedCape.render(scale);
	}
}
