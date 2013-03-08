package main;

import java.util.Vector;

import model.Episode;
import model.Session;
import pares.ParesPic;
import download.WritePic;

public class ParseWorker implements Runnable {

	private Session session;

	public ParseWorker(Session session) {
		this.session = session;
	}

	public void run() {
		while (!session.isDone())
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (!episode.isDownload())
					if (new ParesPic().parseOneEpisode(episode.getPicture(), 1))
						episode.setDownload(true);
			}
	}
}
