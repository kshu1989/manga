package pares;

import model.Session;

public class ParsePageUrlWorker{

	private Session session;

	public ParsePageUrlWorker(Session session) {
		this.session = session;
	}

	public void run() {
		ParesUrl parser = new ParesUrl();
		parser.paresMangaPage(this.session);
	}
}
