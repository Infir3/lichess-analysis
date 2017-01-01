package de.sb.LichessAnalysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.*;

import com.sun.istack.internal.Nullable;

/**
 * @author Stefan Becking
 *
 */
public class WebCrawler {

	private static final String START_USER = "jobst";
	private static final int CRAWL_DEPTH = 3;

	private static HashMap<String, Boolean> crawledUsers = new HashMap<String, Boolean>();

	public static void main(String[] args) throws IOException {

		HashSet<Game> games = null;

		HashSet<String> users = new HashSet<String>();

		HashMap<String, Boolean> crawledUsers = new HashMap<String, Boolean>();

		games = getUserGames(START_USER);

		System.out.println("User " + START_USER + " has been crawled.");

		crawledUsers.put(START_USER, true);

		// get all users who played in the games
		for (Game game : games) {
			users.add(game.white);
			users.add(game.black);
		}

		getGamesOfManyUsers(users);

		for (int i = 0; i < CRAWL_DEPTH; i++) {

		}

		System.out.println("Numbers of games fetched: " + games.size());
		for (Game game : games) {
			System.out.println("id: " + game.id + " white: " + game.white + " black: " + game.black + " eco: "
					+ game.eco + " name: " + game.name);
		}

		saveToFile(games, null);

	}

	/**
	 * Get all the games from one user.
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	private static HashSet<Game> getUserGames(String user) throws IOException {

		String urlString = "https://en.lichess.org/api/user/" + user + "/games?with_opening=1";

		URL url = new URL(urlString);
		InputStream inputStream = url.openStream();
		JsonReader rdr = Json.createReader(inputStream);
		JsonObject obj = rdr.readObject();
		JsonArray results = obj.getJsonArray("currentPageResults");
		HashSet<Game> games = new HashSet<Game>();

		for (JsonObject result : results.getValuesAs(JsonObject.class)) {
			String id = result.getString("id");
			String white = result.getJsonObject("players").getJsonObject("white").getString("userId");
			String black = result.getJsonObject("players").getJsonObject("black").getString("userId");

			JsonObject opening = result.getJsonObject("opening");

			games.add(new Game(id, white, black, opening.getString("eco"), opening.getString("name")));
		}

		return games;

	}

	private static HashSet<Game> getGamesOfManyUsers(Set<String> users) throws IOException {

		HashSet<Game> games = new HashSet<Game>();

		for (String user : users) {
			if (crawledUsers.get(user) == null) {
				System.out.println("User " + user + " has not been crawled yet ...");

				games.addAll(getUserGames(user));

			} else {
				System.out.println("User " + user + " has already been crawled.");
			}
		}

		return games;

	}

	protected static void saveToFile(Set<Game> games, @Nullable String fileName) {
		String fileNameFinal;
		
		Logger log = Logger.getLogger(de.sb.LichessAnalysis.WebCrawler.class.getName());

		int numberOfResults = games.size();

		if (fileName == null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			fileNameFinal = "game_data_" + timeStamp;
		} else {
			fileNameFinal = fileName;
		}

		log.log(Level.INFO, "Writing " + numberOfResults + " entries ...");
		try {			

			PrintWriter out = new PrintWriter(fileNameFinal);

			for (Game game : games) {
				// write file
				out.print(game.id);
				out.print(";");
				out.print(game.white);
				out.print(";");
				out.print(game.black);
				out.print(";");
				out.print(game.eco);
				out.print(";");
				out.print(game.name);
				out.print(";");
				out.print(System.getProperty("line.separator"));
			}

			out.close();
			log.log(Level.INFO, "Done!");

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "Write error!");
			e.printStackTrace();
		}
	}

}
