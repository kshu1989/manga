package pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author kshu
 * 
 */
public class ParesPicUrl{
	private Map<String, Integer> picsUrls;
	private static String charset = "GBK";
	private static int totalPag = -1;
	private int curPage = -1;
	private String parseUrl;
	private String mangaName;

	private Log _log = LogFactory.getLog(ParesPicUrl.class);

	public ParesPicUrl(String parseUrl, String mangaName) {
		this.picsUrls = new HashMap<String, Integer>();
		this.parseUrl = parseUrl;
		this.mangaName = mangaName;
	}

	public String getMangaName() {
		return this.mangaName;
	}

	public Map<String, Integer> getPicsUrls() {
		ArrayList<String> urls = new ArrayList<String>();

		if (this.parsePage(parseUrl, urls, 1)) {
			return picsUrls;
		}
		return null;
	}

	private boolean parseCharset(InputStream is1, String url) {

		org.jsoup.nodes.Document docforCharset;
		try {
			docforCharset = Jsoup.parse(is1, "GBK", url);
			Elements metas = docforCharset.select("meta[content]");

			for (org.jsoup.nodes.Element meta : metas) {
				System.out.println(meta.attributes().get("content"));
				String str = meta.attributes().get("content");
				int start = str.lastIndexOf("charset=");
				if (start > -1) {
					charset = str.substring(start + "charset=".length());
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

			_log.info("ParesPicUrl->parseCharset: IOException="
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		_log.info("ParesPicUrl->parseCharset: url=" + url);
		return false;
	}

	/**
	 * @param is1
	 * @param url
	 * @param level
	 * @return 海贼王[685]桃之助便是在下的大名 | 共18页 | 当前第1页 | 跳转至第
	 */
	private boolean parseTotalAndCurPage(InputStream is1, String url,
			int level, String mangaName) {

		org.jsoup.nodes.Document docForPag;
		try {
			docForPag = Jsoup.parse(is1, charset, url);

			Elements pages = docForPag.select("td[valign]");

			for (org.jsoup.nodes.Element link : pages) {
				String str = link.html().substring(0, 100);

				String s[] = str.replace('|', '~').split("~");

				for (int i = 0; i < s.length; i++) {
					if (level == 1) {
						if (s[i].contains("共") && s[i].contains("页")) {
							int start = s[i].indexOf("共");
							int end = s[i].indexOf("页");
							totalPag = Integer.parseInt(s[i].substring(start
									+ "共".length(), end));
						}
						// 解析漫画名称
						if (s[i].contains(mangaName)) {
							System.out.println(s[i]);
							this.mangaName = s[i];
						}
					}
					// 当前第1页
					if (s[i].contains("当前第") && s[i].contains("页")) {
						// System.out.println(s[i]);
						int start = s[i].indexOf("当前第");
						int end = s[i].indexOf("页");
						this.curPage = Integer.parseInt(s[i].substring(start
								+ "当前第".length(), end));
						// System.out.println(this.curPage);
					}
				}
			}
		} catch (IOException e) {
			_log.info("ParesPicUrl->parseCharset: IOException="
					+ e.getMessage());
			e.printStackTrace();
		}

		if (totalPag > 0 && this.curPage > 0) {
			return true;
		}
		_log.info("ParesPicUrl->parseTotalAndCurPage: url=" + url + " level="
				+ level);
		return false;
	}

	/**
	 * @param is1
	 * @param url
	 * @param level
	 *            if level == -1 only parse url page
	 * @param urls
	 * @return
	 */
	private boolean parseImage(InputStream is1, String url, int level,
			List<String> urls) {
		try {

			UserAgentContext context = new SimpleUserAgentContext();
			DocumentBuilderImpl dbi = new DocumentBuilderImpl(context);
			Document document = dbi
					.parse(new InputSourceImpl(is1, url, charset));

			Element ex = document.getDocumentElement();
			NodeList nl = ex.getElementsByTagName("img");

			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);
				if (element.getAttributes().getLength() == 1
						&& element.hasAttribute("src")) {
					if (level == -1) {
						urls.add(element.getAttribute("src"));
						return true;
					} else {
						picsUrls.put(element.getAttribute("src"), level);
						urls.add(url);
						System.out.println("pic url : " + url);
						return true;
					}
				}
			}
		} catch (IOException e) {
			_log.info("ParesPicUrl->parseCharset: IOException="
					+ e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			_log.info("ParesPicUrl->parseCharset: SAXException="
					+ e.getMessage());
			e.printStackTrace();
		}
		_log.info("ParesPicUrl->parseImage: url=" + url + " level=" + level);
		return false;
	}

	private void parseNextUrl(InputStream is1, String url, int level,
			List<String> urls) throws IOException {

		try {
			org.jsoup.nodes.Document docForNext = Jsoup
					.parse(is1, charset, url);
			Elements links = docForNext.select("a[href]");
			for (org.jsoup.nodes.Element lin : links) {
				String href = lin.absUrl("href");
				if (!urls.contains(href) && !href.contains("exit")) {
					System.out.println("next page url : " + href);
					if (totalPag > 0) {
						if (level < totalPag)
							parsePage(href, urls, level + 1);
					}
				}
			}
		} catch (IOException e) {
			_log.info("ParesPicUrl->parseCharset: IOException="
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean parseOnlyImage(String url) {
		Logger.getLogger("").setLevel(Level.OFF);

		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		System.out.println("Fetching %s..." + url);
		try {

			URLConnection connection = urlObj.openConnection();
			InputStream in = connection.getInputStream();

			this.parseImage(in, url, -1, null);

		} catch (Exception e) {
			System.out.println("parsePage(" + url + "):  " + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean parsePage(String url, List<String> urls, int level) {
		Logger.getLogger("").setLevel(Level.OFF);

		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		System.out.println("Fetching %s..." + url);
		try {

			URLConnection connection = urlObj.openConnection();
			InputStream in = connection.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Fake code simulating the copy
			// You can generally do better with nio if you need...
			// And please, unlike me, do something about the Exceptions :D
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
			InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
			is1.mark(0);
			if (!parseCharset(is1, url)) {
				return false;
			}
			is1.reset();
			if (!parseTotalAndCurPage(is1, url, level, mangaName)) {
				return false;
			}
			is1.reset();
			if (!parseImage(is1, url, level, urls)) {
				return false;
			}
			is1.reset();
			parseNextUrl(is1, url, level, urls);
			is1.close();
		} catch (Exception e) {
			System.out.println("parsePage(" + url + "):  " + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// http://comic.kukudm.com/comiclist/4/24286/17.htm
	public int getPicName(String url) {
		int start = url.lastIndexOf("/");
		int end = url.lastIndexOf(".");
		String name = url.substring(start + 1, end);
		// System.out.println(name);
		int num = Integer.parseInt(name);

		return num;
	}

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		String url = "http://comic.kukudm.com/comiclist/4/24286/1.htm";
		ParesPicUrl p = new ParesPicUrl(url, "死神");

		ArrayList<String> urls = new ArrayList<String>();

		if (p.parsePage(url, urls, 1)) {
			for (int i = 0; i < p.picsUrls.size(); i++) {
				System.out.println("haha");
			}
		}
		System.exit(0);
	}
}

/*
 * public boolean parsePage(String url, List<String> urls, int level) {
 * Logger.getLogger("").setLevel(Level.OFF);
 * 
 * boolean picflag = false;
 * 
 * URL urlObj = null; try { urlObj = new URL(url); } catch (Exception e) {
 * System.out.println(e); return false; }
 * 
 * System.out.println("Fetching %s..." + url); try {
 * 
 * URLConnection connection = urlObj.openConnection(); InputStream in =
 * connection.getInputStream();
 * 
 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Fake code
 * simulating the copy // You can generally do better with nio if you need... //
 * And please, unlike me, do something about the Exceptions :D byte[] buffer =
 * new byte[1024]; int len; while ((len = in.read(buffer)) > -1) {
 * baos.write(buffer, 0, len); } baos.flush();
 * 
 * InputStream is1 = new ByteArrayInputStream(baos.toByteArray()); is1.mark(0);
 * 
 * if (level == 1) {
 * 
 * org.jsoup.nodes.Document docforCharset = Jsoup.parse(is1, "GBK", url);
 * Elements metas = docforCharset.select("meta[content]");
 * 
 * for (org.jsoup.nodes.Element meta : metas) {
 * System.out.println(meta.attributes().get("content")); String str =
 * meta.attributes().get("content"); int start = str.lastIndexOf("charset="); if
 * (start > -1) { charset = str.substring(start + "charset=".length()); } }
 * 
 * is1.reset(); org.jsoup.nodes.Document docForPag = Jsoup.parse(is1, charset,
 * url); Elements pages = docForPag.select("td[valign]");
 * 
 * for (org.jsoup.nodes.Element link : pages) { String str =
 * link.html().substring(0, 100);
 * 
 * String s[] = str.split(str.replace('|', '~')); for (int i = 0; i < s.length;
 * i++) { System.out.println(s[i]); if (s[i].contains("共") &&
 * s[i].contains("页")) { int start = s[i].indexOf("共"); int end =
 * s[i].indexOf("页"); totalPag = Integer.parseInt(s[i].substring(start +
 * "共".length(), end)); } } } }
 * 
 * // InputStream is2 = new ByteArrayInputStream(baos.toByteArray()); // Open
 * new InputStreams using the recorded bytes
 * 
 * is1.reset(); UserAgentContext context = new SimpleUserAgentContext();
 * DocumentBuilderImpl dbi = new DocumentBuilderImpl(context); Document document
 * = dbi .parse(new InputSourceImpl(is1, url, charset));
 * 
 * Element ex = document.getDocumentElement(); NodeList nl =
 * ex.getElementsByTagName("img");
 * 
 * for (int i = 0; i < nl.getLength(); i++) { Element element = (Element)
 * nl.item(i); if (element.getAttributes().getLength() == 1 &&
 * element.hasAttribute("src")) {
 * System.out.println(element.getAttribute("src"));
 * picsUrls.put(element.getAttribute("src"), level); urls.add(url); picflag =
 * true; System.out.println("get pic url " + url); break; } }
 * 
 * if (!picflag) { throw new MyException("can not parse pic url!", url, level);
 * }
 * 
 * is1.reset(); org.jsoup.nodes.Document docForNext = Jsoup .parse(is1, charset,
 * url); Elements links = docForNext.select("a[href]"); for
 * (org.jsoup.nodes.Element lin : links) { String href = lin.absUrl("href"); if
 * (!urls.contains(href) && !href.contains("exit")) {
 * System.out.println("get next page url " + href); if (totalPag > 0) { if
 * (level < totalPag) parsePage(href, urls, level + 1); } } }
 * 
 * is1.close(); } catch (NumberFormatException e) { totalPag = -1; } catch
 * (Exception e) { System.out.println("parsePage(" + url + "):  " + e);
 * e.printStackTrace(); return false; } return true; }
 */

/*
 * 
 * import java.io.* ; import org.lobobrowser.html.*; import
 * org.lobobrowser.html.gui.*; import org.lobobrowser.html.parser.*; import
 * org.lobobrowser.html.test.* ; import org.w3c.dom.* ; import
 * org.xml.sax.InputSource; import org.lobobrowser.html.domimpl.HTMLElementImpl
 * ; import java.net.*; import java.util.* ; import java.util.logging.* ;
 *//**
 * Render - This object is a wrapper for the Cobra Toolkit, which is part of
 * the Lobo Project (http://html.xamjwg.org/index.jsp). Cobra is a
 * "pure Java HTML renderer and DOM parser."
 * <p>
 * Render opens a URL, uses Cobra to render that HTML and apply JavaScript. It
 * then does a simple tree traversal of the DOM to print beginning and end tag
 * names.
 * <p>
 * Subclass this class and override the <i>doElement(org.w3c.dom.Element
 * element)</i> and <i>doTagEnd(org.w3c.dom.Element element)</i> methods to do
 * some real work. In the base class, doElement() prints the tag name and
 * doTagEnd() prints a closing version of the tag.
 * <p>
 * This class is a rewrite of org.benjysbrain.htmlgrab.Render that uses JRex.
 * <p>
 * Copyright (c) 2008 by Ben E. Cline. This code is presented as a teaching aid.
 * No warranty is expressed or implied.
 * <p>
 * http://www.benjysbrain.com/
 * 
 * @author Benjy Cline
 * @version 1/2008
 */
/*
 * 
 * public class Render { String url ; // The page to be processed.
 * 
 * // These variables can be used in subclasses and are created from // url.
 * baseURL can be used to construct the absolute URL of the // relative URL's in
 * the page. hostBase is just the http://host.com/ // part of the URL and can be
 * used to construct the full URL of // URLs in the page that are site relative,
 * e.g., "/xyzzy.jpg". // Variable host is set to the host part of url, e.g.,
 * host.com.
 * 
 * String baseURL ; String hostBase ; String host ;
 *//**
 * Create a Render object with a target URL.
 */
/*
 * 
 * public Render(String url) { this.url = url ; }
 *//**
 * Load the given URL using Cobra. When the page is loaded, recurse on the
 * DOM and call doElement()/doTagEnd() for each Element node. Return false on
 * error.
 */
/*
 * 
 * public boolean parsePage() { // From Lobo forum. Disable all logging.
 * 
 * Logger.getLogger("").setLevel(Level.OFF);
 * 
 * // Parse the URL and build baseURL and hostURL for use by doElement() // and
 * doTagEnd() ;
 * 
 * URI uri = null ; URL urlObj = null ; try { uri = new URI(url) ; urlObj = new
 * URL(url) ; } catch(Exception e) { System.out.println(e) ; return false ; }
 * 
 * String path = uri.getPath() ;
 * 
 * host = uri.getHost() ; String port = "" ; if(uri.getPort() != -1) port =
 * Integer.toString(uri.getPort()) ; if(!port.equals("")) port = ":" + port ;
 * 
 * baseURL = "http://" + uri.getHost() + port + path ; hostBase = "http://" +
 * uri.getHost() + port ;
 * 
 * // Open a connection to the HTML page and use Cobra to parse it. // Cobra
 * does not return until page is loaded.
 * 
 * try { URLConnection connection = urlObj.openConnection(); InputStream in =
 * connection.getInputStream();
 * 
 * UserAgentContext context = new SimpleUserAgentContext(); DocumentBuilderImpl
 * dbi = new DocumentBuilderImpl(context); Document document = dbi.parse(new
 * InputSourceImpl(in, url, "ISO-8859-1")) ;
 * 
 * // Do a recursive traversal on the top-level DOM node.
 * 
 * Element ex = document.getDocumentElement() ; doTree((Node) ex) ; }
 * catch(Exception e) { System.out.println("parsePage(" + url + "):  " + e) ;
 * return false ; }
 * 
 * return true ; }
 *//**
 * Recurse the DOM starting with Node node. For each Node of type Element,
 * call doElement() with it and recurse over its children. The Elements refer to
 * the HTML tags, and the children are tags contained inside the parent tag.
 */
/*
 * 
 * public void doTree(Node node) { if(node instanceof Element) { Element element
 * = (Element) node ;
 * 
 * // Visit tag.
 * 
 * doElement(element) ;
 * 
 * // Visit all the children, i.e., tags contained in this tag.
 * 
 * NodeList nl = element.getChildNodes() ; if(nl == null) return ; int num =
 * nl.getLength() ; for(int i=0; i<num; i++) doTree(nl.item(i)) ;
 * 
 * // Process the end of this tag.
 * 
 * doTagEnd(element) ; } }
 *//**
 * Simple doElement to print the tag name of the Element. Override to do
 * something real.
 */
/*
 * 
 * public void doElement(Element element) { System.out.println("<" +
 * element.getTagName() + ">") ; }
 *//**
 * Simple doTagEnd() to print the closing tag of the Element. Override to do
 * something real.
 */
/*
 * 
 * public void doTagEnd(Element element) { System.out.println("</" +
 * element.getTagName() + ">") ; }
 *//**
 * Main: java com.benjysbrain.cobra.Render [url]. Open Render on www.cnn.com
 * by default. Parse the page and print the beginning and end tags.
 */
/*
 * 
 * public static void main(String[] args) { String url ="http://www.cnn.com/" ;
 * if(args.length == 1) url = args[0] ; Render p = new Render(url) ;
 * p.parsePage() ; System.exit(0) ; } }
 */