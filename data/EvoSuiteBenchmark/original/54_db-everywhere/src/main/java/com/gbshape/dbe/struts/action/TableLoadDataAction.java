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

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.factory.ViewStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.idb.ViewStructure;
import com.gbshape.dbe.sql.Select;
import com.gbshape.dbe.sql.SimpleCount;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.SelectResultBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;

public class TableLoadDataAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();

    	DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

    	String tableName = request.getParameter("table");

    	//DBEUtils.dumpRequest(request);

    	String tm = request.getParameter("tm");
    	String where = request.getParameter("where");
    	String orderby = request.getParameter("orderby");
    	String select = request.getParameter("selectCols");
    	String nbDisplay = request.getParameter("nbDisplay");
    	if(StringUtils.isNotEmpty(nbDisplay) && StringUtils.isNumeric(nbDisplay)) {
    		session.setAttribute("nbDisplay_"+tm,nbDisplay);
    	}

    	if(StringUtils.isEmpty(select)){
    		select = (String) session.getAttribute("select_"+tm);
    	}

    	SelectResultBean queryResult = null;

    	String whereSession = (String) session.getAttribute("where_"+tm);
    	String orderBySession = (String) session.getAttribute("orderby_"+tm);

        if(whereSession != null || orderBySession !=  null) {

	    	if(StringUtils.isEmpty(where)){
	    		where = whereSession;
	    	}
	    	if(where != null) {
	    		where = where.trim();
	    		where = DBEHelper.decodeAjax(dataBean, where);
	    	}

	    	if(StringUtils.isEmpty(orderby)){
	    		orderby = orderBySession;
	    	}

	        String query = "SELECT "+select+" FROM "+tableName;
	        if(StringUtils.isNotEmpty(where)){
	        	query = query + " WHERE "+ where;
	        }
	        if(StringUtils.isNotEmpty(orderby)){
	        	query = query + " ORDER BY "+ orderby;
	        }

	        queryResult = Select.executeQuery(dataBean, query);

	        session.setAttribute("query_"+tm, query);

        } else {
        	queryResult = new SelectResultBean();
        	where = "";
        }
        session.setAttribute("queryResult_"+tm, queryResult);
        request.setAttribute("tm", tm);
        request.setAttribute("table", tableName);
        session.setAttribute("where_"+tm, where);
        session.setAttribute("orderby_"+tm, orderby);

        request.setAttribute("sortable", "false"); //TODO change it later
        request.setAttribute("editable", "true");

        //retreive the numbers of Rows in the table
        String countRequest = "SELECT COUNT(*) NB FROM "+tableName;
        int count = SimpleCount.executeQuery(dataBean,countRequest);
        request.setAttribute("nbRows", new Integer(count));

        // get table structure
        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
        TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);
        ArrayList columns = tableStructureBean.getColumns();
        request.setAttribute("columns", columns);

        // check if is view
        ViewStructure viewStructure = ViewStructureFactory.getInstance(dataBean.getDbType());
        boolean isView = viewStructure.isView(dataBean, tableName);
        if(isView) {
        	request.setAttribute("editable", "false");
        }


        if(StringUtils.isEmpty(select)) {
        	select = "";
			for(int i=0;i < columns.size();i++) {
				if(i>0) {
					select += ", ";
				}
				select += ((ColumnBean) columns.get(i)).getName();
			}
		}
        session.setAttribute("select_"+tm, select);

        //add informations about columns
        ColumnBean[] columnBeans = queryResult.getColumnBeans();
        if(columnBeans != null) {
	        for (int i = 0; i < columns.size(); i++) {
	        	ColumnBean columnBean = (ColumnBean) columns.get(i);
	        	for (int j = 0; j < columnBeans.length; j++) {
					if(columnBeans[j].getName().equals(columnBean.getName())) {
						columnBeans[j].setKey(columnBean.isKey());
						columnBeans[j].setDataType(columnBean.getDataType());
					}
				}
			}
        }

		//queryResult.setColumnBeans((ColumnBean[]) columns.toArray(new ColumnBean[columns.size()]));

		queryResult.setTable(tableName);

        DBEHelper.setLogs(request, queryResult.getMessages());

		return mapping.findForward("showList");

    }
}
