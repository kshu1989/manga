package download;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;

import pares.ParesUrl;

import model.Episode;
import model.Session;

public class WritePictureWorker extends Thread {
	static Logger log = Logger.getLogger(ParesUrl.class.getName());

	static public void main(String[] args) {
		System.out.println(File.separator);
		System.out.println(File.pathSeparatorChar);
	}

	private Session session;

	public WritePictureWorker(Session s) {
		this.session = s;
	}

	@Override
	public void run() {
		while (true) {
			try {
				log.warn("acquire");
				session.semp.acquire();
				log.warn("get acquire");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.isParsed() && !episode.isDownlandAndSetTrue()) {
					String dir = session.getSaveDirectoryPath()
							+ File.separator + episode.getName()
							+ File.separator;
					WritePicture wp = new WritePicture(dir);
					log.warn("write " + episode.getPicture().getPageUrl());
					boolean re = wp.wirtePicture(episode.getPicture());
					if (re) {
						episode.setDownloaded(re);
					} else {
						episode.setDownloading(re);
						session.semp.release();
					}
				}
			}
		}
	}
}
