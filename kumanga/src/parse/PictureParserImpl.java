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
		String nextPageUrl = parseNextPageUrl();
		String pictureUrl = parsePictureUrl();
		System.out.println("next " + nextPageUrl);
		System.out.println("pictur " + pictureUrl);
		
//		driver.close();
		picture.setPictureUrl(pictureUrl);

		if (nextPageUrl.equals("")) {
			return;
		}

		Picture nextPicture = new Picture();
		nextPicture.setPageUrl(nextPageUrl);
		picture.setNextPic(nextPicture);

		parseOneEpisode(nextPicture);
	}

	private String parsePictureUrl() {
		WebElement element = null;
		try {
//			driver.get("http://mh.socomic.com/comiclist/3/1450/1.htm");
//			System.out.println("begin");
			element = driver.findElement(By.xpath("//img[@id='comicpic']"));
//			System.out.println("end");
			if (null != element) {
				// log.error("sk" + driver.getCurrentUrl() + "-->"
				// + element.getAttribute("src"));
				// System.out.println("sk" + driver.getCurrentUrl() + "-->"
				// + element.getAttribute("src"));
				return element.getAttribute("src");
			}
		} catch (NoSuchElementException no) {
//			System.out.println("exception");
			log.error(no.getMessage());
			try {
//				System.out.println("1start");
				List<WebElement> elements = this.driver.findElements(By
						.xpath("//img[count(@*) = 1]"));
//				System.out.println("1end");
				String picture = null;
				for (WebElement node : elements) {
					picture = node.getAttribute("src");
					if (picture.endsWith(".jpg")) {
						// log.error("ks" + driver.getCurrentUrl() + "-->"
						// + picture );
						// System.out.println("sk" + driver.getCurrentUrl()
						// + "-->" + picture);
						return picture;
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				log.error("parsePictureUrl: " + driver.getCurrentUrl() + "-->");
			}
		}
		return "";
	}

	private String parseNextPageUrl() {
		// driver.get("http://www.socomic.com/comiclist/3/3/1.htm");
		try {
			WebElement element = this.driver.findElement(By
					.xpath("//img[@src='/images/d.gif']/.."));
			if (null == element) {
				throw new Exception("parse next url failed");
			}
			return element.getAttribute("href");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return "";
	}

	public static void main(String[] args) {
		PictureParserImpl p = new PictureParserImpl();
		p.parsePictureUrl();
	}
}
