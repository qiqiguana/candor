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

package com.gbshape.dbe.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.gbshape.dbe.idb.TableList;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.TableBean;
import com.gbshape.dbe.utils.DBEHelper;

public class MysqlTableList implements TableList {

	public ArrayList getTableList(DBDataBean dataBean) {

		ArrayList tableList = new ArrayList();
		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		    //ResultSet rs=stmt.executeQuery("SHOW TABLES ;");

			ResultSet rsStatus=stmt.executeQuery("SHOW table status ");

			HashMap views = new HashMap();
		    while(rsStatus.next()){
		    	String tableName = rsStatus.getString("Name");
		    	String comment = rsStatus.getString("Comment");
		    	if(comment.equals("VIEW")) {
		    		views.put(tableName, tableName);
		    	}
		    }

		    ResultSet rs=stmt.executeQuery("SHOW TABLES ");

		    while(rs.next()){
		    	TableBean tableBean = new TableBean();
		        String tableName = rs.getString(1);
		        if(views.containsKey(tableName)) {
		        	tableBean.setView(true);
		        }

		        tableBean.setName(tableName);
		        tableList.add(tableBean);
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


		return tableList;
	}

}
