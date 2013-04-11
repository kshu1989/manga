package test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import model.Picture;
import model.Season;

import org.apache.commons.lang3.CharSet;
import org.junit.BeforeClass;
import org.junit.Test;

import download.WritePicture;

public class WritePictureTest {

	private static Season session = new Season();

	private static Picture picture;
	private static WritePicture wp;

	@BeforeClass
	public static void setUp() {
		//right http://tt.kukudm.com:81/kuku6comic6/200910/20091011/467/Comic.kukudm.com.rar%20JOJO_001-00203A.jpg
		String url = "http://tt.kukudm.com:81/kuku6comic6/200910/20091011/467/Comic.kukudm.com.rar JOJO_001-00203A.jpg";
		picture = new Picture();
		picture.setIndex(1);
		picture.setNextPic(new Picture());
		picture.setPictureUrl(url);
		wp = new WritePicture("");
	}

	@Test
	public void testWirtePicture() throws UnsupportedEncodingException {
		try {
			URL url = new URL(picture.getPictureUrl());
			String urlEncoder = URLEncoder.encode(" ", "US-ASCII");
			System.out.println(urlEncoder);
			String urlDecoder = URLDecoder.decode(urlEncoder, "ISO-8859-1");
//			URI uri = new URI(urlEncoder);
			System.out.println(urlDecoder);
		} 
//		catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// wp.wirtePicture(picture);
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}