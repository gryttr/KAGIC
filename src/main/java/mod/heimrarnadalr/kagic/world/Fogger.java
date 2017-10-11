package mod.heimrarnadalr.kagic.world;

import org.lwjgl.opengl.GL11;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fogger {
	private static final ResourceLocation RES_UNDERTEARS_OVERLAY = new ResourceLocation("kagic:textures/blocks/rose_tears_overlay.png");
	private static final int FADE_TICKS = 60;
	private int fadeTicksLeft = 0;
	private float fogColor1;
	private float fogColor2;
	
	private static float[] getBiomeFogColor(Biome biome) {
		if (biome == ModBiomes.FLOATINGPEAKS) {
			return new float[] {247F / 255F, 220F / 255F, 218F / 255F};
		} else if (biome == ModBiomes.KINDERGARTEN) {
			return new float[] {215F / 255F, 211F / 255F, 221F / 255F};
		} else {
			return new float[] {0, 0, 0};
		}	
	}
	
	@SubscribeEvent
	public void onFogColor(EntityViewRenderEvent.FogColors event) {
		this.fogColor2 = this.fogColor1;
		Entity entity = event.getEntity();
		World world = entity.world;
		Biome biome = world.getBiome(entity.getPosition());
		if (biome == ModBiomes.FLOATINGPEAKS || biome == ModBiomes.KINDERGARTEN || this.fadeTicksLeft > 0) {
			float[] biomeColor = Fogger.getBiomeFogColor(biome);
			float red = event.getRed() + (biomeColor[0] - event.getRed()) * ((float) this.fadeTicksLeft / (float) Fogger.FADE_TICKS);
			float green = event.getGreen() + (biomeColor[1] - event.getGreen()) * ((float) this.fadeTicksLeft / (float) Fogger.FADE_TICKS);;
			float blue = event.getBlue() + (biomeColor[2] - event.getBlue()) * ((float) this.fadeTicksLeft / (float) Fogger.FADE_TICKS);;
			
			float partialTicks = (float) event.getRenderPartialTicks();
			float celestialAngle = world.getCelestialAngle((float)partialTicks);
			
			float f = 0.25F + 0.75F * (float)Minecraft.getMinecraft().gameSettings.renderDistanceChunks / 32.0F;
			f = 1.0F - (float)Math.pow((double)f, 0.25D);
			Vec3d vec3d = world.getSkyColor(entity, partialTicks);
			float f1 = (float)vec3d.x;
			float f2 = (float)vec3d.y;
			float f3 = (float)vec3d.z;

			float f3_2 = world.getLightBrightness(new BlockPos(Minecraft.getMinecraft().getRenderViewEntity()));
			float f2_2 = f3_2 * (1.0F - f) + f;
			this.fogColor1 += (f2_2 - this.fogColor1) * 0.1F;
			
			if (Minecraft.getMinecraft().gameSettings.renderDistanceChunks >= 4)
			{
				double d0 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) > 0.0F ? -1.0D : 1.0D;
				Vec3d vec3d2 = new Vec3d(d0, 0.0D, 0.0D);
				float f5 = (float)entity.getLook(partialTicks).dotProduct(vec3d2);
				if (f5 < 0.0F)
				{
					f5 = 0.0F;
				}

				if (f5 > 0.0F)
				{
					float[] afloat = world.provider.calcSunriseSunsetColors(celestialAngle, partialTicks);
					if (afloat != null)
					{
						f5 = f5 * afloat[3];
						red = red * (1.0F - f5) + afloat[0] * f5;
						green = green * (1.0F - f5) + afloat[1] * f5;
						blue = blue * (1.0F - f5) + afloat[2] * f5;
					}
				}
			}

			red += (f1 - red) * f;
			green += (f2 - green) * f;
			blue += (f3 - blue) * f;
			
			float baseScale = MathHelper.clamp(MathHelper.cos(celestialAngle * (float)Math.PI * 2.0F) * 2.0F + 0.5F, 0, 1);

			red *= baseScale * 0.94F + 0.06F;
			green *= baseScale * 0.94F + 0.06F;
			blue *= baseScale * 0.91F + 0.09F;
			
			float f8 = world.getRainStrength(partialTicks);

			if (f8 > 0.0F)
			{
				float f4 = 1.0F - f8 * 0.5F;
				float f10 = 1.0F - f8 * 0.4F;
				red *= f4;
				green *= f4;
				blue *= f10;
			}

			float f9 = world.getThunderStrength(partialTicks);

			if (f9 > 0.0F)
			{
				float f11 = 1.0F - f9 * 0.5F;
				red *= f11;
				green *= f11;
				blue *= f11;
			}

			IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint(world, entity, partialTicks);

			//Forge Moved to Block.
			Vec3d viewport = ActiveRenderInfo.projectViewFromEntity(entity, partialTicks);
			BlockPos viewportPos = new BlockPos(viewport);
			IBlockState viewportState = world.getBlockState(viewportPos);
			Vec3d inMaterialColor = viewportState.getBlock().getFogColor(world, viewportPos, viewportState, entity, new Vec3d(red, green, blue), partialTicks);
			red = (float)inMaterialColor.x;
			green = (float)inMaterialColor.y;
			blue = (float)inMaterialColor.z;
			
			float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
			red *= f13;
			green *= f13;
			blue *= f13;
			double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks) * world.provider.getVoidFogYFactor();

			if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(MobEffects.BLINDNESS))
			{
				int i = ((EntityLivingBase)entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();

				if (i < 20)
				{
					d1 *= (double)(1.0F - (float)i / 20.0F);
				}
				else
				{
					d1 = 0.0D;
				}
			}

			if (d1 < 1.0D)
			{
				if (d1 < 0.0D)
				{
					d1 = 0.0D;
				}

				d1 = d1 * d1;
				red = (float)((double)red * d1);
				green = (float)((double)green * d1);
				blue = (float)((double)blue * d1);
			}

			if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(MobEffects.NIGHT_VISION))
			{
				int i = ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
				float f15 = i > 200 ? 1.0F : 0.7F + MathHelper.sin(((float)i - partialTicks) * (float)Math.PI * 0.2F) * 0.3F;
				float f6 = 1.0F / red;

				if (f6 > 1.0F / green)
				{
					f6 = 1.0F / green;
				}

				if (f6 > 1.0F / blue)
				{
					f6 = 1.0F / blue;
				}

				red = red * (1.0F - f15) + red * f6 * f15;
				green = green * (1.0F - f15) + green * f6 * f15;
				blue = blue * (1.0F - f15) + blue * f6 * f15;
			}

			if (Minecraft.getMinecraft().gameSettings.anaglyph)
			{
				float f16 = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
				float f17 = (red * 30.0F + green * 70.0F) / 100.0F;
				float f7 = (red * 30.0F + blue * 70.0F) / 100.0F;
				red = f16;
				green = f17;
				blue = f7;
			}

			event.setRed(red);
			event.setGreen(green);
			event.setBlue(blue);
		}
		if (biome == ModBiomes.FLOATINGPEAKS || biome == ModBiomes.KINDERGARTEN) {
			this.fadeTicksLeft = Math.min(++this.fadeTicksLeft, Fogger.FADE_TICKS);
		} else {
			this.fadeTicksLeft = Math.max(0, --this.fadeTicksLeft);
		}
	}
	/*Temporarily disabled
	@SubscribeEvent
	public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
		Entity entity = event.getEntity();
		World world = entity.world;
		Biome biome = world.getBiome(entity.getPosition());
		if (biome == ModBiomes.KINDERGARTEN) {
			event.setDensity(0.5F);
			event.setCanceled(true);
		}
	}*/
	
	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
		if (event.getPlayer().world.getBlockState(event.getBlockPos()).getBlock() == ModBlocks.ROSE_TEARS) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(RES_UNDERTEARS_OVERLAY);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			float f = Minecraft.getMinecraft().player.getBrightness();
			GlStateManager.color(f, f, f, 0.5F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.pushMatrix();
			float f1 = 4.0F;
			float f2 = -1.0F;
			float f3 = 1.0F;
			float f4 = -1.0F;
			float f5 = 1.0F;
			float f6 = -0.5F;
			float f7 = -event.getPlayer().rotationYaw / 64.0F;
			float f8 = event.getPlayer().rotationPitch / 64.0F;
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex((double)(4.0F + f7), (double)(4.0F + f8)).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex((double)(0.0F + f7), (double)(4.0F + f8)).endVertex();
			bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex((double)(0.0F + f7), (double)(0.0F + f8)).endVertex();
			bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex((double)(4.0F + f7), (double)(0.0F + f8)).endVertex();
			tessellator.draw();
			GlStateManager.popMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			event.setCanceled(true);
		}
	}
}
