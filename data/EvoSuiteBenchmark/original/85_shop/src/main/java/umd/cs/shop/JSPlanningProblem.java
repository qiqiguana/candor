package umd.cs.shop;
import java.io.*;



public class JSPlanningProblem  

{

    /*==== instance variables ====*/

    private String name;

    private JSState state;

    private JSTasks tasks;

    private String domainName;

    

    /*==== constructor ====*/

    JSPlanningProblem(StreamTokenizer tokenizer)
    {
   
        name = JSUtil.readWord(tokenizer,"Expecting Planning Problem name");
        if (name.equals("%%%")){
            JSUtil.println("Line : "+tokenizer.lineno() +" Expecting Planning Problem name");
            throw new JSParserError(); //return;
        }            
        domainName = JSUtil.readWord(tokenizer,"Planning Problem");
        if (domainName.equals("%%%")){
            JSUtil.println("Line : "+tokenizer.lineno() +" Expecting Planning Domain name");
            throw new JSParserError(); //return;
        }   
       
        state = new JSState(tokenizer);
       
        tasks = new JSTasks(tokenizer); 
        if (!JSUtil.expectTokenType(JSJshopVars.rightPar,tokenizer,"Expecting ')' for PlanningProblem"))
            throw new JSParserError(); //return;
        
       // JSUtil.flagParser("Planning problem parsed succesfully");

    }
    
    public void assignState(JSState aState)
    {
        state = aState;
    }

    public void makeTask(JSTaskAtom pred)
    {
      tasks = new JSTasks();
      tasks.addElement(pred);
    }

    
     public JSState state()
     {
       return state;
     }
     
     public JSTasks tasks()
     {
       return tasks;
     }
     
      public String Name()
     {
       return name;
     }
     
     public void print()
  {
      
       JSUtil.print("(");
       JSUtil.print("make-problem ");
       JSUtil.print(name);
       JSUtil.println(domainName);
       state.print();
       tasks.print();
       JSUtil.println(")");
  }
}

