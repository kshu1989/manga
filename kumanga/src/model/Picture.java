package model;

public class Picture {

	private String pictureUrl;
	private String pageUrl;
	private int index;
	private Picture nextPic;

	public Picture() {
		this.index = 1;
	}

	public Picture(String pic, String page, int index) {
		this.pageUrl = page;
		this.pictureUrl = pic;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Picture getNextPic() {
		return nextPic;
	}

	public void setNextPic(Picture nextPic) {
		this.nextPic = nextPic;
	}
}
