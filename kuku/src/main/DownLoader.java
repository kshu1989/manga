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

import org.apache.log4j.PropertyConfigurator;

import model.Episode;
import model.Picture;
import model.Session;

import pares.ParesUrl;
import pares.ParsePicureUrlWorker;
import download.WritePictureWorker;

public class DownLoader {

	public static void main(String[] args) {

		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		java.util.logging.Logger.getLogger("org.lobobrowser").setLevel(
				java.util.logging.Level.OFF);
		PropertyConfigurator.configure(".//src//log4j.properties");
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

		Vector<Episode> v = new Vector<Episode>();
		for (int i = session.getEpisodes().size() - 1; i > session
				.getEpisodes().size() - 3; i--) {
			Episode ep = (Episode) session.getEpisodes().get(i);
			v.add(ep);
			int j = 0;
			Picture pi = ep.getPicture();
			while (pi != null) {
				pi = pi.getNextPic();
				if (j++ > 4)
					break;
			}
		}
//		session.setEpisodes(v);

		for (Episode e : (Vector<Episode>) session.getEpisodes()) {
			Picture pi = e.getPicture();
			while (pi != null) {
				System.out.println(e.getName() + "--->" + pi.getPageUrl());
				pi = pi.getNextPic();
			}
		}

		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 2; i++) {
			ParsePicureUrlWorker ppuworker = new ParsePicureUrlWorker(session);
			executor.execute(ppuworker);
		}

		for (int i = 0; i < 2; i++) {
			WritePictureWorker wpworker = new WritePictureWorker(session);
			executor.execute(wpworker);
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
		}

		// for (Episode ep : (Vector<Episode>) session.getEpisodes()) {
		//
		// Picture pi = ep.getPicture();
		// while (pi != null) {
		// System.out.println(ep.getName() + ":" + pi.getPageUrl() + "-->"
		// + pi.getPictureUrl());
		// pi = pi.getNextPic();
		// }
		// }

		// for (int i = 0; i < 4; i++) {
		// Episode ep = (Episode) session.getEpisodes().get(i);
		// v.add(ep);
		// int j = 0;
		// Picture pi = ep.getPicture();
		// while (pi != null) {
		// pi = pi.getNextPic();
		// if (j++ > 4)
		// break;
		// }
		// }
		System.out.println("Finished all threads");
	}
}
