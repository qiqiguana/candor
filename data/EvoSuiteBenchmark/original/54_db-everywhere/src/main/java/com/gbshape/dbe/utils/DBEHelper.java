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

package com.gbshape.dbe.utils;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import com.gbshape.dbe.struts.bean.ColumnBean;
import com.gbshape.dbe.struts.bean.DBDataBean;
import com.gbshape.dbe.struts.bean.DataTypeBean;
import com.gbshape.dbe.struts.bean.MessageBean;
import com.gbshape.dbe.struts.bean.TableStructureBean;

public final class DBEHelper {

	//private static Logger log = Logger.getLogger(DBEHelper.class.getName());

	private final static String MANIFEST = "META-INF/MANIFEST.MF";

	private final static String BUILD_REFERENCE = "Implementation-Version";

	public static Connection getConnection(DBDataBean dataBean) throws ClassNotFoundException, SQLException {
		Class.forName(dataBean.getDriver());
		String connectionURL = "jdbc:"+dataBean.getDbType()+":"+dataBean.getUrl()+"/"+dataBean.getDbName();
		if(dataBean.getDbType().equals("oracle")){
			connectionURL = "jdbc:"+dataBean.getDbType()+":"+dataBean.getUrl()+":"+dataBean.getDbName();
		}
		Connection connection = null;
		if(StringUtils.isEmpty(dataBean.getUser())){
			connection = DriverManager.getConnection(connectionURL);
		} else {
			connection = DriverManager.getConnection(connectionURL, dataBean.getUser(),dataBean.getPasswd());
		}
		return connection;
	}

	public static void setLogs(HttpServletRequest request, MessageBean message) {

		//System.out.println("setlogs "+message);

		HttpSession session = request.getSession();
		LinkedList logList = (LinkedList) session.getAttribute("logList");
		if(logList == null) {
			logList = new LinkedList();
		}

		logList.addLast(message);

		session.setAttribute("logList",logList);
	}

	public static void setLogs(HttpServletRequest request, ArrayList messages) {

		//System.out.println("setlogs "+message);

		HttpSession session = request.getSession();
		LinkedList logList = (LinkedList) session.getAttribute("logList");
		if(logList == null) {
			logList = new LinkedList();
		}

		for(int i=0; i< messages.size();i++) {
			logList.addLast(messages.get(i));
		}

		session.setAttribute("logList",logList);
	}

	public static String decodeISO(DBDataBean dataBean, String property) {
		String encodeType = dataBean.getEncoding();
		try {
			String value = URLDecoder.decode(URLEncoder.encode(property, encodeType), "ISO-8859-1");
			return value;
		} catch (Exception e) {
			return property;
		}
	}

	public static String decodeAjax(DBDataBean dataBean, String property) {
		String encodeType = dataBean.getEncoding();
		try {
			String value = URLDecoder.decode(URLEncoder.encode(property, encodeType), "UTF-8");
			//System.out.println("--------------------"+property+" "+value);
			return value;
		} catch (Exception e) {
			return property;
		}
	}

	/**
	 * Create an KeyValue HasMap form a JSON string
	 * @param data
	 * @return
	 */
	public static HashMap parseJSON(String data) {
	    if(data.startsWith("{")){
	        data = data.substring(1);
	        data = data.substring(0, data.length()-1);
	    }
	    System.out.println(data);
	    HashMap keyValueMap = new HashMap();
		ArrayList keyVals = new ArrayList();
		String[] keyValues = StringUtils.split(data, ",");
		String previousKV = "";
		for (int i = 0; i < keyValues.length; i++) {
			String keyValue = keyValues[i];
			if (keyValue.startsWith("\"")) {
				if (!previousKV.equals("")) {
					keyVals.add(previousKV);
				}
				previousKV = keyValue;
			} else {
				previousKV = previousKV + "," + keyValue;
			}
		}
		keyVals.add(previousKV);

		for (int i = 0; i < keyVals.size(); i++) {
			String keyVal = (String) keyVals.get(i);

			String key = keyVal.substring(0, keyVal.indexOf(":"));
			key = StringUtils.replace(key, "\"", "");
			String value = keyVal.substring((keyVal.indexOf(":") + 1));
			value = StringUtils.replace(value, "\"", "");

			keyValueMap.put(key, value);
		}

		return keyValueMap;
	}

	public static void appendValue(DBDataBean dataBean, StringBuffer where, ColumnBean columnBean, String value) {
		if(StringUtils.isEmpty(value)) {
			value = "null";
		}
		value = StringUtils.replace(value, "'", getApostropheWrapper(dataBean));
		if (value.equalsIgnoreCase("null")) {
			where.append("= NULL ");
		} else {
			DataTypeBean dataTypeBean = getDataTypeBean(dataBean,columnBean.getDataType());
			where.append("= ");
			where.append(dataTypeBean.getValueWrapper());
			where.append(decodeAjax(dataBean, value));
			where.append(dataTypeBean.getValueWrapper());
			where.append(" ");
		}
	}

