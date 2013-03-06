package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

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