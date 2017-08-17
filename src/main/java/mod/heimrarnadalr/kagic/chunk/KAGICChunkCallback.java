package mod.heimrarnadalr.kagic.chunk;

import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class KAGICChunkCallback implements LoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		//The only time we'll have tickets is if the player quit the world while warping 
		//Since we only need the chunks loaded for the duration of the warp, we don't have anything to do here
	}

}
