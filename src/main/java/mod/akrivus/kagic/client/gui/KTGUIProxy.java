package mod.akrivus.kagic.client.gui;

import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class KTGUIProxy implements IGuiHandler {
	public static final int GUIWARPPADID = 0;
	public static final int GUIWARPPADSELECTIONID = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUIWARPPADID) {
			return new GUIWarpPadContainer();
		} else if (ID == GUIWARPPADSELECTIONID) {
			return new GUIWarpPadSelectionContainer();
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUIWARPPADID && world.isRemote) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityWarpPadCore) {
				return new GUIWarpPad((TileEntityWarpPadCore) te);
			}
		} else if (ID == GUIWARPPADSELECTIONID) {
			/*BlockPos pos = new BlockPos(x, y, z);
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityWarpPadCore) {
				return new GUIWarpPadSelection((TileEntityWarpPadCore) te);
			}	*/		
		}
		return null;
	}
}
