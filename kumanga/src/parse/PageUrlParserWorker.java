package parse;

import model.Session;

public class PageUrlParserWorker{

	private Session session;

	public PageUrlParserWorker(Session session) {
		this.session = session;
	}

	public void run() {
		ParesUrl parser = new ParesUrl();
		parser.paresMangaPage(this.session);
	}
}
