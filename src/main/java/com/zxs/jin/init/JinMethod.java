package com.zxs.jin.init;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JinMethod {
	private String className;
	private String methodName;
	public JinMethod(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	public void exec(JinContext c) {
		try {
			Class clazz = Class.forName(className);
			Method m = clazz.getMethod(methodName, JinContext.class);
			Object controller = clazz.newInstance();
			m.invoke(controller, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.json(500, e.getMessage());
		}
	}
	
}
