package de.sb.LichessAnalysis;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.json.*;

public class WebCrawler {

	private static final String startUser = "jobst";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String urlString = "https://en.lichess.org/api/user/" + startUser + "/games?with_opening=1";
		System.out.println(urlString);
		URL url;
		try {
			url = new URL(urlString);
			URLConnection yc = url.openConnection();

			InputStream inputStream = url.openStream();

			JsonReader rdr = Json.createReader(inputStream);

			JsonObject obj = rdr.readObject();

			JsonArray results = obj.getJsonArray("currentPageResults");

			ArrayList<String> users = new ArrayList<String>();
			ArrayList<Game> games = new ArrayList<Game>();
			
			users.add(startUser);

			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				String white = result.getJsonObject("players").getJsonObject("white").getString("userId");
				String black = result.getJsonObject("players").getJsonObject("black").getString("userId");

				if (white.equals(startUser)) {
					users.add(black);
				} else {
					users.add(white);
				}
				
				JsonObject opening = result.getJsonObject("opening");
				
				games.add(new Game(opening.getString("eco"), opening.getString("name")));			

//				System.out.println(white);
//				System.out.println(black);
//
//				System.out.println(result.getString("id"));
//				System.out.println(result.getBoolean("rated"));
				// System.out.print(result.getJsonObject("from").getString("name"));
				// System.out.print(": ");
				// System.out.println(result.getString("message", ""));
				// System.out.println("-----------");
			}
			
			for (String user : users) {
				System.out.println(user);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
