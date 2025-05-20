package com.hf.sfm.test;

import java.util.ArrayList;
import java.util.List;

import com.hf.sfm.system.pdo.AWorker;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.json.JSONObject;

/**
 * 数据转换工具类，用于将单个对象、List转换为json、xml格式的字符串
 */
public class ExtUtil {
	
	/**
	 * 将list对象转换为json格式的数据
	 * @param totalNum，记录总数
	 * @param inList，需要转换的list
	 * @return
	 */
	public static String getJsonFromList(long totalNum,List inList){
		Total total = new Total();
		total.setTotalNum(totalNum);
		total.setResultList(inList);
		JSONObject jsonArray = JSONObject.fromObject(total);
		System.out.println("hhshhshd:");
		System.out.println("mj said:");
		return jsonArray.toString();
	}
	
	/**
	 * 将单个对象转换为json格式
	 * @param inObject
	 * @return
	 */
	public static String getJsonFromObject(Object inObject){
		JSONObject jsonString = JSONObject.fromObject(inObject);
		return jsonString.toString();
	}
	
	/**
	 * 将List转化为xml格式的数据
	 * @param totalNum
	 * @param inList，需要转换的list
	 * @return String
	 */
	public static String getXmlFromList(long totalNum,List inList){
		XStream xs = new XStream(new DomDriver());
		Total total = new Total();
		total.setTotalNum(totalNum);
		//创建临时的List对象
		List results = inList;
		results.add(total);
		//创建XStream对象
//		XStream xs = new XStream(new DomDriver());
		//为所有的类创建别名，别名为不包含包名的类名
		for (int i = 0; i < results.size(); i++) {
			Class clzz = results.get(i).getClass();
			//得到全限定类名
			String fullName = clzz.getName();
			//以"."分割字符串
			String [] temp = fullName.split("\\.");
			xs.alias(temp[temp.length-1], clzz);
		}
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xs.toXML(results);
		return xmlString;
	}
	
	/**
	 * 将一个Object对象转化为xml格式输出
	 * @param object
	 * @return
	 */
	public static String getXmlFromObject(Object object){
		XStream xs = new XStream(new DomDriver());
		Class clazz = object.getClass();
		String [] temp = clazz.getName().split("\\.");
		xs.alias(temp[temp.length-1], clazz);
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xs.toXML(object);
		return xmlString;
	}
	
	public static void main(String[] args) {
		ExtUtil eu = new ExtUtil();
		AWorker user = new AWorker();
		user.setAccount("123");
		user.setIdno("021522412");
		user.setGroupid("10022");
		List l = new ArrayList();
		l.add(user);
		System.out.println(eu.getJsonFromList(l.size(), l));
	}
	
}
