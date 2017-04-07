package com.huaming.pst.pub;

import java.io.Serializable;

import com.huaming.pst.bean.WhereParams;



public class Method {
	/**
	 * where重写
	 * @param pram
	 * @return
	 */
	public static WhereParams where(String field, String where, Serializable value){
			return new WhereParams(field , where , value);
	}
	public static WhereParams where(String field, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return where(field, where, value);
	}
	public static WhereParams createDefault(){
		return new WhereParams(null, null, null);
	}
	
}
