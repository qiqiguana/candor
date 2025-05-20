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
import org.apache.struts.actions.DispatchAction;

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.struts.form.ColumnForm;
import com.gbshape.dbe.utils.DBEHelper;

public class AlterTableAction extends DispatchAction {

	//private static Logger log = Logger.getLogger(AlterTableAction.class.getName());

	public ActionForward drop(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String field = request.getParameter("field");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.alterDrop(request, dataBean, tableName, field);

		return mapping.findForward("none");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String check = request.getParameter("check");

        String tableName = request.getParameter("table");

        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
        TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);

        ColumnForm columnForm = (ColumnForm) form;

        String name = columnForm.getName();

        ColumnBean columnBean = null;
        ArrayList columns = tableStructureBean.getColumns();
        for (int i = 0; i < columns.size(); i++) {
        	ColumnBean cb = (ColumnBean) columns.get(i);
        	if(name.equals(cb.getName())) {
        		columnBean = cb;
        		break;
        	}
		}

        //tableStructure.alterModify(request, dataBean, tableName, columnBean, columnForm);
        ArrayList querys = tableStructure.alterModify(request, dataBean, tableName, columnBean, columnForm);

        if(StringUtils.isEmpty(check)){
	        StringBuffer errorMessages = new StringBuffer("");

	        for(int i=0; i < querys.size(); i++) {
	    		String query = (String) querys.get(i);
	    		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
	        	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	        	errorMessages.append(nonSelectResultBean.getErrorMessage());
	    	}

	        return mapping.findForward("done");
        } else {
        	request.setAttribute("querys",querys);
        	return mapping.findForward("sql");
        }
    }

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String check = request.getParameter("check");

        String tableName = request.getParameter("table");

        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
        //TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);

        ColumnForm columnForm = (ColumnForm) form;

        //String name = columnForm.getName();

        //tableStructure.alterAdd(request, dataBean, tableName, columnForm);
        ArrayList querys = tableStructure.alterAdd(request, dataBean, tableName, columnForm);

        if(StringUtils.isEmpty(check)){
	        StringBuffer errorMessages = new StringBuffer("");

	        for(int i=0; i < querys.size(); i++) {
	    		String query = (String) querys.get(i);
	    		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
	        	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	        	errorMessages.append(nonSelectResultBean.getErrorMessage());
	    	}

	        return mapping.findForward("done");
        } else {
        	request.setAttribute("querys",querys);
        	return mapping.findForward("sql");
        }
    }

	public ActionForward dropIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String keyName = request.getParameter("keyName");
		String columnName = request.getParameter("columnName");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.dropIndex(request, dataBean, tableName, keyName,columnName);

		return mapping.findForward("done");
	}

	public ActionForward dropUnique(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String keyName = request.getParameter("keyName");
		String columnName = request.getParameter("columnName");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.dropUnique(request, dataBean, tableName, keyName,columnName);

		return mapping.findForward("done");
	}

	public ActionForward dropPrimary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String keyName = request.getParameter("keyName");
		String columnName = request.getParameter("columnName");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.dropPrimary(request, dataBean, tableName, keyName,columnName);

		return mapping.findForward("done");
	}

	public ActionForward unique(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String field = request.getParameter("field");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.addUnique(request, dataBean, tableName, field);

		return mapping.findForward("done");
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");
		String field = request.getParameter("field");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		tableStructure.addIndex(request, dataBean, tableName, field);

		return mapping.findForward("done");
	}

	public ActionForward changePrimary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		String tableName = request.getParameter("table");

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);
		ArrayList columns = tableStructureBean.getColumns();

		//DBEUtils.dumpRequest(request);

		for(int i=0;i<columns.size();i++){
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			String key = request.getParameter(columnBean.getName());
			if(StringUtils.isNotEmpty(key) && key.equals("1")) {
				columnBean.setKey(true);
			} else {
				columnBean.setKey(false);
			}
		}

		tableStructure.changePrimary(request, dataBean, tableName, columns);

		return mapping.findForward("done");
	}

}
