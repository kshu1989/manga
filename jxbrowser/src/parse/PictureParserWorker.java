package parse;

import java.util.Vector;

import org.apache.log4j.Logger;

import model.Episode;
import model.Season;

public class PictureParserWorker extends Thread {

	static Logger log = Logger.getLogger(PictureParserWorker.class.getName());

	public static void main(String[] args) {
		Vector s = new Vector();
		for (int i = 0; i < 100; i++) {
			Episode e = new Episode();
			e.setName("" + i);
			s.add(e);
		}

		Season se = new Season();
		se.setEpisodes(s);

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new PictureParserWorker(se));
			t.start();
		}

		int sum = 0;
		for (int i = 0; i < 100; i++) {
			// if (((Episode) s.get(i)).getIsDownLoad()) {
			sum++;
		}
	}

	private Season session;

	public PictureParserWorker(Season session) {
		this.session = session;
	}

	public void run() {
		int num[] = new int[session.getEpisodes().size()];
		for (int i = 0; i < num.length; i++)
			num[i] = 0;

		while (true) {
			int sum = 0;
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (episode.isParsed()) {
					sum += 1;
				} else {
					break;
				}
			}
			if (sum == session.getEpisodes().size()) {
				break;
			}
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (!episode.isParsingAndSetTure() && !episode.isParsed()) {
					// new PictureParserImpl().parseOneEpisode(episode
					// .getPicture());
					episode.setParsed(true);
					episode.setParsing(false);
					session.semp.release();
					log.fatal("release " + episode.getName() + "done");
				}
			}
		}
	}
}
