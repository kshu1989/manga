package test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLCollection;

public class ParseImagesTest {
	private static final String TEST_URI = "http://comic.kukudm.com/comiclist/4/1426/1.htm";

	public static void main(String[] args) throws Exception {
		UserAgentContext uacontext = new SimpleUserAgentContext();
		DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);
		URL url = new URL(TEST_URI);
		InputStream in = url.openConnection().getInputStream();
		try {
			Reader reader = new InputStreamReader(in, "GBK");
			InputSourceImpl inputSource = new InputSourceImpl(reader, TEST_URI);
			Document d = builder.parse(inputSource);
			HTMLDocumentImpl document = (HTMLDocumentImpl) d;
			HTMLCollection images = document.getImages();
			int length = images.getLength();
			for (int i = 0; i < length; i++) {
				System.out.println("- Image#" + i + ": " + images.item(i));
			}
		} finally {
			in.close();
		}
	}
}
