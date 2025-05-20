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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MessageBean implements Serializable{

	public static final int INFO_TYPE = 0;
	public static final int WARNING_TYPE = 1;
	public static final int ERROR_TYPE = 2;

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS");

	private String message = "";
	private int type = INFO_TYPE;
	private GregorianCalendar creation = new GregorianCalendar();

	public String getCreation() {
		return SDF.format(creation.getTime());
	}
	public MessageBean(String message, int type) {
		super();
		this.message = message;
		this.type = type;
	}
	public MessageBean(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
