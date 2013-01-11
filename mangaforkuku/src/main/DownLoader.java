package main;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.Episode;
import pares.ParesUrl;

public class DownLoader {

	private static final String path = "G:\\myfiles\\pictures\\";
	private static final String url = "http://comic.kukudm.com/comiclist/4/";
	private static final String mangaName = "海贼王";
	private static final int NTHREDS = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		ParesUrl paresUrl = new ParesUrl();
		try {
			List<Episode> episodes = paresUrl.paresEpisode(url);

			for (int i = episodes.size(); i > 0; i--) {
				Runnable worker = new Worker(episodes.get(i).getUrl(), path,
						mangaName);
				executor.execute(worker);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			System.out.println("Finished all threads");
		}
	}
}

/*
 * for (int i = episodes.size() - 2; i > 0; i--) { ParesPicUrl p = new
 * ParesPicUrl(episodes.get(i).getUrl(), mangaName); Map<String, Integer>
 * picUrls = p.getPicsUrls(); if (picUrls != null) { WritePic wp = new
 * WritePic(picUrls, p.getMangaName(), path); wp.run(); } }
 */
