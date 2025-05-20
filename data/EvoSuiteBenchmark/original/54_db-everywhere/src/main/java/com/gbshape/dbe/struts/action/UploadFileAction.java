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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;
import org.apache.struts.upload.FormFile;

import au.com.bytecode.opencsv.CSVReader;

import com.gbshape.dbe.importdata.ImportTask;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.form.UploadFileForm;

public class UploadFileAction extends ForwardAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

		UploadFileForm myForm = (UploadFileForm) form;

		// Process the FormFile
		FormFile myFile = myForm.getFile();
		/*String contentType = myFile.getContentType();
		String fileName = myFile.getFileName();
		int fileSize = myFile.getFileSize();
		byte[] fileData = myFile.getFileData();*/

		ArrayList datas = new ArrayList();

		String separator = request.getParameter("separator");
		if(StringUtils.isEmpty(separator)){
			separator= ";";
		}

		String quotechar= request.getParameter("quotechar");
		if(StringUtils.isEmpty(quotechar)){
			quotechar= "\"";
		}

		char sep = separator.charAt(0);
		char quote = quotechar.charAt(0);

		InputStream inputStream = myFile.getInputStream();
		CSVReader reader = new CSVReader(new InputStreamReader(inputStream),sep,quote);
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			datas.add(nextLine);
		}

		String importSelect = request.getParameter("importSelect");

		try {
			Class cl = Class.forName(importSelect);
			ImportTask importTask = (ImportTask) cl.newInstance();
			importTask.execute(request, dataBean, datas, request.getParameterMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("importDone");
	}
}
