package pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.lobobrowser.html.parser.*;
import org.lobobrowser.html.test.*;
import org.lobobrowser.html.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import model.Episode;
import config.ConfigContext;

public class ParesPic {

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

		System.out.println(new ParesPic().parseImage(read, url));

		System.out.println("over!");
		System.exit(0);
	}

	public void parseWorker(Episode episode) {
		parseNextUrl(episode.getUrls().get(0), episode.getUrlsForPics());
	}

	public String parseOnlyImage(String url) {
		try {
			URL urlObj = new URL(url);
			URLConnection connection = urlObj.openConnection();
			InputStream in = connection.getInputStream();

			return this.parseImage(in, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String parseImage(InputStream is, String url) {
		try {
			UserAgentContext context = new SimpleUserAgentContext();
			DocumentBuilderImpl dbi = new DocumentBuilderImpl(context);
			Document document = dbi.parse(new InputSourceImpl(is, url,
					Episode.CHARSET));

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String parseNextUrl(InputStream is, String url) throws IOException {
		org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(is,
				Episode.CHARSET, url);
		org.jsoup.select.Elements elements = doc.select("a[href]");

		for (org.jsoup.nodes.Element element : elements) {
			if (element.childNodeSize() == 1 && element.child(0).hasAttr("src")
					&& element.child(0).attr("src").equals("/images/d.gif")) {
				return element.absUrl("href");
			}
		}
		return "";
	}

	private void parseNextUrl(String url, Map<String, String> urlForPic) {
		try {
			String picUrl = null;
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

			picUrl = parseImage(read, url);
			if (picUrl != null && !picUrl.equals("")) {
				urlForPic.put(url, picUrl);
			} else {
				urlForPic.put(url, "");
			}
			System.out.println(url + " ---> " + picUrl);
			read.reset();

			org.jsoup.nodes.Document docForNext = Jsoup.parse(read,
					ConfigContext.charset, url);
			Elements links = docForNext.select("a[href]");
			for (org.jsoup.nodes.Element lin : links) {
				String href = lin.absUrl("href");
				if (!urlForPic.containsKey(href) && !href.contains("exit")) {
					in.close();
					read.close();
					baos.close();
					in = null;
					read = null;
					baos = null;
					buffer = null;
					parseNextUrl(href, urlForPic);
				}
			}
		} catch (ConnectException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
