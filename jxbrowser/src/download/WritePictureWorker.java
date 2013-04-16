package download;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
import model.Episode;
import model.Picture;
import model.Season;

public class WritePictureWorker extends Thread {
	static Logger log = Logger.getLogger(WritePictureWorker.class.getName());

	static public void main(String[] args) {
		// System.out.println(File.separator);
		// System.out.println(File.pathSeparatorChar);

		Picture p1 = new Picture();
		p1.setPictureUrl("http://tt.kukudm.com:81/newkuku/2013/201303/20130330/白雪公主與7個囚犯第01話/0409L.jpg");
		Picture p2 = new Picture();
		p2.setPictureUrl("http://tt.kukudm.com:81/newkuku/2013/201303/20130330/白雪公主與7個囚犯第01話/02-0306I.jpg");
		p2.setIndex(2);
		// Picture p3 = new Picture();
		// p3.setPictureUrl("http://tt.kukudm.com:81/newkuku/2013/201303/20130330/白雪公主與7個囚犯第01話/0409L.jpg");
		// p3.setIndex(3);

		p1.setNextPic(p2);
		// p2.setNextPic(p3);

		Episode e = new Episode();
		e.setName("白雪公主与7个囚犯 1话");
		e.setPicture(p1);
		e.setParsed(true);

		Vector v = new Vector();
		v.add(e);

		Season s = new Season();
		s.setEpisodes(v);
		s.setMangaName("白雪公主与7个囚犯");
		// s.setMangaUrl(mangaUrl);
		// s.setRegex(regex);
		s.setSaveDirectoryPath("D:\\movies\\japan");
		s.semp.release();

		WritePictureWorker w = new WritePictureWorker(s);
		w.run();
	}

	private Season session;

	public WritePictureWorker(Season s) {
		this.session = s;
	}

	@Override
	public void run() {
		try {
			log.fatal("acquire 1");
			session.semp.acquire();
			log.fatal("acquire 2");
		} catch (InterruptedException e) {
			log.fatal("Method: WritePictureWorker.run" + " Message: "
					+ e.getMessage() + " Season: "
					+ this.session.getMangaName());
		}
		while (true) {
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.isParsed() && !episode.isDownloaded()
						&& !episode.isDownlondingAndSetTrue()) {
					String dir = session.getSaveDirectoryPath()
							+ File.separator + session.getMangaName()
							+ File.separator + episode.getName()
							+ File.separator;
					// WritePicture wp = new WritePicture(dir);
					// wp.wirtePicture(episode.getPicture());
					episode.setDownloaded(true);
					episode.setDownloading(false);
				}
			}

			int num[] = new int[session.getEpisodes().size()];
			for (int i = 0; i < num.length; i++)
				num[i] = 0;

			int sum = 0;
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.isDownloaded()) {
					sum += 1;
				} else {
					break;
				}
			}
			if (sum == session.getEpisodes().size()) {
				break;
			}
		}
	}
}
