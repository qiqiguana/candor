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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;

public class InsertRowAction extends ForwardAction  {

	//private static Logger log = Logger.getLogger(InsertRowAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	//DBEUtils.dumpRequest(request);

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String tableName = request.getParameter("table");
        String tm = request.getParameter("tm");

        request.setAttribute("tm", tm);
        request.setAttribute("table", tableName);

        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);
		request.setAttribute("tableStructureBean", tableStructureBean);

		StringBuffer query = DBEHelper.createInsertQuery(request, dataBean, tableName, tableStructureBean);

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

		request.setAttribute("rowCount", String.valueOf(nonSelectResultBean.getRowCount()));

        return mapping.findForward("form");
    }
}
