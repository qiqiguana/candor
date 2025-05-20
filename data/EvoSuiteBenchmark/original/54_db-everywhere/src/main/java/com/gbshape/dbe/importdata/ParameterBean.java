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

public class ParameterBean  implements java.io.Serializable{

	private String name = "";
	private String defaultValue = "";
	private int type = 0;
	private ArrayList values = new ArrayList();
	private String label = "";
	private boolean translatedLabel = false;

	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isTranslatedLabel() {
		return translatedLabel;
	}
	public void setTranslatedLabel(boolean translatedLabel) {
		this.translatedLabel = translatedLabel;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ArrayList getValues() {
		return values;
	}
	public void setValues(ArrayList values) {
		this.values = values;
	}


}
