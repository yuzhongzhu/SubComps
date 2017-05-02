package com.huaming.junit;

import java.io.File;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * 子类继承此类并实现以下两个注解
 * @RunWith(FrameWorkJunitTest.class)
 * @ContextConfiguration({"springconfig1.xml","springconfig2.xml",.....})
 * @ContextConfiguration(locations="classpath:resources/junit-spring.xml")
 *
 * @author Administrator
 *
 */

public class FrameWorkJunitTest  extends SpringJUnit4ClassRunner{

	public FrameWorkJunitTest(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
    static {
        try {
        	String abPath = FrameWorkJunitTest.class.getClassLoader().getResource("").getPath();
        	System.out.println(abPath);
        	File f = new File(abPath.substring(1));
        	System.out.println(f.getPath());
        	String parentPath = f.getParentFile().toString()+File.separator+"classes"+File.separator;
           Log4jConfigurer.initLogging(parentPath+"log4j_config.xml");
        	//Log4jConfigurer.initLogging("classpath*:log4j_config.xml");

        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Cannot initialize log4j");
        }
    }
}
