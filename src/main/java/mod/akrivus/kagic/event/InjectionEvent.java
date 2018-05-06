package mod.akrivus.kagic.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InjectionEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final int depth;
	public final boolean automatic;
	public InjectionEvent(World world, BlockPos pos, int depth, boolean automatic) {
		this.world = world;
		this.pos = pos;
		this.depth = depth;
		this.automatic = automatic;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
