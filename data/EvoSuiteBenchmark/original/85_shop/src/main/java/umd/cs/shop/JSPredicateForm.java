package umd.cs.shop;
import java.io.*;
import java.util.*;

public class JSPredicateForm extends Vector
{

    /*==== instance variables ====*/


     JSPredicateForm()
     {
        super();
     }    

// HICAP: Constructor used by NeoEditor to evaluate preconditions of methods.
// aNeoEditor.state.questions (an ArrayList of CaseQues 's).
// In a CaseQues: aCaseQues.question.predicate
// aCaseQues.getAnswer().equals("Yes")
  public JSPredicateForm( String text)
    {
        // (<task-name> <term1> ... <termN>)
        // Example: (!set-money ?p2 ?m2 ,(+ ?m2 ?amount)) 
        super();
        // System.out.println("JSPRedicateForm init with text: " + text);
        if( text!=null)
        {
          JSPredicateFormInit(
            new StreamTokenizer( new StringReader( text)));
        }
    }

// HICAP: To add to state:
//   1. add a Question: shop gives aJSPredicateForm and aString
//      describing it:  aState.addQuestion( JSPredicateForm predicate,
//      String questionTitle )
//      returns Question: method makes a new question, adds it to
//      the questionList, and makes a new CaseQues with that question
//      and answer "Yes" and adds it to the State.
//   2. add a CaseQues: question in 1 with answer "Yes".
//      public Question addQuestion( JSPredicateForm, String)
//      public void deleteFromState(  JSPredicateForm inPredicate):
//      find a Question in state whose predicate.equals( JSPredicateForm t)
//      the inPredicate

// HICAP: To add to state:
//  aNeoEditor.addToState( JSPredicateForm pred)
// To delete from state:
//   aNeoEditor.deleteFromState( JSPredicateForm pred)
  public JSPredicateForm(StreamTokenizer tokenizer)
    {
        // Parse elements of the form: (<name> <term1> ... <termN>)
        // Example: (!set-money ?p2 ?m2 ,(+ ?m2 ?amount)) 
        //       or: ,(+ ?m2 ?amount)
        super();
        JSPredicateFormInit( tokenizer);
    }

  void JSPredicateFormInit( StreamTokenizer tokenizer)
    {
        if( tokenizer==null) throw new JSParserError();//return;
       // JSUtil.flagParser("in PredicateForm()");
      
        if (!JSUtil.expectTokenType(JSJshopVars.leftPar, 
              tokenizer, "expected '('"))
                 throw new JSParserError();//  return; // error: expected '('
                 
        
        if (!JSUtil.readToken(tokenizer, "Reading Predicate"))
                   throw new JSParserError();//return; // error: 
          
        if (tokenizer.ttype == JSJshopVars.rightPar){
            JSUtil.println("Unexpected ) while reading Predicate");
                 throw new JSParserError();// return; 
          
        }
       
        if (tokenizer.ttype == JSJshopVars.exclamation)
        {
           if (!JSUtil.expectTokenType(tokenizer.TT_WORD, 
              tokenizer, "Operator name expected"))
                throw new JSParserError();//return;
            tokenizer.pushBack();
            String w = JSUtil.readWord(tokenizer, "JSPredicateForm");
            if (w.equals("%%%"))//means that an error occur
                throw new JSParserError();//    return; 
            this.addElement("!"+w);
        }
       else
       {
         if (tokenizer.ttype == tokenizer.TT_WORD && tokenizer.sval.equalsIgnoreCase("not"))
          {
            this.addElement(new String(tokenizer.sval));
            this.addElement(new JSPredicateForm(tokenizer));
            if (JSUtil.expectTokenType(JSJshopVars.rightPar,tokenizer, "JSPredicateForm"))
                return; // Parse succesfull
            else 
              throw new JSParserError();
         }
        
       if (tokenizer.ttype == tokenizer.TT_WORD && tokenizer.sval.equalsIgnoreCase("call"))
          {
            this.addElement(new String(tokenizer.sval));
            tokenizer.ttype= JSJshopVars.leftPar;
            tokenizer.pushBack();
            JSTerm t=new JSTerm(tokenizer);
            t.makeEval(true);
            this.addElement(t);
            return; // Parse succesfull
             
         }
        else{
          if (tokenizer.ttype != tokenizer.TT_WORD){
              JSUtil.println("PlanningDomain: error");	
              throw new JSParserError(); //return;
         }
         
         tokenizer.pushBack();
         String w = JSUtil.readWord(tokenizer, "JSPredicateForm");
         if (w.equals("%%%"))//means that an error occur
              throw new JSParserError(); 
         this.addElement(w);
        
       }
     }
        
     if (!JSUtil.readToken(tokenizer, "JSPredicateForm"))
              throw new JSParserError(); // return;
     while (tokenizer.ttype != JSJshopVars.rightPar){
            tokenizer.pushBack();
            this.addElement(new JSTerm(tokenizer));
            if (!JSUtil.readToken(tokenizer, "JSPredicateForm"))
               throw new JSParserError(); //return;
     }
          
}


public void print()
  {
       
       if (this.size() == 0)
       {
        JSUtil.println("Predicateform.print(): 0 elements");
        return;
       }
       String str = (String) this.elementAt(0);
       JSUtil.print("(");
       JSUtil.print(str+" ");
       if (str.equalsIgnoreCase("not"))
       {
        JSPredicateForm el;
        el = (JSPredicateForm) this.elementAt(1);
        el.print();
       }
       else
       {
         JSTerm el;
         for (short i = 1; i < this.size(); i++)
         {
          el = (JSTerm) this.elementAt(i);
          el.print();
         }
       }
       JSUtil.print(" ) ");
    }
    
