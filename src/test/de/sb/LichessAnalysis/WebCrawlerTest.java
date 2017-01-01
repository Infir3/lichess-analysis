package de.sb.LichessAnalysis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class WebCrawlerTest {

	WebCrawler crawler;

	@Before
	public void setUp() {
		crawler = new WebCrawler();
	}

	@Test
	public void crawl() throws IOException {
		crawler.crawl("Jobst");
		assertFalse((crawler.getGames().isEmpty()));
	}

	@Test
	public void saveToFile() {

		HashSet<Game> games = new HashSet<Game>();

		games.add(new Game("1", "a", "b", "B77", "Sicilian Defense: Dragon Variation, Yugoslav Attack, Main Line"));
		games.add(new Game("2", "a", "b", "B80", "Sicilian Defense: Scheveningen Variation"));
		games.add(new Game("3", "a", "b", "B01", "Scandinavian Defense: Blackburne Gambit"));

		crawler.setGames(games);

		String fileName = crawler.saveToFile("game_data_Unit_Test.txt");

		File file = new File(fileName);
		assertTrue(file.exists());

	}

}
