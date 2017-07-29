package mod.akrivus.kagic.init;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ModMetrics {
	private static boolean sentStats;
	@SuppressWarnings("deprecation")
	public static Update setMetrics(EntityPlayer player) {
		if (!sentStats && !KAGIC.VERSION.equals("@version")) {
			try {
				if (ModConfigs.useMetrics) {
					String json = getOnlineString("http://ip-api.com/json");
					Map<String, Object> data = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
					String country = "Unknown";
					if (data.get("status").toString().equals("success")) {
						country = data.get("country").toString();
					}
					getOnlineString("https://docs.google.com/forms/d/e/1FAIpQLScMPMMJYaVLEvWE99iBUO0o6F8iQ8j9e7OltzBmnuxVL8NTlw/formResponse?entry.1282213138=" + KAGIC.VERSION + "&entry.1143927987=" + KAGIC.MCVERSION + "&entry.226770572=" + FMLCommonHandler.instance().getSide() + "&entry.757444925=" + (player == null ? "" : (player.capabilities.isCreativeMode ? "CREATIVE" : "SURVIVAL")) + "&entry.1920609394=" + (player == null ? "" : URLEncoder.encode(player.getName())) + "&entry.1429084681=" + URLEncoder.encode(country) + "&submit=Submit");
				}
				if (ModConfigs.notifyOnUpdates) {
					String json = getOnlineString("http://akrivus.x10host.com/KAGIC.json");
					Map<String, Map<String, String>> data = new Gson().fromJson(json, new TypeToken<Map<String, Map<String, String>>>() {}.getType());
					return new Update(data.get(KAGIC.MCVERSION).get("version"), data.get(KAGIC.MCVERSION).get("changelog"), data.get(KAGIC.MCVERSION).get("patreon"), data.get(KAGIC.MCVERSION).get("discord"), data.get(KAGIC.MCVERSION).get("download"));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			sentStats = true;
		}
		return null;
	}
	public static String getOnlineString(String url) throws Exception {
		URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
	}
	public static class Update {
		private final String modVersion;
		private final String[] changelog;
		private final String discordLink;
		private final String patreonLink;
		private final String downloadLink;
		public Update(String version, String changelog, String patreon, String discord, String download) {
			this.modVersion = version;
			this.changelog = changelog.split("\n");
			this.patreonLink = patreon;
			this.discordLink = discord;
			this.downloadLink = download;
		}
		public String getModVersion() {
			return this.modVersion;
		}
		public String[] getChangelogs() {
			return this.changelog;
		}
		public String getPatreonLink() {
			return this.patreonLink;
		}
		public String getDiscordLink() {
			return this.discordLink;
		}
		public String getDownloadLink() {
			return this.downloadLink;
		}
	}
}
