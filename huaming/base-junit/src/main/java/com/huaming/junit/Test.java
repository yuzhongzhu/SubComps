package com.huaming.junit;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;

public class Test {
	
	public static void main(String[] args) {
		String abPath = Test.class.getClassLoader().getResource("").getPath();
    	System.out.println(abPath);
    	File f = new File(abPath.substring(1));
    	System.out.println(f.getPath());
    	String parentPath = f.getParentFile().toString()+File.separator+"classes"+File.separator;
    	DOMConfigurator.configure( parentPath+"log4j_config.xml" );
		BizTestLog4j test = new BizTestLog4j();
		test.test();
	}
	
    
 } 
