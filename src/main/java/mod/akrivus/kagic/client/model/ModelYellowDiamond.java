package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class ModelYellowDiamond extends ModelBiped {
	private ModelRenderer bipedNeck;
	private ModelRenderer bipedSkirt;
	private ModelRenderer bipedRightShoulder;
	private ModelRenderer bipedLeftShoulder;
	
	public ModelYellowDiamond() {
		super(0.0F, 0.0F, 128, 128);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-5F, -50F, -5F, 10, 12, 10);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
	    // Hair.
		this.bipedHeadwear = new ModelRenderer(this, 40, 0);
		this.bipedHeadwear.addBox(-5F, -50F, -5F, 10, 12, 10, 1.1F);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
	    // Neck.
		this.bipedNeck = new ModelRenderer(this, 80, 0);
		this.bipedNeck.addBox(-1F, -38F, -1F, 2, 6, 2);
		this.bipedNeck.setRotationPoint(0F, 0F, 0F);
		// Body.
		this.bipedBody = new ModelRenderer(this, 88, 28);
		this.bipedBody.addBox(-6F, -32F, -3F, 12, 24, 6);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		// Skirt.
		this.bipedSkirt = new ModelRenderer(this, 24, 28);
		this.bipedSkirt.addBox(-7F, -9F, 0F, 14, 28, 6);
		this.bipedSkirt.setRotationPoint(0F, 0F, 0F);
		// Right shoulder.
		this.bipedRightShoulder = new ModelRenderer(this, 88, 0);
		this.bipedRightShoulder.addBox(-16F, -34F, -4F, 10, 6, 8);
		this.bipedRightShoulder.setRotationPoint(0F, 0F, 0F);
		// Left shoulder.
		this.bipedLeftShoulder = new ModelRenderer(this, 88, 14);
		this.bipedLeftShoulder.addBox(6F, -34F, -4F, 10, 6, 8);
		this.bipedLeftShoulder.setRotationPoint(0F, 0F, 0F);
		// Right arm.
		this.bipedRightArm = new ModelRenderer(this, 64, 28);
		this.bipedRightArm.addBox(-7F, 0F, -2F, 4, 32, 4);
		this.bipedRightArm.setRotationPoint(0F, -32F, 0F);
		// Left arm.
		this.bipedLeftArm = new ModelRenderer(this, 64, 64);
		this.bipedLeftArm.addBox(3F, 0F, -2F, 4, 32, 4);
		this.bipedLeftArm.setRotationPoint(0F, -32F, 0F);
		// Right leg.
		this.bipedRightLeg = new ModelRenderer(this, 0, 22);
		this.bipedRightLeg.addBox(-6F, 0F, -3F, 6, 32, 6);
		this.bipedRightLeg.setRotationPoint(0F, 0F, 0F);
		// Left leg.
		this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
		this.bipedLeftLeg.addBox(0F, 0F, -3F, 6, 32, 6);
		this.bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
	}
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedNeck.render(scale);
		this.bipedBody.render(scale);
		this.bipedSkirt.render(scale);
		this.bipedRightShoulder.render(scale);
		this.bipedLeftShoulder.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		super.copyModelAngles(this.bipedHead, this.bipedNeck);
		this.bipedRightLeg.rotationPointY = -8F;
		this.bipedLeftLeg.rotationPointY = -8F;
		this.bipedSkirt.rotateAngleX = 0.2094395F;
		this.bipedHead.rotateAngleX = 0.0F;
		this.bipedHeadwear.rotateAngleX = 0.0F;
		this.bipedNeck.rotateAngleX = 0.0F;
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
