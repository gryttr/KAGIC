package mod.heimrarnadalr.kagic.world;

import mod.akrivus.kagic.entity.EntityCorruptedGem;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedAmethyst;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedCarnelian;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedJasper;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedRoseQuartz;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWaterBear;
import mod.akrivus.kagic.entity.gem.corrupted.EntityCorruptedWatermelonTourmaline;
import mod.akrivus.kagic.entity.gem.corrupted.EntityTongueMonster;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CorruptedGemSpawner {
	private static final Biome.SpawnListEntry CORRUPTED_AMETHYST_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedAmethyst.class, 1, 1, 1);
	private static final Biome.SpawnListEntry CORRUPTED_CARNELIAN_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedCarnelian.class, 1, 1, 1);
	private static final Biome.SpawnListEntry CORRUPTED_JASPER_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedJasper.class, 1, 1, 1);
	private static final Biome.SpawnListEntry CORRUPTED_ROSE_QUARTZ_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedRoseQuartz.class, 1, 1, 1);
	private static final Biome.SpawnListEntry CORRUPTED_WATERMELON_TOURMALINE_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedWatermelonTourmaline.class, 1, 1, 1);
	private static final Biome.SpawnListEntry TONGUE_MONSTER_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityTongueMonster.class, 1, 1, 1);
	private static final Biome.SpawnListEntry WATER_BEAR_SPAWN_ENTRY = new Biome.SpawnListEntry(EntityCorruptedWaterBear.class, 1, 1, 1);

	@SubscribeEvent
	public void onPotentialSpawn(WorldEvent.PotentialSpawns event) {
		if (event.getType() == EnumCreatureType.MONSTER) {
			event.getList().add(CORRUPTED_AMETHYST_SPAWN_ENTRY);
			event.getList().add(CORRUPTED_CARNELIAN_SPAWN_ENTRY);
			event.getList().add(CORRUPTED_JASPER_SPAWN_ENTRY);
			event.getList().add(CORRUPTED_ROSE_QUARTZ_SPAWN_ENTRY);
			event.getList().add(CORRUPTED_WATERMELON_TOURMALINE_SPAWN_ENTRY);
			event.getList().add(TONGUE_MONSTER_SPAWN_ENTRY);
			event.getList().add(WATER_BEAR_SPAWN_ENTRY);
		}
	}

}
