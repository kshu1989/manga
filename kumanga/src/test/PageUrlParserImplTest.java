/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import model.Episode;
import model.Picture;
import model.Season;

import org.junit.BeforeClass;
import org.junit.Test;

import parse.PageUrlParser;
import parse.PageUrlParserImpl;

/**
 * @author kshu
 * 
 */
public class PageUrlParserImplTest {

	private static Season session = new Season();
	
	@BeforeClass
	public static void setUp() {
		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					new FileInputStream("./src/config.properties"), "utf8"));
			Properties prop = new Properties();
			prop.load(is);
			mangaName = prop.getProperty("mangaName");
			mangaUrl = prop.getProperty("mangaUrl");
			charset = prop.getProperty("charset");
			saveDestination = prop.getProperty("saveDestination");

			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		session.setDone(false);
		session.setMangaName(mangaName);
		session.setMangaUrl(mangaUrl);
		session.setSaveDirectoryPath(saveDestination);
		session.setRegex(mangaName + "[\\[_]");
	}

	@Test
	public void testParseSessionPageUrl() {
		PageUrlParser pup = new PageUrlParserImpl();
		try {
			pup.parseSessionPageUrl(session);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(session.getEpisodes().size());
		for (Episode e : (Vector<Episode>) session.getEpisodes()) {
			
			Picture pi = e.getPicture();
			while (pi != null) {
				System.out.println(e.getName() + "--->" + pi.getPageUrl());
				pi = pi.getNextPic();
			}
		}
	}

}
