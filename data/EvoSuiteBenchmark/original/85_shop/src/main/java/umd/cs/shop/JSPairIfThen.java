package umd.cs.shop;
import java.io.*;



public class JSPairIfThen
{

  /*==== instance variables ====*/

  private JSListLogicalAtoms ifPart;

  private JSTasks thenPart;
  
  private String name;
  
  JSPairIfThen()
  {
    super();
  }
  
  JSPairIfThen(StreamTokenizer tokenizer)
  {
    super();
    ifPart =  new JSListLogicalAtoms(tokenizer);
    thenPart = new JSTasks(tokenizer);
        
  }

  

  public JSListLogicalAtoms ifPart()

  {

    return ifPart;

  }

  

  public JSTasks thenPart()

  {

    return thenPart;

  }

public void setName (String newname)
{
    name=newname;
}

public String Name ()
{
    return name;
}

public void print()
  {
    JSListLogicalAtoms ip = this.ifPart();
    JSTasks tp = this.thenPart();
   JSUtil.println("Name : " + name);
   ip.print();
   tp.print();
  }
  
  public JSPairIfThen standarizerPIT()
  {
    JSListLogicalAtoms ifP = this.ifPart();
    JSTasks thenP = this.thenPart();
    JSPairIfThen newPIT = new JSPairIfThen();
    
    newPIT.ifPart = ifP.standarizerListLogicalAtoms();
    newPIT.thenPart = thenP.standarizerTasks();
    
    return newPIT;
    
  }
  
}  
