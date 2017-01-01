package de.sb.LichessAnalysis;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class WebCrawlerTest {
	
	WebCrawler crawler;

	@Before
	public void setUp() {
	}
	
	@Test
	public void saveToFile(){
		
		HashSet<Game> games = new HashSet<Game>();
		
		games.add(new Game("1", "a", "b", "B77", "Sicilian Defense: Dragon Variation, Yugoslav Attack, Main Line"));
		games.add(new Game("2", "a", "b", "B80", "Sicilian Defense: Scheveningen Variation"));
		games.add(new Game("3", "a", "b", "B01", "Scandinavian Defense: Blackburne Gambit"));
		
		WebCrawler.saveToFile(games, "game_data_Unit_Test.txt");		
		
	}
	
}
