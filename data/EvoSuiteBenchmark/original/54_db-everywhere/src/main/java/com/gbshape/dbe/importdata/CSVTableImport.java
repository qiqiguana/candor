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

package com.gbshape.dbe.importdata;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.gbshape.dbe.factory.TableListFactory;
import com.gbshape.dbe.factory.TableStructureFactory;
import com.gbshape.dbe.idb.TableList;
import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.utils.DBEUtils;

public class CSVTableImport implements ImportTask{

	public String getDescription(){
		return "Simple csv import for one table";
	}

	public int getDataType(){
		return ImportTask.CSV_DATATYPE;
	}

	public ArrayList getParameters(HttpServletRequest request){
		ArrayList parameters = new ArrayList();

		ArrayList values = new ArrayList();
		HttpSession session = request.getSession();
		DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");

        TableList tableList = TableListFactory.getInstance(dataBean.getDbType());
        ArrayList tArrayList = tableList.getTableList(dataBean);

        int size = tArrayList.size();
		for (int i = 0; i < size; i++) {
        	TableBean tableBean = (TableBean) tArrayList.get(i);
        	if(!tableBean.isView()){
        		values.add(tableBean.getName());
        	}
		}

		ParameterBean tables = new ParameterBean();
		tables.setTranslatedLabel(true);
		tables.setLabel("import.param.tables");
		tables.setName("tableName");
		tables.setValues(values);
		tables.setType(ImportTask.SELECT_TYPE);

		parameters.add(tables);

		return parameters;
	}

	public String execute(HttpServletRequest request,DBDataBean dataBean, ArrayList datas, Map parameters){

		DBEUtils.dumpRequest(request);

		String tableName = "";
		if(parameters != null) {
			String[] params = (String[]) parameters.get("tableName");
			tableName = (params)[0];
		}
		if(StringUtils.isEmpty(tableName)) {
			tableName = request.getParameter("tableName");
		}

		TableStructure tableStructure = TableStructureFactory.getInstance(dataBean.getDbType());
		TableStructureBean tableStructureBean = tableStructure.getStructure(dataBean, tableName);

		int size = datas.size();
		for (int j = 0; j < size; j++) {

			String[] data = (String[]) datas.get(j);

			StringBuffer query = new StringBuffer("INSERT INTO " + tableName + " (");
			ArrayList columns = tableStructureBean.getColumns();
			for (int i = 0; i < columns.size(); i++) {
				ColumnBean columnBean = (ColumnBean) columns.get(i);
				if(i >0) { query.append(","); }
				query.append(columnBean.getName());
			}
			query.append(") VALUES (");

			for (int i = 0; i < columns.size(); i++) {
				if(i >0) { query.append(","); }
				ColumnBean columnBean = (ColumnBean) columns.get(i);
				String value = data[i];
				DBEHelper.appendValuesNoDecodeAjax(dataBean,query,columnBean, value);
			}

			query.append(") ");

			NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query.toString());
			//DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
			if(StringUtils.isNotEmpty(nonSelectResultBean.getErrorMessage())) {
				DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
			}

		}

		return null;
	}

}
