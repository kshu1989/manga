package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import model.Picture;

import org.apache.log4j.Logger;

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
			// pic.setPictureUrl("http://a3.att.hudong.com/37/22/01300000358882123812227242241.jpg");
			String url = pic.getPictureUrl().replace(" ",
					URLEncoder.encode(pic.getPageUrl(), "UTF-8"));
			URI uri = new URI(url);
			URL encodeUrl = new URL(uri.toASCIIString());

			HttpURLConnection con = (HttpURLConnection) encodeUrl.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; rv:19.0) Gecko/20100101 Firefox/19.0");
			con.setRequestProperty("accept",
					"t	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setRequestProperty("Accept-Language",
					"zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Host", "tt.kukudm.com:81");
			// con.setDoInput(true);
			con.setConnectTimeout(10000);
			InputStream is = con.getInputStream();
			OutputStream os = new FileOutputStream(this.pictureSavePath
					+ File.separator + pic.getIndex() + ".jpg");
			byte[] b = new byte[1024 * 10];
			int length;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.fatal("Method: wirtePicture" + " Message: " + e.getMessage()
					+ " Page Url: " + pic.getPageUrl() + " Picture Url: "
					+ pic.getPictureUrl() + " Index: " + pic.getIndex());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pic.getNextPic() != null) {
			pic.getNextPic().setIndex(pic.getIndex() + 1);
			wirtePicture(pic.getNextPic());
		}
	}
}
