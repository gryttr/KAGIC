package mod.heimrarnadalr.kagic.world;

import mod.akrivus.kagic.init.KAGIC;
import mod.akrivus.kagic.init.ModBiomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GenEventCanceller {

	@SubscribeEvent
	public void onGeneration(PopulateChunkEvent.Populate event) {
		World world = event.getWorld();
		Biome biome = world.getBiome(new BlockPos(event.getChunkX(), 0, event.getChunkZ()));
		if (biome == ModBiomes.KINDERGARTEN) {
			event.setResult(Result.DENY);
		}
		if (biome == ModBiomes.STRAWBERRYBATTLEFIELD && (event.getType() == EventType.LAVA || event.getType() == EventType.LAKE)) {
			event.setResult(Result.DENY);
		}
	}
}
