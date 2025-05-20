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

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import au.com.bytecode.opencsv.CSVWriter;

import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.Select;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.FieldBean;
import com.gbshape.dbe.struts.bean.SelectResultBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public class ExportDataAction extends ForwardAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String tm = request.getParameter("tm");
		String type = request.getParameter("type");
		String file = request.getParameter("file");
		if (StringUtils.isEmpty(type)) {
			type = "csv";
		}
		request.setAttribute("type", type);

		String tableName = request.getParameter("tableName");

		String query = request.getParameter("query");
		if (StringUtils.isEmpty(query)) {
			query = (String) session.getAttribute("query_"+tm);
		}

		if (StringUtils.isEmpty(query)) {
			query = "SELECT * FROM " + tableName;
		} else {
			query = query.trim();
		}

		String separator = request.getParameter("separator");
		if (StringUtils.isEmpty(separator)) {
			separator = ",";
		}
		String enclosed = request.getParameter("enclosed");
		if (StringUtils.isEmpty(enclosed)) {
			enclosed = "\"";
		}
		String escaped = request.getParameter("escaped");
		if (StringUtils.isEmpty(escaped)) {
			escaped = "\"";
		}
		String endLine = request.getParameter("add_character");
		if (StringUtils.isEmpty(endLine)) {
			endLine = "\n";
		}
		String showcsvnames = request.getParameter("showcsvnames");
		if (StringUtils.isEmpty(showcsvnames)) {
			showcsvnames = "no";
		}

		endLine = StringUtils.replace(endLine, "\\n", "\n");
		endLine = StringUtils.replace(endLine, "\\r", "\r");


		if (StringUtils.isNotEmpty(query)) {

			DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
			TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());

			if (query.toUpperCase().startsWith("SELECT") || query.toUpperCase().startsWith("SHOW") || query.toUpperCase().startsWith("EXPLAIN")) {

				SelectResultBean queryResult = (SelectResultBean) session.getAttribute("queryResult_" + tm);

				String data = request.getParameter("data");

				if (queryResult == null && !(data == null && type.equals("sql"))) {
					queryResult = Select.executeQuery(dataBean, query);
					ArrayList messages = queryResult.getMessages();
					DBEHelper.setLogs(request, messages);
				}

				ArrayList export = null;

				if (queryResult != null) {

					if (queryResult.getValueListHandler() == null) {
						request.setAttribute("errorMessage", queryResult.getErrorMessage());
						return mapping.findForward("noResult");
					}

					Collection resultList = queryResult.getValueListHandler().getList();
					int resultSize = resultList.size() + 10;
					export = new ArrayList(resultSize);
					if (type.equals("csv")) {
						Iterator iterator = resultList.iterator();

						if (showcsvnames.equals("yes")) {
							ColumnBean[] columnBeans = queryResult.getColumnBeans();
							String[] values = new String[columnBeans.length];
							for (int j = 0; j < columnBeans.length; j++) {
								ColumnBean columnBean = columnBeans[j];
								values[j] = columnBean.getName();
							}
							export.add(values);
						}

						while (iterator.hasNext()) {
							FieldBean[] fieldBeans = (FieldBean[]) iterator.next();
							String[] values = new String[fieldBeans.length];
							for (int j = 0; j < fieldBeans.length; j++) {
								FieldBean fieldBean = fieldBeans[j];
								String val = fieldBean.getValue();
								values[j] = val;
							}
							export.add(values);
						}
					} else if (type.equals("sql") && StringUtils.isNotEmpty(tableName)) {
						Iterator iterator = resultList.iterator();

						TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);

						String structure = request.getParameter("structure");
						if(structure != null) {
							TableStatusBean tableStatusBean = tableStructure.getStatus(dataBean, tableName, true);
							CreateTableXmlBean createTableXmlBean = new CreateTableXmlBean();
					        createTableXmlBean.setColumns(tableStructureBean.getColumns());
					        createTableXmlBean.setName(tableStructureBean.getTableName());
					        createTableXmlBean.setComment(tableStatusBean.getComment());
					        ArrayList querys = tableStructure.create(request, dataBean, createTableXmlBean);
					        for (int i = 0; i < querys.size(); i++) {
					        	String line = (String) querys.get(i);
					        	export.add(line+";");
					        }
					        export.add(" ");
						}

						while (iterator.hasNext()) {
							FieldBean[] fieldBeans = (FieldBean[]) iterator.next();
							String[] values = new String[fieldBeans.length];
							for (int j = 0; j < fieldBeans.length; j++) {
								FieldBean fieldBean = fieldBeans[j];
								String val = fieldBean.getValue();
								values[j] = val;
							}

							String insert = DBEHelper.createInsertQuery(values, dataBean, tableName, tableStructureBean, ";");

							export.add(insert);
						}
					}


				}

				if (file == null) {

					request.setAttribute("query", query);
					request.setAttribute("export", export);

					request.setAttribute("separator", separator);
					request.setAttribute("enclosed", enclosed);
					request.setAttribute("escaped", escaped);
					request.setAttribute("endLine", endLine);

					return mapping.findForward("showList");
				} else {
					String fileName = request.getParameter("fileName");

					ServletOutputStream out = response.getOutputStream();
					response.setContentType("application/text");
					response.setHeader("Content-disposition", "filename=\"" + fileName + "\"");

					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);

					if (type.equals("csv")) {

						CSVWriter writer = new CSVWriter(outputStreamWriter, CharUtils.toChar(separator), CharUtils.toChar(enclosed), CharUtils.toChar(escaped), endLine);
						writer.writeAll(export);
						writer.close();

					} else if (type.equals("sql") && StringUtils.isNotEmpty(tableName)) {
						if(export == null) {
							export = new ArrayList();
						}

						int size = export.size();
						for (int i = 0; i < size; i++) {
							String line = (String) export.get(i);
							out.println(line);
						}
					}

					return null;
				}
			}
		}

		return mapping.findForward("noResult");
	}

}
