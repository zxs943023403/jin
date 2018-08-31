package com.zxs.jin.init;

import java.util.concurrent.ConcurrentHashMap;

public class Controllers {
	private ConcurrentHashMap<String, Object> controller;
	private static Controllers controllers = new Controllers();
	private Controllers() {
		controller = new ConcurrentHashMap<String, Object>();
		
	}
	public static Controllers getInstance() {
		return controllers;
	}
}
