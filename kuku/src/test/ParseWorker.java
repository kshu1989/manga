package test;

import java.util.Vector;

import model.Episode;
import model.Session;
import pares.ParesPic;
import download.WritePic;

public class ParseWorker implements Runnable {

	public static void main(String[] args) {
		Vector s = new Vector();
		for (int i = 0; i < 1000; i++) {
			Episode e = new Episode();
			e.setName("" + i);
			s.add(e);
		}

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new ParseWorker(s));
			t.start();
		}
	}

	private Vector session;

	public ParseWorker(Vector session) {
		this.session = session;
	}

	public void run() {
		for (Episode episode : (Vector<Episode>) session) {
			if (!episode.isDownload()) {
				episode.setDownload(true);
				System.out.println(episode.getName() + "done");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
