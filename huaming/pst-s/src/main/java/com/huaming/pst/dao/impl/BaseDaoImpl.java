package com.huaming.pst.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.huaming.pst.bean.BaseBean;
import com.huaming.pst.bean.Param;
import com.huaming.pst.bean.WhereParams;
import com.huaming.pst.dao.BaseDao;
import com.huaming.pst.pub.C;
import com.huaming.pst.pub.FieldName;
import com.huaming.pst.pub.Method;
import com.huaming.pst.util.SqlUtil;
@SuppressWarnings("restriction")
@Repository("baseDao")
public class BaseDaoImpl<T extends BaseBean, PK extends Serializable> implements BaseDao <T, PK>{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sqlSessionTemplateASS")
	private SqlSessionTemplate sqlSessionTemplateASS;

	private String pkName; // 主键字段

	private String idName; // 对应id名称

	private String seq; // 当前主键
	
	private SqlUtil<?> sqlUtil;

	public BaseDaoImpl() {
		super();

		this.sqlUtil = new SqlUtil<T>();
		// 习惯统一用‘id’做约束了，所以这里我给固定死了，不想固定的话可以修改这里
		this.pkName = "id";

		this.idName = "id";

		this.seq = "id";

	}
	
	public int addLocal(T po) {
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = "insert into " + tableName + " (";
		String Params = "";
		String values = "";

		List<Param> ParamList = SqlUtil.getParamListofStatic(po);

		int index = 0;
		for (int i = 0; i < ParamList.size(); i++) {
			if (ParamList.get(i).getValue() == null || (ParamList.get(i).getValue() + "").equals("0")) {
				continue;
			} else {
				if (index > 0) {
					Params += ",";
					values += ",";
				}
				Params += ParamList.get(i).getField();
				Object value = ParamList.get(i).getValue();
				if (value instanceof byte[]) {
					values += "'" + new String((byte[]) value) + "'";
				} else if (value instanceof String) {
					values += "'" + value + "'";
				} else {
					values += value;
				}

				index++;
			}
		}
		sql += Params + ") value (" + values + ");";

		SqlUtil.setFieldValue(po, "id", nextId(tableName));
		logger.info("==============addLocal==============="+sql);
		return sqlSessionTemplateASS.insert("addLocal", sql);
	}

	public int add(T po) {
		//init(po.getClass());
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = "insert into " + tableName + " (";
		String Params = "";
		String values = "";
		List<Param> ParamList = SqlUtil.getParamListofStatic(po);
		for (int i = 0; i < ParamList.size(); i++) {
			Params += ParamList.get(i).getField();
			if (ParamList.get(i).getValue() == null) {
				values += "null";
			} else {
				values += "'" + ParamList.get(i).getValue() + "'";
			}

			if (i < ParamList.size() - 1) {
				Params += ",";
				values += ",";
			}
		}
		sql += Params + ") value (" + values + ");";
		SqlUtil.setFieldValue(po, "id", nextId(tableName));
		logger.info("=============[add]================"+sql);
		return sqlSessionTemplateASS.insert("add", sql);
	}
		
