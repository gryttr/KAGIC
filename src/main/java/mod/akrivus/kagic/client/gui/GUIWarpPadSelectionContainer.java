package mod.akrivus.kagic.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class GUIWarpPadSelectionContainer extends Container {
	public GUIWarpPadSelectionContainer() {
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
