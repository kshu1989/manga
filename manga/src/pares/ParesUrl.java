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

	public Vector<Episode> paresEpisode(String url, String name)
			throws IOException {

		String OnePiece = "^" + name + "[0-9]+话";
		Pattern pattern = Pattern.compile(OnePiece);

		print("Fetching %s...", url);

		Vector<Episode> urlHouses = new Vector<Episode>();
		// Document doc = Jsoup.connect(url).get();

		Document doc = Jsoup.connect(url).data("query", "Java")
				.userAgent("Mozilla").cookie("auth", "token").timeout(3000)
				.post();

		Elements links = doc.select("a[href]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			String title = link.text();
			if (pattern.matcher(title).matches()) {
				Episode episode = new Episode(title, link.attr("abs:href"));
				urlHouses.add(episode);
			}
		}
		return urlHouses;
	}

	private void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
}
