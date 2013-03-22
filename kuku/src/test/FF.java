package test;

import java.util.regex.Pattern;

public class FF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pattern p1 = Pattern.compile("海贼王[\\[_]");
		String a = "sfsadfsadf/1.html海贼王[";
		
		System.out.println(p1.matcher(a).find());
	}

}