	public static void appendValueNoDecodeAjax(DBDataBean dataBean, StringBuffer where, ColumnBean columnBean, String value) {
		if(StringUtils.isEmpty(value)) {
			value = "null";
		}
		value = StringUtils.replace(value, "'", getApostropheWrapper(dataBean));
		if (value.equalsIgnoreCase("null")) {
			where.append("= NULL ");
		} else {
			DataTypeBean dataTypeBean = getDataTypeBean(dataBean,columnBean.getDataType());
			where.append("= ");
			where.append(dataTypeBean.getValueWrapper());
			where.append(decodeISO(dataBean, value));
			where.append(dataTypeBean.getValueWrapper());
			where.append(" ");
		}
	}

	public static void appendValues(DBDataBean dataBean, StringBuffer query ,ColumnBean columnBean, String value) {
		if(StringUtils.isEmpty(value)) {
			value = "null";
		}
		value = StringUtils.replace(value, "'", getApostropheWrapper(dataBean));
		if (value.equalsIgnoreCase("null")) {
			query.append(" NULL ");
		} else {
			DataTypeBean dataTypeBean = getDataTypeBean(dataBean,columnBean.getDataType());
			query.append(dataTypeBean.getValueWrapper());
			query.append(decodeAjax(dataBean, value));
			query.append(dataTypeBean.getValueWrapper());
			query.append(" ");
		}
	}

	public static void appendValuesNoDecodeAjax(DBDataBean dataBean, StringBuffer query ,ColumnBean columnBean, String value) {
		if(StringUtils.isEmpty(value)) {
			value = "null";
		}
		value = StringUtils.replace(value, "'", getApostropheWrapper(dataBean));
		if (value.equalsIgnoreCase("null")) {
			query.append(" NULL ");
		} else {
			DataTypeBean dataTypeBean = getDataTypeBean(dataBean,columnBean.getDataType());
			query.append(dataTypeBean.getValueWrapper());
			query.append(decodeISO(dataBean, value));
			query.append(dataTypeBean.getValueWrapper());
			query.append(" ");
		}
	}

	public static boolean isDBAvailable(String dbType) {
		try {
            new XMLConfiguration(dbType+".xml");
        } catch (ConfigurationException e) {
            return false;
        }
        return true;
	}

