package com.huaming.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.huaming.junit.BizTestLog4j;
import com.huaming.junit.FrameWorkJunitTest;
@RunWith(FrameWorkJunitTest.class)
@ContextConfiguration({"classpath*:resources/junit-spring.xml"})
public class BizTestLog4jTest  {
	@Test
	public void testTest() {
		BizTestLog4j test = new BizTestLog4j();
		test.test();
	}

}
