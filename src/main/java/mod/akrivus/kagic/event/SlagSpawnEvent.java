package mod.akrivus.kagic.event;

import mod.akrivus.kagic.entity.EntitySlag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SlagSpawnEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final EntitySlag slag;
	public SlagSpawnEvent(World world, BlockPos pos, EntitySlag slag) {
		this.world = world;
		this.pos = pos;
		this.slag = slag;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
