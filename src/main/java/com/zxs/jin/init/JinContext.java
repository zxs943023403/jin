package com.zxs.jin.init;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;

public class JinContext {
	private FullHttpRequest request;
	private JSON requestParams;
	private String requestStr;
	private HttpHeaders headers;
	private int state = 0;
	private String result = "";
	private Map<String, String> httpParams;

	public JinContext(FullHttpRequest request) {
		this.request = request;
		headers = request.headers();
		ByteBuf buf = request.content();
		requestStr = buf.toString(CharsetUtil.UTF_8);
		if ("application/json".equals(headers.get("Content-Type"))) {
			requestParams = (JSON) JSON.parse(requestStr);
		}
		
		httpParams = new HashMap<String, String>();
	}
	
	public void addUrlParams(String urlParam) {
		urlParam = urlParam.substring(urlParam.indexOf("?")+1, urlParam.length());
		String[] params = urlParam.split("&");
		for (String str : params) {
			httpParams.put(str.split("=")[0], str.split("=")[1]);
		}
	}
	
	public void json(int state,String result) {
		this.state = state;
		this.result = result;
	}

	public int getState() {
		return state;
	}

	public String getResult() {
		return result;
	}
	
	public <T> T bind(T obj){
		if (!"application/json".equals(headers.get("Content-Type"))) {
			return obj;
		}
		obj = (T) requestParams.toJavaObject(obj.getClass());
		return obj;
	}
	
	public String getHeader(String key) {
		return headers.get(key);
	}
	
	public String query(String key) {
		return httpParams.get(key);
	}
	
}
