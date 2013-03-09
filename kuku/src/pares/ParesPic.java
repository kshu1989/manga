package pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Episode;
import model.Picture;
import model.Session;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import test.TestLog4j;

public class ParesPic {

	static Logger log = Logger.getLogger(ParesPic.class.getName());

	public static void main(String[] args) throws IOException {

		Logger.getLogger("").setLevel(Level.OFF);
		String url = "http://www.socomic.com/comiclist/4/32265/1.htm";

		URL urlObj = new URL(url);

		URLConnection connection = urlObj.openConnection();
		InputStream in = connection.getInputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		InputStream read = new ByteArrayInputStream(baos.toByteArray());
		read.mark(0);

		Picture p = new Picture();
		p.setPageUrl("http://www.socomic.com/comiclist/4/29067/1.htm");

		new ParesPic().parseOneEpisode(p, 1);

		while (p != null) {
			System.out.println(p.getPageUrl() + "----->" + p.getPictureUrl());
			p = p.getNextPic();
		}

		System.out.println("over!");
		System.exit(0);
	}

	public boolean parseOneEpisode(Picture pic, int level) {
		Picture nextPic = null;
		try {

			URL urlObj = new URL(pic.getPageUrl());
			URLConnection connection = urlObj.openConnection();
			InputStream in = connection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			InputStream read = new ByteArrayInputStream(baos.toByteArray());
			read.mark(0);
			String picUrl = parsePageImage(read, pic.getPageUrl());
			read.reset();
			String nextUrl = parseNextPageUrl(read, pic.getPageUrl());

			if (nextUrl == null) {
				return false;
			}
			pic.setIndex(level);
			pic.setPictureUrl(picUrl);
			nextPic = new Picture();
			nextPic.setPageUrl(nextUrl);
			if (nextUrl.equals("exit")) {
				return true;
			}
			pic.setNextPic(nextPic);
		} catch (ConnectException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
		return parseOneEpisode(nextPic, ++level);
	}

	private String parsePageImage(InputStream is, String url) {
		try {
			UserAgentContext context = new SimpleUserAgentContext();
			DocumentBuilderImpl dbi = new DocumentBuilderImpl(context);
			Document document = dbi.parse(new InputSourceImpl(is, url,
					Session.CHAR_SET));

			Element ex = document.getDocumentElement();
			NodeList nl = ex.getElementsByTagName("img");

			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);
				if (element.getAttributes().getLength() == 1
						&& element.hasAttribute("src")) {
					return element.getAttribute("src");
				} else {
					// <img id="comicpic" name="comicpic"
					// src="http://cc.kukudm.com/comic/kuku2comic/bleach/Vol_13/068LBR5B9.jpg">
					if (element.getAttribute("id").equals("comicpic")) {
						return element.getAttribute("src");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String parseNextPageUrl(InputStream is, String url)
			throws IOException {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(is,
				Session.CHAR_SET, url);
		org.jsoup.select.Elements elements = doc.select("a[href]");

		for (org.jsoup.nodes.Element element : elements) {
			if (element.childNodeSize() == 1 && element.child(0).hasAttr("src")
					&& element.child(0).attr("src").equals("/images/d.gif")) {
				if (element.absUrl("href").contains("exit"))
					return "exit";
				else
					return element.absUrl("href");
			}
		}
		return null;
	}
}
