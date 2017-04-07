package com.huaming.pst.bean;

public class FmtParm {

	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 查询
	 */
	private String select;
	
	/**
	 * 响应的实体类
	 */
	private Class<? extends BaseBean> po;
	
	/**
	 * 查询参数
	 */
	private WhereParams where;
	
	
	public FmtParm(){}
	
	public FmtParm(String fieldName, String select, Class<? extends BaseBean> po, WhereParams where){
		this.fieldName = fieldName;
		this.select = select;
		this.po = po;
		this.where = where;
	}
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public Class<? extends BaseBean> getPo() {
		return po;
	}
	public void setPo(Class<? extends BaseBean> po) {
		this.po = po;
	}
	public WhereParams getWhere() {
		return where;
	}
	public void setWhere(WhereParams where) {
		this.where = where;
	}
	
	
}
