package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("./src/config.property");
		Properties prop = new Properties();
		prop.load(is);

		for (Object key : prop.keySet()) {
			System.out.println(key + "=" + prop.get(key));
		}
	}
}