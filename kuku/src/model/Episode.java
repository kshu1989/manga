package model;

/**
 * @author kshu
 * 
 */
public class Episode {

	private String name;
	private Picture picture;
	private boolean isDownload;
	private boolean isParse;
	private boolean downloaded;

	public Episode() {
		this.isDownload = false;
		this.isParse = false;
		this.downloaded = false;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		synchronized (this) {
			this.downloaded = downloaded;
		}
	}

	public boolean isDownloadedAndSetTrue() {
		synchronized (this) {
			boolean tem = downloaded;
			downloaded = true;
			return tem;
		}
	}

	public boolean getIsDownLoad() {
		return this.isDownload;
	}

	public boolean getIsParse() {
		return this.isParse;
	}

	public boolean isDownloadAndSetTrue() {
		synchronized (this) {
			boolean tem = isDownload;
			isDownload = true;
			return tem;
		}
	}

	public boolean isParseAndSetTrue() {
		synchronized (this) {
			boolean tem = isParse;
			isParse = true;
			return tem;
		}
	}

	public void setDownload(boolean isDownload) {
		synchronized (this) {
			this.isDownload = isDownload;
		}
	}

	public void setParse(boolean isParse) {
		synchronized (this) {
			this.isParse = isParse;
		}
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