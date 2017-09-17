package mod.akrivus.kagic.linguistics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OnlineHelper {
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
	public static void setOnlineString(String url) throws Exception {
		Thread request = new Thread(new ThreadedRequest(url));
		request.start();
	}
	static class ThreadedRequest implements Runnable {
		private String url;
		public ThreadedRequest(String url) {
			this.url = url;
		}
		public void run() {
			try {
				OnlineHelper.getOnlineString(this.url);
			}
			catch (Exception e) {
				System.out.println("Message failed.");
			}
		}
	}
}
