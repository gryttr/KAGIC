package mod.akrivus.kagic.client.model;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPeridot extends ModelGem {
	private ModelRenderer squareHair;

	private ModelRenderer normalBody;
	private ModelRenderer normalRightArm;
	private ModelRenderer normalLeftArm;
	private ModelRenderer normalRightLeg;
	private ModelRenderer normalLeftLeg;
	
	private ModelRenderer smallBody;
	private ModelRenderer smallRightArm;
	private ModelRenderer smallLeftArm;
	private ModelRenderer smallRightLeg;
	private ModelRenderer smallLeftLeg;
	
	public ModelPeridot() {
		super(0.0F, 0.0F, 64, 64, false, -0F);
		// Head.
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -4F, -4F, 2, 2, 2);
	    this.bipedHead.setRotationPoint(0F, 0F, 0F);
		if (KAGIC.isHalloween() || KAGIC.isBirthday() || KAGIC.isChristmas()) {
			this.bipedHead.addChild(this.witchHat);
		}

		// Hair.
		this.bipedHeadwear = new ModelRenderer(this, 2, 2);
		this.bipedHeadwear.addBox(-5.5F, -5.5F, -3.5F, 11, 11, 7);
	    this.bipedHeadwear.setRotationPoint(0F, 0F, 0F);
		// Squaridot
		this.squareHair = new ModelRenderer(this, 0, 45);
		this.squareHair.addBox(-7F, -8F, -3.49F, 14, 12, 7);
	    this.squareHair.setRotationPoint(0F, 0F, 0F);
	    // Body.
	    this.normalBody = new ModelRenderer(this, 8, 20);
	    this.normalBody.addBox(-3F, 4F, -2F, 6, 10, 4);
	    this.normalBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
	    this.normalRightArm = new ModelRenderer(this, 28, 20);
	    this.normalRightArm.addBox(0F, -4F, -1F, 2, 10, 2);
	    this.normalRightArm.setRotationPoint(0F, 8F, 0F);
	    // Left arm.
	    this.normalLeftArm = new ModelRenderer(this, 36, 20);
	    this.normalLeftArm.addBox(-2F, -4F, -1F, 2, 10, 2);
	    this.normalLeftArm.setRotationPoint(0F, 8F, 0F);
	    // Right leg.
	    this.normalRightLeg = new ModelRenderer(this, 0, 20);
	    this.normalRightLeg.addBox(-3F, 2F, -1F, 2, 10, 2);
	    this.normalRightLeg.setRotationPoint(0F, 8F, 0F);
	    // Left leg.
	    this.normalLeftLeg = new ModelRenderer(this, 0, 20);
	    this.normalLeftLeg.addBox(1F, 2F, -1F, 2, 10, 2);
	    this.normalLeftLeg.setRotationPoint(0F, 8F, 0F);

	    /* Defective parts */
	    
	    // Body.
	    this.smallBody = new ModelRenderer(this, 8, 20);
	    this.smallBody.addBox(-3F, 8F, -2F, 6, 8, 4);
	    this.smallBody.setRotationPoint(0F, 0F, 0F);
	    // Right arm.
	    this.smallRightArm = new ModelRenderer(this, 28, 20);
	    this.smallRightArm.addBox(0F, 0F, -1F, 2, 8, 2);
	    this.smallRightArm.setRotationPoint(0F, 8F, 0F);
	    // Left arm.
	    this.smallLeftArm = new ModelRenderer(this, 36, 20);
	    this.smallLeftArm.addBox(-2F, 0F, -1F, 2, 8, 2);
	    this.smallLeftArm.setRotationPoint(0F, 8F, 0F);
	    // Right leg.
	    this.smallRightLeg = new ModelRenderer(this, 0, 20);
	    this.smallRightLeg.addBox(-3F, 4F, -1F, 2, 8, 2);
	    this.smallRightLeg.setRotationPoint(0F, 8F, 0F);
	    // Left leg.
	    this.smallLeftLeg = new ModelRenderer(this, 0, 20);
	    this.smallLeftLeg.addBox(1F, 4F, -1F, 2, 8, 2);
	    this.smallLeftLeg.setRotationPoint(0F, 8F, 0F);

	    this.bipedHead.addChild(this.bipedHeadwear);
	    this.bipedHead.addChild(this.squareHair);
	}
	
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entity instanceof EntityPeridot) {
			EntityPeridot peridot = (EntityPeridot) entity;
			if (!peridot.isDefective()) {
				this.bipedBody = this.normalBody;
				this.bipedRightArm = this.normalRightArm;
				this.bipedLeftArm = this.normalLeftArm;
				this.bipedRightLeg = this.normalRightLeg;
				this.bipedLeftLeg = this.normalLeftLeg;
			} else {
				this.bipedBody = this.smallBody;
				this.bipedRightArm = this.smallRightArm;
				this.bipedLeftArm = this.smallLeftArm;
				this.bipedRightLeg = this.smallRightLeg;
				this.bipedLeftLeg = this.smallLeftLeg;
			}
		}
		else {
			this.bipedBody = this.normalBody;
			this.bipedRightArm = this.normalRightArm;
			this.bipedLeftArm = this.normalLeftArm;
			this.bipedRightLeg = this.normalRightLeg;
			this.bipedLeftLeg = this.normalLeftLeg;
		}
		
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		
		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);
	}
	
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.bipedHeadwear.rotateAngleX = 0;
		this.bipedHeadwear.rotateAngleY = 0;
		this.bipedHeadwear.rotateAngleZ = 0.7853982f;
		if (entity instanceof EntityPeridot) {
			EntityPeridot peridot = (EntityPeridot) entity;
			if (!peridot.isDefective()) {
				this.bipedHead.offsetY = -0.2F;
			} else {
				this.bipedHead.offsetY = .05F;
			}
		}
		else {
			this.bipedHead.offsetY = -0.2F;
		}
	}
	
	@Override
	public void renderWitchHat(float scale) {
		boolean isHidden = this.squareHair.isHidden;
		this.squareHair.isHidden = true;
		this.bipedHead.render(scale);
		this.squareHair.isHidden = isHidden;
	}
}