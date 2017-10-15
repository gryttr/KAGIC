package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class ModelQuartz extends ModelBiped {
    private final ModelRenderer bipedCape;
    private final ModelRenderer witchHat;
    
    public ModelQuartz() {
    	this(0F, false);
    }
    
    public ModelQuartz(float modelSize, boolean isArmor) {
		super(modelSize, 0.0F, 64, isArmor ? 32 : 64);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		if (!isArmor) {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize);
		} else {
			this.bipedHead.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize + 0.5F);
		}
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
	    // Hair.
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4F, -12F, -4F, 8, 8, 8, modelSize + 1.1F);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Body.
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-5F, -4F, -3F, 10, 16, 6, modelSize);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
		this.bipedRightArm = new ModelRenderer(this, 48, 16);
		this.bipedRightArm.addBox(-4F, -4F, -2F, 4, 14, 4, modelSize);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
	    // Left arm.
		this.bipedLeftArm = new ModelRenderer(this, 48, 34);
		this.bipedLeftArm.addBox(0F, -4F, -2F, 4, 14, 4, modelSize);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
		// Right leg.
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(1F, 0F, -2F, 4, 12, 4, modelSize);
		this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
	  	// Left leg.
		this.bipedLeftLeg = new ModelRenderer(this, 0, 32);
		this.bipedLeftLeg.addBox(-5F, 0F, -2F, 4, 12, 4, modelSize);
		this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);

        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0F, -4.0F, -2.f, 10, 20, 1, modelSize);
        
        if (!isArmor && KAGIC.isHalloween()) {
	        float hatHeight = -4F;
	        this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 128);
	        this.witchHat.setRotationPoint(-6.0F, -10.03125F, -6.0F);
	        this.witchHat.setTextureOffset(0, 64).addBox(0.0F, hatHeight, 0.0F, 12, 2, 12, modelSize);
	        this.bipedHead.addChild(this.witchHat);
	        ModelRenderer middle = (new ModelRenderer(this)).setTextureSize(64, 128);
	        middle.setRotationPoint(2.75F, -4.0F, 3.0F);
	        middle.setTextureOffset(0, 78).addBox(0.0F, hatHeight, 0.0F, 7, 4, 7, modelSize);
	        middle.rotateAngleX = -0.05235988F;
	        middle.rotateAngleZ = 0.02617994F;
	        this.witchHat.addChild(middle);
	        ModelRenderer top = (new ModelRenderer(this)).setTextureSize(64, 128);
	        top.setRotationPoint(1.75F, -4.0F, 2.0F);
	        top.setTextureOffset(0, 89).addBox(0.0F, hatHeight, 0.0F, 4, 4, 4, modelSize);
	        top.rotateAngleX = -0.10471976F;
	        top.rotateAngleZ = 0.05235988F;
	        middle.addChild(top);
	        ModelRenderer tip = (new ModelRenderer(this)).setTextureSize(64, 128);
	        tip.setRotationPoint(1.75F, -2.0F, 2.0F);
	        tip.setTextureOffset(0, 97).addBox(0.0F, hatHeight, 0.0F, 1, 2, 1, 0.25F);
	        tip.rotateAngleX = -0.20943952F;
	        tip.rotateAngleZ = 0.10471976F;
	        top.addChild(tip);
        } else {
        	this.witchHat = null;
        }
    }
    
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	
    public void renderCape(float scale)
    {
        this.bipedCape.render(scale);
    }

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*
        if (entityIn.isSneaking()) {
            this.bipedCape.rotationPointY = 2.0F;
        } else {
            this.bipedCape.rotationPointY = 0.0F;
        }
        this.bipedCape.rotationPointY = -4.0F;
        this.bipedBody.rotationPointZ = -1f;*/
	}
	
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        
        ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
        if (itemstack != null && itemstack.getItem() == Items.BOW && ((EntityGem) entitylivingbaseIn).isSwingingArms()) {
            if (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT) {
                this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
            else {
                this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }
        super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
    }
}
