package htmlUnit;

import java.io.FileOutputStream;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test1 {
	//
	// @Test
	// public void homePage() throws Exception {
	// final WebClient webClient = new WebClient();
	// final HtmlPage page = webClient
	// .getPage("file:///C:/Users/kshu.ADOBENET/Desktop/test%20html/index.html");
	// Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit",
	// page.getTitleText());
	//
	// final String pageAsXml = page.asXml();
	// Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
	//
	// final String pageAsText = page.asText();
	// Assert.assertTrue(pageAsText
	// .contains("Support for the HTTP and HTTPS protocols"));
	//
	// webClient.closeAllWindows();
	// }

	@Test
	public void homePage_Firefox() throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		HtmlPage refDesing = webClient
				.getPage("file:///C:/Users/kshu.ADOBENET/Desktop/test%20html/index.html");
		
		DomElement e = refDesing.getElementById("a2");
		if(e instanceof HtmlImage){
			System.out.println(e.getAttribute("src"));
		}
		FileOutputStream fos1 = new FileOutputStream(
				"a.txt");
		fos1.write(refDesing.asXml().getBytes());
		fos1.close();
		webClient.closeAllWindows();
	}
}
