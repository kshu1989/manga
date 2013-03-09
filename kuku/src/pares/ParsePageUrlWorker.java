package pares;

import model.Session;

public class ParsePageUrlWorker {

	private Session session;

	public ParsePageUrlWorker(Session session) {
		this.session = session;
	}

	public boolean run() {
		ParesUrl parser = new ParesUrl();
		return parser.paresMangaPage(this.session);
	}
}
