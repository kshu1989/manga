package parse;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import model.Episode;
import model.Picture;
import model.Session;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PageUrlParserImpl implements PageUrlParser {

	@Override
	public void parseSessionPageUrl(Session session) throws Exception {
		if (session == null || session.getMangaUrl() == null) {
			throw new Exception("session is null");
		}
		WebDriver driver = new HtmlUnitDriver();
		driver.get(session.getMangaUrl());

		// WebElement myDynamicElement = (new WebDriverWait(driver, 10))
		// .until(ExpectedConditions.presenceOfElementLocated(By
		// .tagName("body")));

		List<WebElement> elements = driver.findElements(By.tagName("a"));
		Pattern p = Pattern.compile(session.getRegex());
		Pattern p1 = Pattern.compile("1.htm*$");
		Vector Episodes = new Vector();
		session.setEpisodes(Episodes);

		for (WebElement element : elements) {
			if (p.matcher(element.getText()).find()
					&& p1.matcher(element.getAttribute("href")).find()) {
				Episode episode = new Episode();

				Picture picture = new Picture();
				picture.setIndex(1);
				picture.setPageUrl(element.getAttribute("href"));
				episode.setName(element.getText());
				episode.setPicture(picture);
				Episodes.add(episode);
			}
		}
		driver.quit();
	}
}
