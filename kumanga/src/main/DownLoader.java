package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Episode;
import model.Picture;
import model.Season;
import model.Volume;

import org.apache.log4j.PropertyConfigurator;

import parse.PageUrlParserWorker;
import parse.PictureParserWorker;
import parse.SeasonUrlParseImplWorker;
import download.WritePictureWorker;

public class DownLoader {

	public static void main(String[] args) {

		String mangaName = null;
		String mangaUrl = null;
		String charset = null;
		String saveDestination = null;
		String regex = null;

		PropertyConfigurator.configure(".//src//log4j.properties");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					new FileInputStream("./src/config.properties"), "utf8"));
			Properties prop = new Properties();
			prop.load(is);
			mangaUrl = prop.getProperty("mangaUrl");
			charset = prop.getProperty("charset");
			saveDestination = prop.getProperty("saveDestination");

			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Volume volume = new Volume();
		volume.setVolumeUrl(mangaUrl);

		new SeasonUrlParseImplWorker(volume).run();
		
//		int j = 0;
//		List<Season> li = new ArrayList<Season>();
//		for (Season s : volume.getSeasons()) {
//			System.out.println(s.getMangaName());
//			System.out.println(s.getMangaUrl());
//			if(j < 2){
//				j ++;
//				li.add(s);
//			}
//		}
//
//		volume.setSeasons(li);
		
		for (Season season : volume.getSeasons()) {
			season.setSaveDirectoryPath(saveDestination);
			new PageUrlParserWorker(season).run();

//			Vector<Episode> v = new Vector<Episode>();
//			int tem = season.getEpisodes().size();
//			for (int i = tem; i > (tem - 3 > 0 ? tem -3 : 0); i--) {
//				Episode ep = (Episode) season.getEpisodes().get(i);
//				v.add(ep);
//				int k = 0;
//				Picture pi = ep.getPicture();
//				while (pi != null) {
//					pi = pi.getNextPic();
//					if (k++ > 4)
//						break;
//				}
//			}
//			season.setEpisodes(v);

			for (Episode e : (Vector<Episode>) season.getEpisodes()) {
				Picture pi = e.getPicture();
				while (pi != null) {
					System.out.println(e.getName() + "--->" + pi.getPageUrl());
					pi = pi.getNextPic();
				}
			}

//			ExecutorService executor = Executors.newFixedThreadPool(1);
//			for (int i = 0; i < 1; i++) {
				PictureParserWorker ppuworker = new PictureParserWorker(season);
				ppuworker.run();
//				executor.execute(ppuworker);
//			}

//			for (int i = 0; i < 2; i++) {
				WritePictureWorker wpworker = new WritePictureWorker(season);
				wpworker.run();
//				executor.execute(wpworker);
//			}
//			executor.shutdown();
//			while (!executor.isTerminated()) {
//			}
			System.out.println("Finished all threads");
		}
	}
}
