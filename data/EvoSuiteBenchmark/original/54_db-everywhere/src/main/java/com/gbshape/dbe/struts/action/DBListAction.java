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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.factory.DataBaseListFactory;
import com.gbshape.dbe.idb.DataBaseList;
import com.gbshape.dbe.struts.bean.DBDataBean;

public class DBListAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

        DataBaseList dataBaseList = DataBaseListFactory.getInstance(dataBean.getDbType());
        ArrayList tArrayList = dataBaseList.getDataBaseList(dataBean);

        request.setAttribute("dataBaseList", tArrayList);

        return mapping.findForward("showList");

    }
}
