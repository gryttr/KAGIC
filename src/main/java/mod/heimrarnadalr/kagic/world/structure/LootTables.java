package mod.heimrarnadalr.kagic.world.structure;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTables {
	public static ResourceLocation SMALL_ARENA;
	public static ResourceLocation GALAXY_WARP;
	public static ResourceLocation SKY_SPIRE;
	
	public static void register() {
		SMALL_ARENA = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/smallarena"));
		GALAXY_WARP = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/galaxywarp"));
		SKY_SPIRE = LootTableList.register(new ResourceLocation(KAGIC.MODID, "chests/skyspire"));
	}

}
