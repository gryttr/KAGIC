package mod.akrivus.kagic.entity.ai;

import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;

public class EntityAIMineOresBySight extends EntityAIBase {
    private final EntityPeridot gem;
	private final double movementSpeed;
	private final int searchRadius;
	private boolean placed = false;
	private double posX;
	private double posY;
	private double posZ;
	private int delay;
	
	public EntityAIMineOresBySight(EntityPeridot gemIn, double speedIn, int radius) {
		this.gem = gemIn;
		this.movementSpeed = speedIn;
		this.searchRadius = radius;
		this.delay = 0;
		this.setMutexBits(0);
	}
	
	@Override
	public boolean shouldExecute() {
		if (this.gem.isMiner()) {
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
		if (this.gem.isDefective() && this.gem.world.rand.nextBoolean()) {
			this.gem.sayClod();
		}
		else {
			boolean found = false;
			for (int x = -this.searchRadius; x <= this.searchRadius && !found; ++x) {
				for (int y = -2; y <= 2 && !found; ++y) {
					for (int z = -this.searchRadius; z <= this.searchRadius && !found; ++z) {
						BlockPos tempPos = this.gem.getPosition().add(new BlockPos(x, y, z));
						Block block =  this.gem.world.getBlockState(tempPos).getBlock();
						if (block.getUnlocalizedName().contains("ore")) {
							ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(Item.getItemFromBlock(block))).copy();
							if (result.getUnlocalizedName().contains("ingot")) {
								boolean canBeSeen = false;
								for (int ox = -1; ox <= 1 && !canBeSeen; ++ox) {
									if (this.gem.world.isAirBlock(tempPos.add(ox, 0, 0))) {
										canBeSeen = true;
									}
								}
								for (int oy = -1; oy <= 1 && !canBeSeen; ++oy) {
									if (this.gem.world.isAirBlock(tempPos.add(0, oy, 0))) {
										canBeSeen = true;
									}
								}
								for (int oz = -1; oz <= 1 && !canBeSeen; ++oz) {
									if (this.gem.world.isAirBlock(tempPos.add(0, 0, oz))) {
										canBeSeen = true;
									}
								}
								if (canBeSeen) {
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
			}
			if (found) {
				this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
			}
		}
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return !this.gem.getNavigator().noPath() && !this.gem.isDefective();
	}
	
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.placed = false;
	}
	
	@Override
	public void updateTask() {
		this.gem.getNavigator().tryMoveToXYZ(this.posX, this.posY, this.posZ, this.movementSpeed);
		if (this.gem.getDistanceSq(this.posX, this.posY, this.posZ) < 8 && !this.placed) {
			this.gem.world.destroyBlock(new BlockPos(this.posX, this.posY, this.posZ), true);
			this.placed = true;
		}
	}
}