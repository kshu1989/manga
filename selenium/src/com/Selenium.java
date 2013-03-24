package com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class Selenium {
	public static void main(String[] args) {
		// Logger.getLogger(Selenium.class);
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = new HtmlUnitDriver(true);
//		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_10);
		
		// HtmlUnitDriver driver = new HtmlUnitDriver();
		// driver.setJavascriptEnabled(true);

		// And now use this to visit Google
		driver.get("http://www.socomic.com/comiclist/3/1507/1.htm");
		// Alternatively the same thing can be done vlike this
		// driver.navigate().to("http://www.google.com");

		// Find the text input element by its name
		// WebElement element = driver.findElement(By.name("q"));

//		List<WebElement> elements = driver.findElements(By.tagName("img"));
		List<WebElement> elements = driver.findElements(By.xpath("//img[@id='comicpic']"));
		for (WebElement element : elements) {
//			System.out.println(element.getAttribute("href"));
			System.out.println(element.getTagName());
			System.out.println(element.getAttribute("src"));
			System.out.println(element.getAttribute("id"));
//			System.out.println(element.getLocation());
//			System.out.println(element.getSize());
		}
		// Enter something to search for
		// element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		// element.submit();

		// Check the title of the page
//		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		// (new WebDriverWait(driver, 10)).until(new
		// ExpectedCondition<Boolean>() {
		// public Boolean apply(WebDriver d) {
		// return d.getTitle().toLowerCase().startsWith("cheese!");
		// }
		// });

		// Should see: "cheese! - Google Search"
//		System.out.println("Page title is: " + driver.getTitle());

		// Close the browser
		driver.quit();
	}
}