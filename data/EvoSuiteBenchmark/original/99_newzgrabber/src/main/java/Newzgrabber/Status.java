package Newzgrabber;
import Newzgrabber.*;

import java.util.*;

public class Status
{

 private static int INITIALCODE = -5;
 private int CODE=INITIALCODE;
 private String Message;

 public Status(String s)
 {
  Message = s;  
  this.parseStatus();
 }

 public boolean statusOK()
 {
  if(CODE == INITIALCODE) this.parseStatus();
  return(CODE >= 200 && CODE < 300);
 }
   
 public int getCode() 
 {
  return CODE;
 }

 private void parseStatus()
 {
  try
  {
   StringTokenizer st = new StringTokenizer(Message);
   CODE = Integer.parseInt(st.nextToken().trim());
  } 
  catch(Exception e)
  {
   CODE = -1;
  }
 }

}
