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

package com.gbshape.dbe.idb;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.struts.form.ColumnForm;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public interface TableStructure {

	public TableStructureBean getStructure(DBDataBean dataBean, String tableName);

	public TableStatusBean getStatus(DBDataBean dataBean, String tableName, boolean showTableStatus);

	public ArrayList alterModify(HttpServletRequest request,DBDataBean dataBean, String tableName, ColumnBean columnBean, ColumnForm columnForm);

	public void drop(HttpServletRequest request,DBDataBean dataBean, String tableName);

	public void truncate(HttpServletRequest request,DBDataBean dataBean, String tableName);

	public void alterDrop(HttpServletRequest request,DBDataBean dataBean, String tableName, String field);

	public ArrayList alterAdd(HttpServletRequest request,DBDataBean dataBean, String tableName, ColumnForm columnForm);

	public ArrayList create(HttpServletRequest request,DBDataBean dataBean, CreateTableXmlBean createTableXmlBean);

	public void dropUnique(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName);

	public void dropIndex(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName);

	public void dropPrimary(HttpServletRequest request,DBDataBean dataBean, String tableName, String keyName, String columnName);

	public void addUnique(HttpServletRequest request,DBDataBean dataBean, String tableName, String field);

	public void addIndex(HttpServletRequest request,DBDataBean dataBean, String tableName, String field);

	public void changePrimary(HttpServletRequest request,DBDataBean dataBean, String tableName, ArrayList columns);
}
