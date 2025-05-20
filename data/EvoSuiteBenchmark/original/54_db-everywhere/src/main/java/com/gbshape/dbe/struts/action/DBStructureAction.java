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
import com.gbshape.dbe.factory.TableListFactory;
import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.factory.ViewStructureFactory;
import com.gbshape.dbe.idb.SequenceStructure;
import com.gbshape.dbe.idb.TableList;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.idb.ViewStructure;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.TableBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.ViewStatusBean;
import com.gbshape.dbe.utils.DBEHelper;

public class DBStructureAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

        TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
        TableList tableList = TableListFactory.getInstance(dataBean.getDbType());
        ArrayList tArrayList = tableList.getTableList(dataBean);
        int size = tArrayList.size();
		ArrayList list = new ArrayList(size);
		boolean showTableStatus = DBEHelper.showTableStatus(dataBean);
        for(int i=0; i < size; i++){
        	TableBean tableBean = (TableBean) tArrayList.get(i);
        	if(StringUtils.isNotEmpty(tableBean.getName()) && !tableBean.isView()){
        		TableStatusBean tableStatusBean = tableStructure.getStatus(dataBean, tableBean.getName(), showTableStatus);
        		list.add(tableStatusBean);
        	}
        }

        request.setAttribute("tableList", list);

        ViewStructure viewStructure = ViewStructureFactory.getInstance(dataBean.getDbType());
        ArrayList viewList = new ArrayList(size);
        for(int i=0; i < size; i++){
        	TableBean tableBean = (TableBean) tArrayList.get(i);
        	if(StringUtils.isNotEmpty(tableBean.getName()) && tableBean.isView()){
        		ViewStatusBean viewStatusBean = viewStructure.getStatus(dataBean, tableBean.getName());
        		viewList.add(viewStatusBean);
        	}
        }

        request.setAttribute("viewList", viewList);
        //System.out.println("-------------"+viewList.size());

        SequenceStructure sequenceStructure = SequenceStructureFactory.getInstance(dataBean.getDbType());
        ArrayList sequenceList = sequenceStructure.getList(dataBean);

        request.setAttribute("sequenceList", sequenceList);

        String tm = request.getParameter("tm");
        request.setAttribute("tm", tm);
        return mapping.findForward("show_structure");

    }
}
