package com.zxs.jin.https;

import java.util.concurrent.ConcurrentHashMap;

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
	protected JinMethod getMethod(String rest,String url) {
		String group = "";
		String method = ""+rest;
		JinMethod todo = null;
		for (String key : groups.keySet()) {
			if (url.startsWith(key)) {
				group = key;
				String tmp = url.replaceFirst(key, "");
				method += "+"+tmp;
				todo = groups.get(group).getMethod(method);
				if (null != todo) {
					return todo;
				}
			}
		}
		return todo;
	}
}
