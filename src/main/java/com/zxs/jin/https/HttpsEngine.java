package com.zxs.jin.https;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.zxs.jin.init.Engine;
import com.zxs.jin.init.JinContext;
import com.zxs.jin.init.JinMethod;

public class HttpsEngine implements Engine {
	private static ConcurrentHashMap<String, Https> groups;
	private static HttpsEngine engine = new HttpsEngine();
	private static List<JinMethod> before;
	private HttpsEngine() {
		groups = new ConcurrentHashMap<String, Https>();
		before = new ArrayList<JinMethod>();
	}
	public static HttpsEngine NEW() {
		return engine;
	}
	public Https group(String group) {
		Https https = new Https();
		groups.put(group, https);
		return https;
	}
	public void Use(JinMethod method) {
		before.add(method);
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
	protected void before(JinContext c) {
		for (JinMethod m : before) {
			m.exec(c);
		}
	}
	public List<JinMethod> getAllMethods(){
		List<JinMethod> methods = new ArrayList<JinMethod>();
		methods.addAll(before);
		for (Https hs : groups.values()) {
			methods.addAll(hs.getMethods());
		}
		return methods;
	}
}
