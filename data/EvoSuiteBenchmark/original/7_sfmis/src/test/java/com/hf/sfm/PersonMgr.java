package com.hf.sfm.test;
/**
 * 用户测试
 * @author Administrator
 *
 */
public class PersonMgr {
	
	public void insert(Person person){
		System.out.println("姓名："+person.getName()+"入职日期："+person.getIndate());
		System.out.println(person.toString());
	}
	
	public void say(String msg){
		System.out.println(msg);
	}
}
