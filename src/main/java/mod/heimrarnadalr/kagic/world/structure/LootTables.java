package mod.heimrarnadalr.kagic.world.structure;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTables {
	public static ResourceLocation SMALL_ARENA;
	public static ResourceLocation GALAXY_WARP;
	public static ResourceLocation SKY_SPIRE;
	public static ResourceLocation CONTROL_ROOM;
	public static ResourceLocation CONTROL_ROOM_MUSIC;
	public static ResourceLocation ROSE_FOUNTAIN;
	public static ResourceLocation OBELISK;
	
	public static void register() {
		SMALL_ARENA = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/smallarena"));
		GALAXY_WARP = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/galaxywarp"));
		SKY_SPIRE = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/skyspire"));
		CONTROL_ROOM = LootTableList.register(new ResourceLocation(KAGIC.MODID, "dispensers/controlroom"));
		CONTROL_ROOM_MUSIC = LootTableList.register(new ResourceLocation(KAGIC.MODID, "dispensers/controlroommusic"));
		ROSE_FOUNTAIN = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/rosefountain"));
		OBELISK = LootTableList.register(new ResourceLocation(KAGIC.MODID, "entities/obelisk"));
	}

}
