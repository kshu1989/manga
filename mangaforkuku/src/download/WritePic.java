package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class WritePic extends Thread {

	private Map<String, Integer> urls;
	private String mangaName;
	private String path;

	public static void main(String[] args) throws Exception {
		String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
		String destinationFile = "G:\\myfiles\\pictures\\海贼王[633]是敌是友\\a.jpg";

		// saveImage(imageUrl, destinationFile);
	}

	public WritePic(Map<String, Integer> urls, String mangaName, String path) {
		this.urls = urls;
		this.mangaName = mangaName;
		this.path = path;
	}

	public void saveImage(String imageUrl, String destinationFile)
			throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File(destinationFile));

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public void run() {
		String path = this.path + this.mangaName.trim() + "\\";
		new File(path).mkdir();
		try {
			WriteUrlsToFile wutf = new WriteUrlsToFile(this.urls, path);
			wutf.writePicUrlsToFile();
			Iterator i = urls.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry<String, Integer> paris = (Map.Entry) i.next();
				saveImage(paris.getKey(), path
						+ paris.getValue().toString().trim() + ".jpg");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
