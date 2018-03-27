package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityRutile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public class EntityAIFindDarkSpots extends EntityAIBase {
    private final EntityRutile gem;
	private final double movementSpeed;
	private final int searchRadius;
	private int currentLight = 8;
	private boolean placed = false;
	private double posX;
	private double posY;
	private double posZ;
	private int delay;
	
	public EntityAIFindDarkSpots(EntityRutile gemIn, double speedIn, int radius) {
		this.gem = gemIn;
		this.movementSpeed = speedIn;
		this.searchRadius = radius;
		this.delay = 0;
		this.setMutexBits(0);
	}
	
	@Override
	public boolean shouldExecute() {
		if (!this.gem.getHeldItemMainhand().isEmpty() && Block.getBlockFromItem(this.gem.getHeldItemMainhand().getItem()) == Blocks.TORCH) {
			if (this.delay > 0) {
				--this.delay;
				return false;
			}
			else {
				this.delay = this.gem.getRNG().nextInt(20);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void startExecuting() {
		boolean found = false;
		for (int x = -this.searchRadius; x <= this.searchRadius && !found; ++x) {
			for (int y = -2; y <= 2 && !found; ++y) {
				for (int z = -this.searchRadius; z <= this.searchRadius && !found; ++z) {
					BlockPos tempPos = this.gem.getPosition().add(new BlockPos(x, y, z));
					if (this.gem.world.isAirBlock(tempPos) && this.gem.world.isSideSolid(tempPos.down(), EnumFacing.UP)) {
						int tempLight = this.gem.world.getLightFor(EnumSkyBlock.BLOCK, tempPos);
						if (tempLight < this.currentLight) {
							this.posX = tempPos.getX();
							this.posY = tempPos.getY();
							this.posZ = tempPos.getZ();
							found = true;
							break;
						}
					}
					else if (this.isFallingBlock(tempPos)) {
						int numberOfBlocks = 0;
						for (int i = 1; i < 5; ++i) {
							if (this.isFallingBlock(tempPos.up(i))) {
								++numberOfBlocks;
							}
						}
						if (numberOfBlocks > 3) {
							this.posX = tempPos.getX();
							this.posY = tempPos.getY();
							this.posZ = tempPos.getZ();
							found = true;
							break;
						}
					}
				}
			}
		}
		if (found) {
			this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return !this.gem.getNavigator().noPath();
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.placed = false;
	}
	
	@Override
	public void updateTask() {
		BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
		this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		if (this.gem.getDistanceSq(this.posX, this.posY, this.posZ) < 5 && (this.gem.world.isAirBlock(pos) || this.isFallingBlock(pos)) && !this.placed) {
			this.gem.placeBlock(Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP), new BlockPos(this.posX, this.posY, this.posZ));
			if (this.isFallingBlock(new BlockPos(this.posX, this.posY + 1, this.posZ))) {
				this.gem.world.destroyBlock(new BlockPos(this.posX, this.posY + 1, this.posZ), true);
			}
			this.gem.getHeldItemMainhand().shrink(1);
			this.placed = true;
		}
	}
	
	public boolean isFallingBlock(BlockPos pos) {
		return this.gem.world.getBlockState(pos).getBlock() instanceof BlockFalling;
	}
}