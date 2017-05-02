package com.huaming.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BizTestLog4j {
	private Logger logger = LoggerFactory.getLogger(BizTestLog4j.class);
	public void test(){
		logger.info("............开始处理");
		System.out.println("处理中.........");
		logger.info("............结束处理");
	}
}
