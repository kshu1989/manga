package main;

import model.Episode;
import pares.ParesPic;
import download.WritePic;

public class Worker implements Runnable {

	private Episode episode;

	public Worker(Episode episode) {
		this.episode = episode;
	}

	public void run() {
		new ParesPic().parseWorker(episode);
		new WritePic().writePic(episode);
	}
}
