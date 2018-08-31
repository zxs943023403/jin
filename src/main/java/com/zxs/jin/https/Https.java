package com.zxs.jin.https;

import java.util.HashMap;
import java.util.Map;

import com.zxs.jin.init.JinMethod;

public class Https {
	private Map<String, JinMethod> methods = new HashMap<String, JinMethod>();
	protected Https() {}
	public void post(String url,JinMethod method) {
		methods.put("POST+"+url, method);
	}
	public void get(String url,JinMethod method) {
		methods.put("GET+"+url, method);
	}
	public void put(String url,JinMethod method) {
		methods.put("PUT+"+url, method);
	}
	public void delete(String url,JinMethod method) {
		methods.put("DELETE+"+url, method);
	}
	protected JinMethod getMethod(String url) {
		return methods.get(url);
	}
}
