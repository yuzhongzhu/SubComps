package com.huaming.pst.testbiz.bean;

import com.huaming.pst.bean.BaseBean;
import com.huaming.pst.pub.FieldName;
import com.huaming.pst.pub.TableName;
@TableName(name="user")
public class User extends BaseBean{
	private Long id;
	@FieldName(name="name")
	private String name;
	private String age;
	private String phone;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
