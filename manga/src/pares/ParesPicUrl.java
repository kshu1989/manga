package pares;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Render - This object is a wrapper for the Cobra Toolkit, which is part of the
 * Lobo Project (http://html.xamjwg.org/index.jsp). Cobra is a
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

public class ParesPicUrl {
	private String reUrl;
	private int picNum;

	public Vector<String> getEpisodePicsUrl(String url)
			throws InterruptedException {
		Vector<String> picsUrl = new Vector<String>();
		int num = 0;
		String urlNew;

		if (parsePage(url)) {
			picsUrl.add(this.reUrl);
			num = this.picNum;
		}

		for (int i = 2; i <= num; i++) {
			urlNew = this.appendUrl(url, i);
			System.out.println("parse : " + urlNew);
			if (parsePage(urlNew)) {
				System.out.println(this.reUrl);
				picsUrl.add(this.reUrl);
				Thread.sleep(3000);
			}
		}
		return picsUrl;
	}

	private String appendUrl(String url, int num) {
		return url + "?p=" + num;
	}

	private boolean parsePage(String url) {
		Logger.getLogger("").setLevel(Level.OFF);

		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		try {
			URLConnection connection = urlObj.openConnection();
			connection.setRequestProperty("Cookie", "imgHost=1");
			InputStream in = connection.getInputStream();

			UserAgentContext context = new SimpleUserAgentContext();
			DocumentBuilderImpl dbi = new DocumentBuilderImpl(context);
			Document document = dbi.parse(new InputSourceImpl(in, url,
					"ISO-8859-1"));

			Element ex = document.getDocumentElement();
			NodeList nl = ex.getElementsByTagName("img");

			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);
				if (element.hasAttribute("id")
						&& element.getAttribute("id").equals("mangaFile")) {
					this.reUrl = element.getAttribute("src");
				}
			}

			this.picNum = ex.getElementsByTagName("option").getLength();

		} catch (Exception e) {
			System.out.println("parsePage(" + url + "):  " + e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String url = "http://www.imanhua.com/comic/55/list_73651.html";
		if (args.length == 1)
			url = args[0];
		ParesPicUrl p = new ParesPicUrl();
		//
		// if(p.parsePage(url)){
		// System.out.println(p.reUrl);
		// }
		//
		List l;
		try {
			l = p.getEpisodePicsUrl(url);

			for (int i = 0; i < l.size(); i++) {
				System.out.println(l.get(i));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}
}

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