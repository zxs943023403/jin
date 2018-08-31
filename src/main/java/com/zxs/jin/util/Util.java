package com.zxs.jin.util;

import java.util.regex.Pattern;

public class Util {
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches(); 
	}
}
