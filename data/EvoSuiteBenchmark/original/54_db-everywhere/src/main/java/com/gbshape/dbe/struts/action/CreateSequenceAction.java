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

import com.gbshape.dbe.factory.SequenceStructureFactory;
import com.gbshape.dbe.idb.SequenceStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.form.SequenceForm;
import com.gbshape.dbe.utils.DBEHelper;

public class CreateSequenceAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

        String tm = request.getParameter("tm");
        request.setAttribute("tm", tm);

        String check = request.getParameter("check");
        SequenceForm sequenceForm = (SequenceForm) form;
        request.setAttribute("sequenceForm", sequenceForm);

        if(StringUtils.isNotEmpty(check)){
        	SequenceStructure sequenceStructure = SequenceStructureFactory.getInstance(dataBean.getDbType());
	        ArrayList querys = sequenceStructure.create(request, dataBean, sequenceForm);
	        request.setAttribute("querys",querys);
	        return mapping.findForward("querys");
        }  else {
        	SequenceStructure sequenceStructure = SequenceStructureFactory.getInstance(dataBean.getDbType());
	        ArrayList querys = sequenceStructure.create(request, dataBean, sequenceForm);

	        StringBuffer errorMessages = new StringBuffer("");

	        for(int i=0; i < querys.size(); i++) {
	    		String query = (String) querys.get(i);
	    		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
	        	DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	        	errorMessages.append(nonSelectResultBean.getErrorMessage());
	    	}

	        if(StringUtils.isNotEmpty(errorMessages.toString())) {
	        	request.setAttribute("errorMessage",errorMessages.toString());
	        	return mapping.findForward("error");
	        } else {
	        	return mapping.findForward("done");
	        }
        }


    }
}
