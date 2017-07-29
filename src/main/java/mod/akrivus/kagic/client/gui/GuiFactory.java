package mod.akrivus.kagic.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory {
	public void initialize(Minecraft minecraftInstance) {
		
	}
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiConfigPage(parentScreen);
	}
	public boolean hasConfigGui() {
		return true;
	}
	public Class<? extends GuiScreen> mainConfigGuiClass()  {
		return GuiConfigPage.class;
	}
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	@SuppressWarnings("deprecation")
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
}