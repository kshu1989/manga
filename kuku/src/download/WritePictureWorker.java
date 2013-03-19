package download;

import java.io.File;
import java.util.Vector;

import model.Episode;
import model.Session;

public class WritePictureWorker extends Thread {

	private Session session;

	public WritePictureWorker(Session s) {
		this.session = s;
	}

	@Override
	public void run() {
		while (true) {
			try {
				session.semp.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.getIsDownLoad() && !episode.isDownloadedAndSetTrue()) {
					String dir = session.getSaveDirectoryPath()
							+ File.pathSeparator + episode.getName()
							+ File.pathSeparator;
					WritePicture wp = new WritePicture(dir);
					boolean re = wp.wirtePicture(episode.getPicture());
					if(!re){
						episode.setDownloaded(re);
						session.semp.release();
					}
				}
			}
		}
	}
}
