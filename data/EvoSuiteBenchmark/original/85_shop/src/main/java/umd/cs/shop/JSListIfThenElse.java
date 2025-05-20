package umd.cs.shop;
import java.util.*;

import java.io.*;



public class JSListIfThenElse extends Vector
{ 
    
    JSListIfThenElse()
    {
        super();
    }

    
   JSListIfThenElse(StreamTokenizer tokenizer)
    {
        super();
        String name;
        JSPairIfThen pair;
        int index=1;
  
        if(!JSUtil.readToken(tokenizer,"Expecting method's IfThenElse list"))
            throw new JSParserError(); //return;
        while (tokenizer.ttype !=  JSJshopVars.rightPar )
        {
            tokenizer.pushBack();
            if (!JSUtil.readToken(tokenizer, "Expecting method definition"))
                throw new JSParserError(); //return;
            if (tokenizer.ttype != tokenizer.TT_WORD){
                tokenizer.pushBack();
                name="Part_"+ index;
            }   
            else {
                tokenizer.pushBack();
                name= JSUtil.readWord(tokenizer, "Expecting name for the method if/then pair.");
                if (name.equals("%%%")){
                    JSUtil.println("Line : "+ tokenizer.lineno()+" Invalid name for a method if/then pair.");
                    throw new JSParserError(); //return;  
                }
            }    
            pair = new JSPairIfThen(tokenizer);
            pair.setName(name);
            this.addElement(pair);
            if(!JSUtil.readToken(tokenizer,"Expecting ')'"))
              throw new JSParserError(); //return;
            index++;
        }
        tokenizer.pushBack();
    }

 public void print()
  {
   
       JSPairIfThen el;
       for (short i = 0; i < this.size(); i++)
       {
        el = (JSPairIfThen) this.elementAt(i);
        el.print();
       }
  }

  JSTasks evalPrec(JSState s, JSSubstitution alpha,JSListAxioms axioms)
    {
	JSPairIfThen pair;
	JSTasks then;
	JSSubstitution beta;

	for (short i=0;i<this.size();i++)
	{
	    pair = (JSPairIfThen) this.elementAt(i);
	    beta = s.satisfies(pair.ifPart(),alpha,axioms);
	    if (!beta.fail())
	    {
	        //JSUtil.flag("****Success*****");
	        //beta.print();
	        then = pair.thenPart();
	        return then.applySubstitutionTasks(beta);
	    }
	}
	then = new JSTasks();
	then.makeFail();
	//JSUtil.flag("Failure");
	return then;

    }

Vector evalPrecAll(JSState s, JSSubstitution alpha,JSListAxioms axioms)
    {
	JSPairIfThen pair;
	JSTasks then;
	JSListSubstitution beta;
	Vector allReductions= new Vector();

	for (short i=0;i<this.size();i++)
	{
	    pair = (JSPairIfThen) this.elementAt(i);
	    
	    beta = s.satisfiesAll(pair.ifPart(),alpha,axioms);
	   
	    if (!beta.fail())
	    {   
	        if (JSJshopVars.flagLevel > 4){
              JSUtil.print("Found an applicable method : ");
              JSUtil.println(pair.Name());
            }
	        then = (JSTasks)pair.thenPart().clone();
	        for (int k=0;k< beta.size();k++)
	          allReductions.addElement(then.applySubstitutionTasks((JSSubstitution)beta.elementAt(k)));  
	       return allReductions;
	    }
	}
	
	return allReductions; 

    }



    public JSListIfThenElse standarizerListIfTE()
    {
        JSListIfThenElse newList = new JSListIfThenElse();
        JSPairIfThen pair;
        
        for (short i =0; i < this.size(); i++)
        {
            pair = (JSPairIfThen) this.elementAt(i);
            newList.addElement(pair.standarizerPIT());
        }
        return newList;
    }

}

