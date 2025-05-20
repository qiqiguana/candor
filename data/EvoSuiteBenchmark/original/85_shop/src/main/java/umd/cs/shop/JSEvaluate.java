package umd.cs.shop;
import java.io.*;
import java.math.*;
import java.util.*;



public final class JSEvaluate {
    
     /*==== instance variables ====*/

    
    private static boolean fail;
   // private static boolean BothInt;
    
    
    public static float numericValue (JSTerm t ) {
     
        float  floatVal;
        fail=false;
        
        if (t.size()>1){
            fail=true;
            return 0;
        }    
        
        
        String strVal=(String)t.elementAt(0);
        
        try{
            floatVal=Float.valueOf(strVal).floatValue();
        }catch (NumberFormatException e) {
            fail=true;
            return 0 ;
        }
         
     /*   try{
            Integer.valueOf(strVal).intValue();
            BothInt=BothInt&true;
        }catch (NumberFormatException e) {
            BothInt=BothInt & false;
        } */   
        return floatVal;
    }
    
   public static JSTerm addsub (float  operant1 ,float  operant2,int optype){
          
        float sum= operant1+operant2 * optype;
        JSTerm t =new JSTerm();
        t.makeConstant();
        /*if (BothInt)
          t.addElement(String.valueOf((int) sum));
        else*/
          t.addElement(String.valueOf(sum));
          
        return t;
    }
    
   public static JSTerm mult (float  operant1 ,float  operant2){
          
        float sum= operant1*operant2;
        JSTerm t =new JSTerm();
        t.makeConstant();
        /*if (BothInt)
          t.addElement(String.valueOf((int) sum));
        else*/
          t.addElement(String.valueOf(sum));
          
        return t;
    }

    public static JSTerm div (float  operant1 ,float  operant2){
        
        if (operant2== 0)
           return new JSTerm();
        float sum= operant1 / operant2;
        JSTerm t =new JSTerm();
        t.makeConstant();
        t.addElement(String.valueOf(sum));    
        return t;
    }

      
    public static JSTerm greater (float operant1,float operant2){
        
        JSTerm t =new JSTerm();
        
        if  (operant1 <= operant2)  
           return t;
        t.makeConstant();
        t.addElement("t");
        return t;

        
    }
    
   public static JSTerm greaterequal (float operant1,float operant2){
        
        JSTerm t =new JSTerm();
        
        if  (operant1 < operant2)  
           return t;
        t.makeConstant();
        t.addElement("t");
        return t;

        
    }
    
    public static JSTerm equal (float operant1,float operant2){
        
        JSTerm t =new JSTerm();
        
        if  (operant1 != operant2)  
           return t;
        t.makeConstant();
        t.addElement("t");
        return t;

        
    }
    
  public static JSTerm notequal (float operant1,float operant2){
        
        JSTerm t =new JSTerm();
        
        if  (operant1 == operant2)  
           return t;
        t.makeConstant();
        t.addElement("t");
        return t;

        
    }
    
    public static JSTerm minOf(float operant1,float operant2){
        
        float min;
        if (operant1  < operant2 )
           min= operant1;
        else
           min= operant2;
        JSTerm t =new JSTerm();
        
        t.makeConstant();
        /*if (BothInt)
          t.addElement(String.valueOf((int) min));
        else*/
          t.addElement(String.valueOf(min));
          
        return t;

        
    }
    
    public static JSTerm maxOf(float operant1,float operant2){
        
       float max;
        if (operant1  > operant2 )
           max= operant1;
        else
           max= operant2;
        JSTerm t =new JSTerm();
        
        t.makeConstant();
        /*if (BothInt)
          t.addElement(String.valueOf((int) max));
        else*/
          t.addElement(String.valueOf(max));
          
        return t;

        
    }
    
public static JSTerm floor (float operant1){
        
        JSTerm t =new JSTerm();
        int floorVal= (int)operant1;
        operant1= floorVal; // Added in Apr 11
        t.makeConstant();
        t.addElement(String.valueOf(operant1));
        return t;      
    }
    
public static JSTerm ceil (float operant1){
        
        JSTerm t =new JSTerm();
        int ceilVal= (int) operant1;
        if (ceilVal < operant1)
          ceilVal++;
        operant1= ceilVal; // Added in Apr 11
        t.makeConstant();
        t.addElement(String.valueOf(operant1));
        return t;      
    }    
public static JSTerm not (JSTerm operant1){
        
        JSTerm t =new JSTerm();
        if (operant1.size()!=0)           
           return t;
        t.makeConstant();
        t.addElement("t");
        return t;   

        
    }    
public static JSTerm member (JSTerm operant1,JSTerm operant2){
        
        JSTerm t =(JSTerm) operant2.clone();
            
        while (! t.isEmpty()) {
            if (!((String) t.elementAt(0)).equals("."))
                return new JSTerm();
            if (operant1.equals((JSTerm)t.elementAt(1)))
              return (JSTerm) operant1.clone(); 
            t=(JSTerm)t.elementAt(2);
        }    
        
        return new JSTerm();

        
    }
    
