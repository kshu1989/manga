package pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.util.Elements;

import model.Episode;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import config.ConfigContext;

public class ParesPic {

	public static void main(String[] args) {

		Logger.getLogger("").setLevel(Level.OFF);
		String url = "http://comic.kukudm.com/comiclist/6/20187/1.htm";
		Episode e = new Episode();
		e.getUrls().add(url);
		new ParesPic().parseWorker(e);
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

	private String parseImage(InputStream is1, String url) {
		try {
			UserAgentContext context = new SimpleUserAgentContext();
			DocumentBuilderImpl dbi = new DocumentBuilderImpl(context);
			Document document = dbi.parse(new InputSourceImpl(is1, url,
					ConfigContext.charset));

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
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
