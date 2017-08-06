package mod.akrivus.kagic.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.networking.TENameMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class GUIWarpPad extends GuiScreen {
	private final TileEntityWarpPadCore tilePad;
	private GuiTextField nameTextField;
	private GuiButton doneButton;
	
	GUIWarpPad(TileEntityWarpPadCore tePad){
		//KAGICTech.instance.chatInfoMessage("Created Warp Pad GUI!");
		this.tilePad = tePad;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void updateScreen() {
		nameTextField.updateCursorCounter();
	}
	
	public void initGui() {
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.nameTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
		this.nameTextField.setMaxStringLength(256);
		this.nameTextField.setText(this.tilePad.name);
		this.nameTextField.setFocused(true);
		this.doneButton = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
	}
	
	public void onGuiClosed() {
		//KAGICTech.instance.chatInfoMessage("Closing GUI");
		KTPacketHandler.INSTANCE.sendToServer(new TENameMessage(nameTextField.getText(), tilePad.getPos().getX(), tilePad.getPos().getY(), tilePad.getPos().getZ()));
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.enabled)
		{
			if (button.id == 0)
			{
				Minecraft.getMinecraft().player.closeScreen();
			}
		}
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) { //ESC key
			Minecraft.getMinecraft().player.closeScreen();
		} else {
			this.nameTextField.textboxKeyTyped(typedChar, keyCode);
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.nameTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.nameTextField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
