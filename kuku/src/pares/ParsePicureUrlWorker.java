package pares;

import java.lang.management.ThreadInfo;
import java.util.Vector;

import model.Episode;
import model.Session;

public class ParsePicureUrlWorker implements Runnable {

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
			if (((Episode) s.get(i)).getIsDownLoad()) {
				sum++;
			}
		}
		System.out.println(sum);
	}

	private Session session;

	public ParsePicureUrlWorker(Session session) {
		this.session = session;
	}

	public void run() {
		boolean flag = true;
		while (flag) {
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				if (!episode.isParseAndSetTrue()) {
					boolean re = new ParesPic().parseOneEpisode(
							episode.getPicture(), 1);
					if (!re) {
						episode.setDownload(false);
					}
				}
			}
			int doneNum = 0;
			for (Episode episode : (Vector<Episode>) session.getEpisodes()) {
				doneNum += episode.getIsParse() ? 1 : 0;
				if (doneNum == session.getEpisodes().size()) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
	}
}
