package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class ModelLapisLazuli extends ModelBiped {
	private ModelRenderer bipedLeftWing;
	private ModelRenderer bipedRightWing;
	private final ModelRenderer witchHat;
	
	public ModelLapisLazuli() {
		super(0.0F, 0.0F, 64, 64);
		// Head.
		this.bipedHead = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 128);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);
		// Hair.
		this.bipedHeadwear = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 128);
		this.bipedHeadwear.addBox(-4F, -8F, -4F, 8, 8, 8, 1.1F);
		this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Body.
		this.bipedBody = (new ModelRenderer(this, 8, 16)).setTextureSize(64, 128);
		this.bipedBody.addBox(-3F, 0F, -2F, 6, 12, 4);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);
		// Right wing.
		this.bipedRightWing = (new ModelRenderer(this, 0, 32)).setTextureSize(64, 128);
        this.bipedRightWing.addBox(0F, -2F, 2F, 10, 20, 1);
        this.bipedRightWing.setRotationPoint(-5F, 0.5F, 0F);
		// Left wing.
		this.bipedLeftWing = (new ModelRenderer(this, 32, 32)).setTextureSize(64, 128);
		this.bipedLeftWing.addBox(-10F, -2F, 2F, 10, 20, 1);
		this.bipedLeftWing.setRotationPoint(-5F, 0.5F, 0F);
		// Right arm.
		this.bipedRightArm = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 128);
		this.bipedRightArm.addBox(0F, 0F, -1F, 2, 12, 2);
		this.bipedRightArm.setRotationPoint(0F, 0F, 0F);
		// Left arm.
		this.bipedLeftArm = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 128);
		this.bipedLeftArm.addBox(-2F, 0F, -1F, 2, 12, 2);
		this.bipedLeftArm.setRotationPoint(0F, 0F, 0F);
		// Right leg.
		this.bipedRightLeg = (new ModelRenderer(this, 28, 16)).setTextureSize(64, 128);
		this.bipedRightLeg.addBox(1F, 0F, -1F, 2, 12, 2);
		this.bipedRightLeg.setRotationPoint(0F, 12F, 0F);
		// Left leg.
		this.bipedLeftLeg = (new ModelRenderer(this, 28, 16)).setTextureSize(64, 128);
		this.bipedLeftLeg.addBox(-3F, 0F, -1F, 2, 12, 2);
		this.bipedLeftLeg.setRotationPoint(0F, 12F, 0F);

        if (KAGIC.isHalloween()) {
	        float hatHeight = -1F;
	        this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 128);
	        this.witchHat.setRotationPoint(-6.0F, -10.03125F, -6.0F);
	        this.witchHat.setTextureOffset(0, 64).addBox(0.0F, hatHeight, 0.0F, 12, 2, 12);
	        this.bipedHead.addChild(this.witchHat);
	        ModelRenderer middle = (new ModelRenderer(this)).setTextureSize(64, 128);
	        middle.setRotationPoint(2.75F, -4.0F, 3.0F);
	        middle.setTextureOffset(0, 78).addBox(0.0F, hatHeight, 0.0F, 7, 4, 7);
	        middle.rotateAngleX = -0.05235988F;
	        middle.rotateAngleZ = 0.02617994F;
	        this.witchHat.addChild(middle);
	        ModelRenderer top = (new ModelRenderer(this)).setTextureSize(64, 128);
	        top.setRotationPoint(1.75F, -4.0F, 2.0F);
	        top.setTextureOffset(0, 89).addBox(0.0F, hatHeight, 0.0F, 4, 4, 4);
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
	
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		if (entityIn instanceof EntityLapisLazuli) {
			EntityLapisLazuli lapis = (EntityLapisLazuli) entityIn;
			if (!lapis.isDefective()/* && !lapis.isFarmer()*/) {
				this.bipedRightWing.render(scale);
				this.bipedLeftWing.render(scale);
			}
		}
		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		float f = 0.2617994F;
        float f1 = -0.2617994F;
        float f2 = 0.0F;
        float f4 = 1.0F;
        if (entityIn.motionY < 0.0D) {
            Vec3d vec3d = (new Vec3d(entityIn.motionX, entityIn.motionY, entityIn.motionZ)).normalize();
            f4 = 1.0F - (float)Math.pow(-vec3d.y, 1.5D);
        }
        else if (entityIn.motionY > 0.0D) {
            Vec3d vec3d = (new Vec3d(entityIn.motionX, entityIn.motionY, entityIn.motionZ)).normalize();
            f4 = 1.0F + (float)Math.pow(vec3d.y, 1.5D);
        }
        f = f4 * 0.34906584F + (1.0F - f4) * f;
        f1 = f4 * -((float)Math.PI / 2F) + (1.0F - f4) * f1;
        this.bipedLeftWing.rotationPointX = 5.0F;
        this.bipedLeftWing.rotationPointY = f2;
        this.bipedLeftWing.rotateAngleX = f;
        this.bipedLeftWing.rotateAngleZ = f1;
        this.bipedRightWing.rotationPointX = -this.bipedLeftWing.rotationPointX;
        this.bipedRightWing.rotationPointY = this.bipedLeftWing.rotationPointY;
        this.bipedRightWing.rotateAngleX = this.bipedLeftWing.rotateAngleX;
        this.bipedRightWing.rotateAngleZ = -this.bipedLeftWing.rotateAngleZ;
        
        if (entityIn.isBeingRidden()) {
        	this.bipedLeftArm.rotateAngleX = (float)(-Math.PI / 2f);
        	this.bipedRightArm.rotateAngleX = (float)(-Math.PI / 2f);
        }
	}
}
