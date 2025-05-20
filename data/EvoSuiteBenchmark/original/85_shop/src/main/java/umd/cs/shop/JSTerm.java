package umd.cs.shop;
import java.io.*;

import java.util.*;

import java.lang.*;



public class JSTerm extends JSPredicateForm

{

    /*==== instance variables ====*/

    private boolean isVariable;

    private boolean isConstant;

    private boolean isFunction;
    
    private boolean isEval;
    
    //private boolean isNumber;//isNumber -> isConstant

    JSTerm()
    {
	super();
    }

    

    JSTerm(StreamTokenizer tokenizer)
    {
        // (<task-name> <term1> ... <termN>)
      
       super();
      isEval=false;
      int ival;
      double dval;
      String varname;
     

        if(!JSUtil.readToken(tokenizer,"Expecting Term"))
              throw new JSParserError(); //return;
             
        if (tokenizer.ttype != JSJshopVars.leftPar){ 
            if (tokenizer.ttype == JSJshopVars.interrogation){
              if (!JSUtil.readToken(tokenizer, "Expecting variable name in term"))
                  throw new JSParserError(); // return;
             tokenizer.pushBack();    
             varname =  JSUtil.readWord(tokenizer,"Expecting variable name in term");   
              this.addElement("?"+varname);  

              isVariable = true;
              isConstant = false;
              isFunction = false;  
              return; // success
            }
              
        if (tokenizer.ttype == JSJshopVars.percent){
            if (!JSUtil.expectTokenType(tokenizer.TT_WORD,tokenizer," Expecting word as term"))
               throw new JSParserError(); //return;
            this.addElement("%" + tokenizer.sval);  
        } 
        else if (tokenizer.ttype == tokenizer.TT_NUMBER)
              this.addElement(new Double (tokenizer.nval).toString());
                 
        else if (tokenizer.ttype == tokenizer.TT_WORD){
             tokenizer.pushBack();
             varname = JSUtil.readWord(tokenizer,"Expecting constant symbol as term");
             this.addElement(varname);
             }
        else {
             JSUtil.println("Line : "+ tokenizer.lineno() +" Term expected");	
             throw new JSParserError(); //return;
        }
        
        isVariable = false;
        isConstant = true;
        isFunction = false;  

        return; // success
                  
        }
       
        isVariable = false;
        isConstant = false;
        isFunction = true;         

       
        if (tokenizer.ttype != JSJshopVars.leftPar){
                JSUtil.println("Line : "+ tokenizer.lineno() +" Expected (");
                throw new JSParserError(); //return;
        }
        if(!JSUtil.readToken(tokenizer,"Expecting Term"))
               throw new JSParserError(); //return;
        
        if ((tokenizer.ttype == tokenizer.TT_WORD)
             && (tokenizer.sval.equalsIgnoreCase("call"))) {
             
             isEval=true;
             if(!JSUtil.readToken(tokenizer,"Expecting Term"))
                throw new JSParserError(); //return;
        }
        
        if ((tokenizer.ttype == tokenizer.TT_WORD)
             && (tokenizer.sval.equalsIgnoreCase("list"))) {
             
             if(!JSUtil.readToken(tokenizer,"Expecting Term"))
                throw new JSParserError(); //return;
               
             if (tokenizer.ttype ==JSJshopVars.rightPar) {
                this.addElement("nil");
                makeConstant();
                return;
             }
             
             tokenizer.pushBack();
             this.addElement(".");
             this.addElement(new JSTerm(tokenizer));
             this.addElement( parseList (tokenizer));
             return;
               
        }
        
        
        if (tokenizer.ttype == JSJshopVars.lessT){
            if(!JSUtil.readToken(tokenizer,"Expecting Term")){
               throw new JSParserError(); //return;
            }
           if (tokenizer.ttype == JSJshopVars.equalT){
            this.addElement("<=");
           }
           else{
            this.addElement("<");
            tokenizer.pushBack();
           }
        }
        else{
            if (tokenizer.ttype == JSJshopVars.greaterT){
                if(!JSUtil.readToken(tokenizer,"Expecting Term")){
                   throw new JSParserError(); //return;
                }
                if (tokenizer.ttype == JSJshopVars.equalT){
                    this.addElement(">=");
                }
                else{
                    this.addElement(">");
                    tokenizer.pushBack();
                }
             }
             else{
                if (tokenizer.ttype == JSJshopVars.plus ||
                    tokenizer.ttype == JSJshopVars.minus||
                    tokenizer.ttype == JSJshopVars.slash||
                    tokenizer.ttype == JSJshopVars.astherisk
                    )
                    
                   this.addElement(JSUtil.stringTokenizer(tokenizer));
                
                else{
                    if (tokenizer.ttype != tokenizer.TT_WORD){
                         JSUtil.println("Line : "+ tokenizer.lineno() +" Term expected");	
                         throw new JSParserError(); //return;
                    }
                    tokenizer.pushBack();
                    String w = JSUtil.readWord(tokenizer,"JSTerm");
                    if (w.equals("%%%")){
                      JSUtil.println("Line : "+ tokenizer.lineno() +" Term expected");	
                      throw new JSParserError(); //return;
                      }
                    this.addElement(w);
               }
             }
        }
        try{
          tokenizer.nextToken();
          while (tokenizer.ttype!= JSJshopVars.rightPar){
            tokenizer.pushBack();
            this.addElement(new JSTerm(tokenizer));
            tokenizer.nextToken();
          }
          } catch (Exception e) {
            JSUtil.println("JSTerm: Error reading control parameters: " + e);
            System.exit(1);
        }
    }