 public static JSTerm minElement (JSTerm operant1t){
        
        float element1;
        float min=Float.MAX_VALUE;
        boolean failed=true;
        JSTerm t =(JSTerm) operant1t.clone();
        // BothInt=true;   
        while (! t.isEmpty()) {
            if (!((String) t.elementAt(0)).equals("."))
                break;
            element1=numericValue((JSTerm)t.elementAt(1));
            if (fail)
                return new JSTerm();
            if (element1< min)    
              min=element1;
            t=(JSTerm)t.elementAt(2);
            if (failed)
            failed = false;
        }  
        
        t =new JSTerm();
        if (failed) 
            return t;
        t.makeConstant();
        /*if (BothInt)
            t.addElement(String.valueOf((int) min));
        else*/
            t.addElement(String.valueOf(min));
        return t;   
        
    }   
 
 public static JSTerm maxElement (JSTerm operant1t){
        
        float element1;
        float max=Float.MIN_VALUE;
        boolean failed=true;
        JSTerm t =(JSTerm) operant1t.clone();
        //BothInt=true;   
        while (! t.isEmpty()) {
            if (!((String) t.elementAt(0)).equals("."))
                break;
            element1=numericValue((JSTerm)t.elementAt(1));
            if (fail)
                return new JSTerm();
            if (element1 > max)    
              max=element1;
            t=(JSTerm)t.elementAt(2);
            if (failed)
            failed = false;
        }  
        
        t =new JSTerm();
        if (failed) 
            return t;
        t.makeConstant();
        /*if (BothInt)
            t.addElement(String.valueOf((int) max));
        else*/
            t.addElement(String.valueOf(max));
        return t;   
        
    }      
 public static int OperantNum (String op){
      
      if (op.equalsIgnoreCase("not") || op.equalsIgnoreCase("floor") || op.equalsIgnoreCase("ceil"))
        return 1;
      else
        return 2;
 }
 
 public static JSTerm applyOperator (String op ,JSTerm operant1t ,JSTerm operant2t ){
        
       
        if (!operant1t.isGround() ||!operant2t.isGround())
           return new JSTerm();
        
        if (op.equalsIgnoreCase("member"))
          return member(operant1t,operant2t);
       
        //BothInt=true;
        float operant1 = numericValue(operant1t);
        if (fail)
          return new JSTerm();
          
        float operant2 = numericValue(operant2t);
        if (fail)
          return new JSTerm();
          
       
        if (op.equals("+"))
          return addsub(operant1,operant2,1);
           
        if (op.equals("-"))
          return addsub(operant1,operant2,-1);
          
        if (op.equals("*"))
          return mult(operant1,operant2);
          
        if (op.equals("/"))
          return div(operant1,operant2);
          
        if (op.equals(">"))
          return greater(operant1,operant2);
          
        if (op.equals(">="))
          return greaterequal(operant1,operant2);
        
        if (op.equals("<"))
          return greaterequal(operant2,operant1);
        
        if (op.equals("<="))
          return greater(operant2,operant1);
   
        if (op.equalsIgnoreCase("equal"))
          return equal(operant2,operant1);

        if (op.equalsIgnoreCase("notequal"))
          return notequal(operant2,operant1);
        
        if (op.equalsIgnoreCase("min"))
          return minOf(operant2,operant1);
        
        if (op.equalsIgnoreCase("max"))
          return maxOf(operant2,operant1);
          
       JSUtil.println("Undefined function to be evaluated  in Call statement");
       
        return new JSTerm();
    }
    
    public static JSTerm applyOperatorUnary (String op ,JSTerm operant1t ){
             
        if (!operant1t.isGround())
           return new JSTerm();
        
        if (op.equals("not"))
          return not(operant1t);
      /*
        if (op.equals("Min"))
          return minElement(operant1t);
        
         if (op.equals("Max"))
          return maxElement(operant1t);
      */  
        
        float operant1 = numericValue(operant1t);
        if (fail)
          return new JSTerm();
        
        if (op.equalsIgnoreCase("floor"))
          return floor(operant1);
        
        if (op.equalsIgnoreCase("ceil"))
          return ceil(operant1);   
                 
      JSUtil.println("Undefined function to be evaluated  in Call statement");
       
      return new JSTerm();
    } 
}
