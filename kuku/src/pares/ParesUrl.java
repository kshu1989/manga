package pares;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import model.Episode;
import model.Picture;
import model.Session;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParesUrl {
	static Logger log = Logger.getLogger(ParesUrl.class.getName());

	public boolean paresMangaPage(Session session) {

		Document doc = null;
		try {
			doc = Jsoup.connect(session.getMangaUrl()).timeout(30000).get();
			Session.CHAR_SET = parseCharset(doc);
			session.setEpisodes(parseEpisodeUrls(session, doc));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Vector parseEpisodeUrls(Session session, Document doc) {
		Vector Episodes = new Vector();
		Elements elements = doc.select("a[href]");
		Pattern p = Pattern.compile(session.getRegex());
		for (Element element : elements) {
			if (p.matcher(element.text()).find()
					&& element.absUrl("href").endsWith("1.htm")) {
				Episode episode = new Episode();

				Picture picture = new Picture();
				picture.setIndex(1);
				picture.setPageUrl(element.absUrl("href"));
				episode.setName(element.text());
				episode.setPicture(picture);
				Episodes.add(episode);
				log.info(episode.getName() + " : " + picture.getPageUrl());
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
		return Session.CHAR_SET;
	}

	public static void main(String[] args) throws IOException {
		String url = "http://comic.kukudm.com/comiclist/4/";
		Session s = new Session();
		s.setMangaUrl(url);

		new ParesUrl().paresMangaPage(s);
		System.out.println(s.getEpisodes());
		System.out.println("ConfigContext.charset");
	}
}