    public StringBuffer toStr()
    {
      StringBuffer res = new StringBuffer();
    
       if (this.size() == 0)
       {
         JSUtil.println("Predicateform.print(): 0 elements");
         return res;
       }
       res.append("(");
      // Added 11/28/200
       String str = (String) this.elementAt(0);
       res.append(str);
       if (str.equalsIgnoreCase("not"))
       {
        JSPredicateForm el;
        el = (JSPredicateForm) this.elementAt(1);
        res.append(el.toStr());
       }
       else
       { //Additions end
       JSTerm el;
       for (short i = 1; i < this.size(); i++)
       {
        el = (JSTerm) this.elementAt(i);
        res.append(el.toStr());
       }
       } 
       res.append(")");
       //JSUtil.flag10("Atom: "+ res);
       return res;
    }
    
    public JSPredicateForm clonePF()
  {
    JSPredicateForm np = new JSPredicateForm();
    // Added 11/28/200
    String str = (String) this.elementAt(0);
    if (str.equalsIgnoreCase("not"))
       {
        np.addElement(this.elementAt(0));
        JSPredicateForm el;
        el = (JSPredicateForm) this.elementAt(1);
        np.addElement (el.clonePF());
       }
    else
     {//Additions end
        np.addElement(this.elementAt(0));
        JSTerm ti;
                
        for (short i = 1; i < this.size(); i++)
            {
                ti = (JSTerm) this.elementAt(i);
                np.addElement(ti.cloneT());
            }
     }   
    return np;
  }
    
 public JSPredicateForm applySubstitutionPF(JSSubstitution alpha)
  {
    JSPredicateForm np = new JSPredicateForm();
    
   /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning ){
        JSUtil.print("applyPF subst:");
        alpha.print();
        JSUtil.print("to:");
        this.print();
    }
    JSUtil.flagPlanning("stop");*/
    
     // Added 11/28/200
    String str = (String) this.elementAt(0);
       
    if (str.equalsIgnoreCase("not"))
       {
        np.addElement(this.elementAt(0));
        JSPredicateForm el;
        el = (JSPredicateForm) this.elementAt(1);
        np.addElement (el.applySubstitutionPF(alpha));
       }
       else
       { //Additions end
    
         np.addElement(this.elementAt(0));
         JSTerm ti;
         /*if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
           np.print();
         JSUtil.flagPlanning("<--");*/
         for (short i = 1; i < this.size(); i++)
           {
           /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
                  np.print();
            JSUtil.flagPlanning("<--");*/
            ti = (JSTerm) this.elementAt(i);
            np.addElement(ti.applySubstitutionT(alpha));
           }
       }     
    /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
           np.print();
     JSUtil.flagPlanning("<-- final applyPF");*/
    return np;
  }
    
public JSSubstitution matches(JSPredicateForm t)
{
    return this.matches(t,new JSSubstitution());
}
    
 public JSSubstitution matches(JSPredicateForm t, JSSubstitution alpha)
  /*this &  t does not have to be ground*/
  {
    
    JSSubstitution beta = new JSSubstitution();
 
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
        
        // Added 11/28/00
        if (functor.equalsIgnoreCase("not")) 
        {
          JSPredicateForm e1= (JSPredicateForm) this.elementAt(1); 
          JSPredicateForm e2= (JSPredicateForm) t.elementAt(1); 
          beta=e1.matches(e2, alpha);
          return beta;
            
        }  
        // Additions end
        
        JSTerm ti;
        JSSubstitution gama = new JSSubstitution();
        JSSubstitution newAlpha = (JSSubstitution) alpha.cloneS();
            
        for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            if (!ti.equals(t.elementAt(i))) {
                gama = ti.matches((JSTerm) t.elementAt(i), newAlpha);
                if (gama.fail())
                {
                    return gama;
                }
                beta.addElements(gama);
                newAlpha.addElements(gama);
            }
        }
       /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning ){
            JSUtil.print("The atom ");
            this.print();
            JSUtil.print("matches ");
            t.print();
            JSUtil.print("With subst. ");
            beta.print();
            JSUtil.print("Initial Subs:");
            alpha.print();
        }
        JSUtil.flagPlanning("stop");*/
        return beta;
  }
  
 public boolean equals( JSPredicateForm t)
  /* t is a ground term*/
  {
  if( t==null) return false;
  
    String functor = (String) this.elementAt(0);

    if (this.size() != t.size())
        {   
            return false;
        }
    String functor1 = (String) t.elementAt(0);
    if (!functor.equalsIgnoreCase(functor1))
    {
      return false;
            
    }
   // Added 11/28/00 
   if (functor.equalsIgnoreCase("not")) 
     {
       JSPredicateForm e1= (JSPredicateForm) this.elementAt(1); 
       JSPredicateForm e2= (JSPredicateForm) t.elementAt(1); 
       return e1.equals(e2);
     }  
   // Additions end 
    
     JSTerm ti;  
     JSTerm oti;
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            oti = (JSTerm) t.elementAt(i);
            if (!ti.equals(oti))
            {
              return false;
            }
        }
        
     return true;
  }

public JSPredicateForm standarizerPredicateForm()
  {
   JSPredicateForm nTA = new JSPredicateForm();
    
    //Added 11/28/00
     String str = (String) this.elementAt(0);
     if (str.equalsIgnoreCase("not"))
       {
        nTA.addElement(this.elementAt(0));
        JSPredicateForm el;
        el = (JSPredicateForm) this.elementAt(1);
        nTA.addElement (el.standarizerPredicateForm());
        return nTA;
       }
    
    nTA.addElement(this.elementAt(0));
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            nTA.addElement(ti.standardizerTerm());
        }
     
    return nTA;
    
  }
 
 
 
}

