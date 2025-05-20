package framework;

import framework.util.PropertyUtils;
//import org.apache.commons.beanutils.PropertyUtils;
import framework.persistence.jdbc.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
//import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Map;
import framework.base.ValueListHandler;
import framework.util.XMLUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.util.ResourceBundle;
import java.lang.reflect.Field;
import sun.misc.SoftCache;
//import java.io.File;

////////////////////////////////////////////////////////////////////////////////

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    String charsetName;
    
    StreamGobbler(InputStream is, String charsetName, String type)
    {
        this.is = is;
        this.type = type;
        this.charsetName = charsetName;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is, charsetName);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}

public class Test 
{

////////////////////////////////////////////////////////////////////////////////
// Do not work ... arg!
  public void reloadApplicationResources()
  {
    Class klass = ResourceBundle.getBundle("ApplicationResources").getClass().getSuperclass();
    Field field = null;
    try 
    {
      field = klass.getDeclaredField("cacheList");
    } 
    catch (NoSuchFieldException noSuchFieldEx) 
    {
      System.out.println(this.getClass().getName()+" : "+noSuchFieldEx.getMessage());
    }
    
    field.setAccessible(true);  // autoriser l'accs au cache
    SoftCache cache = null;

    try 
    {
      cache = (SoftCache)field.get(null);
      System.out.println("Cache size="+cache.size());
      System.out.println("Cache content="+cache.values());
    } 
    catch (IllegalAccessException illegalAccessEx) 
    {
      System.out.println(this.getClass().getName()+" : "+illegalAccessEx.getMessage());
    }

    cache.clear();
    System.out.println("Cache size after clear="+cache.size());
    field.setAccessible(false);   // Ne pas oublier de refuser l'accs  
  }

////////////////////////////////////////////////////////////////////////////////
  public static String convert(int r, int g, int b)
  {
    String[] RGB = new String[256];
    String[] hex = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    int k=0;
    for (int i = 0; i < 16; i++) 
    {	
      for (int j = 0; j < 16; j++) 
      {		
        RGB[k] = hex[i] + hex[j];
        k++;
      }
    }

    String rr = RGB[r];
    String gg = RGB[g];
    String bb = RGB[b];
    
    return "#" + rr + gg + bb;	
  }

////////////////////////////////////////////////////////////////////////////////
  public static void digesterLimitation()
  throws Exception
  {
    View view = new View();
//   View2 view = new View2();
    ValueListHandler vlh = new ValueListHandler();
    Map map = PropertyUtils.describe(view);
    System.out.println(String.valueOf(map));
  }

////////////////////////////////////////////////////////////////////////////////
  public static void arg()
  throws Exception
  {
    BeanInfo beanInfo = Introspector.getBeanInfo(framework.persistence.jdbc.Component.class);
    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < descriptors.length; i++) 
    {
      String name = descriptors[i].getName();
      System.out.println("name="+name);
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public static void runtime(String command)
  throws Exception
  {
    String osName = System.getProperty("os.name" );
    String[] cmd = new String[3];
    String charsetName = "UTF-8";

    if( "Windows 2000".equalsIgnoreCase(osName) || "Windows NT".equalsIgnoreCase(osName) )
    {
        cmd[0] = "cmd.exe" ;
        cmd[1] = "/C" ;
        cmd[2] = command;
        charsetName = "Cp437";
    }
    else if( "Windows 98".equalsIgnoreCase(osName) || "Windows 95".equalsIgnoreCase(osName) )
    {
        cmd[0] = "command.com" ;
        cmd[1] = "/C" ;
        cmd[2] = command;
        charsetName = "Cp437";
    }
    else
    {
      System.out.println("Unknown OS: "+osName);
      return;
    }
    
    Runtime rt = Runtime.getRuntime();
    System.out.println("Executing " + cmd[0] + " " + cmd[1] 
                       + " " + cmd[2]);
    Process proc = rt.exec(cmd);
    // any error message?
    StreamGobbler errorGobbler = new 
        StreamGobbler(proc.getErrorStream(), charsetName, "ERROR");            
    
    // any output?
    StreamGobbler outputGobbler = new 
        StreamGobbler(proc.getInputStream(), charsetName, "OUTPUT");
        
    // kick them off
    errorGobbler.start();
    outputGobbler.start();
                            
    // any error???
    int exitVal = proc.waitFor();
    System.out.println("ExitValue: " + exitVal); 
  }

////////////////////////////////////////////////////////////////////////////////
  public static void empDeptTest()
  throws Exception
  {
    Emp emp = new Emp();
    emp.setComm((double)10.5);
    emp.setDeptNo(new BigDecimal(100));
    emp.setEmpNo("A117");
    emp.setHireDate(new java.sql.Date(System.currentTimeMillis()));
    emp.setJob("Engineer");
    emp.setMgr(new Integer(99));
    emp.setSal(new Double(99.99));
    emp.setLastLogin(new Timestamp(System.currentTimeMillis()));
    
    Dept dept = new Dept();
    dept.setDeptNo(new BigDecimal(100));
    dept.setDName("IT");
    dept.setLoc("San Francisco");
    emp.setDeptObject(dept);
    
    System.out.println(XMLUtils.toString(emp.toXML()));
  }
  
////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args)
  {
    if (true) return;
    try
    {
      System.out.println("TRY");
//      empDeptTest();
/*
      File url1 = new File("M:/javabb/web/WEB-INF/src/views\\com.acnielsen.nsp.por.cma.Companyview.xml");
      File url2 = new File("M:/javabb/web/WEB-INF/src/views/com.acnielsen.nsp.por.CMA.CompanyView.xml");
//      File url2 = new File("M:/javabb/web/WEB-INF/src/globalParameters.xml");
      System.out.println("url1="+url1);
      System.out.println("url2="+url2);
      System.out.println("url1==url2?="+url1.equals(url2));
*/      
      
//      System.out.println(String.valueOf(Charset.availableCharsets()));
//    System.out.println(convert(100, 200, 250));
//      digesterLimitation();
//      arg();
      //runtime("dir");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      System.out.println("FINALLY");
    }
  }
  





}