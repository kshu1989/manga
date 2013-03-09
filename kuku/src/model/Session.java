package model;

import java.util.Vector;

public class Session {

	public static String CHAR_SET;
	private String mangaName;
	private String mangaUrl;
	private Vector Episodes;
	private boolean done;
	private String saveDirectoryPath;

	public String getSaveDirectoryPath() {
		return saveDirectoryPath;
	}

	public void setSaveDirectoryPath(String saveDirectoryPath) {
		this.saveDirectoryPath = saveDirectoryPath;
	}

	public Session() {
		Episodes = new Vector();
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getMangaUrl() {
		return mangaUrl;
	}

	public void setMangaUrl(String mangaUrl) {
		this.mangaUrl = mangaUrl;
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
