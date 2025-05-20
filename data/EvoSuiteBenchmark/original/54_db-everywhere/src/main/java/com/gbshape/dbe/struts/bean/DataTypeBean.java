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

public final class DataTypeBean  implements java.io.Serializable{

	/*<name>VARCHAR</name>
    		<len>true</len>
    		<valueWrapper>'</valueWrapper>
    		<maxlength>256</maxlength>
    		<forceLength>true</forceLength>*/

	private String name = "";
	private boolean len = false;
	private boolean dec = false;
	private String valueWrapper = "";
	private int maxlength = 0;
	private int formSize = 0;
	private boolean forceLength = false;
	private String formInput = "text";
	private String format = "";

	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFormInput() {
		return formInput;
	}
	public void setFormInput(String formInput) {
		this.formInput = formInput;
	}
	public boolean isDec() {
		return dec;
	}
	public void setDec(boolean dec) {
		this.dec = dec;
	}
	public boolean isForceLength() {
		return forceLength;
	}
	public void setForceLength(boolean forceLength) {
		this.forceLength = forceLength;
	}
	public boolean isLen() {
		return len;
	}
	public void setLen(boolean len) {
		this.len = len;
	}
	public int getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValueWrapper() {
		return valueWrapper;
	}
	public void setValueWrapper(String valueWrapper) {
		this.valueWrapper = valueWrapper;
	}
	public int getFormSize() {
		return formSize;
	}
	public void setFormSize(int formSize) {
		this.formSize = formSize;
	}

}
