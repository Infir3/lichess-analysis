package de.sb.LichessAnalysis;

import java.io.IOException;
import java.util.Set;

public class Launcher {
	
	private static final String START_USER = "jobst";
	private static final int CRAWL_DEPTH = 3;

	public static void main(String[] args) throws IOException {
		
		WebCrawler crawler = new WebCrawler();
		
		crawler.crawl(START_USER);
		
		crawler.saveToFile();
		
	}

}
