package download;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class WriteUrlsToFile {
	private Map<String, Integer> picUrls;
	private String path;
	private static String name = "url.txt";

	public WriteUrlsToFile(Map<String, Integer> picUrls, String path) {
		this.picUrls = picUrls;
		this.path = path;
	}

	public void writePicUrlsToFile() throws IOException {
		PrintWriter p = new PrintWriter(new File(path + name));
		Iterator j = picUrls.entrySet().iterator();
		while (j.hasNext()) {
			Map.Entry<String, Integer> paris = (Map.Entry) j.next();

			p.println("    " + paris.getKey() + "    " + paris.getValue());
		}
		p.close();
	}
}
