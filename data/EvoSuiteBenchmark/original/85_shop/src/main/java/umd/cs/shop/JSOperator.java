package umd.cs.shop;
import java.io.*;

import java.util.*;

public class JSOperator  

{

    /*==== instance variables ====*/

    private JSTaskAtom head;
 
    private JSListLogicalAtoms precondition;
    private JSListLogicalAtoms deleteList;

    private JSListLogicalAtoms addList;
    
    private double  cost=1;
    

    /**** constructor ****/
    
    JSOperator()
    {
        super();
    }

    

    JSOperator(StreamTokenizer tokenizer)
    {              

        head = new JSTaskAtom(tokenizer);
        precondition= new JSListLogicalAtoms(tokenizer);
        deleteList = new JSListLogicalAtoms(tokenizer);
        addList = new JSListLogicalAtoms(tokenizer);
        
        
        if(!JSUtil.readToken(tokenizer,"Expecting ) or operator cost"))
              throw new JSParserError(); //return;
        
        if (tokenizer.ttype == tokenizer.TT_NUMBER)
             cost = tokenizer.nval;
         else
            tokenizer.pushBack();
     }  
    
public void print()
  {
      
       JSUtil.print("(");
       JSUtil.print(":Operator ");
       head.print();
       precondition.print();
       JSUtil.print("-: ");
       deleteList.print();
       JSUtil.print("+: ");
       addList.print();
       //if (cost != 1)
       JSUtil.print(cost + " ");
       JSUtil.println(")");
       
//       JSUtil.flag("<-- operator");
  }
  
  public JSTaskAtom head()
  {
    return head;
  }
  
  public JSListLogicalAtoms addList()
  {
    return addList;
  }
  
  public JSListLogicalAtoms deleteList()
  {
    return deleteList;
  }
  public JSListLogicalAtoms precondition()
  {
    return precondition;
  }
  public double cost()
  {
    return cost;
  }
  
  public JSOperator standarizerOp()
  {
     JSOperator newOp = new JSOperator();
     JSTaskAtom ta = this.head();
     JSListLogicalAtoms pre = this.precondition();
     JSListLogicalAtoms addL = this.addList();
     JSListLogicalAtoms delL = this.deleteList();
     
     newOp.head = ta.standarizerTA();
     newOp.deleteList = delL.standarizerListLogicalAtoms();
     newOp.addList = addL.standarizerListLogicalAtoms();
     newOp.precondition = pre.standarizerListLogicalAtoms();
     newOp.cost = cost;
     return newOp;
  }
}

