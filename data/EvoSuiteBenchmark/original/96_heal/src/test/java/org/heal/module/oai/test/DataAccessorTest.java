/*
 * Created on Dec 14, 2004
 *
 */
package org.heal.module.oai.test;

import javax.sql.DataSource;

import junit.framework.TestCase;

import com.ora.jsp.sql.DataSourceWrapper;

/**
 * @author Seth Wright
 *
 */

public class DataAccessorTest extends TestCase {

	public void testDataAccessor() throws Exception {		
		DataSource ds = null;
//		String jdbcDriverClassName = null;
//		String jdbcURL = null;	
		String jdbcUser = null;
		String jdbcPassword = null;

		String jdbcDriverClassName = "com.thinweb.tds.Driver";
		String jdbcURL = "jdbc:twtds:sqlserver://localhost:9229/healdev";
		
		if(jdbcURL == null) {
//			jdbcURL = "jdbc:odbc:heal";
		}
		if(jdbcDriverClassName == null) {
//			jdbcDriverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
		}
		if(jdbcUser == null) {
			jdbcUser = "swright";
		}
		if(jdbcPassword == null) {
			jdbcPassword = "Jaheq@#e";
		}
		// use the DataSourceWrapper from the O'Reilly JSP library
		ds = new DataSourceWrapper(jdbcDriverClassName, jdbcURL, jdbcUser, jdbcPassword);
		
//		Connection con = ds.getConnection();
//		Statement stmt = con.createStatement();

//		stmt.executeQuery("blinky");
	}

}
