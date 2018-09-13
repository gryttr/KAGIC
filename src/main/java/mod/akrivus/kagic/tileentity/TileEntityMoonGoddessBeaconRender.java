package mod.akrivus.kagic.tileentity;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityMoonGoddessBeaconRender extends TileEntitySpecialRenderer<TileEntityMoonGoddessStatue> {
	public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");
	public static final float[] COLORS = new float[] {1F, 1F, 1F};

	@Override
	public void render(TileEntityMoonGoddessStatue statue, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.alphaFunc(516, 0.1F);
        this.bindTexture(TEXTURE_BEACON_BEAM);

        float beamRenderScale = statue.calculateBeamRenderScale();

        if (beamRenderScale > 0.0D)
        {
            GlStateManager.disableFog();

            renderBeam(x, y, z, partialTicks, beamRenderScale, statue.getWorld().getTotalWorldTime(), 512, COLORS, 0.375D, 0.4D);

            GlStateManager.enableFog();
        }
	}

    public static void renderBeam(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int height, float[] colors, double beamRadius, double glowRadius)
    {
        GlStateManager.glTexParameteri(3553, 10242, 10497);
        GlStateManager.glTexParameteri(3553, 10243, 10497);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        double d0 = totalWorldTime + partialTicks;
        double d1 = height < 0 ? d0 : -d0;
        double d2 = MathHelper.frac(d1 * 0.2D - (double)MathHelper.floor(d1 * 0.1D));
        float red = colors[0];
        float green = colors[1];
        float blue = colors[2];
        double d3 = d0 * 0.025D * -1.5D;
        double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * beamRadius;
        double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * beamRadius;
        double d6 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * beamRadius;
        double d7 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * beamRadius;
        double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * beamRadius;
        double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * beamRadius;
        double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * beamRadius;
        double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * beamRadius;
        double d12 = 0.0D;
        double d13 = 1.0D;
        double d14 = -1.0D + d2;
        double d15 = (double)height * textureScale * (0.5D / beamRadius) + d14;
        float midTransparency = 1.0F;
        float outerTransparency = 0.25F;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.depthMask(false);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(x + d4, y + (double)height, z + d5).tex(1.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d4, y, z + d5).tex(1.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d6, y, z + d7).tex(0.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d6, y + (double)height, z + d7).tex(0.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d10, y + (double)height, z + d11).tex(1.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d10, y, z + d11).tex(1.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d8, y, z + d9).tex(0.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d8, y + (double)height, z + d9).tex(0.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d6, y + (double)height, z + d7).tex(1.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d6, y, z + d7).tex(1.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d10, y, z + d11).tex(0.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d10, y + (double)height, z + d11).tex(0.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d8, y + (double)height, z + d9).tex(1.0D, d15).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d8, y, z + d9).tex(1.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d4, y, z + d5).tex(0.0D, d14).color(red, green, blue, midTransparency).endVertex();
        bufferbuilder.pos(x + d4, y + (double)height, z + d5).tex(0.0D, d15).color(red, green, blue, midTransparency).endVertex();
        tessellator.draw();
        d3 = 0.5D - glowRadius;
        d4 = 0.5D - glowRadius;
        d5 = 0.5D + glowRadius;
        d6 = 0.5D - glowRadius;
        d7 = 0.5D - glowRadius;
        d8 = 0.5D + glowRadius;
        d9 = 0.5D + glowRadius;
        d10 = 0.5D + glowRadius;
        d11 = 0.0D;
        d12 = 1.0D;
        d13 = -1.0D + d2;
        d14 = (double)height * textureScale + d13;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(x + d3, y + (double)height, z + d4).tex(1.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d3, y, z + d4).tex(1.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d5, y, z + d6).tex(0.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d5, y + (double)height, z + d6).tex(0.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d9, y + (double)height, z + d10).tex(1.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d9, y, z + d10).tex(1.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d7, y, z + d8).tex(0.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d7, y + (double)height, z + d8).tex(0.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d5, y + (double)height, z + d6).tex(1.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d5, y, z + d6).tex(1.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d9, y, z + d10).tex(0.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d9, y + (double)height, z + d10).tex(0.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d7, y + (double)height, z + d8).tex(1.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d7, y, z + d8).tex(1.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d3, y, z + d4).tex(0.0D, d13).color(red, green, blue, outerTransparency).endVertex();
        bufferbuilder.pos(x + d3, y + (double)height, z + d4).tex(0.0D, d14).color(red, green, blue, outerTransparency).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
    }

    @Override
    public boolean isGlobalRenderer(TileEntityMoonGoddessStatue te)
    {
        return true;
    }
}
