package mod.akrivus.kagic.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DrainBlockEvent extends Event {
	public final World world;
	public final BlockPos ore;
	public final IBlockState state;
	public final Block block;
	public DrainBlockEvent(World world, BlockPos ore, IBlockState state, Block block) {
		this.world = world;
		this.ore = ore;
		this.state = state;
		this.block = block;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
