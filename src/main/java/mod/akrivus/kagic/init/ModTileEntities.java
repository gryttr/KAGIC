package mod.akrivus.kagic.init;

import mod.akrivus.kagic.tileentity.TileEntityIncubator;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.akrivus.kagic.tileentity.WarpRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ModTileEntities {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "kagic:incubator");
        GameRegistry.registerTileEntity(TileEntityWarpPadCore.class, "kagic:warp_pad_core");
	}
}
