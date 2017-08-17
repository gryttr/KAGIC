package mod.akrivus.kagic.dimensions.lion;

import mod.akrivus.kagic.init.ModBiomes;
import mod.akrivus.kagic.init.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderLion extends WorldProvider {
	public void createBiomeProvider() {
        this.biomeProvider = new BiomeProviderSingle(ModBiomes.LION);
        this.hasSkyLight = true;
        //this.hasNoSky = false;
    }
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderLion(this.world, this.world.getSeed());
    }
    public DimensionType getDimensionType() {
		return ModDimensions.HOMEWORLD;
	}
    public boolean isSurfaceWorld() {
        return true;
    }
    public int getAverageGroundLevel() {
        return 6;
    }
    public boolean canCoordinateBeSpawn(int x, int z) {
        return this.world.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
    }
    public BlockPos getSpawnCoordinate() {
        return new BlockPos(0, 80, 0);
    }
    public boolean canRespawnHere() {
    	return false;
    }
    public String getWelcomeMessage() {
    	return "ROAR!";
    }
    public String getDepartMessage() {
    	return "ROAR!";
    }
    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
    	return new Vec3d(244D / 255D, 146D / 255D, 163D / 255D);
    }
    @SideOnly(Side.CLIENT)
    public Vec3d getCloudColour(float partialTicks) {
    	return new Vec3d(1.0D, 1.0D, 1.0D);
    }
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float x, float y) {
        return new Vec3d(1.0D, 1.0D, 1.0D);
    }
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
    	return 8.0F;
    }
}
