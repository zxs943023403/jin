package com.zxs.jin.https;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zxs.jin.init.JinContext;
import com.zxs.jin.init.JinMethod;

public class Https {
	private Map<String, Map<String, JinMethod>> rests = new HashMap<String, Map<String,JinMethod>>();
	protected Https() {}
	public void post(String url,JinMethod method) {
		add("POST", url, method);
	}
	public void get(String url,JinMethod method) {
		add("GET", url, method);
	}
	public void put(String url,JinMethod method) {
		add("PUT", url, method);
	}
	public void delete(String url,JinMethod method) {
		add("DELETE", url, method);
	}
	
	private void add(String rest,String url,JinMethod method) {
		Map<String, JinMethod> methods;
		if (rests.containsKey(rest)) {
			methods = rests.get(rest);
			methods.put(url, method);
		}else {
			methods = new HashMap<String, JinMethod>();
			methods.put(url, method);
		}
		rests.put(rest, methods);
	}
	
	protected JinMethod getMethod(String url,JinContext c) {
		System.out.println(url);
		String[] urls = url.split("/");
		url = "";
//		for (int i = 1; i < urls.length; i++) {
//			url += "/"+urls[i];
//		}
		Map<String, JinMethod> methods = rests.get(urls[0]);
		Map<String, String> urlparams;
		findkey:for (String key : methods.keySet()) {
			if (!hasParam(key) && !hasStar(key) && key.equals(url)) {
				break;
			}
			int index = -1;
			String[] keys = key.split("/");
			if (keys.length != urls.length) {
				continue;
			}
			urlparams = new HashMap<String, String>();
			url = "";
			for (int i = 0; i < keys.length; i++) {
				if ("".equals(keys[i])) {
					continue;
				}
				if (hasParam(keys[i])) {
					String pk = keys[i].replace(":", "");
					urlparams.put(pk, urls[i]);
					url += "/"+keys[i];
				}else if (hasStar(keys[i])) {
					String todo = urls[i];
					String act = keys[i];
					String psHead = "(?<=";
					String psTail = "(?=";
					String[] acts = act.split("[*]");
					for (int j = 0; j < acts.length - 1; j++) {
						String ps = psHead + acts[j] + ")"+"(.+)" + psTail + acts[j+1] + ")";
						Pattern p = Pattern.compile(ps);
						Matcher m = p.matcher(todo);
						if (m.find()) {
							urlparams.put(acts[j]+acts[j+1], m.group());
						}else {
							continue findkey;
						}
					}
					url += "/"+keys[i];
				}else if(keys[i].equals(urls[i])) {
					url += "/"+keys[i];
				}else {
					continue findkey;
				}
			}
			c.addStarParams(urlparams);
			break findkey;
		}
		return rests.get(urls[0]).get(url);
	}
	
	private boolean hasParam(String key) {
		return key.indexOf(":")!=-1;
	}
	
	private boolean hasStar(String key) {
		return key.indexOf("*") != -1;
	}
	
}
