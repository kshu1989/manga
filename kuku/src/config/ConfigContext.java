package config;

import java.io.File;

public class ConfigContext {

	public static String ComicPicName; // 对应网页中<img>属性的name和id
	public static String comicPicId;

	public static String mangaUrl = "http://comic.kukudm.com/comiclist/6/index.htm";
	public static final int NTHREDS = 10;
	public static String mangaName = "死神";
	public static String charset = "GBK";
	public static String saveDestination = "G:\\myfiles\\pictures\\";


	public ConfigContext(String maganName) {
		this.mangaName = maganName;
		this.saveDestination = "G:\\myfiles\\pictures\\" + mangaName + "\\";
		File f = new File(this.saveDestination);
		f.mkdirs();
	}
}
