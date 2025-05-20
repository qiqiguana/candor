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
package com.gbshape.dbe.xml;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.gbshape.dbe.struts.bean.ColumnBean;


public class CreateTableXmlBean implements Serializable{

	private String name = "";
	private String comment = "";

	private ArrayList columns = new ArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public ArrayList getColumns() {
		return columns;
	}

	public void addColumn(ColumnBean columnBean) {
		columns.add(columnBean);
	}

	public void setColumns(ArrayList columns) {
		this.columns = columns;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
