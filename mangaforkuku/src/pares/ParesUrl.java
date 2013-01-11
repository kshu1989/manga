package pares;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import model.Episode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParesUrl {

	public Vector<Episode> paresEpisode(String url) throws IOException {

		print("Fetching %s...", url);

		Vector<Episode> urlHouses = new Vector<Episode>();
		// Document doc = Jsoup.connect(url).get();

		Document doc = Jsoup.connect(url).timeout(10000).get();
		// .userAgent("Mozilla").timeout(10000)
		// .post();

		Elements links = doc.select("a[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			// String title = link.text();
			if (link.html().contains("海贼王[") || link.html().contains("海贼王_")) {
				if (!link.attr("abs:href").equals(url)) {
					Episode episode = new Episode(link.text(),
							link.attr("abs:href"));
					urlHouses.add(episode);
					System.out.println(episode.getUrl());
				}
			}
		}
		return urlHouses;
	}

	private void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public static void main(String[] args) throws IOException {
		String url = "http://comic.kukudm.com/comiclist/4/";
		new ParesUrl().paresEpisode(url);
	}
}
