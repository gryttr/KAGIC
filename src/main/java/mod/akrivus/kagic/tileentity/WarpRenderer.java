package mod.akrivus.kagic.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WarpRenderer extends TileEntitySpecialRenderer<TileEntityWarpPadCore> {
	private static final ResourceLocation warpStream = new ResourceLocation("kagic:textures/blocks/warpstream.png");
	private static final double height = 6;
	
	private void renderVertex(BufferBuilder renderer, double x, double y, double z, double u, double v) {
		renderer.pos(x, y, z).tex(u, v).color(1.0f, 1.0f, 1.0f, 0.75f).lightmap(0, 255).endVertex();
	}

	@Override
	public void render(TileEntityWarpPadCore tilePad, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (tilePad.isWarping() && tilePad.isValidPad()) {
			Tessellator tessellator = Tessellator.getInstance();
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y + 1.0, z);

			GlStateManager.enableBlend();
			
			GlStateManager.enableAlpha();
			//GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			GlStateManager.disableCull();
			
			GlStateManager.depthMask(false);
			GlStateManager.enableDepth();

			GlStateManager.enableTexture2D();
			
			ResourceLocation beamIcon = warpStream;
			bindTexture(beamIcon);

			long ticks = (System.currentTimeMillis() / 100) % 10;
			float i1 = 0;//ticks / 10.0f;
			float i2 = 1;//i1 + .1f;

			GlStateManager.color(1, 1, 1, 1);

			BufferBuilder renderer = tessellator.getBuffer();
			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

			double offset = -1.0;
			double beamPercent = tilePad.renderCooldown > 0 ? 0 : ((double) tilePad.renderTicks / (double) tilePad.warpTicks) - partialTicks / (double) tilePad.warpTicks;
			double topHeight = this.height - beamPercent * this.height;
			
			renderVertex(renderer, offset, topHeight - 1, offset, 1, .5);		// -|
			renderVertex(renderer, 1-offset, topHeight - 1, offset, 0, .5);	// |-
			renderVertex(renderer, 1-offset, 0, offset, 0, 1);					// |_
			renderVertex(renderer, offset, 0, offset, 1, 1);					// _|

			renderVertex(renderer, 1-offset, topHeight - 1, 1-offset, 1, .5);
			renderVertex(renderer, offset, topHeight - 1, 1-offset, 0, .5);
			renderVertex(renderer, offset, 0, 1-offset, 0, 1);
			renderVertex(renderer, 1-offset, 0, 1-offset, 1, 1);

			renderVertex(renderer, offset, topHeight - 1, 1-offset, 1, .5);
			renderVertex(renderer, offset, topHeight - 1, offset, 0, .5);
			renderVertex(renderer, offset, 0, offset, 0, 1);
			renderVertex(renderer, offset, 0, 1-offset, 1, 1);

			renderVertex(renderer, 1-offset, topHeight - 1, offset, 1, .5);
			renderVertex(renderer, 1-offset, topHeight - 1, 1-offset, 0, .5);
			renderVertex(renderer, 1-offset, 0, 1-offset, 0, 1);
			renderVertex(renderer, 1-offset, 0, offset, 1, 1);

			// TOP //

			renderVertex(renderer, offset, topHeight, offset, 1, 0);			// -|
			renderVertex(renderer, 1-offset, topHeight, offset, 0, 0);			// |-
			renderVertex(renderer, 1-offset, topHeight - 1, offset, 0, .5);		// |_
			renderVertex(renderer, offset, topHeight - 1, offset, 1, .5);		// _|

			renderVertex(renderer, 1-offset, topHeight, 1-offset, 1, 0);
			renderVertex(renderer, offset, topHeight, 1-offset, 0, 0);
			renderVertex(renderer, offset, topHeight - 1, 1-offset, 0, .5);
			renderVertex(renderer, 1-offset, topHeight - 1, 1-offset, 1, .5);

			renderVertex(renderer, offset, topHeight, 1-offset, 1, 0);
			renderVertex(renderer, offset, topHeight, offset, 0, 0);
			renderVertex(renderer, offset, topHeight - 1, offset, 0, .5);
			renderVertex(renderer, offset, topHeight - 1, 1-offset, 1, .5);

			renderVertex(renderer, 1-offset, topHeight, offset, 1, 0);
			renderVertex(renderer, 1-offset, topHeight, 1-offset, 0, 0);
			renderVertex(renderer, 1-offset, topHeight - 1, 1-offset, 0, .5);
			renderVertex(renderer, 1-offset, topHeight - 1, offset, 1, .5);

			tessellator.draw();
			
			if (tilePad.renderTicks > 0) {
				--tilePad.renderTicks;
			}
			if (tilePad.renderCooldown > 0) {
				--tilePad.renderCooldown;
			}
			if (tilePad.renderTicks == 0 && tilePad.renderCooldown == 0) {
				tilePad.renderCooldown = tilePad.warpCooldownTicks;
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableLighting();
			//GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			
			GlStateManager.popMatrix();
		}
	}
}
