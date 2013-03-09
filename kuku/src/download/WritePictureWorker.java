package download;

import java.io.File;
import java.util.Vector;

import pares.ParesPic;
import model.Episode;
import model.Session;

public class WritePictureWorker implements Runnable {

	private Session session;

	public WritePictureWorker(Session s) {
		this.session = s;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		while (flag) {
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (!episode.isDownloadAndSetTrue()) {
					String dir = session.getSaveDirectoryPath()
							+ File.pathSeparator + episode.getName()
							+ File.pathSeparator;
					WritePicture wp = new WritePicture(dir);
					boolean re = wp.wirtePicture(episode.getPicture());
					if (!re) {
						episode.setDownload(false);
					}
				}
			}
			int doneNum = 0;
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				doneNum += episode.getIsDownLoad() ? 1 : 0;
				if (doneNum == session.getEpisodes().size()) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
	}
}
