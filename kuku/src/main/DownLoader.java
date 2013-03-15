package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Episode;
import model.Session;
import pares.ParsePageUrlWorker;
import test.TestLog4j;

public class DownLoader {

	static Logger log = Logger.getLogger(DownLoader.class.getName());
	
	public static void main(String[] args) {

		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		Logger.getLogger("").setLevel(Level.OFF);
		log.info("Hello this is an info masdfessage");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					new FileInputStream("./src/config.properties"), "utf8"));
			Properties prop = new Properties();
			prop.load(is);
			mangaName = prop.getProperty("mangaName");
			mangaUrl = prop.getProperty("mangaUrl");
			charset = prop.getProperty("charset");
			saveDestination = prop.getProperty("saveDestination");

			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Session session = new Session();
		session.setDone(false);
		session.setMangaName(mangaName);
		session.setMangaUrl(mangaUrl);
		session.setSaveDirectoryPath(saveDestination);
		session.setRegex(mangaName + "[\\[_]");

		ExecutorService executor = Executors.newFixedThreadPool(10);
		ParsePageUrlWorker worker = new ParsePageUrlWorker(session);
		executor.execute(worker);
		
		for (int i = 0; i < 10; i++) {
			
		}

		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}
		System.out.println("Finished all threads");

		for (Episode e : (Vector<Episode>) session.getEpisodes()) {
			System.out.println(e.getPicture().getPageUrl());
		}

		
	}
}
