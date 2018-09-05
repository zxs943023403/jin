package com.zxs.jin.https;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zxs.jin.init.JinContext;
import com.zxs.jin.init.JinMethod;

public class HttpsEngine {
	private static ConcurrentHashMap<String, Https> groups;
	private static HttpsEngine engine = new HttpsEngine();
	private HttpsEngine() {
		groups = new ConcurrentHashMap<String, Https>();
	}
	public static HttpsEngine NEW() {
		return engine;
	}
	public Https group(String group) {
		Https https = new Https();
		groups.put(group, https);
		return https;
	}
	public void Use() {
		
	}
	protected JinMethod getMethod(String rest,String url,JinContext c) {
		String group = "";
		String method = ""+rest;
		JinMethod todo = null;
		for (String key : groups.keySet()) {
			if (url.startsWith(key)) {
				group = key;
				String tmp = url.replaceFirst(key, "");
				method += ""+tmp;
				todo = groups.get(group).getMethod(method,c);
				if (null != todo) {
					return todo;
				}
			}
		}
		return todo;
	}
	
	public static void main(String[] args) {
		String url = "aadadccnhgdd";
		String act = "aa*cc*dd";
		String psHead = "(?<=";
		String psTail = "(?=";
		String[] acts = act.split("[*]");
		for (int i = 0; i < acts.length - 1; i++) {
			String ps = psHead + acts[i] + ")"+"(.+)" + psTail + acts[i+1] + ")";
			Pattern p = Pattern.compile(ps);
			Matcher m = p.matcher(url);
			while (m.find()) {
				System.out.println(m.group());
			}
		}
//		System.out.println(url.replace(ps, ""));
	}
	
}
