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

package com.gbshape.dbe.sqlserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gbshape.dbe.idb.SequenceStructure;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.SequenceStructureBean;
import com.gbshape.dbe.struts.form.SequenceForm;
import com.gbshape.dbe.utils.DBEHelper;

public class SqlserverSequenceStructure implements SequenceStructure{

	private static Logger log = Logger.getLogger(SqlserverSequenceStructure.class.getName());

	public ArrayList getList(DBDataBean dataBean) {
		ArrayList sequences = new ArrayList();

		/*Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();

			ResultSet rs=stmt.executeQuery("SHOW TABLE STATUS ");

		    while(rs.next()){
		    	String name = rs.getString("NAME");
		        String auto = rs.getString("Auto_Increment");
		        if(auto != null) {
		        	SequenceStructureBean sequenceStructureBean = new SequenceStructureBean();
			        sequenceStructureBean.setName(name);
			        sequenceStructureBean.setMinValue(1);
			        sequenceStructureBean.setMaxValue("");
			        sequenceStructureBean.setIncrementBy(1);
			        sequenceStructureBean.setLastNumber(rs.getInt("Auto_Increment"));

			        sequences.add(sequenceStructureBean);
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
		}*/

		return sequences;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String sequence) {
		// TODO Auto-generated method stub
	}

	public ArrayList create(HttpServletRequest request,DBDataBean dataBean, SequenceForm sequenceForm) {
		return null;
	}


}
