package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Episode;
import model.Picture;
import model.Session;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pares.ParesUrl;
import pares.ParsePicureUrlWorker;
import download.WritePictureWorker;

public class DownLoader {

	static Logger log = Logger.getLogger(DownLoader.class.getName());

	public static void main(String[] args) {

		PropertyConfigurator.configure("./src/log4j.properties");

		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		// Logger.getLogger("").setLevel(Level.SEVERE);

		// log.warning("Hello this is an info masdfessage");
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Session session = new Session();
		session.setDone(false);
		session.setMangaName(mangaName);
		session.setMangaUrl(mangaUrl);
		session.setSaveDirectoryPath(saveDestination);
		session.setRegex(mangaName + "[\\[_]");

		if (!new ParesUrl().paresMangaPage(session)) {
			System.out.println("parse error");
			return;
		}

		for (Episode e : (Vector<Episode>) session.getEpisodes()) {
			System.out.println(e.getPicture().getPageUrl());
		}

		for (int i = 0; i < 4; i++) {
			Episode ep = (Episode) session.getEpisodes().get(i);
			Vector<Episode> v = new Vector<Episode>();
			v.add(ep);
			int j = 0;
			Picture pi = ep.getPicture();
			while (pi != null) {
				pi = pi.getNextPic();
				if (j++ > 4)
					break;
			}
		}

		for (Episode e : (Vector<Episode>) session.getEpisodes()) {
			System.out.println(e.getPicture().getPageUrl());
			Picture pi = e.getPicture();
			while (pi != null) {
				System.out.println(pi.getPageUrl() + "--->"
						+ pi.getPictureUrl());
				pi = pi.getNextPic();
			}
		}

		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 2; i++) {
			WritePictureWorker wpworker = new WritePictureWorker(session);
			executor.execute(wpworker);
		}

		for (int i = 0; i < 2; i++) {
			ParsePicureUrlWorker ppuworker = new ParsePicureUrlWorker(session);
			executor.execute(ppuworker);
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}
}
