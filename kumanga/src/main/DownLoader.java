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
import model.Season;
import model.Volume;

import org.apache.log4j.PropertyConfigurator;

import parse.PageUrlParserWorker;
import parse.PictureParserWorker;
import parse.SeasonUrlParseImplWorker;
import download.WritePictureWorker;

public class DownLoader {

	public static void main(String[] args) {

		int threadPollNum = 4;
		int downloadThreadNum = 2;
		int parseingThreadNum = 2;

		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		PropertyConfigurator.configure(".//src//log4j.properties");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					new FileInputStream("./src/config.properties"), "utf8"));
			Properties prop = new Properties();
			prop.load(is);
			mangaUrl = prop.getProperty("mangaUrl");
			charset = prop.getProperty("charset");
			saveDestination = prop.getProperty("saveDestination");

			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Volume volume = new Volume();
		volume.setVolumeUrl(mangaUrl);

		// new SeasonUrlParseImplWorker(volume).run();

		/*
		 * for testing
		 */
		Season s = new Season();
		s.setMangaName("asdf");
		s.setMangaUrl("http://comic.kukudm.com/comiclist/5/index.htm");
		s.setSaveDirectoryPath("D:\\movies\\japan");
		
		Season s1 = new Season();
		s1.setMangaName("zcxv");
		s1.setMangaUrl("http://comic.kukudm.com/comiclist/5/index.htm");
		s1.setSaveDirectoryPath("D:\\movies\\japan");
		
		volume.getSeasons().add(s);
		volume.getSeasons().add(s1);

		// for (int p = volume.getSeasons().size() - 1; p >= 0; p--) {
		for (Season season : volume.getSeasons()) {
			// Season season = volume.getSeasons().get(p);

			season.setSaveDirectoryPath(saveDestination);
			season.setRegex(season.getMangaName() + "[\\[_ ]");

			// new PageUrlParserWorker(season).run();

			/*
			 * for testing
			 */
			Picture p = new Picture();
			p.setIndex(1);
			p.setPageUrl("http://comic.kukudm.com/comiclist/5/32712/1.htm");

			Picture p2 = new Picture();
			p2.setIndex(1);
			p2.setPageUrl("http://comic.kukudm.com/comiclist/5/32712/2.htm");

			Episode epi = new Episode();
			epi.setName("zhentan");
			epi.setPicture(p);
			p.setEpisode(epi);

			Episode epi2 = new Episode();
			epi2.setName("zhentan2");
			epi2.setPicture(p2);
			p.setEpisode(epi2);

			Vector l = new Vector<Episode>();
			l.add(epi);
			l.add(epi2);
			season.setEpisodes(l);

			ExecutorService executor = Executors
					.newFixedThreadPool(threadPollNum);
			for (int i = 0; i < parseingThreadNum; i++) {
				PictureParserWorker ppuworker = new PictureParserWorker(season);
				// ppuworker.run();
				executor.execute(ppuworker);
			}

			// for (Episode e : (Vector<Episode>) season.getEpisodes()) {
			// Picture pi = e.getPicture();
			// while (pi != null) {
			// System.out.println(e.getName() + "--->" + pi.getPageUrl());
			// pi = pi.getNextPic();
			// }
			// }

			for (int i = 0; i < downloadThreadNum; i++) {
				WritePictureWorker wpworker = new WritePictureWorker(season);
				// wpworker.run();
				executor.execute(wpworker);
			}
			executor.shutdown();
			while (!executor.isTerminated()) {
				// try {
				// Thread.sleep(10000);
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// int num[] = new int[season.getEpisodes().size()];
				// for (int i = 0; i < num.length; i++)
				// num[i] = 0;
				//
				// int sum = 0;
				// for (Episode episode : (Vector<Episode>)
				// season.getEpisodes()) {
				// if (episode.isDownloaded()) {
				// sum += 1;
				// } else {
				// break;
				// }
				// }
				// if (sum == season.getEpisodes().size()) {
				// for (int i = 0; i < downloadThreadNum; i++) {
				// season.semp.release();
				// System.out.println("asfd");
				// }
				// break;
				// }
				//
			}
			System.out.println("Finished all threads");
		}
	}
}
