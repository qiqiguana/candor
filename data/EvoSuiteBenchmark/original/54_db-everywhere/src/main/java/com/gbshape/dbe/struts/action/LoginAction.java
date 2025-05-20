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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.gbshape.dbe.factory.DataBaseFactory;
import com.gbshape.dbe.idb.DataBase;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.MessageBean;
import com.gbshape.dbe.struts.form.DBDataForm;
import com.gbshape.dbe.utils.DBEHelper;

public class LoginAction extends ForwardAction  {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession();

        DBDataForm dataForm = (DBDataForm) form;
        DBDataBean dataBean = new  DBDataBean();

        BeanUtils.copyProperties(dataBean, dataForm);

        String jdbcDriver = DBEHelper.getJDBCDriver(dataBean);
        dataBean.setDriver(jdbcDriver);

        String connectionStartURL = DBEHelper.getConnectionStartURL(dataBean);
        if(!dataBean.getUrl().startsWith(connectionStartURL)){
        	dataBean.setUrl(connectionStartURL+dataBean.getUrl());
        }

        session.setAttribute("DBDataBean", dataBean);

        Locale locale = new Locale(dataBean.getLanguage().toLowerCase());
        session.setAttribute("org.apache.struts.action.LOCALE", locale);
		session.setAttribute("language", dataBean.getLanguage().toLowerCase());

		DataBase dataBase = DataBaseFactory.getInstance(dataBean.getDbType());
        String loginTest = dataBase.testLogin(dataBean);

        if(StringUtils.isEmpty(loginTest)) {
        	DBEHelper.setLogs(request, new MessageBean("Login succesfull"));
        	return mapping.findForward("start");
        } else {
        	request.setAttribute("error", loginTest);
        	return mapping.findForward("back");
        }

    }
}
