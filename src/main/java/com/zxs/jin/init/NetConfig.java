package com.zxs.jin.init;

import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import io.netty.util.internal.StringUtil;

public class NetConfig {
	private static NetConfig config = new NetConfig();
	private static HashMap<String,Object> map;
	public static NetConfig getConfig() {
		return config;
	}
	private NetConfig() {
		map = new HashMap<String,Object>();
		Yaml yaml = new Yaml();
		Map yamlMap = yaml.load(this.getClass().getClassLoader().getResourceAsStream("properties.yaml"));
		changeMap(yamlMap, "");
	}
	
	private void changeMap(Map yamlMap,String keyAt) {
		for (Object key : yamlMap.keySet()) {
			Object value = yamlMap.get(key);
			if (value instanceof Map) {
				changeMap((Map) value,(StringUtil.isNullOrEmpty(keyAt+"")?"":keyAt+".")+key);
			}else {
				map.put((StringUtil.isNullOrEmpty(keyAt+"")?"":keyAt+".")+key, value);
			}
		}
	}
	
	public String getValue(String key,String defaults) {
		if (map.containsKey(key)) {
			return map.get(key)+"";
		}
		return defaults;
	}
	
	public String getValue(String key) {
		return getValue(key, null);
	}
	
}
