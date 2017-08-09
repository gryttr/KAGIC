package mod.akrivus.kagic.init;

import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.akrivus.kagic.tileentity.WarpRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModTESRs {
	public static void register() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWarpPadCore.class, new WarpRenderer());
	}
}
