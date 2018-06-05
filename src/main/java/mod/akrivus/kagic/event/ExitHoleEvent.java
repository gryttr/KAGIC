package mod.akrivus.kagic.event;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ExitHoleEvent extends Event {
	public final World world;
	public final BlockPos block;
	public ExitHoleEvent(World world, BlockPos block) {
		this.world = world;
		this.block = block;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