  public JSTerm parseList(StreamTokenizer tokenizer){
    
    JSTerm t=new JSTerm();
    JSTerm cdr,r;
    
    if ( !JSUtil.readToken(tokenizer,"Expecting elements of the list"))
        throw new JSParserError(); //return new JSTerm();
    
    if (tokenizer.ttype ==JSJshopVars.rightPar) {
                t.addElement("nil");
                t.makeConstant();
                return t;
    }
    
  //   if (tokenizer.ttype == tokenizer.TT_WORD && tokenizer.sval.equals(".")) {
    if (tokenizer.ttype ==JSJshopVars.dot) {
        
      cdr= new JSTerm(tokenizer)  ;
      if (!JSUtil.expectTokenType(JSJshopVars.rightPar,tokenizer,"Expecting ')' for term."))
          throw new JSParserError(); //return new JSTerm();
      if (cdr.isVariable || ((String)(cdr.elementAt(0))).equalsIgnoreCase("nil") || ((String)(cdr.elementAt(0))).equals("."))   
        return cdr;
      t.addElement(".");
      t.addElement(cdr);
      r=new JSTerm();
      r.addElement("nil");
      r.makeConstant();
      t.addElement(r);
      return t;
        
    }        
             
    tokenizer.pushBack();
    t.makeFunction();
    t.addElement(".");
    t.addElement(new JSTerm(tokenizer));
    t.addElement( parseList (tokenizer));
    return t;
    
    
  }
  private void printList (JSTerm t)
  {
     String str="nil" ;
     JSTerm tt;
     if (str.equalsIgnoreCase((String)t.elementAt(0)))
       {
        JSUtil.print(") ");
        return;
        }
    if (t.isVariable)
       {
        JSUtil.print(" . ");
        t.print();
        JSUtil.print(") ");
        return;
        } 
    
    tt= (JSTerm) t.elementAt(1);
    tt.print();
    JSUtil.print(" ");
    tt= (JSTerm) t.elementAt(2);
    printList(tt);     
        
   }
  
  
  public void print()
  {
   
    if (this.isVariable || this.isConstant)
    {
        JSUtil.print(this.elementAt(0)+" ");
    }
    else
    {
       JSUtil.print("(");
       if (this.elementAt(0).equals("."))
         {
              JSUtil.print("list ");
              printList(this);
              return;
         } 
       
       if (this.isEval)
         JSUtil.print("call ");
         
       JSUtil.print(this.elementAt(0)+" ");
       JSTerm el;
       for (short i = 1; i < this.size(); i++)
       {
        el = (JSTerm) this.elementAt(i);
        el.print();
       }
       JSUtil.print(")");
    }
  }
  
