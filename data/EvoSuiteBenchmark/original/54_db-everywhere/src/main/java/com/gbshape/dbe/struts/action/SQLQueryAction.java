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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.sql.Select;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.SelectResultBean;
import com.gbshape.dbe.utils.DBEHelper;

public class SQLQueryAction extends ForwardAction  {

    private static final String NO_RESULT = "noResult";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();

        String tm = request.getParameter("tm");
        String nbDisplay = request.getParameter("nbDisplay");
    	if(StringUtils.isNotEmpty(nbDisplay) && StringUtils.isNumeric(nbDisplay)) {
    		session.setAttribute("nbDisplay_"+tm,nbDisplay);
    	}

        String query = request.getParameter("query");
        if(StringUtils.isEmpty(query)){
          query = "";
        } else {
          query = query.trim();
        }
        query = StringUtils.replace(query,"\n"," ");

        if(StringUtils.isNotEmpty(query)) {

          //add the query into the sqlList
          ArrayList sqlList = (ArrayList) session.getAttribute("SQL_LIST");
          if(sqlList == null){
            sqlList = new ArrayList();
          }
          sqlList.add(query);
          //limit querys to 30 in memory
          while(sqlList.size() > 30){
            sqlList.remove(0);
          }
          session.setAttribute("SQL_LIST", sqlList);

        	String sqlDelimiter = request.getParameter("sqlDelimiter");
        	if(StringUtils.isEmpty(sqlDelimiter)) {
        		sqlDelimiter = "###########"; // no sqlDelimiter, simulate an impossible one
        	}

        	DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        	query = DBEHelper.decodeAjax(dataBean,query);

        	String[] querys = StringUtils.split(query, sqlDelimiter);

        	String action = "";
        	for (int i = 0; i < querys.length; i++) {
				String q = querys[i];
				action = executeRequest(mapping, request, session, tm, q, dataBean);
				if(action.equals(NO_RESULT)) {
					break;
				}
			}

        	return mapping.findForward(action);
        }

        return mapping.findForward(NO_RESULT);
    }

	private String executeRequest(ActionMapping mapping, HttpServletRequest request, HttpSession session, String tm, String query, DBDataBean dataBean) {
		String action = NO_RESULT;

		if(query.toUpperCase().startsWith("SELECT") || query.toUpperCase().startsWith("SHOW") || query.toUpperCase().startsWith("EXPLAIN")) {
			SelectResultBean queryResult = Select.executeQuery(dataBean, query);
		    session.setAttribute("queryResult_"+tm, queryResult);
		    session.setAttribute("query_"+tm, query);
		    request.setAttribute("tm", tm);
		    request.setAttribute("createView", "ok");

		    ArrayList messages = queryResult.getMessages();
			DBEHelper.setLogs(request, messages);
			if(queryResult.getValueListHandler() == null) {
				request.setAttribute("errorMessage", queryResult.getErrorMessage());
				action = NO_RESULT;
		    }

			action = "showList";
		} else {
			NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);

			DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
		    int rowCount = nonSelectResultBean.getRowCount();
		    Integer rc = (Integer) request.getAttribute("rowCount");
		    if(rc != null) {
		    	rowCount = rowCount + rc.intValue();
		    }
			request.setAttribute("rowCount", new Integer(rowCount));
		    if(StringUtils.isNotEmpty(nonSelectResultBean.getErrorMessage())) {
				request.setAttribute("errorMessage", nonSelectResultBean.getErrorMessage());
				action = NO_RESULT;
		    } else {
		    	action = "showRowCount";
		    }
		}
		return action;
	}
}
