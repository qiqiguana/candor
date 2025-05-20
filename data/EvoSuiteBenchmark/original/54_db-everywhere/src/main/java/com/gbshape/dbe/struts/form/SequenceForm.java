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

package com.gbshape.dbe.struts.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

public class SequenceForm  extends ActionForm implements java.io.Serializable{

	private String name ="";
	private String start ="";
	private String increment ="1";
	private String min ="";
	private String max ="";
	private String cache ="";
	private String cycle ="no";
	private String comment ="";

	public String getCache() {
		return cache;
	}


	public void setCache(String cache) {
		this.cache = cache;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getCycle() {
		return cycle;
	}


	public void setCycle(String cycle) {
		this.cycle = cycle;
	}


	public String getIncrement() {
		return increment;
	}


	public void setIncrement(String increment) {
		this.increment = increment;
	}


	public String getMax() {
		return max;
	}


	public void setMax(String max) {
		this.max = max;
	}


	public String getMin() {
		return min;
	}


	public void setMin(String min) {
		this.min = min;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
