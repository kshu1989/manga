package parse;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.Episode;
import model.Picture;
import model.Season;
import model.Volume;

public class SeasonUrlParseImpl implements SeasonUrlParse {

	static Logger log = Logger.getLogger(PageUrlParserImpl.class.getName());

	private WebDriver driver;

	public SeasonUrlParseImpl() {
		this.driver = new HtmlUnitDriver();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Volume v = new Volume();
		v.setVolumeUrl("http://comic.kukudm.com/comictype/3_1.htm");

		SeasonUrlParseImpl season = new SeasonUrlParseImpl();
		season.parseVolumePageUrl(v);
		for (Season s : v.getSeasons()) {
			System.out.println(s.getMangaName());
			System.out.println(s.getMangaUrl());
		}
	}

	@Override
	public void parseVolumePageUrl(Volume volume) {
		try {
			if (volume == null || volume.getVolumeUrl() == null) {
				throw new Exception("volume is null");
			}
			parseVolume(volume.getVolumeUrl(), volume);
		} catch (Exception e) {
			log.fatal("Methord: parseVolumePageUrl " + "Exception: "
					+ e.getMessage() + " Volume Url: " + volume.getVolumeUrl());
			driver.quit();
		}
	}

	private void parseVolume(String url, Volume volume) throws Exception {
		driver.get(url);
		parsePage(volume);
		String nextPage = this.getNextPage();
		// System.out.println(nextPage);
		if (nextPage == null) {
			throw new Exception("Method: parseVolume Url: " + url);
		}
		if (nextPage.equals("")) {
			return;
		}
		parseVolume(nextPage, volume);
		driver.quit();
	}

	private String getNextPage() {
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(By
						.xpath("//a[@href]")));
		List<WebElement> elements = driver.findElements(By.xpath("//a[@href]"));
		if (elements == null) {
			return null;
		}
		for (WebElement element : elements) {
			if (element.getText().equals("下一页")) {
				return element.getAttribute("href");
			}
		}
		return "";
	}

	private void parsePage(Volume volume) {
		List<WebElement> elements = driver.findElements(By
				.xpath("//dl[@id='comicmain']/descendant::a"));
		for (WebElement element : elements) {
			if (!element.getText().equals("")) {
				Season season = new Season();
				season.setMangaName(element.getText());
				season.setMangaUrl(element.getAttribute("href"));
				season.setRegex(season.getMangaName() + "[\\[_ ]");
				volume.getSeasons().add(season);
			}
		}
	}
}
