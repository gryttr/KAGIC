package mod.akrivus.kagic.util.flying;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class FlyingNodeProcessor extends WalkNodeProcessor {
	private float avoidsWater;
	
	@Override
	public void init(IBlockAccess sourceIn, EntityLiving mob) {
        super.init(sourceIn, mob);
        this.avoidsWater = mob.getPathPriority(PathNodeType.WATER);
    }
	
	@Override
    public void postProcess() {
        this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
        super.postProcess();
    }
	
	@Override
    public PathPoint getStart() {
        double i;
        if (this.getCanSwim() && this.entity.isInWater()) {
            i = this.entity.getEntityBoundingBox().minY;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.entity.posX), (int) i, MathHelper.floor(this.entity.posZ));
            for (Block block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.blockaccess.getBlockState(blockpos$mutableblockpos).getBlock()) {
                ++i;
                blockpos$mutableblockpos.setPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
            }
        }
        else {
            i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5D);
        }
        BlockPos blockpos1 = new BlockPos(this.entity);
        PathNodeType pathnodetype1 = this.getPathNodeType(this.entity.world, blockpos1.getX(), (int) i, blockpos1.getZ());
        if (this.entity.getPathPriority(pathnodetype1) < 0.0F) {
            Set<BlockPos> set = Sets.<BlockPos>newHashSet();
            set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().minZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().minX, i, this.entity.getEntityBoundingBox().maxZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().minZ));
            set.add(new BlockPos(this.entity.getEntityBoundingBox().maxX, i, this.entity.getEntityBoundingBox().maxZ));
            for (BlockPos blockpos : set) {
                PathNodeType pathnodetype = this.getPathNodeType(this.entity, blockpos);
                if (this.entity.getPathPriority(pathnodetype) >= 0.0F) {
                    return super.openPoint(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                }
            }
        }
        return super.openPoint(blockpos1.getX(), (int) i, blockpos1.getZ());
    }
	
	@Override
    public PathPoint getPathPointToCoords(double x, double y, double z) {
        return super.openPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
	
	@Override
    public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
        int i = 0;
        PathPoint pathpoint = this.openPoint(currentPoint.x, currentPoint.y, currentPoint.z + 1);
        PathPoint pathpoint1 = this.openPoint(currentPoint.x - 1, currentPoint.y, currentPoint.z);
        PathPoint pathpoint2 = this.openPoint(currentPoint.x + 1, currentPoint.y, currentPoint.z);
        PathPoint pathpoint3 = this.openPoint(currentPoint.x, currentPoint.y, currentPoint.z - 1);
        PathPoint pathpoint4 = this.openPoint(currentPoint.x, currentPoint.y + 1, currentPoint.z);
        PathPoint pathpoint5 = this.openPoint(currentPoint.x, currentPoint.y - 1, currentPoint.z);
        if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint;
        }
        if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint1;
        }
        if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint2;
        }
        if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint3;
        }
        if (pathpoint4 != null && !pathpoint4.visited && pathpoint4.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint4;
        }
        if (pathpoint5 != null && !pathpoint5.visited && pathpoint5.distanceTo(targetPoint) < maxDistance) {
            pathOptions[i++] = pathpoint5;
        }
        boolean flag = pathpoint3 == null || pathpoint3.costMalus != 0.0F;
        boolean flag1 = pathpoint == null || pathpoint.costMalus != 0.0F;
        boolean flag2 = pathpoint2 == null || pathpoint2.costMalus != 0.0F;
        boolean flag3 = pathpoint1 == null || pathpoint1.costMalus != 0.0F;
        boolean flag4 = pathpoint4 == null || pathpoint4.costMalus != 0.0F;
        boolean flag5 = pathpoint5 == null || pathpoint5.costMalus != 0.0F;
        if (flag && flag3) {
            PathPoint pathpoint6 = this.openPoint(currentPoint.x - 1, currentPoint.y, currentPoint.z - 1);
            if (pathpoint6 != null && !pathpoint6.visited && pathpoint6.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint6;
            }
        }
        if (flag && flag2) {
            PathPoint pathpoint7 = this.openPoint(currentPoint.x + 1, currentPoint.y, currentPoint.z - 1);
            if (pathpoint7 != null && !pathpoint7.visited && pathpoint7.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint7;
            }
        }
        if (flag1 && flag3) {
            PathPoint pathpoint8 = this.openPoint(currentPoint.x - 1, currentPoint.y, currentPoint.z + 1);
            if (pathpoint8 != null && !pathpoint8.visited && pathpoint8.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint8;
            }
        }
        if (flag1 && flag2) {
            PathPoint pathpoint9 = this.openPoint(currentPoint.x + 1, currentPoint.y, currentPoint.z + 1);
            if (pathpoint9 != null && !pathpoint9.visited && pathpoint9.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint9;
            }
        }
        if (flag && flag4) {
            PathPoint pathpoint10 = this.openPoint(currentPoint.x, currentPoint.y + 1, currentPoint.z - 1);
            if (pathpoint10 != null && !pathpoint10.visited && pathpoint10.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint10;
            }
        }
        if (flag1 && flag4) {
            PathPoint pathpoint11 = this.openPoint(currentPoint.x, currentPoint.y + 1, currentPoint.z + 1);
            if (pathpoint11 != null && !pathpoint11.visited && pathpoint11.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint11;
            }
        }
        if (flag2 && flag4) {
            PathPoint pathpoint12 = this.openPoint(currentPoint.x + 1, currentPoint.y + 1, currentPoint.z);
            if (pathpoint12 != null && !pathpoint12.visited && pathpoint12.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint12;
            }
        }
        if (flag3 && flag4) {
            PathPoint pathpoint13 = this.openPoint(currentPoint.x - 1, currentPoint.y + 1, currentPoint.z);
            if (pathpoint13 != null && !pathpoint13.visited && pathpoint13.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint13;
            }
        }
        if (flag && flag5) {
            PathPoint pathpoint14 = this.openPoint(currentPoint.x, currentPoint.y - 1, currentPoint.z - 1);
            if (pathpoint14 != null && !pathpoint14.visited && pathpoint14.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint14;
            }
        }
        if (flag1 && flag5) {
            PathPoint pathpoint15 = this.openPoint(currentPoint.x, currentPoint.y - 1, currentPoint.z + 1);
            if (pathpoint15 != null && !pathpoint15.visited && pathpoint15.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint15;
            }
        }
        if (flag2 && flag5) {
            PathPoint pathpoint16 = this.openPoint(currentPoint.x + 1, currentPoint.y - 1, currentPoint.z);
            if (pathpoint16 != null && !pathpoint16.visited && pathpoint16.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint16;
            }
        }
        if (flag3 && flag5) {
            PathPoint pathpoint17 = this.openPoint(currentPoint.x - 1, currentPoint.y - 1, currentPoint.z);
            if (pathpoint17 != null && !pathpoint17.visited && pathpoint17.distanceTo(targetPoint) < maxDistance) {
                pathOptions[i++] = pathpoint17;
            }
        }
        return i;
    }
	
	@Override
    protected PathPoint openPoint(int x, int y, int z) {
        PathPoint pathpoint = null;
        PathNodeType pathnodetype = this.getPathNodeType(x, y, z, this.entity);
        float f = this.entity.getPathPriority(pathnodetype);
        if (f >= 0.0F) {
            pathpoint = super.openPoint(x, y, z);
            pathpoint.nodeType = pathnodetype;
            pathpoint.costMalus = Math.max(pathpoint.costMalus, f);

            if (pathnodetype == PathNodeType.WALKABLE) {
                ++pathpoint.costMalus;
            }
        }
        return pathnodetype != PathNodeType.OPEN && pathnodetype != PathNodeType.WALKABLE ? pathpoint : pathpoint;
    }
	
	@Override
    public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
        EnumSet<PathNodeType> enumset = EnumSet.<PathNodeType>noneOf(PathNodeType.class);
        PathNodeType pathnodetype = PathNodeType.BLOCKED;
        pathnodetype = this.getPathNodeType(blockaccessIn, x, y, z, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn, enumset, pathnodetype, new BlockPos(x, y, z));
        if (enumset.contains(PathNodeType.FENCE)) {
            return PathNodeType.FENCE;
        }
        else {
            PathNodeType pathnodetype1 = PathNodeType.BLOCKED;
            for (PathNodeType pathnodetype2 : enumset) {
                if (entitylivingIn.getPathPriority(pathnodetype2) < 0.0F) {
                    return pathnodetype2;
                }
                if (entitylivingIn.getPathPriority(pathnodetype2) >= entitylivingIn.getPathPriority(pathnodetype1)) {
                    pathnodetype1 = pathnodetype2;
                }
            }
            if (pathnodetype == PathNodeType.OPEN && entitylivingIn.getPathPriority(pathnodetype1) == 0.0F) {
                return PathNodeType.OPEN;
            }
            else {
                return pathnodetype1;
            }
        }
    }
	
	@Override
    public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoorsIn, boolean canEnterDoorsIn, EnumSet<PathNodeType> set, PathNodeType type, BlockPos pos) {
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                for (int k = 0; k < zSize; ++k) {
                    int l = i + x;
                    int i1 = j + y;
                    int j1 = k + z;
                    PathNodeType pathnodetype = this.getPathNodeType(blockaccessIn, l, i1, j1);
                    if (pathnodetype == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoorsIn && canEnterDoorsIn) {
                        pathnodetype = PathNodeType.WALKABLE;
                    }
                    if (pathnodetype == PathNodeType.DOOR_OPEN && !canEnterDoorsIn) {
                        pathnodetype = PathNodeType.BLOCKED;
                    }
                    if (pathnodetype == PathNodeType.RAIL && !(blockaccessIn.getBlockState(pos).getBlock() instanceof BlockRailBase) && !(blockaccessIn.getBlockState(pos.down()).getBlock() instanceof BlockRailBase)) {
                        pathnodetype = PathNodeType.FENCE;
                    }
                    if (i == 0 && j == 0 && k == 0) {
                        type = pathnodetype;
                    }
                    set.add(pathnodetype);
                }
            }
        }
        return type;
    }
	
	@Override
    public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
    	PathNodeType pathnodetype = this.getPathNodeTypeRaw(blockaccessIn, x, y, z);
        if (pathnodetype == PathNodeType.OPEN && y >= 1) {
            Block block = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
            PathNodeType pathnodetype1 = this.getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
            if (pathnodetype1 != PathNodeType.DAMAGE_FIRE && block != Blocks.MAGMA && pathnodetype1 != PathNodeType.LAVA) {
                if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS) {
                    pathnodetype = PathNodeType.DAMAGE_CACTUS;
                }
                else {
                    pathnodetype = pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER ? PathNodeType.WALKABLE : PathNodeType.OPEN;
                }
            }
            else {
                pathnodetype = PathNodeType.DAMAGE_FIRE;
            }
        }
        pathnodetype = this.checkNeighborBlocks(blockaccessIn, x, y, z, pathnodetype);
        return pathnodetype;
    }
	
	@Override
    protected PathNodeType getPathNodeTypeRaw(IBlockAccess blockaccessIn, int x, int y, int z) {
    	BlockPos blockpos = new BlockPos(x, y, z);
        IBlockState iblockstate = blockaccessIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        Material material = iblockstate.getMaterial();
        PathNodeType type = block.getAiPathNodeType(iblockstate, blockaccessIn, blockpos);
        if (type != null) {
        	return type;
        }
        return material == Material.AIR ? PathNodeType.OPEN : (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY ? (block == Blocks.FIRE ? PathNodeType.DAMAGE_FIRE : (block == Blocks.CACTUS ? PathNodeType.DAMAGE_CACTUS : (block instanceof BlockDoor && material == Material.WOOD && !((Boolean)iblockstate.getValue(BlockDoor.OPEN)).booleanValue() ? PathNodeType.DOOR_WOOD_CLOSED : (block instanceof BlockDoor && material == Material.IRON && !((Boolean)iblockstate.getValue(BlockDoor.OPEN)).booleanValue() ? PathNodeType.DOOR_IRON_CLOSED : (block instanceof BlockDoor && ((Boolean)iblockstate.getValue(BlockDoor.OPEN)).booleanValue() ? PathNodeType.DOOR_OPEN : (block instanceof BlockRailBase ? PathNodeType.RAIL : (!(block instanceof BlockFence) && !(block instanceof BlockWall) && (!(block instanceof BlockFenceGate) || ((Boolean)iblockstate.getValue(BlockFenceGate.OPEN)).booleanValue()) ? (material == Material.WATER ? PathNodeType.WATER : (material == Material.LAVA ? PathNodeType.LAVA : (block.isPassable(blockaccessIn, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED))) : PathNodeType.FENCE))))))) : PathNodeType.TRAPDOOR);
    }
	
    private PathNodeType getPathNodeType(EntityLiving entity, BlockPos pos) {
        return this.getPathNodeType(pos.getX(), pos.getY(), pos.getZ(), entity);
    }
    
    private PathNodeType getPathNodeType(int x, int y, int z, EntityLiving entity) {
        return this.getPathNodeType(this.blockaccess, x, y, z, entity, this.entitySizeX, this.entitySizeY, this.entitySizeZ, /*this.getCanBreakDoors(),*/ this.getCanOpenDoors(), this.getCanEnterDoors());
    }
    
    @Override
    public PathNodeType checkNeighborBlocks(IBlockAccess blockaccessIn, int x, int y, int z, PathNodeType pathnodetype) {
        BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.retain();
        if (pathnodetype == PathNodeType.WALKABLE) {
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    if (i != 0 || j != 0) {
                        Block block = blockaccessIn.getBlockState(blockpos.setPos(i + x, y, j + z)).getBlock();
                        if (block == Blocks.CACTUS) {
                            pathnodetype = PathNodeType.DANGER_CACTUS;
                        }
                        else if (block == Blocks.FIRE) {
                        	pathnodetype = PathNodeType.DANGER_FIRE;
                        }
                        else if (block.isBurning(blockaccessIn, blockpos)) {
                        	pathnodetype = PathNodeType.DAMAGE_FIRE;
                        }
                    }
                }
            }
        }
        blockpos.release();
        return pathnodetype;
    }
}