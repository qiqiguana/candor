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

package com.gbshape.dbe.sapdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gbshape.dbe.idb.ViewStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.ViewColumnBean;
import com.gbshape.dbe.struts.bean.ViewStatusBean;
import com.gbshape.dbe.struts.bean.ViewStructureBean;
import com.gbshape.dbe.utils.DBEHelper;

public class SapdbViewStructure implements ViewStructure{

	private static Logger log = Logger.getLogger(SapdbViewStructure.class.getName());

	public ViewStructureBean getStructure(DBDataBean dataBean, String viewName) {
		ViewStructureBean viewStructureBean = new ViewStructureBean();
		viewStructureBean.setViewName(viewName);

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
			String query = "select DEFINITION from VIEWDEFS where viewname = '"+viewName+"' ";
		    ResultSet rsDef=stmt.executeQuery(query);

		    while(rsDef.next()){
		    	String definition = rsDef.getString("DEFINITION");
		    	viewStructureBean.setDefinition(definition);
		    }

		    ResultSet rs=stmt.executeQuery("select * from viewcolumns where viewname='"+viewName+"'");

		    ArrayList columns = new ArrayList();

		    while(rs.next()){
		    	ViewColumnBean viewColumnBean = new ViewColumnBean();
		    	viewColumnBean.setName(rs.getString("VIEWCOLUMNNAME"));
		    	viewColumnBean.setTableName(rs.getString("TABLENAME"));
		    	viewColumnBean.setColumnName(rs.getString("COLUMNNAME"));

		        columns.add(viewColumnBean);
		    }

		    viewStructureBean.setColumns(columns);


		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}


		return viewStructureBean;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "DROP VIEW \""+tableName+"\" ";
        NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public String create(HttpServletRequest request,DBDataBean dataBean, String viewName, String query) {
		String createQuery ="CREATE VIEW "+viewName+" AS "+query;

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, createQuery);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

		return nonSelectResultBean.getErrorMessage();
	}

	public ViewStatusBean getStatus(DBDataBean dataBean, String name) {
		ViewStatusBean viewStatusBean = new ViewStatusBean();
		viewStatusBean.setName(name);
		return viewStatusBean;
	}

	public ArrayList getDefinition(DBDataBean dataBean, String name) {
		ArrayList querys = new ArrayList();

    	return querys;
	}

	public boolean isView(DBDataBean dataBean, String viewName) {
		boolean isView = false;
		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		    ResultSet rs=stmt.executeQuery("select count(*) c from tables where type = 'VIEW' and TABLENAME = '"+viewName+"' ");

		    while(rs.next()){
		    	int count = rs.getInt("c");
		    	if(count > 0 ) {
		    		isView = true;
		    	}
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
		return isView;
	}

}
