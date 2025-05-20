package framework.persistence.jdbc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.sql.rowset.CachedRowSet;
//import com.sun.rowset.CachedRowSetImpl;
//import oracle.jdbc.rowset.OracleCachedRowSet;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Collection;

import framework.util.jdbc.Parameter;
import framework.util.jdbc.JDBCUtils;
import framework.util.FileUtils;

public class Test 
{



  public static void main(String[] args)
  {
    Connection connection = null;
    long start = 0;
    long end = 0;
    
    try 
    {
      Collection files = FileUtils.listFiles("M:/javabb/web/WEB-INF/src", "est", false);
      Iterator it = files.iterator();
      while (it.hasNext()) 
      {
        Object item = (Object)it.next();
        System.out.println(String.valueOf(item));
        
      }


/*
      View view = new View();
      view.setSqlQuery("hahaha");
      System.out.println("Property="+org.apache.commons.beanutils.PropertyUtils.getProperty(view, "sqlQuery"));
*/
/*
      connection = JDBCPool.getInstance().getConnection();

      Collection result = JDBCHelper.getDatabaseTables(connection, null);
      Iterator it = result.iterator();
      while (it.hasNext()) 
      {
        Map table = (Map)it.next();
        System.out.println(String.valueOf(table));
      }
*/      
/*      
      Map keywords = JDBCHelper.getDatabaseKeywords();
      System.out.println(String.valueOf(keywords));
      String sqlQuery = StringUtils.prettyPrint(keywords, "select * from test_table", "<b>", "</b>", "toUpperCase");
      System.out.println(sqlQuery);
*/
//      System.out.println(JDBCHelper.prettyPrint(connection, "select * from test_table", "111", "222", "toUpperCase"));
/*
System.out.println("Autocommit="+connection.getAutoCommit());      
System.out.println(JDBCHelper.getRowCountFromQuery(connection, "select * from test_table", null, null, null));
      autocommit(connection);
System.out.println(JDBCHelper.getRowCountFromQuery(connection, "select * from test_table", null, null, null));
*/
//      insertRows(connection);
//      comparePositioning(connection);
     
      System.out.println("********* Main OK");
    } 
    catch (Exception ex) 
    {
      try { connection.rollback(); } catch(Exception exe) {}
      ex.printStackTrace();
    } 
    finally 
    {
//      pool.releaseConnection(connection);
    }
    
  }
  

////////////////////////////////////////////////////////////////////////////////
  public static ResultSet absolute(Connection connection)
  throws Exception 
  {
    String sql = "select * from MRPO_ADDRESS";

//    String sql = "select * from TPPO_REF_CODE_LBL";
//    String sql = "select * from TPPO_REF_COMBO";
    
    ResultSet result = JDBCUtils.executeQuery(connection, sql, null, null, null, ResultSet.TYPE_SCROLL_INSENSITIVE);

    return result;
  } 

////////////////////////////////////////////////////////////////////////////////
  public static void comparePositioning(Connection connection)
  throws Exception 
  {
    int index = 10;//74500;      
    long start = 0;
    long end = 0;
    long memUsage = 0;
//    String sql = "select RCL_REF_RDO_ID from TPPO_REF_CODE_LBL";
//    String sql = "select RDO_ID from TPPO_REF_COMBO";
    String sql = "select * from test_table";
//    String sql = "select * from db";

memUsage = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024;    
System.out.println("[MEMORY USAGE]>> "+memUsage);

//    PreparedStatement st = connection.prepareStatement(sql);
//    PreparedStatement st = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    PreparedStatement st = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//    PreparedStatement st = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
    start = System.currentTimeMillis();
    ResultSet rs = st.executeQuery();
    end = System.currentTimeMillis();
    System.out.println("Query Time ="+(end-start));
    rs.setFetchDirection(ResultSet.FETCH_FORWARD);
    rs.setFetchSize(500);
    System.out.println(JDBCUtils.getResultSetType(rs.getType()));

    start = System.currentTimeMillis();
    int count = 0;
      while(rs.next()) count++;
//      rs.last(); count = rs.getRow();
memUsage = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024;    
System.out.println("[MEMORY USAGE]>> "+memUsage);
    end = System.currentTimeMillis();
    System.out.println("Count Time ="+(end-start));
    System.out.println("Rows ="+count);

    start = System.currentTimeMillis();
    if (!(rs instanceof CachedRowSet))   rs.close();
    end = System.currentTimeMillis();
    System.out.println("Close Time ="+(end-start));









    /*
    start = System.currentTimeMillis();  
          OracleCachedRowSet crs = new OracleCachedRowSet();
          crs.populate(rs);
          for (int i=0; i<index; i++) {crs.next(); crs.getObject(1);}
    end = System.currentTimeMillis();
    System.out.println("Time ="+(end-start));
    */
/*
    start = System.currentTimeMillis();  
//          for (int i=0; i<index; i++) {rs.next();}
          rs.absolute(index);
          while (rs.next())  {String toto = ""+rs.getObject(1); }//System.out.println(""+rs.getObject(1));
    end = System.currentTimeMillis();
    System.out.println("Time ="+(end-start));
*/


  }

////////////////////////////////////////////////////////////////////////////////
  public static void insertRows(Connection connection)
  throws Exception
  {
      PreparedStatement st = connection.prepareStatement("insert into test_table values (?)");
      for (int i=30000; i<80000; i++) 
      {
        st.clearParameters();
        st.setInt(1, i);
        st.addBatch();        
      }
      st.executeBatch();
      connection.commit();      
  }

////////////////////////////////////////////////////////////////////////////////
  public static void autocommit(Connection connection)
  throws Exception
  {
      PreparedStatement st = connection.prepareStatement("insert into test_table values (123456)");
      st.execute();
      if (true) throw new SQLException("BOOM!");
      connection.commit();      
  }

////////////////////////////////////////////////////////////////////////////////
  public static void testFetchSize(Connection connection)
  throws Exception
  {
    /*
          ResultSet rset = absolute(connection);
    long start = System.currentTimeMillis();  
    rset.setFetchSize(100);
    System.out.println("Fetch size = "+rset.getFetchSize());      
    rset.absolute(500);
    //rset.setFetchSize(10);
    System.out.println("Fetch size = "+rset.getFetchSize());      
    for (int i=0; i<5; i++) rset.next();
    long end = System.currentTimeMillis();
    System.out.println("Time ="+(end-start));
          rset.close();
    */
  }

////////////////////////////////////////////////////////////////////////////////
  public static void propertyDescriptor()
  {
    try
    {
        BeanInfo beanInfo = null;
        PropertyDescriptor descriptors[] = null;
        try {
            beanInfo = Introspector.getBeanInfo(Parameter.class);
        } catch (IntrospectionException e) {
//            return (new PropertyDescriptor[0]);
          e.printStackTrace();
        }
        descriptors = beanInfo.getPropertyDescriptors();
        if (descriptors == null) {
            descriptors = new PropertyDescriptor[0];
        }
        for (int i=0; i<descriptors.length; i++) 
        {
          System.out.println(descriptors[i].getName());
        }
         
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    
  }
  

////////////////////////////////////////////////////////////////////////////////
// ENTITY
/*
  public static void main9(String[] args)
  {
    GlobalParameters.getDefaultLocale();

    EmpModule module = new EmpModule();
		String entityName = "Emp";
    
    try
    {
      module.connect();

      Entity entity = module.findEntity(entityName);
      System.out.println(String.valueOf(entity));

long start = System.currentTimeMillis();
			Map target = new HashMap();
			target.put("Empno", "1");
			target.put("Ename", "Georges");
			target.put("Job", "Glandeur");
target.put("tutu", "William");
			target.put("Sal", new BigDecimal(10));
			target.put("Hiredate", new java.sql.Date((new java.util.Date()).getTime()));

			entity.create(target);
      System.out.println("***** create OK ! ");

//			target.clear();
			entity.load(target);
      System.out.println("***** create OK ! ");

//			target.remove("Empno");
			target.put("Empno", new BigDecimal("1"));
			target.put("Ename", "William");
			target.remove("Hiredate");
			entity.store(target);
      System.out.println("***** store OK ! ");

			entity.remove(target);
      System.out.println("***** remove OK ! ");

			Collection parameters = new ArrayList();
			parameters.add(new Object[] {"Ename", "=", "TEMP_0"});
			entity.remove(parameters);
      System.out.println("***** remove (parameters) OK ! ");

long end = System.currentTimeMillis();
long total = end-start;
System.out.println("===> TOTAL TIME : "+total);

//			module.commit();

      System.out.println("***** Main END ! ");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      module.disconnect();
    }

  }
*/




}