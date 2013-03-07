package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author kshu
 * 
 */
public class Episode {

	public static String CHARSET = "GBK";

	private String name;
	private String firPageUrl;
	private Vector content;
	
	public Episode(){
		content = new Vector();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirPageUrl() {
		return firPageUrl;
	}

	public void setFirPageUrl(String firPageUrl) {
		this.firPageUrl = firPageUrl;
	}

	public Vector getContent() {
		return content;
	}

	public void setContent(Vector content) {
		this.content = content;
	}
}