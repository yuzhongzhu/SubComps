package com.huaming.pst.bean;
/**
 * 通用参数
 * @author Michael
 *
 */
public class Param {
	Object value;
	String field;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Param(String field,Object value) {
		super();
		this.value = value;
		this.field = field;
	}
	public Param() {
		super();
	}
	
	
	
	
}
