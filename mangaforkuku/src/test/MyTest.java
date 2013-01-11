package test;

import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;
import model.Episode;
import pares.ParesPicUrl;
import pares.ParesPicUrls;
import pares.ParesUrl;

public class MyTest extends TestCase {

	private ParesUrl tester;
	private ParesPicUrl pic;

	protected void setUp() {
		tester = new ParesUrl();
		pic = new ParesPicUrl();
	}

	public void testParesEpisode() {
		try {
			Vector<Episode> urlHouses = tester.paresEpisode("http://comic.kukudm.com/comiclist/4/", "海贼王");
			
			for(int i = 0 ; i < urlHouses.size(); i ++){
				System.out.println(urlHouses.get(i).getUrl());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetPicName(){
		String url = "http://comic.kukudm.com/comiclist/4/24286/17.htm";
		pic.getPicName(url);
	}
	
//	public void testParesPicture(){
//		String url = "http://www.imanhua.com/comic/55/list_51628.html?p=3";
//		ParesPicUrl p = new ParesPicUrl();
//		
//		System.out.println(p.getReUrl());
//	}
}
