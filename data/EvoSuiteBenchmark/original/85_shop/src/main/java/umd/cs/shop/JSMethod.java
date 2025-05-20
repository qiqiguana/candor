package umd.cs.shop;
import java.util.*;

import java.io.*;

public class JSMethod 
{
    /*==== instance variables ====*/

    private JSTaskAtom head;

    private JSListIfThenElse ifThenElseList;
    
    private boolean notDummy;
 
    JSMethod(StreamTokenizer tokenizer)
    {
       head =  new JSTaskAtom(tokenizer); 
       ifThenElseList = new JSListIfThenElse(tokenizer);
       notDummy = true;

    }
    
    JSMethod()
    {
     super();
     notDummy = false;
     
    }
    
    public boolean notDummy()
    {
        return notDummy;
    }
public void setName (String mName )
{
    JSPairIfThen pair;
    for (int i=0; i< ifThenElseList.size();i++) {
      pair= (JSPairIfThen)ifThenElseList.elementAt(i);
      pair.setName(mName+ pair.Name());
    }
}

public void print()
  {
       
       JSUtil.print("(");
       JSUtil.print(":Method ");
       head.print();
       ifThenElseList.print();
       JSUtil.println(")");
       
       
//       JSUtil.flag("<-- method");
  }
  
  public JSTaskAtom head()
  {
    return head;
  }
  
  public JSListIfThenElse ifThenElseList()
  {
    return ifThenElseList;
  }
  
  public JSMethod standarizerMet()
  {
     JSMethod newMet = new JSMethod();
     JSTaskAtom ta = this.head();
     JSListIfThenElse ifTEList = this.ifThenElseList();
     
     newMet.notDummy = this.notDummy();
     newMet.head = ta.standarizerTA();
     newMet.ifThenElseList = ifTEList.standarizerListIfTE();
     
     return newMet;
  }
}

