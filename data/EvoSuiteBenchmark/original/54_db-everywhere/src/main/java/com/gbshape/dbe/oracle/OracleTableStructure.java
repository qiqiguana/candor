/*
Copyright 2007 DB-Everywhere
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.gbshape.dbe.oracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.ConstraintBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.struts.form.ColumnForm;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public class OracleTableStructure implements TableStructure{


	private static Logger log = Logger.getLogger(OracleTableStructure.class.getName());

	public TableStructureBean getStructure(DBDataBean dataBean, String tableName) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		TableStructureBean tableStructureBean = new TableStructureBean();
		tableStructureBean.setTableName(tableName);

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			//get PrimaryKeys
			Statement stmt= connection.createStatement();

		    String query = "SELECT alc.constraint_name, CASE alc.constraint_type "+
	        " WHEN 'P' THEN 'PRIMARY KEY' "+
	        " WHEN 'R' THEN 'FOREIGN KEY' "+
	        " WHEN 'U' THEN 'UNIQUE' "+
	        " WHEN 'C' THEN 'CHECK' "+
	        " END \"constraint_type\", "+
	        " alc.DELETE_RULE \"on_delete\", "+
	        " CASE alc.deferrable WHEN 'NOT DEFERRABLE' THEN 0 ELSE 1 END \"deferrable\", "+
	        " CASE alc.deferred WHEN 'IMMEDIATE' THEN 1 ELSE 0 END \"initially_deferred\", "+
	        " alc.search_condition, "+
	        " alc.table_name, "+
	        " cols.column_name, "+
	        " cols.position, "+
	        " r_alc.table_name \"references_table\", "+
	        " r_cols.column_name \"references_field\", "+
	        " r_cols.position \"references_field_position\" "+
	        " FROM all_cons_columns cols "+
	        " LEFT JOIN all_constraints alc "+
	        " ON alc.constraint_name = cols.constraint_name "+
	        " AND alc.owner = cols.owner "+
	        " LEFT JOIN all_constraints r_alc "+
	        " ON alc.r_constraint_name = r_alc.constraint_name "+
	        " AND alc.r_owner = r_alc.owner "+
	        " LEFT JOIN all_cons_columns r_cols "+
	        " ON r_alc.constraint_name = r_cols.constraint_name "+
	        " AND r_alc.owner = r_cols.owner "+
	        " AND cols.position = r_cols.position "+
	        " WHERE alc.constraint_name = cols.constraint_name and alc.TABLE_NAME = '"+tableName+"' ";

		    //System.out.println(query);
		    ResultSet rs=stmt.executeQuery(query);

		    HashMap indexMap = new HashMap();
		    while(rs.next()){
		    	String columnName = rs.getString("COLUMN_NAME");
		    	String type = rs.getString("constraint_type");
		    	ArrayList constraintList = new ArrayList();
		    	if(indexMap.containsKey(columnName)){
		    		constraintList = (ArrayList)  indexMap.get(columnName);
		    	}
		    	ConstraintBean constraintBean = new  ConstraintBean();
		    	constraintBean.setConstraintType(type);
		    	if(type.equals("PRIMARY KEY")) {
		    		constraintBean.setKey(true);
		    	} else if(type.equals("UNIQUE")){
		    		constraintBean.setUnique(true);
        		}
		    	constraintBean.setConstraintName(rs.getString("CONSTRAINT_NAME"));
		    	constraintBean.setColumnName(columnName);
		    	constraintList.add(constraintBean);
		    	indexMap.put(columnName, constraintList);
		    }
		    stmt.close();

			stmt= connection.createStatement();
		    rs=stmt.executeQuery("Select * from USER_TAB_COLUMNS where TABLE_NAME = '"+tableName+"' order by COLUMN_ID ");

		    ArrayList columns = new ArrayList();

		    while(rs.next()){
		    	ColumnBean columnBean = new ColumnBean();
		    	String columnName = rs.getString("COLUMN_NAME");
		        columnBean.setName(columnName);
		        columnBean.setDataType(rs.getString("DATA_TYPE"));
		        columnBean.setOriginalDT(rs.getString("DATA_TYPE"));
		        columnBean.setLen(rs.getInt("DATA_PRECISION"));
		        columnBean.setDec(rs.getInt("DATA_SCALE"));
		        if(!rs.getString("DATA_TYPE").equalsIgnoreCase("NUMBER")){
		        	columnBean.setLen(rs.getInt("DATA_LENGTH"));
		        }
		        columnBean.setMode(rs.getString("DATA_TYPE_MOD"));
		        columnBean.setDefaultValue(rs.getString("DATA_DEFAULT"));

		        //columnBean.setKey(rs.getString("DATA_TYPE_MOD").equalsIgnoreCase("KEY"));
		        //columnBean.setCodeType(rs.getString("CODETYPE"));
		        boolean nullable = rs.getString("NULLABLE").equalsIgnoreCase("YES");
				columnBean.setNullable(nullable);
		        //columnBean.setComment(rs.getString("COMMENT"));

				if(indexMap.containsKey(columnName)){
		        	ArrayList constraintList = (ArrayList) indexMap.get(columnName);
		        	ArrayList cl = columnBean.getConstraintList();
		        	if(cl == null) {
		        		cl = new ArrayList();
		        	}
		        	cl.addAll(constraintList);
		        	columnBean.setConstraintList(cl);
		        	for(int j=0;j<constraintList.size();j++){
		        		ConstraintBean constraintBean = (ConstraintBean) constraintList.get(j);
		        		if(constraintBean.isIndex()){
		        			columnBean.setIndex(true);
		        		}
		        		if(constraintBean.isUnique()){
		        			columnBean.setUnique(true);
		        		}
		        		if(constraintBean.isKey()){
		        			columnBean.setKey(true);
		        		}
		        	}
		        }

		        columns.add(columnBean);
		    }

		    tableStructureBean.setColumns(columns);


		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		stopWatch.stop();

		return tableStructureBean;
	}

	public TableStatusBean getStatus(DBDataBean dataBean, String tableName, boolean showTableStatus) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		//select BYTES from user_segments where segment_name = 'PLAN_TABLE'
		//select  object_name, created, TIMESTAMP  from user_objects where object_type='TABLE'

		TableStatusBean tableStatusBean = new TableStatusBean();
		tableStatusBean.setTableName(tableName);

		if(showTableStatus) {
			Connection connection = null;
			try {
				connection = DBEHelper.getConnection(dataBean);

				Statement stmt= connection.createStatement();
			    String query = "select BYTES, CREATED, LAST_DDL_TIME, c.NUM_ROWS from user_segments,user_objects,all_all_tables c where c.table_name = '"+tableName+"' and object_name = '"+tableName+"' and segment_name(+) = object_name and object_type='TABLE' ";

			    //System.out.println(query);
			    ResultSet rs=stmt.executeQuery(query);

			    while(rs.next()){
	//		    	tableStatusBean.setEngine(rs.getString("Engine"));
	//		    	tableStatusBean.setVersion(rs.getString("version"));
	//		    	tableStatusBean.setRowFormat(rs.getString("row_format"));
	//		    	tableStatusBean.setRows(rs.getString("rowcount"));
	//		    	tableStatusBean.setAvgRowLength(rs.getString("avg_row_length"));
			    	double usedSize = rs.getDouble("BYTES")/1024;
			    	tableStatusBean.setDataLength(new BigDecimal(usedSize));
			    	tableStatusBean.setTotalLength(new BigDecimal(usedSize));
	//		    	tableStatusBean.setMaxDataLength(rs.getString("max_data_length"));
	//		    	tableStatusBean.setIndexLength(rs.getString("index_length"));
	//		    	tableStatusBean.setDataFree(rs.getString("data_free"));
	//		    	tableStatusBean.setAutoIncrement(rs.getString("auto_increment"));

			    	tableStatusBean.setCreateTime(DBEHelper.getCalendar(rs, "CREATED"));
			    	tableStatusBean.setUpdateTime(DBEHelper.getCalendar(rs, "LAST_DDL_TIME"));

	//		    	tableStatusBean.setCollation(rs.getString("collation"));

			    	tableStatusBean.setRows(rs.getString("NUM_ROWS"));

			    }

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		stopWatch.stop();


		return tableStatusBean;
	}

	public ArrayList alterModify(HttpServletRequest request,DBDataBean dataBean, String tableName, ColumnBean columnBean, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName().toUpperCase();

		//		 DATATYPE
        String dataType = columnForm.getDataType();
		StringBuffer alterQuery = new StringBuffer("");
    	String dim = columnForm.getDim();
    	if(dataType.equals("VARCHAR") || dataType.equals("CHAR") || dataType.equals("VARCHAR2") || dataType.equals("CHAR2")) {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" MODIFY  (\""+name+"\" "+dataType+"("+dim+") ");
    	} else if(dataType.equals("NUMBER")) {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" MODIFY  (\""+name+"\" "+dataType+"("+dim+") ");
    	} else {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" MODIFY  (\""+name+"\" "+dataType+" ");
    	}

//    	 DEFAULTVALUE
        if(columnForm.getDefaultValue() == null) { columnForm.setDefaultValue(""); }
        if(StringUtils.isNotEmpty(columnForm.getDefaultValue())) {
        	String value = ""+columnForm.getDefaultValue().trim()+"";
        	if(columnForm.getDefaultValue().equals("null")) {
        		value = "null";
        	}
        	if(!value.equals("")) {
        		alterQuery.append(" DEFAULT "+value+" ");
        	}
        }

    	if(columnForm.getNotnull().equals("true")) {
    		alterQuery.append(" NOT NULL ");
		}

    	alterQuery.append(" ) ");

    	querys.add(alterQuery.toString().toUpperCase());


        //COMMENT
        /*if(columnForm.getComment() == null) { columnForm.setComment(""); }
        if(StringUtils.isNotEmpty(columnForm.getComment())) {
        	String value = "'"+columnForm.getComment()+"'";
        	if(columnForm.getComment().equals("null")) {
        		value = "null";
        	}
        	String alterQueryComment = "COMMENT ON COLUMN \""+tableName+"\".\""+name+"\" IS "+value+" ";
        	//NonSelectResultBean nonSelectResultBean2 = NonSelect.executeQuery(dataBean, alterQueryComment);
        	//DBEHelper.setLogs(request, nonSelectResultBean2.getMessages());
        	querys.add(alterQueryComment);
        }*/

        return querys;
	}

	public ArrayList alterAdd(HttpServletRequest request,DBDataBean dataBean, String tableName, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName().toUpperCase();

		//		 DATATYPE
        String dataType = columnForm.getDataType();
		StringBuffer alterQuery = new StringBuffer("");
    	String dim = columnForm.getDim();
    	if(dataType.equals("VARCHAR") || dataType.equals("CHAR") || dataType.equals("VARCHAR2") || dataType.equals("CHAR2")) {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" ADD (\""+name+"\" "+dataType+"("+dim+") ");
    	} else if(dataType.equals("NUMBER")) {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" ADD (\""+name+"\" "+dataType+"("+dim+") ");
    	} else {
    		alterQuery.append("ALTER TABLE \""+tableName+"\" ADD (\""+name+"\" "+dataType+" ");
    	}

