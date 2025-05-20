package Newzgrabber;
import Newzgrabber.*;

import java.io.*;
import java.util.*;


public class IniUtility
{

 public static Vector getProperties(String prop,String fn)
 {
  try
  {
   BufferedReader br = new BufferedReader(new FileReader(fn));
   String NextLine = null;
   boolean inprop = false;
   Vector v = new Vector();
   while((NextLine = br.readLine()) != null)
   {
    String TrimLine = NextLine.trim();
    if(TrimLine.toLowerCase().equals("[" + prop.toLowerCase() + "]"))
    {
     inprop = true;
     continue;
    }
    if(inprop)
    {
     if(TrimLine.startsWith("[")) break;
     try
     {
      StringTokenizer st = new StringTokenizer(TrimLine,"=");
      String key = st.nextToken();
      String value = st.nextToken();
      Object[] oa = new Object[2];
      oa[0] = key;
      oa[1] = value;
      v.add(oa);
     }
     catch(Exception e) { continue; }
    }
   }
   br.close(); 
   return v;
  }
  catch(Exception ve) 
  {
   return null;
  }
 }
 
 public static Vector getProperties(String prop)
 {
  return getProperties(prop,"Newzgrabber.ini");
 }

}