package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

import model.Picture;

public class WritePicture {

	private String pictureSavePath;

	public static void main(String[] args) {
		// System.out.println(new WritePicture(new Picture(),
		// "D:\\movies\\������\\��һ��\\").makeDirctory());

	}

	public WritePicture(String savePath) {
		this.pictureSavePath = this.makeDirctory(savePath);
	}

	private String makeDirctory(String path) {
		File f = new File(path);
		if (f.exists()) {
			if (!f.isDirectory()) {
				f.delete();
				f.mkdirs();
			}
		} else {
			f.mkdirs();
		}
		return f.getAbsolutePath();
	}

	public boolean wirtePicture(Picture pic) {
		try {
			URL url = new URL(pic.getPictureUrl());
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
					+ "Windows NT 5.1; en-US; rv:1.8.0.11) ");
			con.setDoInput(true);
			InputStream is = con.getInputStream();
			OutputStream os = new FileOutputStream(this.pictureSavePath
					+ pic.getIndex() + ".jpg");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return wirtePicture(pic.getNextPic());
	}
}
