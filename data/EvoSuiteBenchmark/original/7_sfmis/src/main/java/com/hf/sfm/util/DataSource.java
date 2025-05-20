package com.hf.sfm.util;



import javax.servlet.http.HttpSession;



public class DataSource {

	private Loader loader = new Loader();
	

	public String getPlanarArrData(BasePara basePara) {
		loader.run(basePara);
		return loader.getArrayResults();
	}
	
	public ListRange getGridData(BasePara basePara){
//		paging=true;
//		System.out.println("sqlpath:"+sqlpath+",queryparams:"+queryparams.length+",paging:"+paging+",start:"+start+",limit:"+limit);
//		String[] commaArr = null;
//		String[] colonArr = null;
//		String queryParams = con.get("queryparams").toString().replace("{", "").replace("}", "");
//		if(queryParams.indexOf(",")>0){
//			commaArr = queryParams.split(",");
//			colonArr = new String[commaArr.length*2];
//			for(int i=0;i<commaArr.length;i++){
//				String[] temp = new String[2];
//				temp = commaArr[i].split(":");
//				for(int j=0;j<temp.length;j++){
//					colonArr[2*i+j] = temp[j];
//				}
//			}
//		}else{
//			colonArr = queryParams.split(":");
//		}
//		BasePara basePara = new BasePara();
//		basePara.setSqlpath(con.get("sqlpath").toString());
//		basePara.setPaging(Boolean.parseBoolean(con.get("paging").toString()));
//		basePara.setStart(Integer.parseInt(con.get("start").toString()));
//		basePara.setLimit(Integer.parseInt(con.get("limit").toString()));
//		basePara.setQueryparams(colonArr);
		System.out.println("sqlpath:"+basePara.getSqlpath()+"\n,paging:"+basePara.isPaging()+"\n,start:"+basePara.getStart()+"\n,limit:"+basePara.getLimit()+",\nsort:"+basePara.getSort()+",\ndir:"+basePara.getDir()+",\nquery:"+basePara.getQueryValue()+",\nquerySql:"+basePara.getQuerySql());
		loader.run(basePara);
		loader.collectToMap();
		System.out.println("********:"+loader.getRange().getData()+"\ntotalSize:"+loader.getRange().getTotalSize());
		return loader.getRange();
	}
	
	public ListRange getComboData(BasePara basePara){
		System.out.println("sqlpath:"+basePara.getSqlpath()+"\n,paging:"+basePara.isPaging()+"\n,start:"+basePara.getStart()+"\n,limit:"+basePara.getLimit()+",\nsort:"+basePara.getSort()+",\ndir:"+basePara.getDir()+",\nqueryValue:"+basePara.getQueryValue()+",\nquerySql:"+basePara.getQuerySql());
//		basePara.setQuerySql(" g.idno = ? or g.mark=? ");
		loader.run(basePara);
		loader.collectToMap("combo");
		System.out.println("********:"+loader.getRange().getData()+"\ntotalSize:"+loader.getRange().getTotalSize());
		return loader.getRange();
	}
	
	/**
	 *获取session的属性值 
	 */
	public String getSession(HttpSession ss,String sessionName){
		String s = ss.getAttribute(sessionName)+"";
		return s;
    }
	
	
	public static void main(String[] args) {
		DataSource data = new DataSource();
		BasePara base = new BasePara();
		Loader loader = new Loader();
		base.setSqlpath("personInfo//sel_all_group");
		base.setPaging(false);
		loader.run(base);
		loader.collectToMap("combo");
		ListRange rang = loader.getRange();
		System.out.println("********:"+loader.getRange().getData()+"\ntotalSize:"+loader.getRange().getTotalSize());
	}
}
