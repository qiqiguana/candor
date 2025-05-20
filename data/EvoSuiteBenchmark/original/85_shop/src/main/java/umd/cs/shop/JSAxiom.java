package umd.cs.shop;
import java.util.*;

import java.io.*;


public class JSAxiom 
{
    /*==== instance variables ====*/

    private JSPredicateForm head;

    private JSListConjuncts tail;
    
    private boolean notDummy;
  
 
    JSAxiom(StreamTokenizer tokenizer)
    {
       head =  new JSPredicateForm(tokenizer);
       
       tail = new JSListConjuncts(tokenizer);
       
       notDummy = true;
       
     }
    
    JSAxiom()
    {
     super();
     notDummy = false;
     
    }
    
    public boolean notDummy()
    {
        return notDummy;
    }

  public void print()
  {    
       JSUtil.print("(");
       JSUtil.print(":- ");
       head.print();
       tail.print();
       JSUtil.println(")");
      
  }
  
  public JSPredicateForm head()
  {
    return head;
  }
  
  public JSListConjuncts tail()
  {
    return tail;
  }
  
  public JSAxiom standarizerAxiom()
  {
     JSAxiom newAx = new JSAxiom();
     JSPredicateForm ta = this.head();
     JSListConjuncts listPs = this.tail();
     
     newAx.notDummy = this.notDummy();
     newAx.head = ta.standarizerPredicateForm();
     newAx.tail = listPs.standarizerListConjuncts();
     
     return newAx;
  }
  
  public void setName (String mName)
  {
       JSListLogicalAtoms listAtom;
    for (int i=0; i< tail.size();i++) {
      listAtom= (JSListLogicalAtoms)tail.elementAt(i);
      listAtom.setName(mName+ listAtom.Name());
    }
    }
}

