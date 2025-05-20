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

package com.gbshape.dbe.postgresql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gbshape.dbe.idb.DataBaseList;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.DataBaseBean;
import com.gbshape.dbe.utils.DBEHelper;

public class PostgresqlDataBaseList implements DataBaseList {

	public ArrayList getDataBaseList(DBDataBean dataBean) {

		ArrayList dbList = new ArrayList();
        Connection connection = null;
        try {
            connection = DBEHelper.getConnection(dataBean);

            Statement stmt= connection.createStatement();
            //ResultSet rs=stmt.executeQuery("SHOW TABLES ;");
            ResultSet rs=stmt.executeQuery("select datname from pg_database  where datname !~ '^template+' order by datname");

            while(rs.next()){
                DataBaseBean dbBean = new DataBaseBean();
                String tableName = rs.getString(1);

                dbBean.setName(tableName);
                dbList.add(dbBean);
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

        return dbList;
	}

}
