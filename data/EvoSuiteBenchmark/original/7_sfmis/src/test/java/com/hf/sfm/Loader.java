package com.hf.sfm.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.hf.sfm.util.HibernateSessionFactory;
import com.hf.sfm.util.OddParamsOfArrayInLoader;


public class Loader {
	private String metaData;
	private String arrayData;
	private String sql;
	private String filepath;
	private int start;
	private int limit;
	private int totalCount;
	private String[] colNames;
	private String[][] pas;
    private final String XMLPATH="sqlfolder/";
    private Session session = null;
    private Query query;
    private String searchsql="undefined";
    
	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	
	public String getArrayData() {
		return arrayData;
	}

	public void setArrayData(String arrayData) {
		this.arrayData = arrayData;
	}
	/**
	 *转换为JSON格式 
	 */
	public String toJSONString(){
		String rs = this.getArrayResults();
		if(rs.equals("")){return "{success:true,totalCount : "+this.totalCount+",rows:[]}";}
		else
		return "{success:true,totalCount : "+this.totalCount+",rows:"+rs+"}";
	}
	
	/**
	 *获取数组形式结果集 
	 */
	public String getArrayResults(){
		List rows = loadDataWithSql();
		if(rows.size()==0){return "";}
		String jsonstr="";
		for(Iterator<?> it = rows.iterator();it.hasNext();){
			Object[] obj=(Object[]) it.next();
			String constr = "{";
			for(int i=0;i<obj.length;i++){
				if(i==obj.length-1){constr+='"'+colNames[i]+'"'+":"+'"'+obj[i]+'"';}
				else{constr+='"'+colNames[i]+'"'+":"+'"'+obj[i]+'"'+",";}
			}
			constr+="}";
			jsonstr+=constr+",";
		}
		jsonstr = "["+jsonstr.substring(0, jsonstr.lastIndexOf(","))+"]";
		return jsonstr;
	}
	
	/**
	 *获取总记录数 
	 */
	public void getCount(){
		String subsql="";
		if(this.sql.lastIndexOf("group by")!=-1){
			subsql = this.sql.substring(this.sql.lastIndexOf("from"), this.sql.lastIndexOf("group by"));
		}else{
			subsql = this.sql.substring(this.sql.lastIndexOf("from"));
		}
		session = HibernateSessionFactory.currentSession();
		query = session.createSQLQuery("select count(1) "+subsql);
		if(pas!=null){
			query = this.getParams(query, pas);
		}
		totalCount = Integer.parseInt(query.uniqueResult()+"");
		HibernateSessionFactory.closeSession();
	}
	
	/**
	 *载入返回JSON格式数据
	 *@param sqlpath  加载grid所写的sql语句文件路径，形式：文件名//节点名，如：grid_person//person_info
	 *@param start 起始记录数
	 *@param limit 每页显示记录数
	 *@param params 接收的参数数据，二维数组，分别保存参数的数据和类型
	 */
	public void run(String sqlpath,int start,int limit,String[][] params,String searchsql){
	   filepath = sqlpath;
	   this.start=start;
	   this.limit=limit;
	   this.pas = params;
	   this.searchsql=searchsql;
	   this.parseXML();
	   this.getColsName();
	   this.getCount();
	   this.setMetaData(this.toJSONString());
	   this.setArrayData(this.getArrayResults());
	}
	
