package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kshu
 * 
 */
public class Episode {

	private String name;
	private List<String> urls;
	private Map urlsForPics;

	public Episode() {
		urls = new ArrayList<String>();
		urlsForPics = new HashMap<String, String>();
	}

	public List<String> getUrls() {
		return urls;
	}

	public Map getUrlsForPics() {
		return urlsForPics;
	}

	public void setUrlsForPics(Map urlsForPics) {
		this.urlsForPics = urlsForPics;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public void addUrl(String url) {
		this.urls.add(url);
	}

	public void addUrlForPics(String url, int n) {
		this.urlsForPics.put(url, n);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}