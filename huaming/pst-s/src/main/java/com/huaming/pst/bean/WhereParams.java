package com.huaming.pst.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.huaming.pst.pub.C;


public class WhereParams {

private String pram;
	
	private String limit;
	
	private String orderBy;
	
	private List<Serializable> parms = new ArrayList<Serializable>();
	
	private List<Serializable> limitParms = new ArrayList<Serializable>();
	

	public WhereParams(String file, String where, Serializable value){
		
		if(null == file && null == where && value == where){
			return;
		}
		
		if (null == value) {
			
			if (where.equals("=")) {
				where = " is";
			}else{
				where = " not ";
			}
			this.pram = " where " + file + where + "null";
		}else{
			if ("like".equals(where)) {
				this.pram = " where " + file + where + " '%" + value + "%'";
			}else{
				this.pram = " where " + file + where + " '" + value + "'";
			}
		}
	}
	

	
	/**
	 * and条件
	 * @param file
	 * @param where
	 * @param value
	 * @return
	 */
	public WhereParams and(String file, String where, Serializable value){
		if (null == value) {
			if (where.equals("=")) {
				where = " is";
			} else {
				where = " not ";
			}
			this.pram = " and " + file + where + "null";
		} else {
			if ("like".equals(where)) {
				this.pram += " and " + file + " " + where + " '%" + value + "%'";
			}else{
				this.pram += " and " + file + " " + where + " '" + value + "'";
			}
		}
		
		return this;
	}
	public WhereParams and(String file, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return and(file, where, value);
	}
	
	/**
	 * or条件
	 * @param file
	 * @param where
	 * @param value
	 * @return
	 */
	public WhereParams or(String file, String where, Serializable value){
		
		if (null == value) {
			if (where.equals("=")) {
				where = " is";
			} else {
				where = " not ";
			}
			this.pram = " or " + file + where + "null";
		} else {
			if ("like".equals(where)) {
				this.pram += " or " + file + where + " '%" + value + "%'";
			}else{
				this.pram += " or " + file + where + " '" + value + "'";
			}
		}
		
		return this;
	}
	public WhereParams or(String file, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return or(file, where, value);
	}
	
	public WhereParams limit(int startNum, int length) {

		this.limit = " limit " + startNum + " , " + length + " ";
		return this;
	}
	
	public WhereParams orderBy(String order){
		if(this.orderBy != null){
			this.orderBy += "," + order;
		}else{
			this.orderBy = order;
		}
		return this;
	}

	@Override
	public String toString() {
		return "WhereParams [pram=" + pram + "]";
	}
	
	/**
	 * 获取prams
	 * @return
	 */
	public String getWhereParams(){
		String p = "";
		p += null == this.pram ? "" : this.pram;
		p += null == this.orderBy ? "" : (" order by " + this.orderBy);
		p += null == this.limit ? "" : this.limit;
		return p;
	}
	
	public Serializable[] listParmsByFmt(){
		parms.addAll(limitParms);
		return parms.toArray(new Serializable[parms.size()]);
	}
	
	public Serializable[] listParms(){
		int length = getWhereParams().indexOf("?");
		if(-1 == length){
			return new Serializable[0];
		}
		parms.addAll(limitParms);
		return parms.toArray(new Serializable[parms.size()]);
	}

}
