package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.gem.fusion.EntityRubyFusion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerRubyFusionItem implements LayerRenderer<EntityRubyFusion> {
	protected final RenderLivingBase<EntityRubyFusion> livingEntityRenderer;
	
	public LayerRubyFusionItem(RenderLivingBase<EntityRubyFusion> livingEntityRenderer) {
		this.livingEntityRenderer = livingEntityRenderer;
	}
	
	@Override
	public void doRenderLayer(EntityRubyFusion rubyFusion, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		boolean flag = rubyFusion.getPrimaryHand() == EnumHandSide.LEFT;
		ItemStack mainHand = flag ? rubyFusion.getHeldItemOffhand() : rubyFusion.getHeldItemMainhand();
		ItemStack offHand = flag ? rubyFusion.getHeldItemMainhand() : rubyFusion.getHeldItemOffhand();
		if (!mainHand.isEmpty() || !offHand.isEmpty()) {
			GlStateManager.pushMatrix();
			this.renderHeldItem(rubyFusion, mainHand, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
			this.renderHeldItem(rubyFusion, offHand, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
			GlStateManager.popMatrix();
		}
	}
	
	private void renderHeldItem(EntityRubyFusion rubyFusion, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
		if (!stack.isEmpty()) {
			GlStateManager.pushMatrix();
			if (rubyFusion.isSneaking()) {
				GlStateManager.translate(0.0F, 0.2F, 0.0F);
			}
			this.setSide(handSide);
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			boolean flag = handSide == EnumHandSide.LEFT;
			GlStateManager.translate((float)(flag ? -1 : 1) / 16F, 0.125F, -0.5F);
			Minecraft.getMinecraft().getItemRenderer().renderItemSide(rubyFusion, stack, camera, flag);
			GlStateManager.popMatrix();
		}
	}
	protected void setSide(EnumHandSide side) {
		((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, side);
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
