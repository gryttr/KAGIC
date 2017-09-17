package mod.heimrarnadalr.kagic.proxies;

import java.util.LinkedHashMap;

import mod.akrivus.kagic.client.gui.GUIGalaxyPadSelection;
import mod.akrivus.kagic.client.gui.GUIWarpPadSelection;
import mod.heimrarnadalr.kagic.worlddata.GalaxyPadLocation;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void openWarpPadSelectionGUI(LinkedHashMap<BlockPos, WarpPadDataEntry> padData, int x, int y, int z) {
		Minecraft.getMinecraft().displayGuiScreen(new GUIWarpPadSelection(padData, x, y, z));		
	}

	@Override
	public void openGalaxyPadSelectionGUI(LinkedHashMap<GalaxyPadLocation, WarpPadDataEntry> padData, int x, int y, int z) {
		Minecraft.getMinecraft().displayGuiScreen(new GUIGalaxyPadSelection(padData, x, y, z));		
	}
}
