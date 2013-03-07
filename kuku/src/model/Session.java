package model;

import java.util.Vector;

public class Session {

	private String mangaName;
	private String mangaUrl;
	private Vector Episodes;

	public String getMangaUrl() {
		return mangaUrl;
	}

	public void setMangaUrl(String mangaUrl) {
		this.mangaUrl = mangaUrl;
	}

	public Session() {
		Episodes = new Vector();
	}

	public String getMangaName() {
		return mangaName;
	}

	public void setMangaName(String mangaName) {
		this.mangaName = mangaName;
	}

	public Vector getEpisodes() {
		return Episodes;
	}

	public void setEpisodes(Vector episodes) {
		Episodes = episodes;
	}
}
