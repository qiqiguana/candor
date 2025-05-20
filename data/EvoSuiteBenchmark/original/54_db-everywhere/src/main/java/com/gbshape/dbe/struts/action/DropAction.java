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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.factory.SequenceStructureFactory;
import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.factory.ViewStructureFactory;
import com.gbshape.dbe.idb.SequenceStructure;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.idb.ViewStructure;
import com.gbshape.dbe.struts.bean.DBDataBean;

public class DropAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
        String tableName = request.getParameter("table");
        String viewName = request.getParameter("view");
        String seq = request.getParameter("seq");

        if(StringUtils.isNotEmpty(tableName)) {
        	TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
        	tableStructure.drop(request, dataBean, tableName);
        }

        if(StringUtils.isNotEmpty(viewName)) {
        	ViewStructure viewStructure = ViewStructureFactory.getInstance(dataBean.getDbType());
        	viewStructure.drop(request, dataBean, viewName);
        }

        if(StringUtils.isNotEmpty(seq)) {
        	SequenceStructure sequenceStructure = SequenceStructureFactory.getInstance(dataBean.getDbType());
        	sequenceStructure.drop(request, dataBean, seq);
        }

        return mapping.findForward("none");

    }
}
