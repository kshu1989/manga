package download;

import java.io.File;
import java.util.Vector;

import org.apache.log4j.Logger;
import model.Episode;
import model.Season;

public class WritePictureWorker extends Thread {
	static Logger log = Logger.getLogger(WritePictureWorker.class.getName());

	static public void main(String[] args) {
		System.out.println(File.separator);
		System.out.println(File.pathSeparatorChar);
	}

	private Season session;

	public WritePictureWorker(Season s) {
		this.session = s;
	}

	@Override
	public void run() {
		while (true) {
			try {
				session.semp.acquire();
			} catch (InterruptedException e) {
				log.error("Method: WritePictureWorker.run" + " Message: " + e.getMessage()
						+" Season: " + this.session.getMangaName());
			}
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.isParsed() && !episode.isDownloaded() && !episode.isDownlondingAndSetTrue()) {
					String dir = session.getSaveDirectoryPath() + File.separator + session.getMangaName()
							+ File.separator + episode.getName()
							+ File.separator;
					WritePicture wp = new WritePicture(dir);
					wp.wirtePicture(episode.getPicture());
					episode.setDownloaded(true);
					episode.setDownloading(false);
				}
			}
		}
	}
}
