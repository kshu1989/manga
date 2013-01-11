package main;

import java.util.Map;
import download.WritePic;
import pares.ParesPicUrl;

public class Worker implements Runnable {

	private String url;
	private String path;
	private String mangaName;

	public Worker(String url, String path, String mangaName) {
		this.url = url;
		this.path = path;
		this.mangaName = mangaName;
	}

	public void run() {
		ParesPicUrl p = new ParesPicUrl(url, mangaName);
		Map<String, Integer> picUrls = p.getPicsUrls();
		if (picUrls != null) {
			WritePic wp = new WritePic(picUrls, p.getMangaName(), path);
			wp.run();
		}
	}
}
