package test;


import java.lang.management.ThreadInfo;
import java.util.Vector;

import model.Episode;
import model.Session;

public class ParseWorker implements Runnable {

	public static void main(String[] args) {
		Vector s = new Vector();
		for (int i = 0; i < 100; i++) {
			Episode e = new Episode();
			e.setName("" + i);
			s.add(e);
		}

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new ParseWorker(s));
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

	private Vector session;

	public ParseWorker(Vector session) {
		this.session = session;
	}

	public void run() {
		boolean flag = true;
		while (flag) {
			for (Episode episode : (Vector<Episode>) session) {
				if (!episode.isDownloadAndSetTrue()) {
					try {
						Thread.sleep(10);
						if ((Math.random() - 0.6) > 0.0) {
							episode.setDownload(false);
							System.out
									.println(Thread.currentThread().getName()
											+ ": " + episode.getName() + ": "
											+ "false");
						} else {
							System.out.println(Thread.currentThread().getName()
									+ ": " + episode.getName() + ": " + "true");
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			int doneNum = 0;
			for (Episode episode : (Vector<Episode>) session) {
				doneNum += episode.getIsDownLoad() ? 1 : 0;
				if (doneNum == session.size()) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
	}
}
