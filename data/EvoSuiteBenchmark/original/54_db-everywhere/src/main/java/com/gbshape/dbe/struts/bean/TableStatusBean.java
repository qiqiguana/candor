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

import java.math.BigDecimal;
import java.util.Calendar;

public class TableStatusBean  implements java.io.Serializable{

	private String tableName = "";
	private String engine = "";
	private String version = "";
	private String rowFormat = "";
	private String rows	 = "";
	private String avgRowLength = "";
	private BigDecimal dataLength = new BigDecimal("0");
	private BigDecimal maxDataLength = new BigDecimal("0");
	private BigDecimal indexLength = new BigDecimal("0");
	private BigDecimal totalLength = new BigDecimal("0");
	private String dataFree = "";
	private String autoIncrement = "";
	private Calendar createTime = null;
	private Calendar updateTime = null;
	private Calendar checkTime = null;
	private String collation = "";
	private String comment = "";

	public String getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(String autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public String getAvgRowLength() {
		return avgRowLength;
	}
	public void setAvgRowLength(String avgRowLength) {
		this.avgRowLength = avgRowLength;
	}
	public Calendar getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Calendar checkTime) {
		this.checkTime = checkTime;
	}
	public String getCollation() {
		return collation;
	}
	public void setCollation(String collation) {
		this.collation = collation;
	}
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}
	public String getDataFree() {
		return dataFree;
	}
	public void setDataFree(String dataFree) {
		this.dataFree = dataFree;
	}
	public BigDecimal getDataLength() {
		return dataLength;
	}
	public void setDataLength(BigDecimal dataLength) {
		this.dataLength = dataLength;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public BigDecimal getIndexLength() {
		return indexLength;
	}
	public void setIndexLength(BigDecimal indexLength) {
		this.indexLength = indexLength;
	}
	public BigDecimal getMaxDataLength() {
		return maxDataLength;
	}
	public void setMaxDataLength(BigDecimal maxDataLength) {
		this.maxDataLength = maxDataLength;
	}
	public String getRowFormat() {
		return rowFormat;
	}
	public void setRowFormat(String rowFormat) {
		this.rowFormat = rowFormat;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public Calendar getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public BigDecimal getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(BigDecimal totalLength) {
		this.totalLength = totalLength;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
