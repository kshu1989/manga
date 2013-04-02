package parse;

import model.Season;

public class PageUrlParserWorker {

	private Season session;

	public PageUrlParserWorker(Season session) {
		this.session = session;
	}

	public void run() {
		PageUrlParser parser = new PageUrlParserImpl();
		parser.parseSessionPageUrl(this.session);
	}
}
