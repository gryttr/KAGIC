package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderRutile;
import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerRutileItem implements LayerRenderer<EntityRutile> {
	protected final RenderRutile livingEntityRenderer;
	
	public LayerRutileItem(RenderRutile renderRutile) {
		this.livingEntityRenderer = renderRutile;
	}

	@Override
	public void doRenderLayer(EntityRutile entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
		ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
		ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
		if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
			GlStateManager.pushMatrix();
			this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}
	
	private void renderHeldItem(EntityRutile entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (((EntityRutile) entity).isDefective()) {
			if (!stack.isEmpty()) {
				GlStateManager.pushMatrix();
				if (entity.isSneaking()) {
					GlStateManager.translate(0.0F, 0.2F, 0.0F);
				}
				this.setSide(handSide);
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				boolean flag = handSide == EnumHandSide.LEFT;
				GlStateManager.translate((float)(flag ? -1 : 1) / 5.5F, 0.1F, -0.9F);
				Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
				GlStateManager.popMatrix();
			}
		}
		else {
			if (!stack.isEmpty()) {
				GlStateManager.pushMatrix();
				if (entity.isSneaking()) {
					GlStateManager.translate(0.0F, 0.2F, 0.0F);
				}
				this.setSide(handSide);
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				boolean flag = handSide == EnumHandSide.LEFT;
				GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.75F);
				Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
				GlStateManager.popMatrix();
			}
			
		}
	}
	
	protected void setSide(EnumHandSide side) {
		((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.04F, side);
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}