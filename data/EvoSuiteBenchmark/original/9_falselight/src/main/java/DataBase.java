import java.sql.*;
//import java.text.*;
//import java.io.*;



public class DataBase
{
  Connection       db;        
  Statement        sql;       
  DatabaseMetaData dbmd;      
                              
                              
                         
  public DataBase(String hostname, String database,String username, String password)
    throws ClassNotFoundException, SQLException
  {
    //String database = datab;
    //String username = user;
    //String password = pass;
    Class.forName("org.postgresql.Driver"); 
    
    db = DriverManager.getConnection("jdbc:postgresql://"+hostname+"/"+database,
                                     username,
                                     password);
    dbmd = db.getMetaData();
    
    System.out.println("Connection to "+dbmd.getDatabaseProductName()+" "+
                       dbmd.getDatabaseProductVersion()+" successful.\n");
    
    sql = db.createStatement();


    /*String sqlText = "create table jdbc_demo (code int, text varchar(20))";
    System.out.println("Executing this command: "+sqlText+"\n");
    sql.executeUpdate(sqlText);

 
    sqlText = "insert into jdbc_demo values (1,'One')";
    System.out.println("Executing this command: "+sqlText+"\n");
    sql.executeUpdate(sqlText);

 
    sqlText = "insert into jdbc_demo values (3,'Four')";
    System.out.println("Executing this command twice: "+sqlText+"\n");
    sql.executeUpdate(sqlText);
    sql.executeUpdate(sqlText);


    sqlText = "update jdbc_demo set text = 'Three' where code = 3";
    System.out.println("Executing this command: "+sqlText+"\n");
    sql.executeUpdate(sqlText);
    System.out.println (sql.getUpdateCount()+
                        " rows were update by this statement\n");


    System.out.println("\n\nNow demostrating a prepared statement...");
    sqlText = "insert into jdbc_demo values (?,?)";
    System.out.println("The Statement looks like this: "+sqlText+"\n");
    System.out.println("Looping three times filling in the fields...\n");
    PreparedStatement ps = db.prepareStatement(sqlText);
    for (int i=10;i<13;i++)
    {
      System.out.println(i+"...\n");
      ps.setInt(1,i);         //set column one (code) to i
      ps.setString(2,"HiHo"); //Column two gets a string
      ps.executeUpdate();
    }
    ps.close();

 
    System.out.println("Now executing the command: "+
                       "select * from jdbc_demo");
    ResultSet results = sql.executeQuery("select * from jdbc_demo");
    if (results != null)
    {
      while (results.next())
      {
        System.out.println("code = "+results.getInt("code")+
                           "; text = "+results.getString(2)+"\n");
      }
    }
    results.close();


    sqlText = "drop table jdbc_demo";
    System.out.println("Executing this command: "+sqlText+"\n");
    sql.executeUpdate(sqlText);


    db.close();
  }

  public static void correctUsage()
  {
    System.out.println("\nIncorrect number of arguments.\nUsage:\n "+
                       "java   \n");
    System.exit(1);
  }
*/
  }
  
public int executeRequeteSQL(String query)
  throws ClassNotFoundException, SQLException{
	  System.out.println("Now executing the command: "+query);
	  ResultSet results =sql.executeQuery(query);
	  if (results != null)
	    {
	      while (results.next())
	      {
	    
	        System.out.println("text = "+results.getString(1)+"\n");
	      }
	    }
	    results.close();
	  return 0;
}
	  
	  
	  
public int executeUpdateSQL(String query)
	  throws ClassNotFoundException, SQLException{
	System.out.println("Now executing the command: "+query);
	
	//sqlText = "insert into jdbc_demo values (1,'One')";
    System.out.println("Executing this command: "+query+"\n");
    sql.executeUpdate(query);
	return 0;
	    
	  }
  
  
}

