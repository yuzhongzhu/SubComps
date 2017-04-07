package com.huaming.pst.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huaming.pst.bean.BaseBean;
import com.huaming.pst.bean.Param;
import com.huaming.pst.pub.FieldName;
import com.huaming.pst.pub.IgnoreField;
import com.huaming.pst.pub.TableName;
/**
 * 
 * @author Michael wuzongming1016@126.com
 * 
 * @param <T>
 */
public class SqlUtil<T extends BaseBean> {
 public static final String T_COL_SEP="_";
 public static final String POJO_BEAN_GET="get";
 public static final String POJO_TF_IS="is";
 public static final String T_KEY_FLAG="ID";
 public static final Logger logger = LoggerFactory.getLogger(SqlUtil.class);
 /**
  * 通过传入的POJO实体
  * 将其对应的
  * @param po
  * @return
  */
 public List<Param> getParamList(BaseBean po){
		List<Param> list = new ArrayList<Param>();
		Class<? extends BaseBean> thisClass = po.getClass();
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Object o = thisClass.newInstance();
			logger.info("============getParamList=================="+o.toString());
			for (Field f : fields) {
				if (!f.getName().equalsIgnoreCase(T_KEY_FLAG) && !f.isAnnotationPresent(IgnoreField.class)) {
					String fName = f.getName();
					String getf = POJO_BEAN_GET;
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1 || fieldType.indexOf("Boolean") != -1) {
						getf = POJO_TF_IS;
					}
					String methodStr = getf + fName.substring(0, 1).toUpperCase() + fName.substring(1);
					String fieldName = "";
					if (f.isAnnotationPresent(FieldName.class)) {
						fieldName = f.getAnnotation(FieldName.class).name();
					} else {
						fieldName = toTableString(fName);
					}
					Method get = thisClass.getMethod(methodStr);
					Object getValue = get.invoke(o);
					Param Param = new Param(fieldName, getValue);
					list.add(Param);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return list;
 }
 
 public List<Param> getParamList(Class<?> clazz){
	 return null;
 }

 public List<Param> getParamListOfSelect(Class<?> po) {
		List<Param> list = new ArrayList<Param>();
		Class<?> thisClass = po;
		Field[] fields = thisClass.getDeclaredFields();
		try {
			Object o = thisClass.newInstance();
			for (Field f : fields) {
				if (!f.isAnnotationPresent(IgnoreField.class)) {
					String fName = f.getName();
					String getf = POJO_BEAN_GET;
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1 || fieldType.indexOf("Boolean") != -1) {
						getf = POJO_TF_IS;
					}
					String methodStr = getf + fName.substring(0, 1).toUpperCase() + fName.substring(1);
					String fieldName = "";
					if (f.isAnnotationPresent(FieldName.class)) {
						fieldName = f.getAnnotation(FieldName.class).name();
					} else {
						fieldName = toTableString(fName);
					}
					Method get = thisClass.getMethod(methodStr);
					Object getValue = get.invoke(o);
					Param pram = new Param(fieldName + " as " + fName, getValue);
					list.add(pram);
				}
			}
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		}
		return list;
	}
 
 public String getTableName(Class<?> entityClass){
	 if(entityClass.isAnnotationPresent(TableName.class)){
			return entityClass.getAnnotation(TableName.class).name();
		}else{
			String tName = toTableString(entityClass.getSimpleName());
			String poName = tName.substring(tName.length() - 2, tName.length());
			if("po".equals(poName)){
				tName = tName.substring(0,tName.length() - 3);
			}
			return tName;
		}
 }
 /**
  * 根据实体类名，以及属性名，判断字段是否存在
  * 如存在返回对应的实体名称，如不存在返回null
  * @param clazz
  * @param filedNm
  * @return
  */
 public Field  getField(Class<?> clazz,String filedNm){
	 Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(filedNm)) {
				return field;
			}
		}
		return null;
 }
 
 public static List<Param> getParamListofStatic(BaseBean po){
		List<Param> paramList = new ArrayList<Param>();
		Class<? extends BaseBean> thisClazz = po.getClass();
		Field[] fields = thisClazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				if (!f.getName().equalsIgnoreCase(T_KEY_FLAG) && !f.isAnnotationPresent(IgnoreField.class)) {
					String fName = f.getName();
					String getf = POJO_BEAN_GET;
					String fieldType = f.getGenericType().toString();
					if (fieldType.indexOf("boolean") != -1 || fieldType.indexOf("Boolean") != -1) {
						getf = POJO_TF_IS;
					}
					
					String fieldName = "";
					if (f.isAnnotationPresent(FieldName.class)) {
						fieldName = f.getAnnotation(FieldName.class).name();
					} else {
						fieldName = new SqlUtil<BaseBean>().toTableString(fName);
					}
					String methodStr = getf + fName.substring(0, 1).toUpperCase() + fName.substring(1);
					Method get = thisClazz.getMethod(methodStr);
					Object getValue = get.invoke(po);
					Param pram = new Param(fieldName, getValue);
					paramList.add(pram);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return paramList;
	}
 
 
 public static void setFieldValue(BaseBean po,String keyNm,Serializable val){
	 
 }
/**
 * 实体POJO类属性命名需遵循驼峰规则，
 * 否则处理失败，无法完成
 * @param fileName
 * @return
 */
public String toTableString(String fieldName) {
	StringBuilder colNm = new StringBuilder(fieldName.substring(0,1).toLowerCase());
	for(int i=1;i<fieldName.length();i++){
		if(!Character.isLowerCase(fieldName.charAt(i))){
			colNm.append(T_COL_SEP);
		}else{
			colNm.append(fieldName.substring(i, i+1));
		}
	}
	return colNm.toString().toLowerCase();
}

/**
 * 取字段名
 * @param po
 * @param fileName
 * @return
 */
public Serializable getFieldValue(BaseBean po, String fieldName){
	try {
		Class<? extends BaseBean> cla = po.getClass();
		Method method = cla.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
		Object o = po;
		Object invoke = method.invoke(o);
		return null == invoke ? null : (Serializable)invoke;
	} catch (NoSuchMethodException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	return null;
}






 
}
