package mod.akrivus.kagic.event;

import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GemSpawnEvent extends Event {
	public final World world;
	public final BlockPos pos;
	public final EntityGem gemSpawned;
	public final boolean mineralSpawned;
	public final boolean canSpawnGem;
	public final float drainedCount;
	public final float baseMinerals;
	public final double highestYield;
	public GemSpawnEvent(World world, BlockPos pos, EntityGem gemSpawned, boolean mineralSpawned, boolean canSpawnGem, float drainedCount, float baseMinerals, double highestYield) {
		this.world = world;
		this.pos = pos;
		this.gemSpawned = gemSpawned;
		this.mineralSpawned = mineralSpawned;
		this.canSpawnGem = canSpawnGem;
		this.drainedCount = drainedCount;
		this.baseMinerals = baseMinerals;
		this.highestYield = highestYield;
	}
	@Override
	public boolean isCancelable() {
		return true;
	}
}
