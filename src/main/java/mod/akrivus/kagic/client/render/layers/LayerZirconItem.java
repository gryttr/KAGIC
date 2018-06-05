package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.render.RenderTopaz;
import mod.akrivus.kagic.client.render.RenderZircon;
import mod.akrivus.kagic.entity.gem.EntityZircon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerZirconItem implements LayerRenderer<EntityZircon> {
    protected final RenderZircon zirconRenderer;
    
    public LayerZirconItem(RenderZircon renderZircon) {
        this.zirconRenderer = renderZircon;
    }
    
    @Override
    public void doRenderLayer(EntityZircon zircon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        boolean flag = zircon.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? zircon.getHeldItemOffhand() : zircon.getHeldItemMainhand();
        ItemStack itemstack1 = flag ? zircon.getHeldItemMainhand() : zircon.getHeldItemOffhand();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            GlStateManager.pushMatrix();
            this.renderHeldItem(zircon, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            this.renderHeldItem(zircon, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }
    
    private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType camera, EnumHandSide handSide) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof ItemEnchantedBook) { 
	        	GlStateManager.pushMatrix();
	            if (entity.isSneaking()) {
	                GlStateManager.translate(0.0F, 0.2F, 0.0F);
	            }
	            this.setSide(handSide);
	            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
	            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
	            GlStateManager.rotate(210.0F, 0.0F, 0.0F, 1.0F);
	            boolean lefty = handSide == EnumHandSide.LEFT;
	            GlStateManager.translate(0.725F, -0.7, lefty ? .5F : 0.0F);
	            Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, lefty);
	            GlStateManager.popMatrix();
            }
            else {
            	GlStateManager.pushMatrix();
    			if (entity.isSneaking()) {
    				GlStateManager.translate(0.0F, 0.2F, 0.0F);
    			}
    			this.setSide(handSide);
    			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
    			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    			boolean flag = handSide == EnumHandSide.LEFT;
    			GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.7F);
    			Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, camera, flag);
    			GlStateManager.popMatrix();
            }
        }
    }
    
    protected void setSide(EnumHandSide side) {
        ((ModelBiped) this.zirconRenderer.getMainModel()).postRenderArm(0.0625F, side);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
