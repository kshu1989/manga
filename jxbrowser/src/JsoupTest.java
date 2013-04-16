import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;

public class JsoupTest {
	public static void main(String[] args) {

		Browser browser = BrowserFactory.createBrowser(BrowserType.IE);
		browser.navigate("file:///C:/Users/kshu.ADOBENET/Desktop/test%20html/index.html");
		browser.waitReady();
//		System.out.println(browser.getContent());
		Document doc = Jsoup.parse(browser.getContent());
		doc.setBaseUri("file:///C:/Users/kshu.ADOBENET/Desktop/test%20html/index.html");
		Elements imgs = doc.select("img[src$=.jpg]");

		for (Element element : imgs) {
			System.out.println(element.absUrl("src"));
			System.out.println(element.attr("src"));
		}
		browser.stop();
	}
}
