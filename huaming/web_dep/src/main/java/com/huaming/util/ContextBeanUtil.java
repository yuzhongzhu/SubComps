package com.huaming.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextBeanUtil implements ApplicationContextAware{

private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		ContextBeanUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在application-context.xml中定义SpringContextHolder");
	}

}