	public void run(String sqlpath,String[][] params,String searchsql){
		this.run(sqlpath, 0, 0, params,searchsql);
	}
	public void run(String sqlpath,String[][] params){
		this.run(sqlpath, 0, 0, params,searchsql);
	}
	
	
	public void run(String sqlpath,int start,int limit,String[] params,String searchsql){
		String[][] planarArr = null;
		try {
			if(params.length%2>0){
				try {
					throw new OddParamsOfArrayInLoader("Loader加载数据时，所传进来的参数为奇数个！");
				} catch (OddParamsOfArrayInLoader e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				planarArr = new String[2][params.length/2];
				for(int i=0,j=0;i<params.length;i=i+2,j++){
					planarArr[0][j]=params[i];
					planarArr[1][j]=params[i+1];
				}
				this.run(sqlpath, start, limit, planarArr,searchsql);
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(String sqlpath,String[] params,String searchsql){
		this.run(sqlpath, 0, 0, params,searchsql);
	}
	public void run(String sqlpath,String[] params){
		this.run(sqlpath, 0, 0, params,searchsql);
	}
	/**
	 *获取sql中的字段名集合 
	 */
	public void getColsName(){
		String colssql = this.sql.substring(this.sql.lastIndexOf("select")+6,this.sql.lastIndexOf("from"));
		String[] cols = colssql.split(",");
		colNames = new String[cols.length];
		for(int i=0;i<cols.length;i++){
			String[] subcols=new String[cols.length];
			if(cols[i].indexOf(" as ")>0){
				subcols = cols[i].split("as");
				colNames[i]=subcols[1].trim();
			}else{
				if(cols[i].indexOf(".")>0){
					colNames[i]=cols[i].substring(cols[i].indexOf(".")+1).trim();
				}else{
					colNames[i]=cols[i].trim();
				}
			}
		}
	}
	
	/**
	 *从xml解析sql 
	 *
	 */
	public void parseXML(){
		String pa = this.getClass().getResource("Loader.class").toString();
		String rootpath = pa.substring(pa.indexOf(":")+2, pa.lastIndexOf("classes")+8).replaceAll("%20", " ");
		String[] path = this.filepath.split("//");
		String filepath = rootpath+XMLPATH+path[0]+".xml";
		File file = new File(filepath);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			for(Iterator it = root.elementIterator();it.hasNext();){
				Element el = (Element) it.next();
				if(el.getName().equals(path[1])){
					sql = el.elementText("main_sql");
					break;
				}
			}
			System.out.println("^^^^^^^^^^^^^^:"+searchsql);
			if(!(searchsql.equals("undefined"))){
				System.out.println("************:"+searchsql);
				this.concatsql();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *连接查询sql 
	 */
	public void concatsql(){
		int groupIndex=0,orderIndex=0;
		String flag =" ";
		String consql = "(";
		String[] fields = searchsql.split(",");
		for(int i=0;i<fields.length;i++){
			if(i==fields.length-1){
				consql+=fields[i]+") ";
			}else{
				consql+=fields[i]+" or ";
			}
		}
		if(this.sql.indexOf("where")>0){
			flag = " and ";
		}else{
			flag = " ";
		}
		if((groupIndex=this.sql.indexOf("group by"))>0||(orderIndex=this.sql.indexOf("order by"))>0){
			 if(groupIndex>0){
				 this.sql=this.sql.substring(0, groupIndex)+flag+consql+this.sql.substring(groupIndex);
			 }else{
				 this.sql=this.sql.substring(0, orderIndex)+flag+consql+this.sql.substring(orderIndex);
			 }
		}else{
			this.sql+=flag+consql;
		}
	}
	/**
	 *查询数据 
	 */
	public List loadDataWithSql(){
		session = HibernateSessionFactory.currentSession();
		query = session.createSQLQuery(this.sql);
		if(pas!=null){
			query = this.getParams(query, pas);
		}
		if(limit>0){
		query.setFirstResult(start);
		query.setMaxResults(limit);
		}
		List rows = query.list();
		HibernateSessionFactory.closeSession();
		return rows;
	}
	
	/**
	 *接收参数 
	 */
	public Query getParams(Query query, String[][] params) {
		if (params != null) {
			String[] pValue = params[0];// 参数值
			String[] pType = params[1]; // 参数值类型
			for (int i = 0; i < pValue.length; i++) {
				if (pType[i].equals("String")) {
					query.setParameter(i, pValue[i]);
				} else if (pType[i].equals("Long")) {
					query.setParameter(i, Long.parseLong(pValue[i]));
				}else if(pType[i].equals("Integer")){
					query.setParameter(i, Integer.parseInt(pValue[i]));
				} else if (pType[i].equals("Double")) {
					query.setParameter(i, Double.parseDouble(pValue[i]));
				} else if (pType[i].equals("Date")) {
					try {
						query.setParameter(i,new SimpleDateFormat("yyyy-MM-dd").parse(pValue[i]));
					} catch (HibernateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return query;
	}
   public static void main(String[] args) {
	Loader loader = new Loader();
	loader.sql="select * from person where name=? order by id";
	loader.searchsql = "age=12,type='1'";
	loader.concatsql();
	System.out.println(loader.sql);
}
}
