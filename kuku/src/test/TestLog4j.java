package test;

import org.apache.log4j.Logger;

public class TestLog4j {

	static Logger log = Logger.getLogger(TestLog4j.class.getName());

	public static void main(String[] args) {

		log.debug("Hello this is an debug message");
		log.info("Hello this is an info message");
	}
}
