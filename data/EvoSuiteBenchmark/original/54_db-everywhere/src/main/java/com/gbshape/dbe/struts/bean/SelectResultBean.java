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

package com.gbshape.dbe.struts.bean;

import com.gbshape.dbe.utils.ValueListHandler;

public class SelectResultBean extends DBEBean implements java.io.Serializable{

	private String table = "";

	private ColumnBean[] columnBeans =null;

	private ValueListHandler valueListHandler= null;

	public ColumnBean[] getColumnBeans() {
		return columnBeans;
	}

	public void setColumnBeans(ColumnBean[] columnBeans) {
		this.columnBeans = columnBeans;
	}

	public ValueListHandler getValueListHandler() {
		return valueListHandler;
	}

	public void setValueListHandler(ValueListHandler valueListHandler) {
		this.valueListHandler = valueListHandler;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
