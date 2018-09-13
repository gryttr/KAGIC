package mod.akrivus.kagic.init;

import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityIncubator;
import mod.akrivus.kagic.tileentity.TileEntityMoonGoddessStatue;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "kagic:incubator");
        GameRegistry.registerTileEntity(TileEntityWarpPadCore.class, "kagic:warp_pad_core");
        GameRegistry.registerTileEntity(TileEntityGalaxyPadCore.class, "kagic:galaxy_pad_core");
        GameRegistry.registerTileEntity(TileEntityMoonGoddessStatue.class, "kagic:moon_goddess_statue");
	}
}