	public T get(PK id, Class<T> entityClass) {
		List<Param>  selectSqlParms = this.sqlUtil.getParamListOfSelect((Class<?>) entityClass);
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getField();
			if (i < selectSqlParms.size() - 1) {
				sql += ",";
			} else {
				sql += " ";
			}
		}
		sql += " from " + tableName + " where id='" + id + "';";
		Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne("getById", sql);
		return handleResult(resultMap, entityClass);
	}
	
	public T get(WhereParams where, Class<T> entityClass) {
		List<Param>  selectSqlParms = this.sqlUtil.getParamListOfSelect((Class<?>) entityClass);
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getField();
			if (i < selectSqlParms.size() - 1) {
				sql += ",";
			} else {
				sql += " ";
			}
		}
		sql += "from " + tableName + where.getWhereParams() + ";";

		Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne("getByParm", sql);

		return handleResult(resultMap, entityClass);
	}
	
	public Serializable getField(PK id, String fileName, Class<T> entityClass) {
		String field = fileName;
		String tabField = "";
		Field f = sqlUtil.getField(entityClass, field);
		if (null == f) {
			logger.error("查询字段失败(无法找到" + entityClass + "中的" + field + "字段)");
			return null;
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(field) + " as " + field;
		} else {
			 tabField = annotation.name() + " as " + field;
		}
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		sql += tabField + " from " + tableName + " where id='" + id + "';";
		Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne("getFieldById", sql);
		return (Serializable) resultMap.get(fileName);
	}
	
	public Serializable getField(WhereParams where, String fileName,Class<T> entityClass) {
		String field = fileName;
		String tabField = "";
		Field f = sqlUtil.getField(entityClass, field);
		if (null == f) {
			logger.error("查询字段失败(无法找到" + entityClass + "中的" + field + "字段)");
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(field) + " as " + field;
		} else {
			 tabField = annotation.name() + " as " + field;
		}
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWhereParams() + ";";
		Map<String, Object> resultMap = sqlSessionTemplateASS.selectOne("getFieldByParm", sql);
		return (Serializable) resultMap.get(field);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> list(WhereParams where,Class<T> entityClass) {
		List<Param>  selectSqlParms = this.sqlUtil.getParamListOfSelect((Class<?>) entityClass);
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		for (int i = 0; i < selectSqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getField();
			if (i < selectSqlParms.size() - 1) {
				sql += ",";
			} else {
				sql += " ";
			}
		}
		sql += "from " + tableName + where.getWhereParams() + ";";
		List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectList", sql);
		List<T> list = new ArrayList();
		for (Map<String, Object> map : selectList) {
			T t = handleResult(map, entityClass);
			list.add(t);
		}
		return list;
	}

	public Serializable[] listFields(WhereParams where, String fieldName,Class<T> entityClass) {
		String field = fieldName;
		String tabField = "";
		Field f = sqlUtil.getField(entityClass, field);
		if (null == f) {
			logger.error("查询指定字段集失败(无法序列化" + entityClass + "中的" + field + "字段)");
			return null;
		}
		FieldName annotation = f.getAnnotation(FieldName.class);
		if (null == annotation) {
			tabField = sqlUtil.toTableString(field) + " as " + field;
		} else {
			 tabField = annotation.name() + " as " + field;
		}
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWhereParams() + ";";
		List<Map<String, Object>> resultMap = sqlSessionTemplateASS.selectList("selectListField", sql);

		Serializable[] fields = new Serializable[resultMap.size()];

		for (int i = 0; i < resultMap.size(); i++) {
			if (null != resultMap.get(i)) {
				fields[i] = (Serializable) resultMap.get(i).get(fieldName);
			}
		}
		return fields;
	}

	public List<Map<String, Serializable>> listFields(WhereParams where, String[] fields,Class<T> entityClass) {
		String tabField = "";
		int index = 1;
		for (String field : fields) {
			Field f = sqlUtil.getField(entityClass, field);
			if (null == f) {
				logger.error("查询指定字段集失败(无法序列化" + entityClass + "中的" + field + "字段)");
			}
			FieldName annotation = f.getAnnotation(FieldName.class);
			if (null == annotation) {
				tabField += sqlUtil.toTableString(field) + " as " + field;
			} else {
				 tabField += annotation.name() + " as " + field;
			}
			if (index < fields.length) {
				tabField += ",";
			}

			index++;
		}
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select ";
		sql += tabField + " from " + tableName + where.getWhereParams() + ";";
		List<Map<String, Serializable>> resultMap = sqlSessionTemplateASS.selectList("selectListField", sql);

		return resultMap;
	}

	public int updateLocal(T po) {
		Serializable id = sqlUtil.getFieldValue(po, "id");
		if(null == id){
			return 0;
		}
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = " update " + tableName +" set";
		List<Param> prams = sqlUtil.getParamList(po);
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getField() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((Boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += " where id='" + id +"';";
		
		return sqlSessionTemplateASS.update("updateLocal", sql);
	}

	public int update(T po) {
		Serializable id = sqlUtil.getFieldValue(po, "id");
		if(null == id){
			return 0;
		}
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = "update " + tableName + " set ";
		List<Param> prams = sqlUtil.getParamList(po);
		
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getField() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((Boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
				if (i < prams.size() -1) {
					sql += ",";
				}
			}else{
				sql += prams.get(i).getField() + "=null";
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += " where id='" + id +"';";
		return sqlSessionTemplateASS.update("update", sql);
	}

	public int updateLocal(T po, WhereParams where) {
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = "update " + tableName + " set ";
		List<Param> prams = sqlUtil.getParamList(po);
		for (int i = 0; i < prams.size(); i++) {
			if(null != prams.get(i).getValue()){
				sql += prams.get(i).getField() + "=";
				Object value = prams.get(i).getValue();
				if (value instanceof byte[] ) {
					sql += "'" + new String((byte[]) value) + "'";
				}else if(value instanceof Boolean){
					sql += "'" + ((Boolean)value == true ? 1 : 0) + "'";
				}else{
					sql += "'" + value + "'";
				}
				if (i < prams.size() -1) {
					sql += ",";
				}
			}
		}
		sql += where.getWhereParams() +"';";
		return sqlSessionTemplateASS.update("updateLocalByPram", sql);
	}

	public int update(T po, WhereParams where) {
		String tableName = this.sqlUtil.getTableName(po.getClass());
		String sql = "update " + tableName + " set ";
		List<Param> sqlParms = this.sqlUtil.getParamList(po.getClass());
		for (int i = 0; i < sqlParms.size(); i++) {
			Object value = sqlParms.get(i).getValue();
			if(null != value){
				if( value instanceof Boolean){
					sql += sqlParms.get(i).getField()+ "='" + (((Boolean)value) == true ? 1 : 0) + "'";
				}else{
					sql += sqlParms.get(i).getField()+ "='" + sqlParms.get(i).getValue() + "'";
				}
				
				
				if (i < sqlParms.size() -1) {
					sql += ",";
				}
			}else{
				sql += sqlParms.get(i).getField() + "=null";
				if (i < sqlParms.size() -1) {
					sql += ",";
				}
			}
		}
		sql += where.getWhereParams() + "';";
		return sqlSessionTemplateASS.update("updateByPram", sql);
	}
	
	public int del(PK id, Class<?> entityClass) {
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "delete from " + tableName + " where id=" + id;
		return sqlSessionTemplateASS.delete("deleteById", sql);
	}

	public int del(WhereParams where, Class<?> entityClass) {
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "delete from " + tableName + where.getWhereParams();
		return sqlSessionTemplateASS.delete("deleteByparm", sql);
	}

	public List<Map<String, Object>> listBySql(String sql) {
		List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectBySql", sql);
		return selectList;
	}

	public int excuse(String sql) {
		return sqlSessionTemplateASS.update("excuse", sql);
	}

	public long count(WhereParams where, Class<?> entityClass) {
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select count(*) from ";

		sql += tableName + where.getWhereParams();

		long count = sqlSessionTemplateASS.selectOne("selectCountByParm", sql);

		return count;
	}

	public long size(Class<?> entityClass) {
		String tableName = this.sqlUtil.getTableName(entityClass);
		String sql = "select count(*) from " + tableName;
		long count = sqlSessionTemplateASS.selectOne("selectCount", sql);
		return count;
	}

	public boolean isExist(T po) {
		WhereParams wherePrams = Method.createDefault();
		List<Param> list = SqlUtil.getParamListofStatic(po);
		for (int i = 0; i < list.size(); i++) {
			String fieldName = list.get(i).getField();
			if (i == 0) {
				wherePrams = Method.where(fieldName, C.EQ, (Serializable)list.get(i).getValue());
			}else{
				wherePrams.and(fieldName, C.EQ, (Serializable)list.get(i).getValue());
			}
		}
		return count(wherePrams,po.getClass()) > 0;
	}

	public boolean isExist(WhereParams where, Class<?> entityClass) {
		return count(where,entityClass) > 0;
	}

	@SuppressWarnings("unchecked")
	public List<T> in(String fieldName, Serializable[] values, Class<?> entityClass) {
		String sql = "select ";
		List<Param> selectSqlParms = this.sqlUtil.getParamListOfSelect(entityClass);
		List<Param> sqlParms = this.sqlUtil.getParamList(entityClass);
		for (int i = 0; i < sqlParms.size(); i++) {
			sql += selectSqlParms.get(i).getField();
			if(i < selectSqlParms.size() -1){
				sql += ",";
			}else{
				sql += " ";
			}
		}
		String tableName = this.sqlUtil.getTableName(entityClass);
		sql += "from " + tableName + " where " + fieldName + " in ";
		String value = "(";
		for(int i = 0; i < values.length; i++){
			if(i < values.length -1){
				value += values[i] + ","; 
			}else{
				value += values[i] + ");"; 
			}
		}
		sql += value;
		
		List<Map<String, Object>> selectList = sqlSessionTemplateASS.selectList("selectIn", sql);
		
		List<T> list = new ArrayList<T>();
		for (Map<String, Object> map : selectList) {
			T t = handleResult(map, (Class<T>) entityClass);
			list.add(t);
		}
		
		return list;
	}

	public long nextId() {
		String tableName = this.sqlUtil.getTableName(null);
		String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_NAME='" + tableName + "' AND TABLE_SCHEMA=(select database())";
		Long idVal = sqlSessionTemplateASS.selectOne("fetchSeqNextval", sql);
		if (null == idVal) {
			logger.error("/********************************");
			logger.error("/表" + tableName + "不存在");
			logger.error("/********************************");
			return 0;
		}
		return idVal;
	}

	public long nextId(String tableName) {
		String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_NAME='" + tableName + "' AND TABLE_SCHEMA=(select database())";
		Long idVal = sqlSessionTemplateASS.selectOne("fetchSeqNextval", sql);
		if (null == idVal) {
			logger.error("/********************************");
			logger.error("/表" + tableName + "不存在");
			logger.error("/********************************");
			return 0;
		}
		return idVal;
	}
	
	private T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
		if (null == resultMap) {
			return null;
		}
		T t = null;
		try {
			t = tClazz.newInstance();
		} catch (InstantiationException e) {
			logger.error("/********************************");
			logger.error("实例化Bean失败(" + tClazz + ")!" + e.getMessage());
			logger.error("/********************************");
		} catch (IllegalAccessException e) {
			logger.error("/********************************");
			logger.error("实例化Bean失败(" + tClazz + ")!" + e.getMessage());
			logger.error("/********************************");
		}
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String key = entry.getKey();
			Serializable val = (Serializable) entry.getValue();
			try {
				SqlUtil.setFieldValue(t, key, val);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("/********************************");
				logger.error("/实例化Bean失败(" + tClazz + ")不能赋值到字段(" + key + "):" + e.getMessage());
				logger.error("/********************************");
			}
		}
		return t;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


	
	
	/**
	 * 是否为SQL表达符号
	 * @param c
	 * @return
	 */
	/*private boolean isC(String c){
		switch (c) {
		case "=":
			return true;
		case "<":
			return true;
		case ">":
			return true;
		case "<>":
			return true;
		case "like":
			return true;
		case "in":
			return true;
		default:
			return false;
		}
	}*/
	
	/**
	 * 从List<String>集合中检查是否有存在的元素
	 * @param list
	 * @param tabName
	 * @return
	 */
	/*private boolean isExcTab (List<String> list, String tabName){
		for (String string : list) {
			if (tabName .equals( string)) {
				return true;
			}
		}
		return false;
	}*/
	



}
