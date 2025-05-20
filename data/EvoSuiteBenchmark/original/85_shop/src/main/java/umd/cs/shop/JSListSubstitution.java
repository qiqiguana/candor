package umd.cs.shop;
import java.util.*;

import java.io.*;



public class JSListSubstitution extends Vector

{

    /*==== instance variables ====*/

    private String label;
   


    
JSListSubstitution()
   {
      super();
     
    }

    
public boolean fail()
  {
      return (this.size()== 0); 
  }
      
 public void print()
   {
    
       JSUtil.print("(");
       
       JSSubstitution s1;
       Enumeration s = elements();
       while(s.hasMoreElements())
       {
         s1 = (JSSubstitution) s.nextElement();
         if( s1!=null)
          s1.print();
       }
       JSUtil.println(")");
  }
  
     
  
}

