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

package com.gbshape.dbe.struts.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;

public class DeleteAction extends ForwardAction  {

	private static Logger log = Logger.getLogger(UpdateDataAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String tableName = request.getParameter("table");

        String data = request.getParameter("data");

		if (log.isInfoEnabled()) {
			log.info("tableName=" + tableName);
			log.info("data=" + data);
		}

		// parse JSON data String
		// {"FOLLOW":"null","URGENT":"N","READ":"N","MESSAGE":"Bonjour , voici la nouvelle version de l intranet avec une fonction POST-IT et une possiblite d envoyer des FAX, a bientot","FROM":"GB","TO":"JK","TEMPS":"2005-08-28 16:35:20.000000"}

		data = data.substring(("{".length()));
		data = data.substring(0, data.indexOf("}"));

		HashMap keyValueMap = DBEHelper.parseJSON(data);

		StringBuffer query = new StringBuffer("DELETE FROM " + tableName + " WHERE ");
		StringBuffer where = new StringBuffer("");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);
		ArrayList columns = tableStructureBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (columnBean.isKey()) {
				String value = (String) keyValueMap.get(columnBean.getName());
				if (!where.toString().equals("")) {
					where.append(" AND ");
				}
				where.append(columnBean.getName());
				DBEHelper.appendValue(dataBean, where, columnBean, value);
			}
		}
		query.append(where);

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

        return mapping.findForward("none");

    }
}
