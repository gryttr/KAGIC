package mod.akrivus.kagic.client.gui;

import mod.akrivus.kagic.init.ModConfigs;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigPage extends GuiConfig {
	public GuiConfigPage(GuiScreen parent) {
        super(parent, ModConfigs.getCategories(), KAGIC.MODID, "kagicConfig", false, false, "Configure KAGIC");
    }
}
