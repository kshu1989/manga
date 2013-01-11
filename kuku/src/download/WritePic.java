package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import model.Episode;
import config.ConfigContext;

public class WritePic {

	private Episode episode;

	public static void main(String[] args) throws Exception {
		System.out.println(new WritePic()
				.getName("http://comic.kukudm.com/comiclist/6/25506/1.htm"));
		ConfigContext c = new ConfigContext("死神");
		String imageUrl = "http://cc.kukudm.com/kuku8comic8/201212/20121212/[死神][第520話]/Kukudm.com_JOJO_0050I9.jpg";
		String destinationFile = "G:\\myfiles\\pictures\\死神\\a.jpg";

		new WritePic().saveImage(imageUrl, destinationFile);
	}

	public void writePic(Episode episode) {
		String mangaDirectory = ConfigContext.saveDestination
				+ episode.getName() + "\\";
		File f = new File(mangaDirectory);
		f.mkdirs();

		Map<String, String> urls = episode.getUrlsForPics();

		for (Map.Entry<String, String> entry : urls.entrySet()) {
			saveImage(entry.getValue(), mangaDirectory
					+ getName(entry.getKey()) + ".jpg");
		}
	}

	private String getName(String pageUrl) {
		int start = pageUrl.lastIndexOf("/");
		int end = pageUrl.lastIndexOf(".");
		return pageUrl.substring(start + 1, end);
	}

	private void saveImage(String imageUrl, String destinationFile) {

		// BufferedImage image = null;
		// try {
		// URL url = new URL(imageUrl);
		// image = ImageIO.read(url);
		//
		// // for png
		// // ImageIO.write(image, "png",new File("/tmp/have_a_question.png"));
		//
		// // for jpg
		// ImageIO.write(image, "jpg", new File(destinationFile));
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// System.out.println(imageUrl + " ---> " + destinationFile);

		try {
			URL url = new URL(imageUrl);
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
					+ "Windows NT 5.1; en-US; rv:1.8.0.11) ");

			con.setDoInput(true);

			InputStream is = con.getInputStream();

			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			os.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
