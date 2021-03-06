package parse;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import model.Episode;
import model.Picture;
import model.Season;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageUrlParserImpl implements PageUrlParser {

	static Logger log = Logger.getLogger(PageUrlParserImpl.class.getName());

	@Override
	public void parseSessionPageUrl(Season session) {
		WebDriver driver = null;
		try {
			if (session == null || session.getMangaUrl() == null) {
				throw new Exception("session is null");
			}
			driver = new HtmlUnitDriver();
			driver.get(session.getMangaUrl());
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By
							.tagName("a")));

			List<WebElement> elements = driver.findElements(By.tagName("a"));

			Pattern p = Pattern.compile(session.getRegex());
			Pattern p1 = Pattern.compile("1.htm*$");

			for (WebElement element : elements) {
				if (p.matcher(element.getText()).find()
						&& p1.matcher(element.getAttribute("href")).find()) {
					Episode episode = new Episode();

					Picture picture = new Picture();
					picture.setIndex(1);
					picture.setPageUrl(element.getAttribute("href"));
					picture.setEpisode(episode);
					episode.setName(element.getText());
					episode.setPicture(picture);
					session.getEpisodes().add(episode);
				}
			}
			driver.quit();
		} catch (Exception e) {
			log.fatal("Methord: parseSessionPageUrl " + session.getMangaName()
					+ e.getMessage());
			driver.quit();
		}
	}
}
