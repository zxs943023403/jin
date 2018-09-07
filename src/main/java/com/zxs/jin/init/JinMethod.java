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
	
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void exec(JinContext c) {
		try {
			Method m = Controllers.getMethod(className, methodName);
			Object controller = Controllers.getController(className,methodName);
			m.invoke(controller, c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.json(500, e.getMessage());
		}
	}
	
}
