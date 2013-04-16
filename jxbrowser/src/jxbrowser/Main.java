package jxbrowser;

import org.w3c.dom.Document;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.dom.DOMDocument;

/**
 * The sample demonstrates how to select an option in SELECT tag element
 * programmatically.
 */
public class Main {
	public static void main(String[] args) {
		Browser browser = BrowserFactory.createBrowser(BrowserType.IE);
		// BrowserServices services = browser.getServices();
		// ScriptErrorWatcher scriptErrorWatcher = services
		// .getScriptErrorWatcher();
		// scriptErrorWatcher.addScriptErrorListener(new ScriptErrorListener() {
		// public void scriptErrorHappened(ScriptErrorEvent event) {
		// System.out.println("event = " + event.getMessage());
		// }
		// });
		browser.navigate("file:///C:/Users/kshu.ADOBENET/Desktop/test%20html/index.html");
		browser.waitReady();

		Document document = browser.getDocument();
		DOMDocument domDocument = (DOMDocument) document;
		
		// NodeList nodes = document.getElementsByTagName("img");
//		for(domDocument)
		System.out.println(domDocument.getImages());
//		System.out.println(browser.getContent());

		browser.stop();
		// for (Node n : nodes.ge) {
		// System.out.println(selectElement.getAttribute("src"));
		// }
		// HTMLCollection options = selectElement.getOptions();
		// HTMLOptionElement optionElement = (HTMLOptionElement)
		// options.item(1);
		// optionElement.setSelected(true);
	}
}