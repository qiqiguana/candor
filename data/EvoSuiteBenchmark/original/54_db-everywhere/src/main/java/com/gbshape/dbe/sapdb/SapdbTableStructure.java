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

package com.gbshape.dbe.sapdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.ConstraintBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.struts.form.ColumnForm;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public class SapdbTableStructure implements TableStructure {

	private static Logger log = Logger.getLogger(SapdbTableStructure.class.getName());

	public TableStructureBean getStructure(DBDataBean dataBean, String tableName) {
		TableStructureBean tableStructureBean = new TableStructureBean();
		tableStructureBean.setTableName(tableName);

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt = connection.createStatement();
			// ResultSet rs=stmt.executeQuery("select * from columns where
			// tablename='"+tableName+"'");

			ResultSet rsIndex = stmt.executeQuery("select * from indexcolumns where tablename='" + tableName + "' ");

			HashMap indexMap = new HashMap();
			while (rsIndex.next()) {
				String columnName = rsIndex.getString("COLUMNNAME");
				boolean unique = rsIndex.getString("TYPE").equals("UNIQUE");
				String keyName = rsIndex.getString("INDEXNAME");
				ArrayList constraintList = new ArrayList();
				if (indexMap.containsKey(columnName)) {
					constraintList = (ArrayList) indexMap.get(columnName);
				}
				ConstraintBean constraintBean = new ConstraintBean();
				constraintBean.setColumnName(columnName);
				constraintBean.setConstraintName(keyName);
				constraintBean.setUnique(unique);
				constraintBean.setIndex(!unique);
				if (unique) {
					constraintBean.setConstraintType("UNIQUE");
				} else {
					constraintBean.setConstraintType("INDEX");
				}
				constraintList.add(constraintBean);
				indexMap.put(columnName, constraintList);
			}

			/*
			 * select
			 * distinct(c.columnname),c.datatype,c.len,c.dec,c.mode,c.codetype,c.nullable,c.comment,c.default,i.type
			 * from columns c,indexcolumns i where c.tablename='TOTO' and
			 * i.tablename(+) = c.tablename and c.columnname = i.columnname(+)
			 */
			ResultSet rs = stmt.executeQuery("select * from columns where tablename='" + tableName + "'");

			ArrayList columns = new ArrayList();

			while (rs.next()) {
				ColumnBean columnBean = new ColumnBean();
				String columnName = rs.getString("COLUMNNAME");
				columnBean.setName(columnName);
				columnBean.setDataType(rs.getString("DATATYPE"));
				columnBean.setOriginalDT(rs.getString("DATATYPE"));
				columnBean.setLen(rs.getInt("LEN"));
				columnBean.setDec(rs.getInt("DEC"));
				columnBean.setMode(rs.getString("MODE"));
				boolean key = rs.getString("MODE").equalsIgnoreCase("KEY");
				columnBean.setKey(key);
				columnBean.setCodeType(rs.getString("CODETYPE"));
				boolean nullable = rs.getString("NULLABLE").equalsIgnoreCase("YES");
				columnBean.setNullable(nullable);
				columnBean.setComment(rs.getString("COMMENT"));
				columnBean.setDefaultValue(rs.getString("DEFAULT"));

				if (key) {
					ArrayList constraintList = new ArrayList();
					ConstraintBean constraintBean = new ConstraintBean();
					constraintBean.setColumnName(columnName);
					constraintBean.setConstraintName("PRIMARY");
					constraintBean.setConstraintType("PRIMARY");
					constraintBean.setKey(true);
					constraintList.add(constraintBean);
					columnBean.setConstraintList(constraintList);
				}

				if (indexMap.containsKey(columnName)) {
					ArrayList constraintList = (ArrayList) indexMap.get(columnName);
					ArrayList cl = columnBean.getConstraintList();
					if (cl == null) {
						cl = new ArrayList();
					}
					cl.addAll(constraintList);
					columnBean.setConstraintList(cl);
					for (int j = 0; j < constraintList.size(); j++) {
						ConstraintBean constraintBean = (ConstraintBean) constraintList.get(j);
						if (constraintBean.isIndex()) {
							columnBean.setIndex(true);
						}
						if (constraintBean.isUnique()) {
							columnBean.setUnique(true);
						}
					}
				}

				columns.add(columnBean);
			}

			tableStructureBean.setColumns(columns);

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		return tableStructureBean;
	}

	public ArrayList alterModify(HttpServletRequest request, DBDataBean dataBean, String tableName, ColumnBean columnBean, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName().toUpperCase();

		// DATATYPE
		String dataType = columnForm.getDataType();
		if (!columnBean.getDataType().equals(dataType)) {
			String alterQuery = "";
			String dim = columnForm.getDim();
			if (dataType.equals("VARCHAR") || dataType.equals("CHAR")) {
				alterQuery = "ALTER TABLE \"" + tableName + "\" MODIFY (\"" + name + "\" " + dataType + "(" + dim + ") " + columnForm.getCodeType() + " )";
			} else if (dataType.equals("FLOAT") || dataType.equals("FIXED")) {
				alterQuery = "ALTER TABLE \"" + tableName + "\" MODIFY (\"" + name + "\" " + dataType + "(" + dim + ") )";
			} else {
				alterQuery = "ALTER TABLE \"" + tableName + "\" MODIFY (\"" + name + "\" " + dataType + " )";
			}
			// NonSelectResultBean nonSelectResultBean =
			// NonSelect.executeQuery(dataBean, alterQuery);
			// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
			querys.add(alterQuery);
		}

		// NOTNULL
		String nnAlterQuery = "";
		if (columnForm.getNotnull().equals("false")) {
			nnAlterQuery = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" NULL ";
		} else {
			nnAlterQuery = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" NOT NULL ";
		}
		// NonSelectResultBean nnNonSelectResultBean =
		// NonSelect.executeQuery(dataBean, nnAlterQuery);
		// DBEHelper.setLogs(request, nnNonSelectResultBean.getMessages());
		querys.add(nnAlterQuery);

		// DEFAULTVALUE
		String defaultValue = columnBean.getDefaultValue();
		if (defaultValue == null) {
			defaultValue = "";
		}
		if (columnForm.getDefaultValue() == null) {
			columnForm.setDefaultValue("");
		}
		if (!defaultValue.equals(columnForm.getDefaultValue())) {
			String keyword = "ALTER";
			if (columnBean.getDefaultValue() == null) {
				keyword = "ADD";
			}
			String value = "'" + columnForm.getDefaultValue() + "'";
			if (columnForm.getDefaultValue().equals("null")) {
				value = "null";
			}
			String alterQuery = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" " + keyword + " DEFAULT " + value + " ";
			// NonSelectResultBean nonSelectResultBean =
			// NonSelect.executeQuery(dataBean, alterQuery);
			// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
			querys.add(alterQuery);
		}

		// UNIQUE

		// COMMENT
		String comment = columnBean.getComment();
		if (comment == null) {
			comment = "";
		}
		if (columnForm.getComment() == null) {
			columnForm.setComment("");
		}
		if (!comment.equals(columnForm.getComment())) {
			String value = "'" + columnForm.getComment() + "'";
			if (columnForm.getComment().equals("null")) {
				value = "null";
			}
			String alterQuery = "COMMENT ON COLUMN \"" + tableName + "\".\"" + name + "\" IS " + value + " ";
			// NonSelectResultBean nonSelectResultBean =
			// NonSelect.executeQuery(dataBean, alterQuery);
			// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
			querys.add(alterQuery);
		}

		return querys;
	}

	public TableStatusBean getStatus(DBDataBean dataBean, String tableName, boolean showTableStatus) {
		TableStatusBean tableStatusBean = new TableStatusBean();
		tableStatusBean.setTableName(tableName);

		if (showTableStatus) {
			Connection connection = null;
			try {
				connection = DBEHelper.getConnection(dataBean);

				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("select * from tables,tablesize where type = 'TABLE' and tablesize.tablename = tables.tablename and tables.tablename = '" + tableName + "' ");

				while (rs.next()) {
					// tableStatusBean.setEngine(rs.getString("Engine"));
					// tableStatusBean.setVersion(rs.getString("version"));
					// tableStatusBean.setRowFormat(rs.getString("row_format"));
					tableStatusBean.setRows(rs.getString("rowcount"));
					// tableStatusBean.setAvgRowLength(rs.getString("avg_row_length"));
					tableStatusBean.setDataLength(rs.getBigDecimal("usedsize"));
					tableStatusBean.setTotalLength(rs.getBigDecimal("usedsize"));
					// tableStatusBean.setMaxDataLength(rs.getString("max_data_length"));
					// tableStatusBean.setIndexLength(rs.getString("index_length"));
					// tableStatusBean.setDataFree(rs.getString("data_free"));
					// tableStatusBean.setAutoIncrement(rs.getString("auto_increment"));
					tableStatusBean.setComment(rs.getString("COMMENT"));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String createDate = rs.getString("createdate");
					String createTime = rs.getString("createtime");
					GregorianCalendar cal = new GregorianCalendar();
					try {
						Date cd = simpleDateFormat.parse(createDate + ' ' + createTime);
						cal.setTime(cd);
					} catch (ParseException e) {
					}
					tableStatusBean.setCreateTime(cal);

					String updateDate = rs.getString("alterdate");
					String updateTime = rs.getString("altertime");
					GregorianCalendar cal2 = new GregorianCalendar();
					try {
						Date cd = simpleDateFormat.parse(updateDate + ' ' + updateTime);
						cal2.setTime(cd);
					} catch (ParseException e) {
					}
					tableStatusBean.setUpdateTime(cal2);

					// tableStatusBean.setCollation(rs.getString("collation"));

				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return tableStatusBean;
	}

	public ArrayList alterAdd(HttpServletRequest request, DBDataBean dataBean, String tableName, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName().toUpperCase();

		// DATATYPE
		String dataType = columnForm.getDataType();
		String alterQuery = "";
		String dim = columnForm.getDim();
		if (dataType.equals("VARCHAR") || dataType.equals("CHAR")) {
			alterQuery = "ALTER TABLE \"" + tableName + "\" ADD (\"" + name + "\" " + dataType + "(" + dim + ") " + columnForm.getCodeType() + " )";
		} else if (dataType.equals("FLOAT") || dataType.equals("FIXED")) {
			alterQuery = "ALTER TABLE \"" + tableName + "\" ADD (\"" + name + "\" " + dataType + "(" + dim + ") )";
		} else {
			alterQuery = "ALTER TABLE \"" + tableName + "\" ADD (\"" + name + "\" " + dataType + " )";
		}
		// NonSelectResultBean nonSelectResultBean =
		// NonSelect.executeQuery(dataBean, alterQuery);
		// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
		querys.add(alterQuery);

		// NOTNULL
		String nnAlterQuery = "";
		if (columnForm.getNotnull().equals("false")) {
			nnAlterQuery = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" NULL ";
		} else {
			nnAlterQuery = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" NOT NULL ";
		}
		// NonSelectResultBean nnNonSelectResultBean =
		// NonSelect.executeQuery(dataBean, nnAlterQuery);
		// DBEHelper.setLogs(request, nnNonSelectResultBean.getMessages());
		querys.add(nnAlterQuery);

		// DEFAULTVALUE
		if (columnForm.getDefaultValue() == null) {
			columnForm.setDefaultValue("");
		}
		if (StringUtils.isNotEmpty(columnForm.getDefaultValue())) {
			String value = "'" + columnForm.getDefaultValue().trim() + "'";
			if (columnForm.getDefaultValue().equals("null")) {
				value = "null";
			}
			if (!value.equals("''")) {
				String alterQueryDefault = "ALTER TABLE \"" + tableName + "\" COLUMN \"" + name + "\" ADD DEFAULT " + value + " ";
				// NonSelectResultBean nonSelectResultBean1 =
				// NonSelect.executeQuery(dataBean, alterQueryDefault);
				// DBEHelper.setLogs(request,
				// nonSelectResultBean1.getMessages());
				querys.add(alterQueryDefault);
			}
		}

		// UNIQUE

		// COMMENT
		if (columnForm.getComment() == null) {
			columnForm.setComment("");
		}
		if (StringUtils.isNotEmpty(columnForm.getComment())) {
			String value = "'" + columnForm.getComment() + "'";
			if (columnForm.getComment().equals("null")) {
				value = "null";
			}
			String alterQueryComment = "COMMENT ON COLUMN \"" + tableName + "\".\"" + name + "\" IS " + value + " ";
			// NonSelectResultBean nonSelectResultBean2 =
			// NonSelect.executeQuery(dataBean, alterQueryComment);
			// DBEHelper.setLogs(request, nonSelectResultBean2.getMessages());
			querys.add(alterQueryComment);
		}

		return querys;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "DROP TABLE \"" + tableName + "\" ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void truncate(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "TRUNCATE TABLE \"" + tableName + "\" ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void alterDrop(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String alterQuery = new String("ALTER TABLE \"" + tableName + "\" DROP (\"" + field + "\") CASCADE ");
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, alterQuery);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public ArrayList create(HttpServletRequest request, DBDataBean dataBean, CreateTableXmlBean createTableXmlBean) {
		ArrayList querys = new ArrayList();

		String tableName = createTableXmlBean.getName();
		tableName = tableName.toUpperCase();
		StringBuffer query = new StringBuffer("CREATE TABLE " + tableName + " (");

		StringBuffer primary = new StringBuffer("");
		// StringBuffer index = new StringBuffer("");
		// StringBuffer unique = new StringBuffer("");
		ArrayList comments = new ArrayList();

		ArrayList columns = createTableXmlBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (columnBean != null) {
				String name = columnBean.getName();
				if (StringUtils.isNotEmpty(name)) {
					if (i > 0) {
						query.append(",");
					}
					query.append("\"" + name + "\" " + columnBean.getDataType() + " ");
					if (columnBean.getLen() > 0) {
						query.append(" (");
						query.append(columnBean.getLen());
						if (columnBean.getDec() > 0) {
							query.append(",");
							query.append(columnBean.getDec());
						}
						query.append(") ");
					}
					if (columnBean.isNullable()) {
						query.append(" NULL ");
					} else {
						query.append(" NOT NULL ");
					}
					if (StringUtils.isNotEmpty(columnBean.getDefaultValue())) {
						if (columnBean.getDataType().equalsIgnoreCase("BOOLEAN")) {
							query.append(" DEFAULT " + columnBean.getDefaultValue() + " ");
						} else {
							query.append(" DEFAULT '" + columnBean.getDefaultValue() + "' ");
						}
					}
					if (StringUtils.isNotEmpty(columnBean.getComment())) {
						comments.add("COMMENT ON COLUMN \"" + tableName.toUpperCase() + "\".\"" + name.toUpperCase() + "\" IS '" + columnBean.getComment().toUpperCase() + "' ");
					}
					if (columnBean.isUnique()) {
						query.append(", UNIQUE(" + columnBean.getName() + ") ");
					}
					if (columnBean.isKey()) {
						if (StringUtils.isNotEmpty(primary.toString())) {
							primary.append(",");
						}
						primary.append("\"" + name + "\"");
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(primary.toString())) {
			query.append(", PRIMARY KEY  (");
			query.append(primary);
			query.append(")");
		}

		query.append(")");
		if (StringUtils.isNotEmpty(createTableXmlBean.getComment())) {
			comments.add("COMMENT ON TABLE \"" + tableName.toUpperCase() + "\" IS '" + createTableXmlBean.getComment().toUpperCase() + "' ");
		}

		// NonSelectResultBean nonSelectResultBean =
		// NonSelect.executeQuery(dataBean, query.toString());
		// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

		querys.add(query.toString().toUpperCase());

		/*
		 * for(int i=0; i < comments.size(); i++) { String commentQuery =
		 * (String) comments.get(i); NonSelectResultBean nonSelectResultBean2 =
		 * NonSelect.executeQuery(dataBean, commentQuery.toString());
		 * DBEHelper.setLogs(request, nonSelectResultBean2.getMessages()); }
		 */

		querys.addAll(comments);

		// return nonSelectResultBean.getErrorMessage();
		return querys;
	}

	public void dropUnique(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("DROP INDEX " + keyName + " ON " + tableName + " ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropIndex(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("DROP INDEX " + keyName + " ON " + tableName + " ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropPrimary(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE \"" + tableName + "\" DROP PRIMARY KEY ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addUnique(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		// CREATE UNIQUE INDEX "INDEX001" ON "DBA"."TOTO"("BB" ASC)
		String id = "UNI_" + field + "_" + tableName;
		if (id.length() > 32) {
			id = id.substring(0, 31);
		}
		String query = new String("CREATE UNIQUE INDEX \"" + id + "\" ON \"" + tableName + "\" (\"" + field + "\" ASC) ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addIndex(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String id = "IDX_" + field + "_" + tableName;
		if (id.length() > 32) {
			id = id.substring(0, 31);
		}
		String query = new String("CREATE INDEX \"" + id + "\" ON \"" + tableName + "\" (\"" + field + "\" ASC) ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void changePrimary(HttpServletRequest request, DBDataBean dataBean, String tableName, ArrayList columns) {
		ArrayList notNulls = new ArrayList();
		StringBuffer keys = new StringBuffer("");
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (columnBean.isKey()) {
				if (StringUtils.isNotEmpty(keys.toString())) {
					keys.append(",");
				}
				keys.append("\"" + columnBean.getName() + "\"");
				if (columnBean.isNullable()) {
					notNulls.add("ALTER TABLE \"" + tableName + "\" COLUMN \"" + columnBean.getName() + "\" NOT NULL");
				}
			}
		}

		for (int i = 0; i < notNulls.size(); i++) {
			String notNullQuery = (String) notNulls.get(i);
			NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, notNullQuery);
			DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
		}

		String query = "ALTER TABLE \"" + tableName + "\" ALTER PRIMARY KEY (" + keys.toString() + ") ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

	}

}
