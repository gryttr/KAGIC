package mod.akrivus.kagic.client.render.layers;

import mod.akrivus.kagic.client.model.ModelGem;
import mod.akrivus.kagic.client.model.ModelQuartz;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerQuartzCape implements LayerRenderer<EntityGem> {
	private final RenderLivingBase<?> gemRenderer;
	private final boolean isBack;
	private final boolean useInsigniaColors;

	public LayerQuartzCape(RenderLivingBase<?> gemRenderer) {
		this(gemRenderer, false, false);
	}

	public LayerQuartzCape(RenderLivingBase<?> gemRenderer, boolean isBack, boolean useInsigniaColors) {
		this.gemRenderer = gemRenderer;
		this.isBack = isBack;
		this.useInsigniaColors = useInsigniaColors;
	}

	@Override
	public void doRenderLayer(EntityGem gem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if ((gem.isPrimary() || gem instanceof EntityHessonite) && !gem.isInvisible() && gem.hasCape())	{
			this.gemRenderer.bindTexture(this.getTexture(gem));
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 0.125F);
			double d0 = gem.prevChasingPosX + (gem.chasingPosX - gem.prevChasingPosX) * (double)partialTicks - (gem.prevPosX + (gem.posX - gem.prevPosX) * (double)partialTicks);
			double d1 = gem.prevChasingPosY + (gem.chasingPosY - gem.prevChasingPosY) * (double)partialTicks - (gem.prevPosY + (gem.posY - gem.prevPosY) * (double)partialTicks);
			double d2 = gem.prevChasingPosZ + (gem.chasingPosZ - gem.prevChasingPosZ) * (double)partialTicks - (gem.prevPosZ + (gem.posZ - gem.prevPosZ) * (double)partialTicks);
			float f = gem.prevRenderYawOffset + (gem.renderYawOffset - gem.prevRenderYawOffset) * partialTicks;
			double d3 = (double)MathHelper.sin(f * 0.017453292F);
			double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
			float f1 = (float)d1 * 10.0F;
			f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
			float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
			float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

			if (f2 < 0.0F) {
				f2 = 0.0F;
			}

			f1 = f1 + MathHelper.sin((gem.prevDistanceWalkedModified + (gem.distanceWalkedModified - gem.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F;

			if (gem.isSneaking()) {
				f1 += 25.0F;
			}

			GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			float[] afloat;
			if (useInsigniaColors) {
				afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getInsigniaColor()]);
			} else {
				afloat = EntitySheep.getDyeRgb(EnumDyeColor.values()[gem.getUniformColor()]);
			}
			GlStateManager.color(afloat[0] * 2, afloat[1] * 2, afloat[2] * 2);
			((ModelGem) this.gemRenderer.getMainModel()).renderCape(0.0625F);
			GlStateManager.popMatrix();
		}
	}

	public String getName(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (loc.getResourceDomain().equals("kagic")) {
	        return loc.getResourcePath().replaceFirst("kagic.", "");
		}
		else {
	        return loc.getResourcePath();
		}
	}
	
	public ResourceLocation getTexture(EntityGem gem) {
		ResourceLocation loc = EntityList.getKey(gem);
		if (isBack) {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/cape_back.png");
		} else {
			return new ResourceLocation(loc.getResourceDomain() + ":textures/entities/" + this.getName(gem) + "/cape.png");
		}
	}
	
	public boolean shouldCombineTextures() {
		return true;
	}
}