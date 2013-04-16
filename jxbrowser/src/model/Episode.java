package model;

/**
 * @author kshu
 * 
 */
public class Episode {

	private String name;
	private Picture picture;
	private boolean parsing;
	private boolean parsed;
	private boolean downloading;
	private boolean downloaded;

	public Episode() {
		this.parsed = false;
		this.parsing = false;
		this.downloading = false;
		this.downloaded = false;
	}

	public boolean isParsingAndSetTure() {
		synchronized (this) {
			boolean temp = this.parsing;
			this.parsing = true;
			return temp;
		}
	}

	public boolean isDownlondingAndSetTrue() {
		synchronized (this) {
			boolean temp = this.downloading;
			this.downloading = true;
			return temp;
		}
	}

	public boolean isParsing() {
		return parsing;
	}

	public void setParsing(boolean parsing) {
		this.parsing = parsing;
	}

	public boolean isParsed() {
		return parsed;
	}

	public void setParsed(boolean parsed) {
		this.parsed = parsed;
	}

	public boolean isDownloading() {
		return downloading;
	}

	public void setDownloading(boolean downloading) {
		this.downloading = downloading;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaned) {
		this.downloaded = downloaned;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}