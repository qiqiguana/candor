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

import com.gbshape.dbe.idb.DataBase;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.utils.DBEHelper;

public class MysqlDataBase implements DataBase {

	private static final String LOGIN_QUERY = "show databases";

	public String testLogin(DBDataBean dataBean) {

		String test = "";

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		   ResultSet rs=stmt.executeQuery(LOGIN_QUERY);

		    while(rs.next()){
		    	String res = rs.getString(1);
		    }

		} catch (ClassNotFoundException e) {
			test=e.getMessage();
		} catch (SQLException e) {
			test=e.getMessage();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					test=e.getMessage();
				}
			}
		}

		return test;
	}

}
