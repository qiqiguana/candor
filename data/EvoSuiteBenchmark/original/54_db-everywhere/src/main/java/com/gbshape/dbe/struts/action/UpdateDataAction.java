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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;

public class UpdateDataAction extends Action {

	private static Logger log = Logger.getLogger(UpdateDataAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (log.isInfoEnabled()) {
			log.info("UpdateDataAction");
		}

		String tableName = request.getParameter("table");
		String data = request.getParameter("data");

		if (log.isInfoEnabled()) {
			log.info("tableName=" + tableName);
			log.info("data=" + data);
		}

		// parse JSON data String
		// {"_oData":{"VALUE":"1.60","ID":"EURO"},"_nId":9}

		//"_sId":"6","_oData":{"VALUE":"CH","ID":"DEFAULT_COUNTRY"}

		//String oData = "\"_oData\":{";

		//data = data.substring((data.indexOf(oData)+oData.length()));
		//data = data.substring(0, data.indexOf("}"));

		HashMap keyValueMap = DBEHelper.parseJSON(data);

		StringBuffer query = new StringBuffer("UPDATE " + tableName + " SET ");
		StringBuffer setClause = new StringBuffer("");
		StringBuffer where = new StringBuffer("");

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);
		ArrayList columns = tableStructureBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (keyValueMap.containsKey(columnBean.getName())) {
				String value = (String) keyValueMap.get(columnBean.getName());
				//value = StringUtils.replace(value, "'", DBEHelper.getApostropheWrapper(dataBean));
				if (columnBean.isKey()) {
					if (!where.toString().equals("")) {
						where.append(" AND ");
					}
					where.append(columnBean.getName());
					DBEHelper.appendValueNoDecodeAjax(dataBean, where, columnBean, value);
				} else {
					if (!setClause.toString().equals("")) {
						setClause.append(" , ");
					}
					setClause.append(columnBean.getName());
					DBEHelper.appendValueNoDecodeAjax(dataBean, setClause, columnBean, value);
				}
			}
		}

		query.append(setClause).append(" WHERE ").append(where);

		String queryString = query.toString();

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, queryString);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

		return mapping.findForward("none");

	}


}
