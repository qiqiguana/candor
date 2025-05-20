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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.MessageBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.utils.DBEHelper;

public final class NonSelect {

    private NonSelect(){}

	private static Logger log = Logger.getLogger(Select.class.getName());

	public static NonSelectResultBean executeQuery(DBDataBean dataBean, String query) {
		int rowCount = 0;
		Connection connection = null;

		NonSelectResultBean nonSelectResultBean = new NonSelectResultBean();

		ArrayList messages = new ArrayList();
		messages.add(new MessageBean(query));

		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();
		    rowCount = stmt.executeUpdate(query);

		    nonSelectResultBean.setRowCount(rowCount);

		    messages.add(new MessageBean(String.valueOf(rowCount)));

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
			nonSelectResultBean.setErrorMessage(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
			nonSelectResultBean.setErrorMessage(e.getMessage());
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
					messages.add(new MessageBean(e.getMessage(),MessageBean.ERROR_TYPE));
					nonSelectResultBean.setErrorMessage(e.getMessage());
				}
			}
		}

		nonSelectResultBean.setMessages(messages);

		return nonSelectResultBean;
	}
}
