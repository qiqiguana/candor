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
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public class CreateTableAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String tableName = request.getParameter("tableName");

        String nl = request.getParameter("numLine");
        if(nl == null) {
        	nl ="0";
        }
		int numLine = Integer.parseInt(nl);

        ArrayList columns = new ArrayList(numLine);
        for(int i=0; i< numLine; i++) {
        	ColumnBean columnBean = new ColumnBean();
        	String name = request.getParameter("name_"+i);
        	//if(StringUtils.isNotEmpty(name)) {
        		columnBean.setName(name);
        		columnBean.setDataType(request.getParameter("dataType_"+i));
        		String dim = request.getParameter("dim_"+i);
        		if(StringUtils.isNotEmpty(dim)) {
	        		String[] dims = StringUtils.split(dim, ",");
	        		if(dims.length>0 && StringUtils.isNumeric(dims[0])) {
	        			columnBean.setLen(Integer.parseInt(dims[0]));
	        		}
	        		if(dims.length>1 && StringUtils.isNumeric(dims[1])) {
	        			columnBean.setDec(Integer.parseInt(dims[1]));
	        		}
        		}
        		columnBean.setDim(dim);
        		columnBean.setCodeType(request.getParameter("codeType_"+i));

        		String notNull = request.getParameter("notnull_"+i);
        		if(StringUtils.isNotEmpty(notNull) && notNull.equalsIgnoreCase("false")) {
        			columnBean.setNullable(true);
        		}

        		columnBean.setDefaultValue(request.getParameter("defaultValue_"+i));
        		columnBean.setComment(request.getParameter("comment_"+i));
        		columnBean.setExtra(request.getParameter("extra_"+i));

        		String constraint = request.getParameter("constraint_"+i);
        		if(StringUtils.isNotEmpty(constraint)) {
        			if(constraint.equalsIgnoreCase("primary")) {
        				columnBean.setKey(true);
        			}
        			if(constraint.equalsIgnoreCase("unique")) {
        				columnBean.setUnique(true);
        			}
        			if(constraint.equalsIgnoreCase("index")) {
        				columnBean.setIndex(true);
        			}
        		}

        		columns.add(columnBean);
        	//}
        }

        CreateTableXmlBean createTableXmlBean = new CreateTableXmlBean();
        createTableXmlBean.setName(tableName);
        createTableXmlBean.setComment(request.getParameter("tableComment"));
        createTableXmlBean.setColumns(columns);

        String tm = request.getParameter("tm");
        if(StringUtils.isNotEmpty(tm)) {
        	request.setAttribute("tm",tm);
        }

        String add = request.getParameter("add");
        String check = request.getParameter("check");
        if(StringUtils.isNotEmpty(add)) {
        	ColumnBean columnBean = new ColumnBean();
        	createTableXmlBean.getColumns().add(columnBean);
        	request.setAttribute("createTableXmlBean",createTableXmlBean);
        	return mapping.findForward("add");
        } else if(StringUtils.isNotEmpty(check)){
        	TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
	        ArrayList querys = tableStructure.create(request, dataBean, createTableXmlBean);
	        request.setAttribute("querys",querys);
	        request.setAttribute("createTableXmlBean",createTableXmlBean);
	        return mapping.findForward("querys");
        } else {
	        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
	        ArrayList querys = tableStructure.create(request, dataBean, createTableXmlBean);

	        StringBuffer errorMessages = new StringBuffer("");

	        for(int i=0; i < querys.size(); i++) {
	    		String query = (String) querys.get(i);
	    		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
	        	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	        	errorMessages.append(nonSelectResultBean.getErrorMessage());
	    	}
	        //String errorMessage = tableStructure.create(request, dataBean, createTableXmlBean);

	        if(StringUtils.isNotEmpty(errorMessages.toString())) {
	        	request.setAttribute("createTableXmlBean",createTableXmlBean);
	        	request.setAttribute("errorMessage",errorMessages.toString());
	        	return mapping.findForward("error");
	        } else {
	        	return mapping.findForward("done");
	        }
        }
    }
}
