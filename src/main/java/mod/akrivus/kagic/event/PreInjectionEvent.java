package mod.akrivus.kagic.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PreInjectionEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final boolean drain;
	public PreInjectionEvent(World world, BlockPos pos, boolean drain) {
		this.world = world;
		this.pos = pos;
		this.drain = drain;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
