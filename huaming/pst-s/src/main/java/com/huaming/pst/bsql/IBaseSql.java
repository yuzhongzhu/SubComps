package com.huaming.pst.bsql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IBaseSql {
	/**
	 * 添加局部保存即非空字段入库
	 * @param value
	 */
	public abstract void addLocal(@Param("value")String value);
	public abstract void add(@Param("value")String value);
	public abstract List<Map<String,Object>> getById(@Param("value")String value);
	public abstract List<Map<String,Object>> getFieldById(@Param("value")String value);
	public abstract List<Map<String,Object>> getByParm(@Param("value")String value);
	public abstract List<Map<String,Object>> getFieldByParm(@Param("value")String value);
	public abstract List<Map<String,Object>> selectList(@Param("value")String value);
	public abstract List<Map<String,Object>> selectListField(@Param("value")String value);
	public abstract List<Map<String,Object>> selectBySql(@Param("value")String value);
	public abstract List<Map<String,Object>> selectIn(@Param("value")String value);
	public abstract Long selectCountByParm(@Param("value")String value);
	public abstract Long selectCount(@Param("value")String value);
	public abstract Long fetchSeqNextval(@Param("value")String value);
	public abstract void updateLocal(@Param("value")String value);
	public abstract void update(@Param("value")String value);
	public abstract void updateLocalByPram(@Param("value")String value);
	public abstract void updateByPram(@Param("value")String value);
	public abstract void deleteById(@Param("value")String value);
	public abstract void deleteByparm(@Param("value")String value);
	public abstract void excuse(@Param("value")String value);
	
	
	
	
	
	
	
}
