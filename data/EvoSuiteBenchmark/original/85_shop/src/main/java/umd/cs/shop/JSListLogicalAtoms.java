package umd.cs.shop;
import java.util.*;

import java.io.*;



public class JSListLogicalAtoms extends Vector

{

    /*==== instance variables ====*/

    private String label=new String();
    private boolean varlist;
    private String name="";
    
    JSListLogicalAtoms()
    {
       super();
       label = "";
       varlist=false;
    }


    JSListLogicalAtoms(StreamTokenizer tokenizer)
    {
     // (<PredicateForm>...<PredicateForm>)
     // Example: ((has-money ?person ?old))
       
       super();
       JSPredicateForm ta;
       label="";
       varlist=false;
       
      
       if(!JSUtil.readToken(tokenizer,"ListLogicalAtoms"))
           throw new JSParserError(); //return; 
       /*  If this is an empty list "nil"    */
       if ((tokenizer.ttype == tokenizer.TT_WORD)&&( tokenizer.sval.equalsIgnoreCase("nil")))
           return; 
       /*  If this is a variable list " ?x"    */    
       if (tokenizer.ttype == JSJshopVars.interrogation){
         tokenizer.pushBack(); 
         JSTerm t= new JSTerm(tokenizer);
         if (t.isEmpty())
           throw new JSParserError(); //return;
         this.addElement(t);
         varlist=true;
         return;
       }
       
       /* If this is a regular list of atoms */
       tokenizer.pushBack(); 
       
       if (!JSUtil.expectTokenType(JSJshopVars.leftPar,tokenizer,
                 "ListLogicalAtoms expecting ("))
            throw new JSParserError(); //return;
       
       if(!JSUtil.readToken(tokenizer,"ListLogicalAtoms"))
           throw new JSParserError(); //return;
       
       // Added 11/28/00    
       if (tokenizer.ttype == JSJshopVars.colon){
        
         if(!JSUtil.readToken(tokenizer," 'first' expected"))
           throw new JSParserError(); //return;  //Error:  There is nothing after :
         if ((tokenizer.ttype != tokenizer.TT_WORD)|| (! tokenizer.sval.equalsIgnoreCase("first")))
           {JSUtil.println("Line : " +tokenizer.lineno()+" Expecting 'first'");
            throw new JSParserError(); //return;  // Error: After colon there must be a word and it must be "first"
           }
         label= tokenizer.sval;
         
         if(!JSUtil.readToken(tokenizer,"Expecting list of logical atoms"))
           throw new JSParserError(); //return; // Error: There must be something after "first"
       }
       //End of additions  
        
       while (tokenizer.ttype != JSJshopVars.rightPar)
        {
            tokenizer.pushBack();
            ta = new JSPredicateForm(tokenizer);
            if (ta.size() != 0)
            {
               this.addElement(ta);
            }
            else{
                JSUtil.flag("Line : " +tokenizer.lineno()+ " ListLogicalAtoms: unexpected Atom");
                throw new JSParserError(); //return;
            }
            if(!JSUtil.readToken(tokenizer,"Expecting ')' "))
              throw new JSParserError(); //return;
        }
      //parsed successfully
     }
     
 public void addElements(JSListLogicalAtoms l)
    {
      for (short i = 0; i < l.size(); i++)
      {
        this.addElement(l.elementAt(i));
      }
      
    }

    
public void print()
  {
    
       if (varlist){
         ((JSTerm)this.elementAt(0)).print();
         return;
       }  
       JSUtil.print("(");
       
       JSPredicateForm el;
       Enumeration e = elements();
       while(e.hasMoreElements())
       {
         el = (JSPredicateForm) e.nextElement();
         if( el!=null)
          el.print();
       }
       JSUtil.println(")  ");
  }
  
  public JSListLogicalAtoms standarizerListLogicalAtoms()
  {
    JSListLogicalAtoms newTs = new JSListLogicalAtoms();
    JSTerm vart;
    
    if (varlist){
        vart=(JSTerm)this.elementAt(0);
        newTs.addElement(vart.standardizerTerm());
        newTs.varlist=true;
        return newTs;        
    }    
     
    
    JSPredicateForm t;
    
    for (short i=0; i < this.size();i++)
    {
        t = (JSPredicateForm) this.elementAt(i);
        newTs.addElement(t.standarizerPredicateForm());
    }
    return newTs;
    
  }
  
   public JSListLogicalAtoms applySubstitutionListLogicalAtoms(JSSubstitution alpha)
  {
    JSListLogicalAtoms nt = new JSListLogicalAtoms();
    JSPredicateForm ti;
    JSPredicateForm nti;
    JSTerm t,newt;
    
   /* if (JSJshopVars.flagLevel > 8) {
     JSUtil.print("apply subs:");
    alpha.print();
    JSUtil.print("On JSListLogicalAtoms:");
    this.print();
    JSUtil.flag("stop");
    }*/
    
    if (varlist) {
      t=(JSTerm)this.elementAt(0);
      newt=alpha.instance(t);
      
      while (! newt.isEmpty()) {
            if (!((String) newt.elementAt(0)).equals("."))
                break;
           ///nti=(JSPredicateForm)newt.elementAt(1);     
           nt.addElement(newt.elementAt(1));
           newt=(JSTerm)newt.elementAt(2);
        }    
      return nt;
    }
    for (short i = 0; i < this.size(); i++)
        {
            ti = (JSPredicateForm) this.elementAt(i);
            //if (JSJshopVars.flagLevel > 8)
            //    ti.print();
            nti = ti.applySubstitutionPF(alpha);
          //  if (JSJshopVars.flagLevel > 8)
          //       nti.print();
            nt.addElement(nti);
          /*  if (JSJshopVars.flagLevel > 8){
                  nt.print();
                  JSUtil.flag("<-- applyJSListLogicalAtoms");
            }*/
        }
        
        
    /*if (JSJshopVars.flagLevel > 8){
        nt.print();
        JSUtil.flag("<-- final applyTasks");
    }*/
    return nt;
  } 

  
   public JSListLogicalAtoms Cdr()
  {
    JSListLogicalAtoms newLA = new JSListLogicalAtoms();
    JSPredicateForm t;
    // added
    newLA.varlist=this.varlist;
    if (this.varlist)
      return newLA;
    // additions end
    
    for (short i=1; i < this.size();i++)
    {
        t = (JSPredicateForm) this.elementAt(i);
        newLA.addElement(t);
    }
    
    return newLA;
    
  }
  
    public String Label()
  {
     return label;
  } 
  
    public String Name()
  {
     return name;
  } 
  
  public void setName(String val)
  {
     name=new String( val);
  } 
  
}

