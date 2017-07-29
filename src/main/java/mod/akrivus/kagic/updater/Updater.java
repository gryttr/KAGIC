package mod.akrivus.kagic.updater;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class Updater {
	public static boolean UPDATED = false;
	public VersionChecker NEW_VERSION = null;
	public Minecraft CURRENT_VERSION = null;
	public EntityPlayer PLAYER = null;
	
	public Updater(EntityPlayer player) {
		if (!UPDATED) {
			NEW_VERSION = new VersionChecker();
			CURRENT_VERSION = Minecraft.getMinecraft();
			PLAYER = player;
			
			if (!NEW_VERSION.errored) {
				if (NEW_VERSION.MINECRAFT_VERSION.equals(CURRENT_VERSION.getVersion())) {
					if (!NEW_VERSION.MOD_VERSION.equals(KAGIC.VERSION)) {
						PLAYER.sendMessage(new TextComponentString("§c" + new TextComponentTranslation("command.kagic.new_version_available").getUnformattedComponentText()));
						PLAYER.sendMessage(new TextComponentString("§c" + new TextComponentTranslation("command.kagic.new_version_adds", NEW_VERSION.MOD_VERSION).getUnformattedComponentText()));
						PLAYER.sendMessage(new TextComponentString(NEW_VERSION.CHANGELOG.replaceAll("\n", "\n")));
						PLAYER.sendMessage(ITextComponent.Serializer.jsonToComponent("[{\"text\":\"§a§n" + new TextComponentTranslation("command.kagic.click_here_to_download").getUnformattedComponentText() + "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" +  NEW_VERSION.UPDATE_URL + "\"}}]"));
					}
				}
			}
			UPDATED = true;
		}
	}
}
