package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestWriteFile {

	public static void main(String[] args) {

		try {
			File f = new File("D:\\movies\\海贼王\\第sdf集\\");
			
			f.createNewFile();
			
			OutputStream os = new FileOutputStream(f);
			String b = "asdfasdfasdf";
			os.write(b.getBytes());
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
