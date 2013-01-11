package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/*public static void main(String[] args) throws Exception {
 String imageUrl = "http://cc.kukudm.com/kuku8comic8/201212/20121205/bleach519rqf-gy-1.0/KUKUDM.COM_JOJO_00103F.jpg";
 String destinationFile = "image.jpg";

 saveImage(imageUrl, destinationFile);
 }

 public static void saveImage(String imageUrl, String destinationFile)
 throws IOException {
 URL url = new URL(imageUrl);
 InputStream is = url.openStream();
 OutputStream os = new FileOutputStream(destinationFile);

 byte[] b = new byte[2048];
 int length;

 while ((length = is.read(b)) != -1) {
 os.write(b, 0, length);
 }

 is.close();
 os.close();
 }

 }*/

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveImageFromUrl {
	public static void main(String[] args) {
		BufferedImage image = null;
		try {

			URL url = new URL(
					"http://cc.kukudm.com/kuku8comic8/201212/20121205/bleach519rqf-gy-1.0/KUKUDM.COM_JOJO_00103F.jpg");
			// read the url
			image = ImageIO.read(url);

			// for png
			// ImageIO.write(image, "png",new File("/tmp/have_a_question.png"));

			// for jpg
			ImageIO.write(image, "jpg", new File("iofn.jpg"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}