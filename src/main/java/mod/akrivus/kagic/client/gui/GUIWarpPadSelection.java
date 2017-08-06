package mod.akrivus.kagic.client.gui;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.worlddata.WarpPadDataEntry;
import mod.heimrarnadalr.kagic.worlddata.WorldDataWarpPad;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GUIWarpPadSelection extends GuiScreen {
	private final TileEntityWarpPadCore tilePad;
	private Map<BlockPos, WarpPadDataEntry> padData = null;
	private SortedMap<Double, BlockPos> sortedPoses = new TreeMap<Double, BlockPos>();
	private GUIWarpPadList padList;
	
	public GUIWarpPadSelection(LinkedHashMap<BlockPos, WarpPadDataEntry> data, int x, int y, int z) {
		//KAGICTech.instance.chatInfoMessage("Getting Warp Pad TE at " + x + ", " + y + ", " + z);
		this.tilePad = (TileEntityWarpPadCore) Minecraft.getMinecraft().world.getTileEntity(new BlockPos(x, y, z));
		this.padData = data;
		//KAGICTech.instance.chatInfoMessage("padData has size of " + padData.size());
		sortedPoses = WorldDataWarpPad.getSortedPositions(padData, this.tilePad.getPos());
	}
	
	public WarpPadDataEntry getPadDataEntry(BlockPos pos) {
		if (this.padData.containsKey(pos)) {
			return this.padData.get(pos);
		} else {
			return null;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void updateScreen() {
	}

	public void initGui() {
		this.padList = new GUIWarpPadList(this, this.tilePad.getPos(), this.sortedPoses, this.mc, this.width, this.height, 0, this.height, 50);	
	}
	
	public void onGuiClosed() {
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		this.padList.actionPerformed(button);
		super.actionPerformed(button);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) { //ESC key
			Minecraft.getMinecraft().player.closeScreen();
		}
	}
	
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.padList.handleMouseInput();
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (!this.padList.mouseClicked(mouseX, mouseY, mouseButton)) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (!this.padList.mouseReleased(mouseX, mouseY, state)) {
			super.mouseReleased(mouseX, mouseY, state);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.padList.drawScreen(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
