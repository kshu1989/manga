package main;

import java.io.IOException;
import java.util.Vector;

import pares.ParesUrl;
import pares.ParesPicUrl;

import model.Episode;

public class DownLoader {
	
	private String url;
	private String mangaName;
	private Vector<Episode> episodes;
	
	public DownLoader(String url, String mangaName){
		this.url = url;
		this.mangaName = mangaName;
	}

	public void run(){
		ParesUrl paresUrl = new ParesUrl();
		ParesPicUrl p = new ParesPicUrl();
		Vector<String> picUrls;
		try {
			episodes = paresUrl.paresEpisode(this.url, this.mangaName);
			
			for(int i = 0 ; i < episodes.size(); i ++){
				picUrls = p.getEpisodePicsUrl(episodes.get(i).getUrl());
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DownLoader dl = new DownLoader("http://www.imanhua.com/comic/55/", "海贼王");
		dl.run();
	}

}
