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

import org.apache.commons.lang.StringUtils;

import com.gbshape.dbe.idb.SequenceStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.SequenceStructureBean;
import com.gbshape.dbe.struts.form.SequenceForm;
import com.gbshape.dbe.utils.DBEHelper;

public class SapdbSequenceStructure implements SequenceStructure{

	//private static Logger log = Logger.getLogger(SapdbSequenceStructure.class.getName());

	public ArrayList getList(DBDataBean dataBean) {
		ArrayList sequences = new ArrayList();

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt= connection.createStatement();

			ResultSet rs=stmt.executeQuery("SELECT * FROM SEQUENCES ");

		    while(rs.next()){
		    	SequenceStructureBean sequenceStructureBean = new SequenceStructureBean();
		        String name = rs.getString("SEQUENCE_NAME");
		        sequenceStructureBean.setName(name);
		        sequenceStructureBean.setMinValue(rs.getInt("MIN_VALUE"));
		        sequenceStructureBean.setMaxValue(rs.getString("MAX_VALUE"));
		        sequenceStructureBean.setIncrementBy(rs.getInt("INCREMENT_BY"));
		        sequenceStructureBean.setLastNumber(rs.getInt("LAST_NUMBER"));

		        sequences.add(sequenceStructureBean);
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

		return sequences;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String sequence) {
		String query = "DROP SEQUENCE "+sequence+" ";

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
    	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public ArrayList create(HttpServletRequest request,DBDataBean dataBean, SequenceForm sequenceForm) {
		ArrayList querys = new ArrayList();

		StringBuffer query = new StringBuffer("CREATE SEQUENCE \"");
		String name = sequenceForm.getName();
		if(StringUtils.isNotEmpty(name)) {
			name = name.toUpperCase();
		}
		query.append(name);
		query.append("\" ");
		query.append(" INCREMENT BY ");
		query.append(sequenceForm.getIncrement());

		if(StringUtils.isNotEmpty(sequenceForm.getStart())) {
			query.append(" START WITH ");
			query.append(sequenceForm.getStart());
		}

		if(StringUtils.isEmpty(sequenceForm.getMin())) {
			query.append(" NOMINVALUE ");
		} else {
			query.append(" MINVALUE ");
			query.append(sequenceForm.getMin());
		}

		if(StringUtils.isEmpty(sequenceForm.getMax())) {
			query.append(" NOMAXVALUE ");
		} else {
			query.append(" MAXVALUE ");
			query.append(sequenceForm.getMax());
		}

		if(sequenceForm.getCycle().equalsIgnoreCase("no")) {
			query.append(" NOCYCLE ");
		} else {
			query.append(" CYCLE ");
		}

		if(StringUtils.isNotEmpty(sequenceForm.getCache())) {
			query.append(" CACHE ");
			query.append(sequenceForm.getCache());
		}

		querys.add(query.toString());

		if(StringUtils.isNotEmpty(sequenceForm.getComment())) {
			String queryComment = "COMMENT ON SEQUENCE \""+name+"\" IS '"+sequenceForm.getComment()+"' ";
			querys.add(queryComment);
		}

		return querys;
	}
}
