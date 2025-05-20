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

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;


public class DBEXmlBean {
	private ArrayList createTableXmlBeans = new ArrayList();

	public ArrayList getCreateTableXmlBeans() {
		return createTableXmlBeans;
	}

	public void setCreateTableXmlBeans(ArrayList createTableXmlBeans) {
		this.createTableXmlBeans = createTableXmlBeans;
	}

	public void addCreateTable(CreateTableXmlBean createTableXmlBean) {
		createTableXmlBeans.add(createTableXmlBean);
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
