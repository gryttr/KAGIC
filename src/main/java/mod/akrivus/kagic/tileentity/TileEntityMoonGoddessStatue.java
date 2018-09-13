package mod.akrivus.kagic.tileentity;

import java.util.List;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMoonGoddessStatue extends TileEntity implements ITickable {
	private double effectiveDistance = 16D;
	private Potion potionEffect = Potion.getPotionFromResourceLocation("night_vision");
	private int lastLightLevel = 0;
    @SideOnly(Side.CLIENT)
    private long beamRenderCounter;
    @SideOnly(Side.CLIENT)
    private float beamRenderScale;

	@Override
	public void update() {
		int lightLevel = this.world.getLightFor(EnumSkyBlock.BLOCK, this.pos);
		if (lightLevel != this.lastLightLevel) {
			this.world.checkLight(this.pos);
			this.lastLightLevel = lightLevel;
		}
		
		if (this.isActive()) {
			this.addPotionEffects();
		}
	}

	public boolean isMidnight() {
		long time = this.world.getWorldTime() % 24000;
		long timeFromMidnight = Math.abs(time - 18000);
		return timeFromMidnight <= 280;
	}
	
	public boolean isFullMoon() {
		return this.world.getCurrentMoonPhaseFactor() == 1F;
	}
	
	public boolean isActive() {
		return this.isMidnight() && this.isFullMoon() && this.world.canSeeSky(this.pos);
	}
	
	public int getLightLevelForNightStage() {
		long time = this.world.getWorldTime() % 24000;
		
		//Daytime
		if (time < 13800 || time > 22200) {
			return 0;
		}
		
		long timeFromMidnight = Math.abs(time - 18000);

		return (int) (15 - timeFromMidnight / 280);
	}
	
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@SideOnly(Side.CLIENT)
	public float calculateBeamRenderScale() {
        if (!this.isActive()) {
            return 0.0F;
        } else {
            int i = (int)(this.world.getTotalWorldTime() - this.beamRenderCounter);
            this.beamRenderCounter = this.world.getTotalWorldTime();

            if (i > 1) {
                this.beamRenderScale -= (float)i / 40.0F;

                if (this.beamRenderScale < 0.0F) {
                    this.beamRenderScale = 0.0F;
                }
            }

            this.beamRenderScale += 0.025F;

            if (this.beamRenderScale > 1.0F) {
                this.beamRenderScale = 1.0F;
            }

            return this.beamRenderScale;
        }
    }
	
	private void addPotionEffects() {
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1)).grow(this.effectiveDistance, (double)this.world.getHeight(), this.effectiveDistance);
        List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

        for (EntityPlayer entityplayer : list) {
            entityplayer.addPotionEffect(new PotionEffect(this.potionEffect, 20 * 20, 1, true, true));
        }		
	}
}
