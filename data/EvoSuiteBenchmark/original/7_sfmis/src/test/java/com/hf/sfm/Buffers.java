 package com.hf.sfm.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * 封装所有页面提交的参数
 * 
 * @author Dream
 */
public class Buffers {

	// 保存页面提交的参数的名称和值
	ArrayList<HashMap<String, Object>> paramsNameAndValueList;
	// 保存要从页面提交的参数名称和类型
	ArrayList<HashMap<String, String>> paramsNameAndTypeList;
	// 记录日志
	Logger log;

	public Buffers() {
		paramsNameAndValueList = new ArrayList<HashMap<String, Object>>();
		paramsNameAndTypeList = new ArrayList<HashMap<String, String>>();
		log = Logger.getLogger(Buffers.class);
	}

	/**
	 * 将页面提交的参数封装到buffer中
	 * 
	 * @param request
	 * @param session
	 */
	public void assembly(HttpServletRequest request, HttpSession session) {
		loadParams(request);
		// 装配request范围内的参数
		if (request != null) {
			// 一条参数名称和类型
			HashMap<String, String> nameMap = new HashMap<String, String>();
			// 一条参数名称和实际值
			HashMap<String, Object> valueMap = new HashMap<String, Object>();
			for (int i = 0; i < paramsNameAndTypeList.size(); i++) {
				nameMap = paramsNameAndTypeList.get(i);
				String paramName = nameMap.get("paramName");
				Object paramValue = request.getParameter(paramName);
				if (paramName != null && paramValue != null) {
					valueMap.put(paramName, paramValue);
				}
				paramsNameAndValueList.add(valueMap);

			}
		}
		// 装配session范围内的参数
		if (session != null) {
			// 一条参数名称和类型
			HashMap<String, String> nameMap = new HashMap<String, String>();
			// 一条参数名称和实际值
			HashMap<String, Object> valueMap = new HashMap<String, Object>();
			for (int i = 0; i < paramsNameAndTypeList.size(); i++) {
				nameMap = paramsNameAndTypeList.get(i);
				String paramName = nameMap.get("paramName");
				Object paramValue = session.getAttribute(paramName);
				if (paramName != null && paramValue != null) {
					valueMap.put(paramName, paramValue);
				}
				paramsNameAndValueList.add(valueMap);
			}
		}
	}

	/**
	 * 从params.xml中读取页面提交的参数名称和类型 每个参数占一行，如name,string
	 */
	public void loadParams(HttpServletRequest request) {
		String basepath = request.getRealPath("");
		String filePath = basepath + "/WEB-INF/classes/params.flds";
		File file = new File(filePath);
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				HashMap<String, String> map = new HashMap<String, String>();
				String[] lineArray = line.split(",");
				String paramName = lineArray[0];
				String paramType = lineArray[1];
				map.put("paramName", paramName);
				map.put("paramType", paramType);
				paramsNameAndTypeList.add(map);

			}
		} catch (FileNotFoundException e) {
			log.error("Buffers loadParams(),文件未找到");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Buffers loadParams(),读取行失败");
			e.printStackTrace();
		}
	}

	/**
	 * 根据参数名称，获取参数实际值
	 * 
	 * @param paramName
	 * @return 参数实际值,类型为String
	 */
	public String getString(String paramName) {

		String resultString = "";
		// 一条参数名称和实际值
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		for (int i = 0; i < paramsNameAndValueList.size(); i++) {
			valueMap = paramsNameAndValueList.get(i);
			if (valueMap.get(paramName) != null) {
				resultString = valueMap.get(paramName).toString();
			}
		}
		return resultString;
	}

	/**
	 * 根据参数名称，获取参数实际值
	 * 
	 * @param paramName
	 * @return 参数实际值,类型为int
	 */
	public int getInt(String paramName) {

		int resultInt = 0;
		// 一条参数名称和实际值
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		for (int i = 0; i < paramsNameAndValueList.size(); i++) {
			valueMap = paramsNameAndValueList.get(i);
			if (valueMap.get(paramName) != null) {
				resultInt = Integer
						.parseInt(valueMap.get(paramName).toString());
			}
		}
		return resultInt;
	}

	/**
	 * 根据参数名称，获取参数实际值
	 * @param paramName
	 * @return 参数实际值,类型为对象
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object getObject(Class clazz) {

		Object obj = new Object();
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		// 获取属性集
		Field[] fields = clazz.getDeclaredFields();
		for (Iterator it = paramsNameAndValueList.iterator(); it.hasNext();) {
			HashMap tradetype = (HashMap) it.next();
			for (int i = 0; i <= fields.length - 1; i++) {
				//一条参数，包括名称、类型、值
				String paramName = fields[i].getName();
				String paramType = fields[i].getType().getName();
				Object paramValue = tradetype.get(paramName);
				if (tradetype.containsKey(paramName)) {
					try {
						if (paramValue != null) {
							if (paramType == "java.lang.String") {
								PropertyUtils.setProperty(obj,paramName,paramValue.toString());
							} else if (paramType == "java.lang.Integer" || paramType == "int") {
								PropertyUtils.setProperty(obj,paramName,new Integer(paramValue.toString()));
							}
						}
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					} catch (NoSuchMethodException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return obj;
	}

}
