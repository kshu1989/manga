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

import org.apache.log4j.Logger;

import model.Picture;

public class WritePicture {
	static Logger log = Logger.getLogger(WritePicture.class.getName());
	private String pictureSavePath;

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

	public void wirtePicture(Picture pic) {
		try {
			URL url = new URL(pic.getPictureUrl());
			URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
					+ "Windows NT 5.1; en-US; rv:1.8.0.11) ");
			con.setDoInput(true);
			InputStream is = con.getInputStream();
			OutputStream os = new FileOutputStream(this.pictureSavePath
					+ File.separator + pic.getIndex() + ".jpg");
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			log.error("Page Url: " + pic.getPageUrl());
			log.error("Picture Url: " + pic.getPictureUrl());
			log.error("Index: " + pic.getIndex());
		}
		if (pic.getNextPic() != null) {
			pic.getNextPic().setIndex(pic.getIndex() + 1);
			wirtePicture(pic.getNextPic());
		}
	}
}
