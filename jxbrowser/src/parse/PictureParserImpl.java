package parse;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Picture;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.WebClient;
import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserServices;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.NewWindowContainer;
import com.teamdev.jxbrowser.NewWindowManager;
import com.teamdev.jxbrowser.NewWindowParams;
import com.teamdev.jxbrowser.prompt.SilentPromptService;

public class PictureParserImpl implements PictureParser {

	static Logger log = Logger.getLogger(PictureParserImpl.class.getName());
	private Document doc;
	private Browser browser;

	public PictureParserImpl() {
		browser = BrowserFactory.createBrowser(BrowserFactory
				.getDefaultBrowserType());
		browser.getServices().setNewWindowManager(new NewWindowManager() {
			public NewWindowContainer evaluateWindow(NewWindowParams params) {
				return null;
			}
		});
		browser.getServices().setPromptService(new SilentPromptService());
	}

	@Override
	public void parseOneEpisode(Picture picture) {

		String nextPageUrl = "";
		String pictureUrl = "";
		try {
			String url = picture.getPageUrl().replace(" ",
					URLEncoder.encode(picture.getPageUrl(), "UTF-8"));
			URI uri = new URI(url);
			// Browser browser = BrowserFactory.createBrowser(BrowserType.IE);

			browser.navigate(uri.toASCIIString());
			browser.waitReady();
			this.doc = Jsoup.parse(browser.getContent());
			this.doc.setBaseUri(uri.toASCIIString());

			nextPageUrl = parseNextPageUrl();
			pictureUrl = parsePictureUrl();
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("Methord: parseOneEpisode" + " Exception: "
					+ e.getMessage() + " Manga Name: " + " Manga Index"
					+ picture.getIndex() + " Page Url: " + picture.getPageUrl()
					+ " Picture Url: " + picture.getPictureUrl());
		}
		System.out.println("next " + nextPageUrl);
		System.out.println("pictur " + pictureUrl);
		picture.setPictureUrl(pictureUrl);
		if (nextPageUrl == null || nextPageUrl.equals("")) {
			return;
		}
		Picture nextPicture = new Picture();
		nextPicture.setPageUrl(nextPageUrl);
		picture.setNextPic(nextPicture);

		parseOneEpisode(nextPicture);
	}

	private String parsePictureUrl() throws Exception {
		Element img = doc.getElementById("comicpic");
		if (null != img) {
			return img.absUrl("src");
		}
		Elements imgs = doc.select("img[src$=.jpg]");

		for (Element element : imgs) {
			return (element.absUrl("src"));
		}
		throw new Exception(
				"Method: parsePictureUrl Message: parse Picture url failed!");
	}

	private String parseNextPageUrl() throws Exception {
		Element nextLink = doc.select("a:has(img[src$=/images/d.gif])").first();
		if (null == nextLink) {
			throw new Exception("next page url failed!");
		}

		String nextPageUrl = nextLink.absUrl("href");
		if (nextPageUrl.contains("exit")) {
			return "";
		}
		return nextPageUrl;
	}

	public static void main(String[] args) {
		Picture p = new Picture();
		p.setPageUrl("file:///C:/Users/kshu.ADOBENET/workspace/jxbrowser/test%20html/index.html");
		PictureParserImpl pp = new PictureParserImpl();
		pp.parseOneEpisode(p);
		System.out.println(p.getPictureUrl());
		System.out.print(p.getNextPic().getPageUrl());
		System.exit(0);
		// p.parsePictureUrl();
	}
}
