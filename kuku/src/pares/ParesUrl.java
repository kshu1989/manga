package pares;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import model.Episode;
import model.Session;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParesUrl {

	public boolean paresMangaPage(Session session) {
		Document doc = null;
		try {
			doc = Jsoup.connect(session.getMangaUrl()).timeout(10000).get();
			Episode.CHARSET = parseCharset(doc);
			Vector<Episode> episodes = parseEpisodeUrls(doc);
			session.setEpisodes(episodes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Vector<Episode> parseEpisodeUrls(Document doc) {
		Vector<Episode> Episodes = new Vector<Episode>();
		Elements elements = (Elements) doc.select("a[href]");
		Pattern p = Pattern.compile("海贼王[_,\\[]");
		for (Element element : elements) {
			if (p.matcher(element.text()).find()) {
				Episode episode = new Episode();
				episode.setName(element.text());
				episode.setFirPageUrl(element.absUrl("href"));
				// System.out.println(element.text());
				Episodes.add(episode);
			}
		}
		return Episodes;
	}

	/**
	 * <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	 * 
	 * @param doc
	 */
	private String parseCharset(Document doc) {
		Elements metas = (Elements) doc.select("meta[content]");
		for (Element meta : metas) {
			String str = meta.attributes().get("content");
			int start = str.lastIndexOf("charset=");
			if (start > -1) {
				return str.substring(start + "charset=".length());
			}
		}
		return Episode.CHARSET;
	}

	public static void main(String[] args) throws IOException {
		String url = "http://comic.kukudm.com/comiclist/4/";
//		Vector v = new ParesUrl().paresEpisodes(url);
		System.out.println("ConfigContext.charset");
	}
}
