package mod.akrivus.kagic.client.render;

import mod.akrivus.kagic.entity.EntityLaser;
import mod.akrivus.kagic.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderLaser extends Render<EntityLaser> {
	public RenderLaser() {
        super(Minecraft.getMinecraft().getRenderManager());
    }
    public void doRender(EntityLaser entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float )z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(ModItems.LASER_BEAM), ItemCameraTransforms.TransformType.GROUND);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    protected ResourceLocation getEntityTexture(EntityLaser entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
