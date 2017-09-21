package mod.heimrarnadalr.kagic.world;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fogger {
	private float fogColor1;
	private float fogColor2;
	
	@SubscribeEvent
	public void onFogColor(EntityViewRenderEvent.FogColors event) {
		this.fogColor2 = this.fogColor1;
		Entity entity = event.getEntity();
		World world = entity.world;
		if (world.getBiome(event.getEntity().getPosition()) == ModBiomes.FLOATINGPEAKS) {
			float red = 247F / 255F;
			float green = 220F / 255F;
			float blue = 218F / 255F;
			
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
	}
	
	@SubscribeEvent
	public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
		
	}
}
