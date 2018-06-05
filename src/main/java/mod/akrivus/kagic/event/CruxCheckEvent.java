package mod.akrivus.kagic.event;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CruxCheckEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final BlockPos ore;
	public final IBlockState state;
	public final Class<EntityGem> gem;
	public CruxCheckEvent(World world, BlockPos pos, BlockPos ore, IBlockState state, Class<EntityGem> gem) {
		this.world = world;
		this.pos = pos;
		this.ore = ore;
		this.state = state;
		this.gem = gem;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