//    	 DEFAULTVALUE
        if(columnForm.getDefaultValue() == null) { columnForm.setDefaultValue(""); }
        if(StringUtils.isNotEmpty(columnForm.getDefaultValue())) {
        	String value = "'"+columnForm.getDefaultValue().trim()+"'";
        	if(columnForm.getDefaultValue().equals("null")) {
        		value = "null";
        	}
        	if(!value.equals("''")) {
        		alterQuery.append(" DEFAULT "+value+" ");
        	}
        }

    	if(columnForm.getNotnull().equals("true")) {
    		alterQuery.append(" NOT NULL ");
		}

    	alterQuery.append(" ) ");

    	querys.add(alterQuery.toString().toUpperCase());


        //COMMENT
        /*if(columnForm.getComment() == null) { columnForm.setComment(""); }
        if(StringUtils.isNotEmpty(columnForm.getComment())) {
        	String value = "'"+columnForm.getComment()+"'";
        	if(columnForm.getComment().equals("null")) {
        		value = "null";
        	}
        	String alterQueryComment = "COMMENT ON COLUMN \""+tableName+"\".\""+name+"\" IS "+value+" ";
        	//NonSelectResultBean nonSelectResultBean2 = NonSelect.executeQuery(dataBean, alterQueryComment);
        	//DBEHelper.setLogs(request, nonSelectResultBean2.getMessages());
        	querys.add(alterQueryComment);
        }*/

        return querys;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "DROP TABLE \""+tableName+"\" ";
        NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void truncate(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "TRUNCATE TABLE \""+tableName+"\" ";
        NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void alterDrop(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String alterQuery = new String("ALTER TABLE \""+tableName+"\" DROP COLUMN \""+field+"\" ");
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, alterQuery);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public ArrayList create(HttpServletRequest request, DBDataBean dataBean, CreateTableXmlBean createTableXmlBean) {

		//put all in uppercase
		String tableName = createTableXmlBean.getName();
		tableName = tableName.toUpperCase();

		ArrayList querys = new ArrayList();

		StringBuffer query = new StringBuffer("CREATE TABLE \""+tableName+"\" (");

		StringBuffer primary = new StringBuffer("");
		//StringBuffer index = new StringBuffer("");
		ArrayList unique = new ArrayList();
		int uniqueNb =1;
		ArrayList comments = new ArrayList();

		ArrayList columns = createTableXmlBean.getColumns();
		for(int i=0;i < columns.size();i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(columnBean != null) {
				String name = columnBean.getName();
				if(StringUtils.isNotEmpty(name)) {
					if(i>0) {
						query.append(",");
					}
					query.append("\""+name+"\" "+columnBean.getDataType()+" ");
					if(columnBean.getLen() > 0) {
						query.append(" (");
						query.append(columnBean.getLen());
						if(columnBean.getDec() > 0) {
							query.append(",");
							query.append(columnBean.getDec());
						}
						query.append(") ");
					}

					if(StringUtils.isNotEmpty(columnBean.getDefaultValue())) {
						if(columnBean.getDataType().equalsIgnoreCase("BOOLEAN")) {
							query.append(" DEFAULT "+columnBean.getDefaultValue()+" ");
						} else {
							query.append(" DEFAULT '"+columnBean.getDefaultValue()+"' ");
						}
					}

					if(columnBean.isNullable()) {
						//query.append(" NULL ");
					} else {
						query.append(" NOT NULL ");
					}

					if(StringUtils.isNotEmpty(columnBean.getComment())) {
						//comments.add("COMMENT ON COLUMN \""+tableName+"\".\""+name+"\" IS '"+columnBean.getComment()+"' ");
					}
					if(columnBean.isUnique()) {
						String cn = getConstraintName( dataBean,  tableName,  name, "U");
						if(StringUtils.isEmpty(cn)) {
							cn = tableName+"_UK"+uniqueNb;
						}
						String uniqueQuery = "alter table \""+tableName+"\" add constraint "+cn+" unique (\""+columnBean.getName()+"\") ";
						unique.add(uniqueQuery.toUpperCase());
						uniqueNb++;
					}
					if(columnBean.isKey()) {
						if(StringUtils.isNotEmpty(primary.toString())) {
							primary.append(",");
						}
						primary.append("\""+name+"\"");
					}
				}
			}
		}

		query.append(")");

		querys.add(query.toString().toUpperCase());

		if(StringUtils.isNotEmpty(primary.toString())) {
			StringBuffer primaryQuery = new StringBuffer("ALTER TABLE \"");
			primaryQuery.append(tableName);
			primaryQuery.append("\" ADD CONSTRAINT \"");

			primaryQuery.append(tableName); //TODO find the constraint name !!!!!!!!!!!
			primaryQuery.append("_PK\" PRIMARY KEY (");
			primaryQuery.append(primary);
			primaryQuery.append(" )");

			querys.add(primaryQuery.toString().toUpperCase());
		}

		if(StringUtils.isNotEmpty(createTableXmlBean.getComment())) {
			comments.add("COMMENT ON TABLE \""+tableName.toUpperCase()+"\" IS '"+createTableXmlBean.getComment().toUpperCase()+"' ");
		}

		querys.addAll(comments);
		querys.addAll(unique);

    	return querys;
	}

	public void dropUnique(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE \""+tableName+"\" DROP CONSTRAINT "+keyName+" ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropIndex(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("DROP INDEX "+keyName+" ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropPrimary(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE \""+tableName+"\" DROP PRIMARY KEY ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addUnique(HttpServletRequest request,DBDataBean dataBean, String tableName, String field) {
		String query = new String("ALTER TABLE \""+tableName+"\" ADD CONSTRAINT "+tableName+"_"+field+"_UK UNIQUE ("+field+") ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addIndex(HttpServletRequest request,DBDataBean dataBean, String tableName, String field) {

	}

	public void changePrimary(HttpServletRequest request,DBDataBean dataBean, String tableName, ArrayList columns) {
		dropPrimary(request, dataBean,  tableName, "", "");

		StringBuffer keys = new StringBuffer("");
		for(int i=0;i<columns.size();i++){
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(columnBean.isKey()) {
				if(StringUtils.isNotEmpty(keys.toString())) {
					keys.append(",");
				}
				keys.append("\""+columnBean.getName()+"\"");
			}
		}

		String query = "ALTER TABLE \""+tableName+"\" ADD CONSTRAINT "+tableName+"_PK PRIMARY KEY ("+keys.toString()+") ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	private String getConstraintName(DBDataBean dataBean, String tableName, String columnName, String type) {
		String cn = "";

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			//get PrimaryKeys
			Statement stmt= connection.createStatement();

		    String query = "SELECT alc.constraint_name, CASE alc.constraint_type "+
	        " WHEN 'P' THEN 'PRIMARY KEY' "+
	        " WHEN 'R' THEN 'FOREIGN KEY' "+
	        " WHEN 'U' THEN 'UNIQUE' "+
	        " WHEN 'C' THEN 'CHECK' "+
	        " END \"constraint_type\", "+
	        " alc.DELETE_RULE \"on_delete\", "+
	        " CASE alc.deferrable WHEN 'NOT DEFERRABLE' THEN 0 ELSE 1 END \"deferrable\", "+
	        " CASE alc.deferred WHEN 'IMMEDIATE' THEN 1 ELSE 0 END \"initially_deferred\", "+
	        " alc.search_condition, "+
	        " alc.table_name, "+
	        " cols.column_name, "+
	        " cols.position, "+
	        " r_alc.table_name \"references_table\", "+
	        " r_cols.column_name \"references_field\", "+
	        " r_cols.position \"references_field_position\" "+
	        " FROM all_cons_columns cols "+
	        " LEFT JOIN all_constraints alc "+
	        " ON alc.constraint_name = cols.constraint_name "+
	        " AND alc.owner = cols.owner "+
	        " LEFT JOIN all_constraints r_alc "+
	        " ON alc.r_constraint_name = r_alc.constraint_name "+
	        " AND alc.r_owner = r_alc.owner "+
	        " LEFT JOIN all_cons_columns r_cols "+
	        " ON r_alc.constraint_name = r_cols.constraint_name "+
	        " AND r_alc.owner = r_cols.owner "+
	        " AND cols.position = r_cols.position "+
	        " WHERE alc.constraint_name = cols.constraint_name and alc.TABLE_NAME = '"+tableName+"' and cols.column_name = '"+columnName+"' and alc.constraint_type =  '"+type+"' ";

		    ResultSet rs=stmt.executeQuery(query);

		    while(rs.next()){
		    	cn = rs.getString("CONSTRAINT_NAME");
		    }
		    stmt.close();


		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}


		return cn;
	}


}
