package test;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import model.Episode;
import pares.ParesPicUrl;
import pares.ParesUrl;

public class MyTest extends TestCase {

	private ParesUrl tester;

	protected void setUp() {
		tester = new ParesUrl();
	}

	public void testParesEpisode() {
		try {
			Vector<Episode> urlHouses = tester.paresEpisode(
					"http://www.imanhua.com/comic/55/", "海贼王");

			for (int i = 0; i < urlHouses.size(); i++) {
				System.out.println(urlHouses.get(i).getUrl());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testParesPicture() {
		String url = "http://www.imanhua.com/comic/55/list_51628.html?p=3";
		ParesPicUrl p = new ParesPicUrl();

		List picUrls = p.getEpisodePicsUrl(url);
		for (int i = 0; i < picUrls.size(); i++) {
			System.out.println(picUrls.get(i));
		}
	}
}
