package com.zxs.jin.init;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class JinContext {
	private FullHttpRequest request;
	private JSON requestParams;
	private String requestStr;
	private HttpHeaders headers;
	private int state = -1;
	private String result = "";
	private String resultType = "";
	private Map<String, String> httpParams;
	private boolean jsonDown = false;

	public JinContext(FullHttpRequest request) {
		this.request = request;
		headers = request.headers();
		ByteBuf buf = request.content();
		requestStr = buf.toString(CharsetUtil.UTF_8);
		ReferenceCountUtil.release(buf);
		
		new Thread(new requestIntoJson()).start();
		
		httpParams = new HashMap<String, String>();
	}
	
	private class requestIntoJson implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				requestParams = (JSON) JSON.parse(requestStr);
			} catch (Exception e) {
				// TODO: handle exception
			}
			jsonDown = true;
		}
		
	}
	
	public void addUrlParams(String urlParam) {
		urlParam = urlParam.substring(urlParam.indexOf("?")+1, urlParam.length());
		String[] params = urlParam.split("&");
		for (String str : params) {
			httpParams.put(str.split("=")[0], str.split("=")[1]);
		}
	}
	
	public void addStarParams(Map<String, String> map) {
		httpParams.putAll(map);
	}
	
	public void json(int state,Object resultObj) {
		this.state = state;
		if (resultObj instanceof String) {
			this.result = resultObj+"";
		}else {
			this.result = JSON.toJSONString(resultObj);
		}
		resultType = "application/json;charset=UTF-8";
	}
	
	public void text(int state,String result) {
		this.state = state;
		this.result = result;
		resultType = "text/html;charset=UTF-8";
	}

	public int getState() {
		return state;
	}

	public String getResult() {
		return result;
	}
	
	public String getResultType() {
		return resultType;
	}
	
	public <T> T bind(T obj){
		try {
			while (!jsonDown) {
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null == requestParams) {
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
