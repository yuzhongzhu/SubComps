package com.huaming.pst.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.huaming.pst.bean.BaseBean;
import com.huaming.pst.bean.WhereParams;

public interface BaseDao <T extends BaseBean, PK extends Serializable> {

	    /**
	     * 添加不为空的记录（只将不为空字段入库，效率高）
	     * @param po
	     * @return 受改变的记录数
	     */
	    public int addLocal(T po);

	    /**
	     * 记录添加（所有字段入库，效率中）
	     * @param po
	     * @return
	     */
	    public int add(T po);

	    /**
	     * 通过主键获取某个记录
	     * @param id 主键
	     * @return PO
	     */
	    public T get(PK id,Class<T> entityClass);

	    /**
	     * 通过主键获取某个字段的值
	     * @param id
	     * @param fileName
	     * @return
	     */
	    public Serializable getField(PK id, String fileName,Class<T> entityClass);

	    /**
	     * 条件获取一条记录
	     * @param t
	     * @param 条件表达式
	     * @return PO
	     */
	    public T get(WhereParams where,Class<T> entityClass);

	    /**
	     * 条件获取某个记录字段
	     * @param where
	     * @param fileName
	     * @return
	     */
	    public Serializable getField(WhereParams where, String fileName,Class<T> entityClass);

	    /**
	     * 条件查询列表
	     * @param where 条件表达式
	     * @return PO列表
	     */
	    public List<T> list(WhereParams where,Class<T> entityClass);

	    /**
	     * 查询某个字段列表
	     * @param where 条件表达式
	     * @param fileName 要查询的字段
	     * @return
	     */
	    public Serializable[] listFields(WhereParams where, String fileName,Class<T> entityClass);

	    /**
	     * 查询某些字段
	     * @param where 条件表达式
	     * @param files 要查询的字段集
	     * @return 查询的PO字段列表
	     */
	    public List<Map<String, Serializable>> listFields(WhereParams where, String[] files,Class<T> entityClass);

	    /**
	     * 更新不为null的BaseBean字段
	     * @param po
	     * @return 受影响的行数
	     */
	    public int updateLocal(T po);

	    /**
	     * 更新PO的所有字段
	     * @param po
	     * @return 受影响的行数
	     */
	    public int update(T po);

	    /**
	     * 条件更新不为null的字段
	     * @param po
	     * @param 条件表达式
	     * @return 受影响的行数
	     */
	    public int updateLocal(T po, WhereParams where);

	    /**
	     * 条件更新所有字段
	     * @param po
	     * @param 条件表达式
	     * @return 受影响的行数
	     */
	    public int update(T po, WhereParams where);

	    /**
	     * 删除某个记录
	     * @param id 主键
	     * @return 受影响的行数
	     */
	    public int del(PK id,Class<?> entityClass);

	    /**
	     * 条件删除某个记录
	     * @param where 条件表达式
	     * @return 受影响的行数
	     */
	    public int del(WhereParams where,Class<?> entityClass);

	    /**
	     * 自定义sql查询
	     * @param po 用于封装返回结果的Bean
	     * @param sql 用于执行查询的Sql
	     * @param args Sql占位付对应的参数
	     * @return 结果集合
	     */
	    public List<Map<String, Object>> listBySql(String sql);

	    /**
	     * 执行自定义sql
	     * @param sql 用于执行的Sql
	     * @param args Sql占位付对应的参数
	     * @return 受影响的行数
	     */
	    public int excuse(String sql);

	    /**
	     * 获取指定条件的记录数
	     * @param where 条件表达式
	     * @return 查询到的记录数
	     */
	    public long count(WhereParams where,Class<?> entityClass);

	    /**
	     * 获取对应表中的记录数
	     * @return 表中的条数
	     */
	    public long size(Class<?> entityClass);

	    /**
	     * 是否存在字段相同的记录（ID以及不为空的字段除外）
	     * @param po 参照实体
	     * @return
	     */
	    public boolean isExist(T po);

	    /**
	     * 是否存在指定条件的记录
	     * @param where 条件表达式
	     * @return
	     */
	    public boolean isExist(WhereParams where,Class<?> entityClass);

	    /**
	     * 内查询
	     * @param fileName 用于内查询的字段
	     * @param values 字段的值
	     * @return 查询到的结果集
	     */
	    public List<T> in(String fileName, Serializable[] values,Class<?> entityClass);

	    /**
	     * 获得下一个序列的值
	     * @return
	     */
	    public long nextId();
	    
	    /**
	     * 获得下一个序列的值
	     * @return
	     */
	    public long nextId(String tableName);
	  //  public List<T> listFormat(WhereParams where, Formatter fmt);

}
