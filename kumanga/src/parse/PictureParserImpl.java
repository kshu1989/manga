package parse;

import java.util.List;

import model.Picture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PictureParserImpl implements PictureParser {

	private WebDriver driver;

	public PictureParserImpl() {
		driver = new HtmlUnitDriver();
	}

	@Override
	public void parseOneEpisode(Picture picture) {

		driver.get(session.getMangaUrl());

		// WebElement myDynamicElement = (new WebDriverWait(driver, 10))
		// .until(ExpectedConditions.presenceOfElementLocated(By
		// .tagName("body")));

	}

	private String parsePictureUrl() {

		driver.get("http://www.socomic.com/comiclist/3/3/97.htm");
		WebElement element = this.driver.findElement(By
				.xpath("//*[@id='comicpic']"));
		if (null != element) {
			return element.getAttribute("src");
		}
		List<WebElement> elements = this.driver.findElements(By
				.xpath("//img[count(@)=3]"));
		for (WebElement node : elements) {
			System.out.println(node.getTagName());
		}
		return "";
	}

	// "/html/body/table[2]/tbody/tr/td/a"
	private String parseNextPageUrl() throws Exception {
		// driver.get("http://www.socomic.com/comiclist/3/3/1.htm");
		WebElement element = this.driver.findElement(By
				.xpath("//img[@src='/images/d.gif']/.."));
		if (null == element) {
			throw new Exception("parse next url failed");
		}
		return element.getAttribute("href");
	}

	public static void main(String[] args) {
		PictureParserImpl p = new PictureParserImpl();
		p.parsePictureUrl();

	}
}