  public JSTerm cloneT()
  {
    JSTerm  nt = new JSTerm();
    // Added 11/29/2000
    nt.isEval=this.isEval;
    // Additions end
    if (this.isConstant()){
        nt.addElement(this.elementAt(0));
        nt.makeConstant();
        return nt;
    }
    
    if (this.isVariable()){
        nt.addElement(this.elementAt(0));
        nt.makeVariable();
        return nt;
    }
    
    nt.addElement(this.elementAt(0));
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            nt.addElement(ti.cloneT());
        }
        nt.makeFunction();
    return nt;
  }
  
  
  
  public JSTerm applySubstitutionT(JSSubstitution alpha)
  {
    if (this.isConstant()){
        return this.cloneT();
    }
    
    if (this.isVariable()){
        JSTerm nt = alpha.instance(this);
        return nt.cloneT();
    }
    /*if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
         this.print();*/
    //JSUtil.flagPlanning("<-- applySubstitutionT");
    JSTerm nt = new JSTerm();
    nt.makeEval(this.isEval);
    nt.makeFunction();
    nt.addElement(this.elementAt(0));
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
           /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
                    nt.print();
            JSUtil.flagPlanning("<--");*/
            ti = (JSTerm) this.elementAt(i);
            nt.addElement(ti.applySubstitutionT(alpha));
        }
    /*if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
             nt.print();
    JSUtil.flagPlanning("<-- final");*/    
    return nt;
  }
  
  public JSSubstitution matches(JSTerm t)
  /* t is a ground JSJSTerm*/
  {
    return matches(t,new JSSubstitution());
  }
  
  public JSSubstitution matches(JSTerm t, JSSubstitution alpha)
  /* t is a ground JSTerm*/
  {
    
    JSSubstitution beta = new JSSubstitution();
    
    if (this.isConstant()){
        if (!t.isConstant()){
           return t.matches(this,alpha);//modified 22/01
        }
        if (!this.equals(t))
        {
            beta.assignFailure();
        }
        return beta;
    }
    
    if (this.isVariable()){
        JSTerm nt = alpha.instance(this);
        if ( ! nt.equals(this))
        {
            return nt.matches(t,alpha);
        }
        beta.addElement(new JSPairVarTerm(this,t));
        return beta;
    }
    // Added 22/01
    if (t.isVariable)
      return t.matches(this);
    
    
    if (!t.isFunction()){               // this is compound
            beta.assignFailure();
            return beta;
        }
        if (this.size() != t.size())
        {
            beta.assignFailure();
            return beta;
        }
        String functor = (String) this.elementAt(0);
        if (!functor.equalsIgnoreCase((String)t.elementAt(0)))
        {
            beta.assignFailure();
            return beta;
        }
        JSTerm ti;
        JSSubstitution gama = new JSSubstitution();
        JSSubstitution newAlpha = (JSSubstitution) alpha.clone();
            
        for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            if (!ti.equals(t.elementAt(i))) {
                gama = ti.matches((JSTerm) t.elementAt(i), newAlpha);
                if (gama.fail())
                {
                    gama.assignFailure();
                    return gama;
                }
                beta.addElements(gama);
                newAlpha.addElements(gama);
            }
        }
        return beta;
  }
  
 public boolean equals(JSTerm t)
  /* t is not necessarily a ground JSTerm*/
  {
   /* if (JSJshopVars.flagLevel > 8 ){
        JSUtil.print("JSTerm:");
        this.print();
        JSUtil.print(" equals: ");
        t.print();
    }*/
   // JSUtil.flag10("?");
    if (this.isConstant()){
        if (!t.isConstant()){
            return false;
        }
        String name = (String) this.elementAt(0);
        String name1 = (String) t.elementAt(0);
        return name.equalsIgnoreCase(name1);
        
    }
    
    if (this.isVariable()){
        if (!t.isVariable()){
            return false;
        }
        String name = (String) this.elementAt(0);
        String name1 = (String) t.elementAt(0);
        return name.equalsIgnoreCase(name1);
    }
    
    if (!t.isFunction()){               // this is compound
            return false;
        }
        if (this.size() != t.size())
        {
            return false;
        }
        String functor = (String) this.elementAt(0);
        String functor1 = (String) t.elementAt(0);
        if (!functor.equalsIgnoreCase(functor1))
        {
            return false;
        }
        JSTerm ti;   
        JSTerm oti;
        for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            oti = (JSTerm) t.elementAt(i);
            if (!ti.equals(oti)) {
                return false;
            }
        }
        return true;
  } 
  
  public boolean isVariable()
  {
    return isVariable;
  }
  
  public boolean isConstant()
  {
    return isConstant;
  }
  
  public boolean isFunction()
  {
    return isFunction;
  }
  
  public boolean isEval()
  {
    return isEval;
  }
  
  public void makeFunction()
  {
     isVariable = false;
     isConstant = false;
     isFunction = true; 
  }
  
  public void makeVariable()
  {
     isVariable = true;
     isConstant = false;
     isFunction = false; 
  }
  
  public void makeConstant()
  {
     isVariable = false;
     isConstant = true;
     isFunction = false; 
  }
  
  public void makeEval( boolean evaluable)
  {
     isEval = evaluable; 
  }
  public StringBuffer toStr()
  {
    StringBuffer res = new StringBuffer();
       
    if (this.isVariable || this.isConstant)
    {
        res.append( this.elementAt(0)+" ");
    }
    else
    {
       res.append("(");
       res.append( this.elementAt(0)+" ");
       JSTerm el;
       for (short i = 1; i < this.size(); i++)
       {
        el = (JSTerm) this.elementAt(i);
        res.append(el.toStr());
       }
       res.append(")");
    }
    
//    JSUtil.flag10("Str. read:"+res);
    return res;
  }
  
  public boolean isGround()
  {
    if (this.isConstant()){
        return true;
    }
    
    if (this.isVariable()){
        return false;
    }
    
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            if (!ti.isGround()){
                return false;
            }
        }
    return true;
  }
  
  public JSTerm standardizerTerm()
  {
    JSTerm  nt = new JSTerm();
    nt.makeEval(this.isEval);
    if (this.isConstant()){
        nt.addElement(this.elementAt(0));
        nt.makeConstant();
        return nt;
    }
    
    if (this.isVariable()){
        nt.addElement(this.elementAt(0) + String.valueOf(JSJshopVars.VarCounter));
        nt.makeVariable();
        return nt;
    }
    
    nt.addElement(this.elementAt(0));
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            nt.addElement(ti.standardizerTerm());
        }
        nt.makeFunction();
    return nt;
  }

  //Added 11/29/2000
 public JSTerm call() 
 {
   
   JSTerm t,f;
   
     
   if (this.isConstant())
     return ((JSTerm)this.clone());
   // Added on 12 Apr
   if (!this.isEval() && this.isVariable())
     return ((JSTerm)this.clone());
  // Additions end   
   if (this.isVariable())
     return new JSTerm();
   // Added on 12 apr
   f= new JSTerm();
   f.makeFunction();
   f.addElement(elementAt(0));
   for (int i = 1 ; i< size(); i++)
     f.addElement(((JSTerm)elementAt(i)).call()); 
   
   if (!this.isEval() )
     return f;
     
   String op= new String ((String)f.elementAt(0));
   JSTerm operant1 = ((JSTerm)f.elementAt(1)).call(); 
   if (operant1.size()==0 && !(op.equalsIgnoreCase("not"))) {
   // JSUtil.println("operan1 failed for " + op + "the operand was :");
   // JSUtil.println("*******");
   // ((JSTerm)f.elementAt(1)).print(); 
   // JSUtil.println("*******");
    return new JSTerm();}
   JSTerm operant2;
   if (JSEvaluate.OperantNum(op) > 1 ) {
        operant2 = ((JSTerm)f.elementAt(2)).call(); 
        if (operant2.size()==0) {
         //   JSUtil.println("operan2 failed for " + op + "the operand was :");
         //   JSUtil.println("*******");
         //   ((JSTerm)f.elementAt(2)).print(); 
         //   JSUtil.println("*******");
            return new JSTerm();}
        t = JSEvaluate.applyOperator(op,operant1, operant2);
   }
   else
       t = JSEvaluate.applyOperatorUnary(op,operant1); 
  
  //JSUtil.println("Operation for " + op + "succeded result is  :");
   // JSUtil.println("$$$$$$$$");
  //  if (t.size() > 0)t.print();
  //  else JSUtil.print("nil");
  //  JSUtil.println("$$$$$$$$");

  return t;
    
 }   
 // Additions ended
}
