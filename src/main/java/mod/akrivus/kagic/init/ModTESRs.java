package mod.akrivus.kagic.init;

import mod.akrivus.kagic.tileentity.TileEntityMoonGoddessBeaconRender;
import mod.akrivus.kagic.tileentity.TileEntityMoonGoddessStatue;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.akrivus.kagic.tileentity.WarpRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModTESRs {
	public static void register() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWarpPadCore.class, new WarpRenderer());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoonGoddessStatue.class, new TileEntityMoonGoddessBeaconRender());
	}
}
