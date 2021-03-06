package pares;

import java.lang.management.ThreadInfo;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import model.Episode;
import model.Session;

public class ParsePicureUrlWorker extends Thread {

	static Logger log = Logger.getLogger(ParesUrl.class.getName());

	public static void main(String[] args) {
		Vector s = new Vector();
		for (int i = 0; i < 100; i++) {
			Episode e = new Episode();
			e.setName("" + i);
			s.add(e);
		}

		Session se = new Session();
		se.setEpisodes(s);

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new ParsePicureUrlWorker(se));
			t.start();
		}

		int sum = 0;
		for (int i = 0; i < 100; i++) {
			// if (((Episode) s.get(i)).getIsDownLoad()) {
			sum++;
		}
	}

	// System.out.println(sum);
	// }

	private Session session;

	public ParsePicureUrlWorker(Session session) {
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
					boolean re = new ParesPic().parseOneEpisode(
							episode.getPicture(), 1);
					if (re) {
						episode.setParsed(re);
						log.warn("release");
						session.semp.release();
					} else {
						/*
						 * this thread is not work, should exit fast and let
						 * other thread wait on this lock to run
						 */
						episode.setParsing(re);
					}
				}
			}
		}
	}
}
