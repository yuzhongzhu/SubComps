package com.huaming.pst.pub;
/**
 * 
 * @author Michael
 * 数据库中相关约束基本配置类
 *
 */
public class DBConstaintVl {
	/**
	 * FieldName注解中默认的name值
	 */
	public static final String COLUMN_DEFAUL_VALUE = "default";
	
	/**
	 * 主键注解中type值得类型，该字段表示整数型自增Id
	 */
	public static final int TABLE_PKEY_TYPE_INTEGER = 0;
	
	/**
	 * 主键注解中type值得类型，该字段表示String型UUID
	 */
	public static final int TABLE_PKEY_TYPE_UUID = 1;
}