	public static String[] getDataTypes(DBDataBean dataBean) {
		String[] datatypes = null;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            int maxIndex = xmlConfiguration.getMaxIndex("dataTypes.dataType")+1;
            datatypes = new String[maxIndex];
            for (int i = 0; i < maxIndex; i++) {
            	datatypes[i] = xmlConfiguration.getString("dataTypes.dataType("+i+").name");
                /*if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").dec")){
                    System.out.println(xmlConfiguration.getBoolean("dataTypes.dataType("+i+").dec"));
                }
                if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").len")){
                    System.out.println(xmlConfiguration.getBoolean("dataTypes.dataType("+i+").len"));
                }
                for (int j = 0; j <= xmlConfiguration.getMaxIndex("dataTypes.dataType("+i+").codeTypes.codeType") ; j++) {
                    System.out.println(xmlConfiguration.getString("dataTypes.dataType("+i+").codeTypes.codeType("+j+")"));
                }*/
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return datatypes;
	}

	public static String[] getDimDataTypes(DBDataBean dataBean) {
		String[] datatypes = null;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            int maxIndex = xmlConfiguration.getMaxIndex("dataTypes.dataType")+1;
            ArrayList dts = new ArrayList();
            for (int i = 0; i < maxIndex; i++) {
            	if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").len") && xmlConfiguration.getBoolean("dataTypes.dataType("+i+").len")){
            		dts.add(xmlConfiguration.getString("dataTypes.dataType("+i+").name"));
                }
            }
            datatypes = (String[]) dts.toArray(new String[dts.size()]);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return datatypes;
	}

	public static String getConnectionStartURL(DBDataBean dataBean) {
		String connectionStartURL = "";
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("connectionStartURL")){
            	connectionStartURL = xmlConfiguration.getString("connectionStartURL");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return connectionStartURL;
	}

    /**
     * @param dataBean
     * @return remarks from DB config file
     */
    public static String getRemarks(DBDataBean dataBean) {
        String remarks = "";
        try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("remarks")){
                remarks = xmlConfiguration.getString("remarks");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return remarks;
    }

	public static String getJDBCDriver(DBDataBean dataBean) {
		String jdbcDriver = "";
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("jdbcDriver")){
            	jdbcDriver = xmlConfiguration.getString("jdbcDriver");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return jdbcDriver;
	}

	public static boolean isColumnExtra(DBDataBean dataBean) {
		boolean columnExtra = false;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("columnExtra")){
            	columnExtra = xmlConfiguration.getBoolean("columnExtra");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return columnExtra;
	}

	public static boolean showDataBases(DBDataBean dataBean) {
		boolean show = false;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("showDataBases")){
            	show = xmlConfiguration.getBoolean("showDataBases");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return show;
	}

	public static boolean canCreateSequence(DBDataBean dataBean) {
		boolean show = false;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("createSequence")){
            	show = xmlConfiguration.getBoolean("createSequence");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return show;
	}

	public static boolean showTableStatus(DBDataBean dataBean) {
		boolean show = true;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("showTableStatus")){
            	show = xmlConfiguration.getBoolean("showTableStatus");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return show;
	}

	public static String getApostropheWrapper(DBDataBean dataBean) {
		String show = "\\'";
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            if(xmlConfiguration.containsKey("apostropheWrapper")){
            	show = xmlConfiguration.getString("apostropheWrapper");
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return show;
	}

	public static GregorianCalendar getCalendar(ResultSet rs, String column) throws SQLException {
		Timestamp createTime = rs.getTimestamp(column);
		if(createTime == null) {
			return null;
		}
    	GregorianCalendar cal = new GregorianCalendar();
    	cal.setTimeInMillis(createTime.getTime());
    	return cal;
	}

	public static DataTypeBean getDataTypeBean(DBDataBean dataBean, String columnType){
		DataTypeBean dataTypeBean = null;
		if(columnType.indexOf("(") != -1) {
			columnType = columnType.substring(0,columnType.indexOf("("));
			System.out.println(columnType);
		}
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration(dataBean.getDbType()+".xml");

            int maxIndex = xmlConfiguration.getMaxIndex("dataTypes.dataType")+1;
            for (int i = 0; i < maxIndex; i++) {
            	String name = xmlConfiguration.getString("dataTypes.dataType("+i+").name");
				if(name.equalsIgnoreCase(columnType)){
					dataTypeBean = new DataTypeBean();
            		dataTypeBean.setName(name);
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").len")) {
            			dataTypeBean.setLen(xmlConfiguration.getBoolean("dataTypes.dataType("+i+").len"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").dec")) {
            			dataTypeBean.setDec(xmlConfiguration.getBoolean("dataTypes.dataType("+i+").dec"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").forceLength")) {
            			dataTypeBean.setForceLength(xmlConfiguration.getBoolean("dataTypes.dataType("+i+").forceLength"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").valueWrapper")) {
            			dataTypeBean.setValueWrapper(xmlConfiguration.getString("dataTypes.dataType("+i+").valueWrapper"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").maxlength")) {
            			dataTypeBean.setMaxlength(xmlConfiguration.getInt("dataTypes.dataType("+i+").maxlength"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").formSize")) {
            			dataTypeBean.setFormSize(xmlConfiguration.getInt("dataTypes.dataType("+i+").formSize"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").formInput")) {
            			dataTypeBean.setFormInput(xmlConfiguration.getString("dataTypes.dataType("+i+").formInput"));
            		}
            		if(xmlConfiguration.containsKey("dataTypes.dataType("+i+").format")) {
            			dataTypeBean.setFormat(xmlConfiguration.getString("dataTypes.dataType("+i+").format"));
            		}
            		break;
                }
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return dataTypeBean;
	}

	public static DBDataBean[] getDBEConfig() {
		DBDataBean[] dbDataBeans = null;
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration("dbe-config.xml");

            int maxIndex = xmlConfiguration.getMaxIndex("dbs.db")+1;
            dbDataBeans = new DBDataBean[maxIndex];
            for (int i = 0; i < maxIndex; i++) {
            	DBDataBean dbDataBean = new DBDataBean();
            	dbDataBean.setNickName(getConfig(xmlConfiguration,"nickName",i));
            	dbDataBean.setUrl(getConfig(xmlConfiguration,"url",i));
            	dbDataBean.setDbType(getConfig(xmlConfiguration,"dbType",i));
            	dbDataBean.setDbName(getConfig(xmlConfiguration,"dbName",i));
            	dbDataBean.setUser(getConfig(xmlConfiguration,"user",i));
            	dbDataBean.setPasswd(getConfig(xmlConfiguration,"password",i));
            	dbDataBean.setEncoding(getConfig(xmlConfiguration,"encoding",i));
            	dbDataBeans[i] = dbDataBean;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbDataBeans;
	}

	public static HashMap getImportTasks() {
		HashMap imports = new HashMap();
		try {
            XMLConfiguration xmlConfiguration = new XMLConfiguration("dbe-config.xml");

            int maxIndex = xmlConfiguration.getMaxIndex("imports.import")+1;
            for (int i = 0; i < maxIndex; i++) {
            	String className = getConfig(xmlConfiguration,"className",i,"imports.import");
            	String name = getConfig(xmlConfiguration,"name",i,"imports.import");
            	imports.put(name, className);
            }

        } catch (ConfigurationException e) {
            //e.printStackTrace();
        }
		return imports;
	}

	private static String getConfig(XMLConfiguration xmlConfiguration,String configNode, int i) {
		return getConfig(xmlConfiguration,configNode, i, "dbs.db");
	}

	private static String getConfig(XMLConfiguration xmlConfiguration,String configNode, int i, String startNode) {
		String value ="";
		if(xmlConfiguration.containsKey(startNode+"("+i+")."+configNode)) {
			return xmlConfiguration.getString(startNode+"("+i+")."+configNode);
		}
		return value;
	}

	/**
     * get Implemetation version
     * @param ctx
     * @return
     */
    public static String getImplementationVersion(ServletContext ctx) {
        String buildReference = "?";
        try {
            InputStream in = ctx.getResourceAsStream(MANIFEST);
            if (in != null) {
                Manifest mfst = new Manifest(in);
                buildReference = mfst.getMainAttributes().getValue(BUILD_REFERENCE);
                in.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return buildReference;
    }

	public static StringBuffer createInsertQuery(HttpServletRequest request, DBDataBean dataBean, String tableName, TableStructureBean tableStructureBean) {
		StringBuffer query = new StringBuffer("INSERT INTO " + tableName + " (");
		ArrayList columns = tableStructureBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(i >0) { query.append(","); }
			query.append(columnBean.getName());
		}
		query.append(") VALUES (");

		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(i >0) { query.append(","); }
			String value = request.getParameter(columnBean.getName());
			if(columnBean.getDataType().toUpperCase().startsWith("SET")) {
				query.append(" '");
				String[] values = StringUtils.split(columnBean.getDim(),",");
				for (int j = 0; j < values.length; j++) {
					value = request.getParameter(columnBean.getName()+"["+j+"]");
					if(value != null) {
					if(j >0) { query.append(","); }
					query.append(value);
					}
				}
				query.append("' ");
			} else {
				appendValues(dataBean,query,columnBean, value);
			}
		}

		query.append(") ");
		return query;
	}

	public static String createInsertQuery(String[] values, DBDataBean dataBean, String tableName, TableStructureBean tableStructureBean, String delimiter) {
		StringBuffer query = new StringBuffer("INSERT INTO " + tableName + " (");
		ArrayList columns = tableStructureBean.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(i >0) { query.append(","); }
			query.append(columnBean.getName());
		}
		query.append(") VALUES (");

		for (int i = 0; i < columns.size(); i++) {
			ColumnBean columnBean = (ColumnBean) columns.get(i);
			if(i >0) { query.append(","); }
			String value = values[i];
			if(columnBean.getDataType().toUpperCase().startsWith("SET")) {
				query.append(" '");
				String[] vals = StringUtils.split(value,",");
				for (int j = 0; j < vals.length; j++) {
					value = vals[j];
					if(value != null) {
					if(j >0) { query.append(","); }
					query.append(value);
					}
				}
				query.append("' ");
			} else {
				appendValues(dataBean,query,columnBean, value);
			}
		}

		query.append(") ");
		query.append(delimiter);
		return query.toString();
	}

    public static boolean isNumeric(String  dataType){
           if(dataType.equalsIgnoreCase("INT")){
               return true;
           } else if(dataType.equalsIgnoreCase("LONG")){
               return true;
           } else if(dataType.equalsIgnoreCase("FIXED")){
               return true;
           } else if(dataType.equalsIgnoreCase("FLOAT")){
               return true;
           } else if(dataType.equalsIgnoreCase("INTEGER")){
               return true;
           } else if(dataType.equalsIgnoreCase("SMALLINT")){
               return true;
           } else if(dataType.equalsIgnoreCase("NUMBER")){
               return true;
           } else if(dataType.equalsIgnoreCase("TINYINT")){
               return true;
           } else if(dataType.equalsIgnoreCase("MEDIUMINT")){
               return true;
           } else if(dataType.equalsIgnoreCase("BIGINT")){
               return true;
           } else if(dataType.equalsIgnoreCase("DOUBLE")){
               return true;
           } else if(dataType.equalsIgnoreCase("DECIMAL")){
               return true;
           }

           return false;
    }


}
