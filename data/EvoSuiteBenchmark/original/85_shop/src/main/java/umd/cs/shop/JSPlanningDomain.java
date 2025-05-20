package umd.cs.shop;
import java.io.*;

import java.util.*;



public class JSPlanningDomain  

{

    /*==== instance variables ====*/

    private String name;

    private JSListAxioms axioms = new JSListAxioms();

    private JSListOperators operators = new JSListOperators();

    private JSListMethods methods = new JSListMethods();

   /*==== constructor ====*/
    public
    JSPlanningDomain(){}
    
    public
    JSPlanningDomain(StreamTokenizer tokenizer)
    {
 
        String w = JSUtil.readWord(tokenizer,"REading Domain Definition");
        if (w.equals("%%%"))
           throw new JSParserError(); //return;
         
        name=w;   
        if (!JSUtil.expectTokenType(JSJshopVars.leftPar,tokenizer,"Expecting '( ' for planning domain"))
          throw new JSParserError(); //return;
            
        parserOpsMethsAxs(tokenizer);
                
        if (!JSUtil.expectTokenType(JSJshopVars.rightPar,tokenizer," Expecting ')' for planning domain"))
           throw new JSParserError(); //return;
           
    }

   
   public void parserOpsMethsAxs(StreamTokenizer tokenizer)
   {     

    if (!JSUtil.readToken(tokenizer,"Expacting domain definition, operators,methods,axioms"))
         throw new JSParserError(); //return;
    if (tokenizer.ttype != JSJshopVars.leftPar &&  
         tokenizer.ttype != JSJshopVars.rightPar){
        JSUtil.println("parserOpsMethsAxs: expected ( or )");	
        throw new JSParserError(); //return;
    }

    String w;
    JSOperator op;
    JSMethod met;
    JSAxiom ax;
    
    while (tokenizer.ttype != JSJshopVars.rightPar){
        if (!JSUtil.expectTokenType(JSJshopVars.colon,tokenizer,"Expecting ':' for axiom,method or operator definition"))
         throw new JSParserError(); //return;
        
        if (!JSUtil.readToken(tokenizer,"Method/axiom/operator definition expected"))
         throw new JSParserError(); //return;
        
        if (tokenizer.ttype == JSJshopVars.minus){
             ax = new JSAxiom(tokenizer);
             ax.setName( "Axiom_"+ (axioms.size()+1)+ "_");
             axioms.addElement(ax);
        }
        else{
            if (tokenizer.ttype != tokenizer.TT_WORD){
                JSUtil.println("Line : " + tokenizer.lineno() +" method/operator expected");
                throw new JSParserError(); //return;
            }
            else {     
               if (tokenizer.sval.equalsIgnoreCase("operator")){
                op = new JSOperator(tokenizer);
                operators.addElement(op);
               }
              else{
                if (tokenizer.sval.equalsIgnoreCase("method")){
                  met = new JSMethod(tokenizer);
                  met.setName( "Method_"+ (methods.size()+1)+ "_");
                  methods.addElement(met);
                  
                }
                else{
                    JSUtil.println("Line : " + tokenizer.lineno() +" Expecting method or operator found text:" + tokenizer.sval); 
                    throw new JSParserError(); //return;
                
                }
             }
          }
     }
     
     if (!JSUtil.expectTokenType(JSJshopVars.rightPar,tokenizer,"Expecting ')' for Domain definition"))//close op/meth/ax
        throw new JSParserError(); //return;
   
    if (!JSUtil.readToken(tokenizer,"PlanningDomain"))
       throw new JSParserError(); //return;
              
    }

   }
  
  public JSPairPlanTSListNodes solve(JSPlanningProblem prob, Vector listNodes)
  {
    JSPairPlanTState pair = new JSPairPlanTState();
    
    if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning ){
        JSUtil.flag("====== SOLVING A NEW PROBLEM====");
      this.print();
      JSUtil.flag("PROBLEM");
      prob.print();
    }
      
      JSTasks tasks = prob.tasks();
      pair = tasks.seekPlan(new JSTState(prob.state(),new JSListLogicalAtoms(), 
      		                                 new JSListLogicalAtoms()), 
      		                    this, new JSPlan(), listNodes);
      
     return new JSPairPlanTSListNodes(pair,listNodes);
  }
    
 
 public JSListPairPlanTStateNodes solveAll(JSPlanningProblem prob,boolean All)
  {
 
    JSListPairPlanTStateNodes allPlans;
    JSTasks tasks = prob.tasks();
    allPlans = tasks.seekPlanAll(new JSTState(prob.state(),new JSListLogicalAtoms(),new JSListLogicalAtoms()),this,All);
   
    return allPlans;
  }  
    
public void print()
  {
     
       JSUtil.print("(");
       JSUtil.print("make-domain ");
       JSUtil.println(name+" ");
       axioms.print();
       operators.print();
       methods.print();
       JSUtil.println(")");
  }

  public JSListMethods methods()
    {
	return methods;
    }

 public JSListAxioms axioms()
    {
	return axioms;
    }
   public JSListOperators operators()
    {
	return operators;
    }

}

