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

package com.gbshape.dbe.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.FieldBean;
import com.gbshape.dbe.struts.bean.MessageBean;
import com.gbshape.dbe.struts.bean.SelectResultBean;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.utils.IteratorException;
import com.gbshape.dbe.utils.ValueListHandler;

public final class Select {

    private Select(){}

	private static Logger log = Logger.getLogger(Select.class.getName());

	public static SelectResultBean executeQuery(DBDataBean dataBean, String query) {
		SelectResultBean result = new SelectResultBean();

		ArrayList messages = new ArrayList();
		messages.add(new MessageBean(query));

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		    ResultSet rs=stmt.executeQuery(query);

		    ResultSetMetaData rmd = rs.getMetaData ();
		    int columnCount = rmd.getColumnCount();

		    ColumnBean[] columnBeans = new ColumnBean[columnCount];

		    for(int i=1;i <=columnCount;i++){
	            /*out.println (rmd.getCatalogName(i));
	            out.println(rmd.getColumnName(i));
	            out.println(rmd.getColumnClassName(i));
	            out.println(rmd.getColumnDisplaySize(i));
	            out.println(rmd.getColumnType(i));
	            out.println(rmd.getColumnTypeName(i));
	            out.println(rmd.isSearchable(i));
	            out.println(rmd.isNullable(i));
	            out.println("<br>");*/
		    	ColumnBean columnBean = new ColumnBean();
		    	String columnName = rmd.getColumnName(i);
		    	if(StringUtils.contains(columnName, "(")) {
		    		columnName = "EXPR"+i;
		    	}
				columnBean.setName(columnName);
		    	columnBean.setClassName(rmd.getColumnClassName(i));
		    	columnBean.setDataType(rmd.getColumnTypeName(i));
		    	int j = i-1;
		    	columnBeans[j] = columnBean;
	        }

		    result.setColumnBeans(columnBeans);

		    ArrayList list = new ArrayList();

		    while(rs.next()){
		    	FieldBean[] fieldBeans = new FieldBean[columnCount];
		    	for(int i=1;i <=columnCount;i++){
		    		FieldBean fieldBean = new FieldBean();
		    		try {
		    			if(columnBeans[(i-1)].getDataType().toLowerCase().indexOf("blob") == -1) {
		    				fieldBean.setValue(rs.getString(i));
		    			} else {
		    				//int blobLength = rs.getBinaryStream(i).length;
		    				fieldBean.setValue("[BLOB]");
		    			}
		    		} catch (SQLException e) {
		    			log.error(e.getMessage(), e);
		    			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
		    		}
		    		fieldBean.setClassName(rmd.getColumnClassName(i));
		    		fieldBean.setSize(rmd.getColumnDisplaySize(i));
		    		int j = i-1;
		    		fieldBeans[j] = fieldBean;
		        }

		    	list.add(fieldBeans);
		    }

		    ValueListHandler valueListHandler = new ValueListHandler();
		    valueListHandler.setList(list);

			messages.add(new MessageBean(list.size()+ " results"));

		    result.setValueListHandler(valueListHandler);

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
			result.setErrorMessage(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
			result.setErrorMessage(e.getMessage());
		} catch (IteratorException e) {
			log.error(e.getMessage(), e);
			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
			result.setErrorMessage(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
					messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
					result.setErrorMessage(e.getMessage());
				}
			}
		}

		result.setMessages(messages);

		return result;
	}
}
