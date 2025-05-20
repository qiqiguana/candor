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

package com.gbshape.dbe.sqlserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.gbshape.dbe.idb.TableStructure;
import com.gbshape.dbe.sql.NonSelect;
import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.NonSelectResultBean;
import com.gbshape.dbe.struts.bean.TableStatusBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;
import com.gbshape.dbe.struts.form.ColumnForm;
import com.gbshape.dbe.utils.DBEHelper;
import com.gbshape.dbe.xml.CreateTableXmlBean;

public class SqlserverTableStructure implements TableStructure {

	public TableStatusBean getStatus(DBDataBean dataBean, String tableName, boolean showTableStatus) {
		TableStatusBean tableStatusBean = new TableStatusBean();
		tableStatusBean.setTableName(tableName);

		if (showTableStatus) {
			/*Connection connection = null;
			try {
				connection = DBEHelper.getConnection(dataBean);

				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("show table status ");

				while (rs.next()) {
					if (tableName.equalsIgnoreCase(rs.getString("Name"))) {
						// tableStatusBean.setEngine(rs.getString("Engine"));
						tableStatusBean.setVersion(rs.getString("version"));
						tableStatusBean.setComment(rs.getString("Comment"));
						tableStatusBean.setRowFormat(rs.getString("row_format"));
						String rows = rs.getString("rows");
						if (StringUtils.isEmpty(rows)) {
							rows = "0";
						}
						tableStatusBean.setRows(rows);
						tableStatusBean.setAvgRowLength(rs.getString("avg_row_length"));
						BigDecimal dl = rs.getBigDecimal("data_length");
						if (dl == null) {
							dl = new BigDecimal("0");
						}
						dl = dl.setScale(2);
						BigDecimal dataLength = (dl).divide(new BigDecimal("1024"), BigDecimal.ROUND_HALF_EVEN).setScale(2);
						tableStatusBean.setDataLength(dataLength);
						tableStatusBean.setMaxDataLength(rs.getBigDecimal("max_data_length"));
						BigDecimal il = rs.getBigDecimal("index_length");
						if (il == null) {
							il = new BigDecimal("0");
						}
						il = il.setScale(2);
						BigDecimal indexLength = (il).divide(new BigDecimal("1024"), BigDecimal.ROUND_HALF_EVEN).setScale(2);
						tableStatusBean.setIndexLength(indexLength);
						BigDecimal totalLength = (il.add(dl)).divide(new BigDecimal("1024"), BigDecimal.ROUND_HALF_EVEN).setScale(2);
						tableStatusBean.setTotalLength(totalLength);
						tableStatusBean.setDataFree(rs.getString("data_free"));
						tableStatusBean.setAutoIncrement(rs.getString("auto_increment"));
						tableStatusBean.setCreateTime(DBEHelper.getCalendar(rs, "create_time"));
						tableStatusBean.setUpdateTime(DBEHelper.getCalendar(rs, "update_time"));
						tableStatusBean.setCheckTime(DBEHelper.getCalendar(rs, "check_time"));
						tableStatusBean.setCollation(rs.getString("collation"));
					}
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
			}*/
		}

		return tableStatusBean;
	}

