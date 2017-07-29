package mod.akrivus.kagic.init;

import mod.akrivus.kagic.tileentity.TileEntityIncubator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void register() {
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "kagic:incubator");
	}
}
