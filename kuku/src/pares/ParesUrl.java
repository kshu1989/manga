package pares;

import java.io.IOException;
import java.util.Vector;

import javax.lang.model.util.Elements;

import model.Episode;
import config.ConfigContext;

public class ParesUrl {

	public Vector<Episode> paresEpisode(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			parseCharset(doc);
			return parseEpisodeUrls(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc = null;
		return null;
	}

	private Vector<Episode> parseEpisodeUrls(Document doc) {
		Vector<Episode> Episodes = new Vector<Episode>();
		Elements l = doc.select("dl[id]");

		for (Element link : l) {
			for (Node ur1l : link.childNodes()) {
				Episode episode = new Episode();
				for (Node u : ur1l.childNodes()) {
					if (u.hasAttr("href")) {
						episode.addUrl(u.absUrl("href"));
						if (((Element) u).html().contains(
								ConfigContext.mangaName)) {
							episode.setName(((Element) u).html());
						}
					}
				}
				if (episode.getName() != null)
					Episodes.add(episode);
			}
		}
		return Episodes;
	}

	private void parseCharset(Document doc) {

		Elements metas = doc.select("meta[content]");

		for (Element meta : metas) {
			String str = meta.attributes().get("content");
			int start = str.lastIndexOf("charset=");
			if (start > -1) {
				ConfigContext.charset = str.substring(start
						+ "charset=".length());
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String url = "http://comic.kukudm.com/comiclist/6/";
		Vector v = new ParesUrl().paresEpisode(url);
		v.add(null);
		System.out.println(ConfigContext.charset);
	}
}
