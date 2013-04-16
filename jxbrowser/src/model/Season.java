package model;

import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Season {

	public static String CHAR_SET;
	private String regex;
	private String mangaName;
	private String mangaUrl;
	private Vector Episodes;
	private String saveDirectoryPath;
	private boolean done;
	final public Semaphore semp = new Semaphore(0);  

	public String getSaveDirectoryPath() {
		return saveDirectoryPath;
	}

	public void setSaveDirectoryPath(String saveDirectoryPath) {
		this.saveDirectoryPath = saveDirectoryPath;
	}

	public Season() {
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

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
