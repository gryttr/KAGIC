package mod.akrivus.kagic.init;

import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mod.akrivus.kagic.linguistics.OnlineHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ModMetrics {
	private static boolean alreadySentStats;
	public static void sendMetrics() {
		if (!ModMetrics.alreadySentStats && ModConfigs.useMetrics) {
			try {
				String locationData = OnlineHelper.getOnlineString("http://ip-api.com/json");
				Map<String, Object> completeLocationSet = new Gson().fromJson(locationData, new TypeToken<Map<String, Object>>() {}.getType());
				String country = "Unknown";
				if (completeLocationSet.get("status").toString().equals("success")) {
					country = completeLocationSet.get("country").toString();
				}
				OnlineHelper.setOnlineString("https://docs.google.com/forms/d/e/1FAIpQLSdRpZTQNnIuAdrK5MS4KuflHSHfmRwiSPAC6WasoyKnWSyQOQ/formResponse?entry.1868827287=" + KAGIC.MCVERSION + "&entry.706783584=" + KAGIC.VERSION + "&entry.1579366467=" + FMLCommonHandler.instance().getSide() + "&entry.1527987054=" + URLEncoder.encode(country, "utf-8") + "&submit=Submit");
			}
			catch (Exception e) {
				System.out.println("Failed to check for updates, is the internet out?");
				e.printStackTrace();
			}
			ModMetrics.alreadySentStats = true;
		}
	}
	public static Update checkForUpdates() {
		if (ModConfigs.notifyOnUpdates) {
			try {
				String updateData = OnlineHelper.getOnlineString("https://cdn.rawgit.com/gryttr/KAGIC/1.12/updates.json");
				Map<String, Map<String, String>> completeDataSet = new Gson().fromJson(updateData, new TypeToken<Map<String, Map<String, String>>>() {}.getType());
				Map<String, String> versionData = (Map<String, String>)(completeDataSet.get(KAGIC.MCVERSION));
				return new Update(versionData.get("version"), versionData.get("download"), versionData.get("discord"));
			}
			catch (Exception e) {
				System.out.println("Failed to check for updates, is the internet out?");
				e.printStackTrace();
			}
		}
		return null;
	}
	public static class Update {
		private final String newVersion;
		private final String downloadLink;
		private final String discordLink;
		public Update(String version, String download, String discord) {
			this.newVersion = version;
			this.downloadLink = download;
			this.discordLink = discord;
		}
		public String getNewVersion() {
			return this.newVersion;
		}
		public String getDownloadLink() {
			return this.downloadLink;
		}
		public String getDiscordLink() {
			return this.discordLink;
		}
	}
}
