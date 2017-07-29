package mod.akrivus.kagic.util.flying;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlying extends PathNavigateGround {
    public PathNavigateFlying(EntityLiving entity, World world) {
        super(entity, world);
    }
    protected PathFinder getPathFinder() {
        this.nodeProcessor = new FlyingNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(this.nodeProcessor);
    }
    protected boolean canNavigate() {
        return this.canFloat() && this.isInLiquid() || !this.theEntity.isRiding();
    }
    protected Vec3d getEntityPosition() {
        return new Vec3d(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ);
    }
    public Path getPathToEntityLiving(Entity entity) {
        return this.getPathToPos(new BlockPos(entity));
    }
    public void onUpdateNavigation() {
        this.updatePath();
        if (!this.noPath()) {
            if (this.canNavigate()) {
                this.pathFollow();
            }
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
                Vec3d vec3d = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());
                if (MathHelper.floor(this.theEntity.posX) == MathHelper.floor(vec3d.xCoord) && MathHelper.floor(this.theEntity.posY) == MathHelper.floor(vec3d.yCoord) && MathHelper.floor(this.theEntity.posZ) == MathHelper.floor(vec3d.zCoord)) {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                }
            }
            if (!this.noPath()) {
                Vec3d finish = this.currentPath.getPosition(this.theEntity);
                this.theEntity.getMoveHelper().setMoveTo(finish.xCoord, finish.yCoord, finish.zCoord, this.speed);
            }
        }
    }
    protected boolean isDirectPathBetweenPoints(Vec3d start, Vec3d finish, int sizeX, int sizeY, int sizeZ) {
        int i = MathHelper.floor(start.xCoord);
        int j = MathHelper.floor(start.yCoord);
        int k = MathHelper.floor(start.zCoord);
        double d0 = finish.xCoord - start.xCoord;
        double d1 = finish.yCoord - start.yCoord;
        double d2 = finish.zCoord - start.zCoord;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0E-8D) {
            return false;
        }
        else {
            double d4 = 1.0D / Math.sqrt(d3);
            d0 = d0 * d4;
            d1 = d1 * d4;
            d2 = d2 * d4;
            double d5 = 1.0D / Math.abs(d0);
            double d6 = 1.0D / Math.abs(d1);
            double d7 = 1.0D / Math.abs(d2);
            double d8 = i - start.xCoord;
            double d9 = j - start.yCoord;
            double d10 = k - start.zCoord;
            if (d0 >= 0.0D) {
                ++d8;
            }
            if (d1 >= 0.0D) {
                ++d9;
            }
            if (d2 >= 0.0D) {
                ++d10;
            }
            d8 = d8 / d0;
            d9 = d9 / d1;
            d10 = d10 / d2;
            int l = d0 < 0.0D ? -1 : 1;
            int i1 = d1 < 0.0D ? -1 : 1;
            int j1 = d2 < 0.0D ? -1 : 1;
            int k1 = MathHelper.floor(finish.xCoord);
            int l1 = MathHelper.floor(finish.yCoord);
            int i2 = MathHelper.floor(finish.zCoord);
            int j2 = k1 - i;
            int k2 = l1 - j;
            int l2 = i2 - k;
            while (j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
                if (d8 < d10 && d8 <= d9) {
                    d8 += d5;
                    i += l;
                    j2 = k1 - i;
                }
                else if (d9 < d8 && d9 <= d10) {
                    d9 += d6;
                    j += i1;
                    k2 = l1 - j;
                }
                else {
                    d10 += d7;
                    k += j1;
                    l2 = i2 - k;
                }
            }
            return true;
        }
    }
    public void setCanEnterDoors(boolean canEnterDoors) {
        this.nodeProcessor.setCanEnterDoors(canEnterDoors);
    }
    public void setCanFloat(boolean canFloat) {
        this.nodeProcessor.setCanSwim(canFloat);
    }
    public boolean canFloat() {
        return this.nodeProcessor.getCanSwim();
    }
    public boolean canEntityStandOnPos(BlockPos pos) {
        return this.world.getBlockState(pos).isSideSolid(this.world, pos, EnumFacing.UP);
    }
}