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
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.utils.DBEHelper;

public final class SimpleCount {

    private SimpleCount(){}

	private static Logger log = Logger.getLogger(SimpleCount.class.getName());

	public static int executeQuery(DBDataBean dataBean, String query) {
		int result = 0;

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		    ResultSet rs=stmt.executeQuery(query);

		    while(rs.next()){
		    	result = rs.getInt(1);
		    }

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

		return result;
	}
}
