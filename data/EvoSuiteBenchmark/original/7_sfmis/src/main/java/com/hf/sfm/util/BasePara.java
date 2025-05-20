package com.hf.sfm.util;

public class BasePara {

	private String sqlpath;
	private int start;
	private int limit;
	private String sort;
	private String dir;
	private String[] queryparams;
	private String[][] arr2params;
	private String ordersql;
	private String groupsql;
	private String sql;
	private boolean paging=false;
	private String queryValue;
	private String querySql;
	
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	public String getSqlpath() {
		return sqlpath;
	}
	public void setSqlpath(String sqlpath) {
		this.sqlpath = sqlpath;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String[] getQueryparams() {
		return queryparams;
	}
	public void setQueryparams(String[] queryparams) {
		this.queryparams = queryparams;
	}
	public String[][] getArr2params() {
		return arr2params;
	}
	public void setArr2params(String[][] arr2params) {
		this.arr2params = arr2params;
	}
	public String getOrdersql() {
		return ordersql;
	}
	public void setOrdersql(String ordersql) {
		this.ordersql = ordersql;
	}
	public String getGroupsql() {
		return groupsql;
	}
	public void setGroupsql(String groupsql) {
		this.groupsql = groupsql;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public boolean isPaging(){
		return this.paging;
	}
	
	public String[][] single2plannar(){
		String[][] planarArr = null;
		try {
			if(queryparams==null){ return null;}
			if(queryparams.length%2>0){
				try {
					throw new OddParamsOfArrayInLoader("Loader加载数据时，所传进来的参数为奇数个！");
				} catch (OddParamsOfArrayInLoader e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				planarArr = new String[2][queryparams.length/2];
				for(int i=0,j=0;i<queryparams.length;i=i+2,j++){
					planarArr[0][j]=queryparams[i];
					planarArr[1][j]=queryparams[i+1];
				}
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return planarArr;
	}
	
}
