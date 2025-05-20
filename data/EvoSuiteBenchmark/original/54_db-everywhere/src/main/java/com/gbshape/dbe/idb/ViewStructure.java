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

import javax.servlet.http.HttpServletRequest;

import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.ViewStatusBean;
import com.gbshape.dbe.struts.bean.ViewStructureBean;

public interface ViewStructure {

	public ViewStructureBean getStructure(DBDataBean dataBean, String tableName);

	public void drop(HttpServletRequest request,DBDataBean dataBean, String tableName);

	public String create(HttpServletRequest request,DBDataBean dataBean, String viewName, String query);

	public ViewStatusBean getStatus(DBDataBean dataBean, String tableName);

	public boolean isView(DBDataBean dataBean, String viewName);

}
