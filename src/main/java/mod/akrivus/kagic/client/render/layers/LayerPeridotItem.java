package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPeridotItem implements LayerRenderer<EntityPeridot> {
    protected final RenderLivingBase<?> livingEntityRenderer;
    public LayerPeridotItem(RenderLivingBase<?> livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
    }
    
    @Override
    public void doRenderLayer(EntityPeridot peridot, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean rightHanded = peridot.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack leftStack = rightHanded ? peridot.getHeldItemOffhand() : peridot.getHeldItemMainhand();
        ItemStack rightStack = rightHanded ? peridot.getHeldItemMainhand() : peridot.getHeldItemOffhand();
        if (!leftStack.isEmpty() || !rightStack.isEmpty()) {
            GlStateManager.pushMatrix();
            this.renderHeldItem(peridot, rightStack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(peridot, leftStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderHeldItem(EntityPeridot peridot, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            if (peridot.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
            this.setSide(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            boolean isLefty = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(isLefty ? -1 : 1) / 10.0F, 0.125F, peridot.isDefective() ? -0.9F : -.75F);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(peridot, stack, camera, isLefty);
            GlStateManager.popMatrix();
        }
    }
    
    protected void setSide(EnumHandSide side) {
        ((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.025F, side);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
