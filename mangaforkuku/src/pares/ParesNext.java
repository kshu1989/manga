package pares;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Episode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParesNext {

	public void paresEpisode(String url) throws IOException {

		print("Fetching %s...", url);

		Vector<Episode> urlHouses = new Vector<Episode>();
		Document doc = Jsoup.connect(url).timeout(10000).get();

		// Document doc = Jsoup.connect(url).data("query", "Java")
		// .userAgent("Mozilla").cookie("auth", "token").timeout(3000)
		// .post();

		
		

		Elements links = doc.select("td[valign]");

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			String str = link.html().substring(0, 100);

			String s[] = str.split(str.replace('|', '~'));
			for (int i = 0; i < s.length; i++) {
				System.out.println(s[i]);
				if (s[i].contains("共") && s[i].contains("页")) {
					int start = s[i].indexOf("共");
					int end = s[i].indexOf("页");
					System.out.println(s[i].substring(start + "共".length(), end));
				}
			}
		}
		// Elements links = doc.select("meta[content]");
		//
		// print("\nLinks: (%d)", links.size());
		// for (Element link : links) {
		// System.out.println(link.attributes().get("content"));
		// String str = link.attributes().get("content");
		// int start = str.lastIndexOf("charset=");
		// String charset = "GBK";
		// if(start > -1){
		// charset = str.substring(start + "charset=".length());
		// }
		//
		// System.out.println(charset.length());
		// }

		// if (title.contains(name+"_") || title.contains(name+"[")) {
		// System.out.println(title + "   " + link.attr("abs:href"));
		// Episode episode = new Episode(title, link.attr("abs:href"));
		// urlHouses.add(episode);
	}

	private void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public static void main(String[] args) throws IOException {
		ParesNext p = new ParesNext();
		String url = "http://comic.kukudm.com/comiclist/4/24286/1.htm";
		p.paresEpisode(url);
	}
}
