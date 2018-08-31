package com.zxs.jin.test;

import com.zxs.jin.init.JinContext;

public class TestController {
	
	public void PostTest(JinContext c) {
		System.out.println("in test post");
		TestVO vo = new TestVO();
		vo = c.bind(vo);
		c.json(200, "success test post:"+vo.ccname);
	}
	
	public void GetTest(JinContext c) {
		System.out.println("in test get");
		
		c.json(200, "success test get"+c.query("bb"));
	}
	
	public void UrlParamsTest(JinContext c) {
		String p = c.query("c");
		c.json(200, "success test param:"+p);
	}
	
}
