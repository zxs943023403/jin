package com.zxs.jin.init;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.zxs.jin.https.HttpsEngine;

public class Controllers {
	private static ConcurrentHashMap<String, Object> controller;
	private static ConcurrentHashMap<String, Method> contMethods;
	private static Controllers controllers = new Controllers();
	private Controllers() {
		controller = new ConcurrentHashMap<String, Object>();
		contMethods = new ConcurrentHashMap<String, Method>();
	}
	public synchronized static Controllers getInstance() {
		return controllers;
	}
	public void initHttps(Engine engine) {
		List<JinMethod> methods = engine.getAllMethods();
		for (JinMethod method : methods) {
			new Thread(new InitMethodExec(method)).start();
		}
	}
	public static Object getController(String key) {
		if (null == controller) {
			return null;
		}
		if (controller.containsKey(key)) {
			return controller.get(key);
		}
		return null;
	}
	public static Object getController(String className,String methodName) {
		return getController(className+"<==>"+methodName);
	}
	public static Method getMethod(String key) {
		if (null == contMethods) {
			return null;
		}
		if (contMethods.containsKey(key)) {
			return contMethods.get(key);
		}
		return null;
	}
	public static Method getMethod(String className,String methodName) {
		return getMethod(className+"<==>"+methodName);
	}
	private class InitMethodExec implements Runnable{
		private JinMethod method;
		
		public InitMethodExec(JinMethod method) {
			this.method = method;
		}


		@Override
		public void run() {
			// TODO Auto-generated method stub
			String className = method.getClassName();
			String methodName = method.getMethodName();
			Object obj = null;
			Method m = null;
			try {
				Class clazz = Class.forName(className);
				m = clazz.getMethod(methodName, JinContext.class);
				obj = clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contMethods.put(className+"<==>"+methodName, m);
			controller.put(className+"<==>"+methodName, obj);
		}
		
	}
}
