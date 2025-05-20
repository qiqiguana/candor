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

import com.gbshape.dbe.struts.bean.DBDataBean;

public interface ImportTask {

	public static final int CSV_DATATYPE = 0;

	public static final int TEXT_TYPE = 0;
	public static final int SELECT_TYPE = 1;

	public String getDescription();

	public int getDataType();

	public ArrayList getParameters(HttpServletRequest request);

	public String execute(HttpServletRequest request,DBDataBean dataBean, ArrayList datas, Map parameters);

}
