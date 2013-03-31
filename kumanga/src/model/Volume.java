package model;

import java.util.ArrayList;
import java.util.List;

public class Volume {

	private String volumeUrl;
	private List<Season> seasons;

	public Volume() {
		this.seasons = new ArrayList<Season>();
	}

	public String getVolumeUrl() {
		return volumeUrl;
	}

	public void setVolumeUrl(String volumeUrl) {
		this.volumeUrl = volumeUrl;
	}

	public List<Season> getSeasons() {
		return seasons;
	}
}
