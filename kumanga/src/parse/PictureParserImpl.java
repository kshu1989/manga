package parse;

import java.util.List;

import model.Picture;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PictureParserImpl implements PictureParser {

	static Logger log = Logger.getLogger(PictureParserImpl.class.getName());
	private WebDriver driver;

	public PictureParserImpl() {
		driver = new HtmlUnitDriver(true);
	}

	@Override
	public void parseOneEpisode(Picture picture) {

		driver.get(picture.getPageUrl());
		String nextPageUrl = "";
		String pictureUrl = "";
		try {
			nextPageUrl = parseNextPageUrl();
			pictureUrl = parsePictureUrl();
		} catch (Exception e) {
			log.error("Methord: parseOneEpisode" + " Manga Name: "
					+ picture.getEpisode().getName() + " Manga Index"
					+ picture.getIndex() + " Page Url: " + picture.getPageUrl()
					+ " Picture Url: " + picture.getPictureUrl());
		}
		// System.out.println("next " + nextPageUrl);
		// System.out.println("pictur " + pictureUrl);

		// driver.close();
		picture.setPictureUrl(pictureUrl);

		if (nextPageUrl == null || nextPageUrl.equals("")) {
			return;
		}

		Picture nextPicture = new Picture();
		nextPicture.setPageUrl(nextPageUrl);
		picture.setNextPic(nextPicture);

		parseOneEpisode(nextPicture);
		driver.close();
		driver.quit();
	}

	private String parsePictureUrl() throws Exception {
		WebElement element = null;
		try {
			// driver.get("http://mh.socomic.com/comiclist/3/1450/1.htm");
			element = driver.findElement(By.xpath("//img[@id='comicpic']"));
			if (null != element) {
				return element.getAttribute("src");
			}
		} catch (NoSuchElementException no) {
			List<WebElement> elements = this.driver.findElements(By
					.xpath("//img[count(@*) = 1]"));
			String picture = null;
			for (WebElement node : elements) {
				picture = node.getAttribute("src");
				if (picture.endsWith(".jpg")) {
					return picture;
				}
			}
			throw new Exception(
					"Method: parsePictureUrl Message: parse Picture url failed!");
		}
		return "";
	}

	private String parseNextPageUrl() throws Exception {
		// driver.get("http://www.socomic.com/comiclist/3/3/1.htm");
		WebElement element = this.driver.findElement(By
				.xpath("//img[@src='/images/d.gif']/.."));
		if (null == element) {
			throw new Exception("next page url failed!");
		}
		String nextPageUrl = element.getAttribute("href");
		if (nextPageUrl.contains("exit")) {
			return "";
		}
		return nextPageUrl;
		// throw new Exception(
		// "Method: parseNextPageUrl Method: parse next url failed");
	}

	public static void main(String[] args) {
		Picture p = new Picture();
		p.setPageUrl("http://comic.kukudm.com/comiclist/1757/32713/2.htm");
		PictureParserImpl pp = new PictureParserImpl();
		pp.parseOneEpisode(p);
		// p.parsePictureUrl();
	}
}
