package mod.akrivus.kagic.updater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VersionChecker {
	public String MINECRAFT_VERSION;
	public String MOD_VERSION;
	public String UPDATE_URL;
	public String CHANGELOG;
	public boolean errored = false;
	
	public VersionChecker() {
		Gson parser = new Gson();
		try {
			Map<String, String> versionData = parser.fromJson(this.getVersionData("http://akrivus.x10host.com/KAGIC-1.11.2-version-data.json"), new TypeToken<Map<String, String>>() {}.getType());
			MINECRAFT_VERSION = versionData.get("mcversion");
			MOD_VERSION = versionData.get("version");
			UPDATE_URL = versionData.get("url");
			CHANGELOG = versionData.get("changes");
		}
		catch (Exception e) {
			errored = true;
		}
	}
	
    public String getVersionData(String url) throws Exception {
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
}
