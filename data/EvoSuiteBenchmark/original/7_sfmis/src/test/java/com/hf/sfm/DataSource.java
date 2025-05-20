package com.hf.sfm.test;

import javax.servlet.http.HttpSession;


public class DataSource extends Loader {

	/**
	 *返回JSON形式的数据 
	 */
//	public String getGridDataWithSearch(String sqlpath,int start,int limit,String[][] params,String searchsql) {
//		this.run(sqlpath, start, limit, params,searchsql);
//		return this.getMetaData();
//	}
	
	public String getPlanarGridDataWithSearch(String sqlpath,int start,int limit,String[] params,String searchsql) {
		this.run(sqlpath, start, limit, params,searchsql);
		return this.getMetaData();
	}
	
	public String getGridData(String sqlpath,int start,int limit,String[] params){
		this.run(sqlpath, start, limit, params,"undefined");
		System.out.println("&&&&&&&&&&&&&&&&&:"+this.getMetaData());
		return this.getMetaData();
	}
//	public String getGridData(String sqlpath,String[][] params){
//		return this.getGridData(sqlpath, 0, 0, params);
//	}
//	
//	public String getGridData(String sqlpath){
//		return this.getGridData(sqlpath,null);
//	}
	
	/**
	 *返回结果集数组 
	 */
//	public String getArrData(String sqlpath,int start,int limit,String[][] params) {
//		try {
//			this.run(sqlpath, start, limit, params);
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return this.getArrayData();
//	}

	public String getArrData(String sqlpath,String[][] params) {
			this.run(sqlpath, params);
		    return this.getArrayData();
	}
	public String getPlanarArrData(String sqlpath,String[] params) {
		this.run(sqlpath, params);
		return this.getArrayData();
	}
	
//	public String getArrData(String sqlpath) {
//		try {
//			this.run(sqlpath);
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return this.getArrayData();
//	}
	
	/**
	 *获取session的属性值 
	 */
	public String getSession(HttpSession ss,String sessionName){
		String s = ss.getAttribute(sessionName)+"";
		return s;
    }
}