	public TableStructureBean getStructure(DBDataBean dataBean, String tableName) {
		TableStructureBean tableStructureBean = new TableStructureBean();
		tableStructureBean.setTableName(tableName);

		Connection connection = null;
		try {
			connection = DBEHelper.getConnection(dataBean);

			Statement stmt = connection.createStatement();


      String queryKey = "SELECT SC.Name, SC.COLID "
        +"FROM SYSOBJECTS SO "
        +"INNER JOIN SYSINDEXKEYS SI ON SO.ID = SI.ID AND SI.INDID = 1 "
        +"INNER JOIN SYSCOLUMNS SC ON SO.ID = SC.ID AND SC.COLID=SI.COLID "
        +"WHERE SO.NAME= '" + tableName + "' "
        +"AND SO.XTYPE='U' ";

      ResultSet rsKey = stmt.executeQuery(queryKey);
      HashMap keyMap = new HashMap();
      while (rsKey.next()) {
        keyMap.put(rsKey.getString("Name"),rsKey.getString("Name"));
      }

      stmt.close();
      stmt = connection.createStatement();

      String query = "SELECT COLUMN_NAME, "
        +"DATA_TYPE, "
        +"CHARACTER_MAXIMUM_LENGTH, "
        +"NUMERIC_PRECISION, "
        +"NUMERIC_SCALE, "
        +"IS_NULLABLE, "
        +"COLUMNPROPERTY(OBJECT_ID(TABLE_NAME), COLUMN_NAME, 'IsIdentity') AS IS_AUTOINCREMENT, "
        +"COLUMN_DEFAULT "
        +"FROM   INFORMATION_SCHEMA.COLUMNS "
        +"WHERE  TABLE_NAME = '" + tableName + "' "
        +"ORDER BY ORDINAL_POSITION  ";


			ResultSet rs = stmt.executeQuery(query);

			ArrayList columns = new ArrayList();

			while (rs.next()) {
				ColumnBean columnBean = new ColumnBean();
				String columnName = rs.getString("COLUMN_NAME");
				columnBean.setName(columnName);
        columnBean.setDim(rs.getString("CHARACTER_MAXIMUM_LENGTH"));

				columnBean.setDataType(rs.getString("DATA_TYPE"));

        if(keyMap.containsKey(columnName)){
          columnBean.setKey(true);
        }
				//columnBean.setMode(rs.getString("Key"));
				//columnBean.setKey(rs.getString("Key").equalsIgnoreCase("PRI"));
				columnBean.setNullable(rs.getString("IS_NULLABLE").equalsIgnoreCase("YES"));
				columnBean.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
				//columnBean.setExtra(rs.getString("Extra"));
				columns.add(columnBean);
			}

			tableStructureBean.setColumns(columns);

			stmt.close();

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

		return tableStructureBean;
	}

	public ArrayList alterModify(HttpServletRequest request, DBDataBean dataBean, String tableName, ColumnBean columnBean, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName();

		/* ALTER TABLE address CHANGE CITY CITY VARCHAR( 31 ) DEFAULT NULL */

		StringBuffer alterQuery = new StringBuffer("ALTER TABLE " + tableName + " CHANGE " + name + " " + name + " ");

		// DATATYPE
		String dataType = columnForm.getDataType();
		String dim = columnForm.getDim();
		if (dataType.equalsIgnoreCase("VARCHAR") || dataType.equalsIgnoreCase("CHAR")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else if (dataType.equals("FLOAT") || dataType.equals("FIXED")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else if (dataType.equals("ENUM") || dataType.equals("SET")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else {
			alterQuery.append(" " + dataType + " ");
		}

		// DEFAULTVALUE
		String defaultValue = columnBean.getDefaultValue();
		if (defaultValue == null) {
			defaultValue = "";
		}
		if (columnForm.getDefaultValue() == null) {
			columnForm.setDefaultValue("");
		}
		String value = "'" + columnForm.getDefaultValue() + "'";
		if (columnForm.getDefaultValue().equals("null")) {
			value = "null";
		}
		alterQuery.append(" DEFAULT " + value + " ");

		// NOTNULL
		if (columnForm.getNotnull().equals("true")) {
			alterQuery.append(" NOT NULL ");
		}

		// NonSelectResultBean nonSelectResultBean =
		// NonSelect.executeQuery(dataBean, alterQuery.toString());
		// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());

		querys.add(alterQuery.toString());

		return querys;
	}

	public void drop(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "DROP TABLE " + tableName + " ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void truncate(HttpServletRequest request, DBDataBean dataBean, String tableName) {
		String query = "TRUNCATE TABLE " + tableName + " ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void alterDrop(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String alterQuery = new String("ALTER TABLE " + tableName + " DROP " + field + " ");
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, alterQuery);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public ArrayList alterAdd(HttpServletRequest request, DBDataBean dataBean, String tableName, ColumnForm columnForm) {
		ArrayList querys = new ArrayList();
		String name = columnForm.getName();

		/* ALTER TABLE address CHANGE CITY CITY VARCHAR( 31 ) DEFAULT NULL */

		StringBuffer alterQuery = new StringBuffer("ALTER TABLE " + tableName + " ADD " + name + " ");

		// DATATYPE
		String dataType = columnForm.getDataType();
		String dim = columnForm.getDim();
		if (dataType.equalsIgnoreCase("VARCHAR") || dataType.equalsIgnoreCase("CHAR")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else if (dataType.equals("FLOAT") || dataType.equals("FIXED")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else if (dataType.equals("ENUM") || dataType.equals("SET")) {
			alterQuery.append(" " + dataType + "(" + dim + ") ");
		} else {
			alterQuery.append(" " + dataType + " ");
		}

		// DEFAULTVALUE
		if (columnForm.getDefaultValue() == null) {
			columnForm.setDefaultValue("");
		}
		String value = "'" + columnForm.getDefaultValue().trim() + "'";
		if (columnForm.getDefaultValue().equals("null")) {
			value = "null";
		}
		if (!value.trim().equals("''")) {
			alterQuery.append(" DEFAULT " + value + " ");
		}

		// NOTNULL
		if (columnForm.getNotnull().equals("true")) {
			alterQuery.append(" NOT NULL ");
		}

		// NonSelectResultBean nonSelectResultBean =
		// NonSelect.executeQuery(dataBean, alterQuery.toString());
		// DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
		querys.add(alterQuery.toString());

		return querys;
	}

	public ArrayList create(HttpServletRequest request, DBDataBean dataBean, CreateTableXmlBean createTableXmlBean) {
		ArrayList querys = new ArrayList();
		StringBuffer query = new StringBuffer("CREATE TABLE '" + createTableXmlBean.getName() + "' (");

		StringBuffer primary = new StringBuffer("");
		StringBuffer index = new StringBuffer("");
		StringBuffer unique = new StringBuffer("");

		ArrayList columns = createTableXmlBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (columnBean != null) {
				String name = columnBean.getName();
				if (StringUtils.isNotEmpty(name)) {
					if (i > 0) {
						query.append(",");
					}
					String dataType = columnBean.getDataType();
					query.append("" + name + " " + dataType + " ");
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
						query.append(" default '" + columnBean.getDefaultValue() + "' ");
					}
					if (StringUtils.isNotEmpty(columnBean.getComment())) {
						query.append(" COMMENT '" + columnBean.getComment() + "' ");
					}
					if (StringUtils.isNotEmpty(columnBean.getExtra())) {
						query.append(" " + columnBean.getExtra() + " ");
					}
					if (columnBean.isKey()) {
						if (StringUtils.isNotEmpty(primary.toString())) {
							primary.append(",");
						}
						primary.append("" + name + "");
					}
					if (columnBean.isIndex()) {
						if (StringUtils.isNotEmpty(index.toString())) {
							index.append(",");
						}
						index.append("" + name + "");
					}
					if (columnBean.isUnique()) {
						if (StringUtils.isNotEmpty(unique.toString())) {
							unique.append(",");
						}
						unique.append("" + name + "");
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(primary.toString())) {
			query.append(", PRIMARY KEY  (");
			query.append(primary);
			query.append(")");
		}

		if (StringUtils.isNotEmpty(index.toString())) {
			query.append(", INDEX  (");
			query.append(index);
			query.append(")");
		}

		if (StringUtils.isNotEmpty(unique.toString())) {
			query.append(", UNIQUE  (");
			query.append(unique);
			query.append(")");
		}

		query.append(")");
		if (StringUtils.isNotEmpty(createTableXmlBean.getComment())) {
			query.append(" COMMENT = '" + createTableXmlBean.getComment() + "' ");
		}

		querys.add(query.toString());

		return querys;
	}

	public void dropUnique(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE " + tableName + " DROP INDEX " + keyName + " ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropIndex(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE " + tableName + " DROP INDEX " + keyName + " ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void dropPrimary(HttpServletRequest request, DBDataBean dataBean, String tableName, String keyName, String columnName) {
		String query = new String("ALTER TABLE " + tableName + " DROP PRIMARY KEY ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addUnique(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String query = new String("ALTER TABLE " + tableName + " ADD UNIQUE (" + field + ") ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void addIndex(HttpServletRequest request, DBDataBean dataBean, String tableName, String field) {
		String query = new String("ALTER TABLE " + tableName + " ADD INDEX (" + field + ") ");

		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}

	public void changePrimary(HttpServletRequest request, DBDataBean dataBean, String tableName, ArrayList columns) {
		StringBuffer keys = new StringBuffer("");
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if (columnBean.isKey()) {
				if (StringUtils.isNotEmpty(keys.toString())) {
					keys.append(",");
				}
				keys.append("" + columnBean.getName() + "");
			}
		}

		String query = "ALTER TABLE " + tableName + " DROP PRIMARY KEY , ADD PRIMARY KEY (" + keys.toString() + ") ";
		NonSelectResultBean nonSelectResultBean = NonSelect.executeQuery(dataBean, query);
		DBEHelper.setLogs(request, nonSelectResultBean.getMessages());
	}
}
