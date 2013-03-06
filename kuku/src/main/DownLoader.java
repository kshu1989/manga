package main;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Episode;
import pares.ParesUrl;
import config.ConfigContext;

public class DownLoader {

	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
		
		new ConfigContext("死神");
		ExecutorService executor = Executors.newFixedThreadPool(ConfigContext.NTHREDS);
		List<Episode> episodes = new ParesUrl().paresEpisode(ConfigContext.mangaUrl);

		System.out.println(episodes.size());
		for (int i = episodes.size() - 1; i >= 214; i--) {
			Runnable worker = new Worker(episodes.get(i));
			executor.execute(worker);
		}
		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();
		// Wait until all threads are finish
		// while (!executor.isTerminated()) {
		// System.out.println("Finished all threads");
		// }
	}
}

/*
 * for (int i = episodes.size() - 2; i > 0; i--) { ParesPicUrl p = new
 * ParesPicUrl(episodes.get(i).getUrl(), mangaName); Map<String, Integer>
 * picUrls = p.getPicsUrls(); if (picUrls != null) { WritePic wp = new
 * WritePic(picUrls, p.getMangaName(), path); wp.run(); } }
 */
