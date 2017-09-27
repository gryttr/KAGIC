package mod.heimrarnadalr.kagic.dispenser;

import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.init.Bootstrap.BehaviorDispenseOptional;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviorGemDispense extends BehaviorDispenseOptional {

	public BehaviorGemDispense() {
	}

	@Override
	protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
	{
		World world = source.getWorld();
		EnumFacing direction = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
		BlockPos spawnPos;
		if (direction == EnumFacing.DOWN) {
			spawnPos = source.getBlockPos().down(4);
		} else if (direction == EnumFacing.UP) {
			spawnPos = source.getBlockPos();
		} else {
			spawnPos = source.getBlockPos().down().offset(direction);
		}
		
		ItemGem gem = (ItemGem)stack.getItem();
		this.successful = gem.spawnGem(world, null, spawnPos, stack);
		ItemStack shrunk = stack.copy();
		shrunk.shrink(1);
		return shrunk;
	}
}
