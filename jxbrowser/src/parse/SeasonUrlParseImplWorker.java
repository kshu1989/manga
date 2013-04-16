package parse;

import model.Volume;

public class SeasonUrlParseImplWorker {

	private Volume volume;

	public SeasonUrlParseImplWorker(Volume volume) {
		this.volume = volume;
	}

	public void run() {
		SeasonUrlParseImpl parser = new SeasonUrlParseImpl();
		parser.parseVolumePageUrl(volume);
	}
}
