package umd.cs.shop;
import java.util.*;

import java.io.*;



public class JSListConjuncts extends Vector
{ 
    
    JSListConjuncts()
    {
        super();
    }

    
   JSListConjuncts(StreamTokenizer tokenizer)
    {
        super();
        JSListLogicalAtoms listAtoms;
        String name;
        int index=1;
        if(!JSUtil.readToken(tokenizer,"Expecting list of conjuncts as tail of the axiom "))
           throw new JSParserError(); // return;
        while (tokenizer.ttype !=  JSJshopVars.rightPar )
        {
            tokenizer.pushBack();
            if (!JSUtil.readToken(tokenizer, "Expecting axiom tail definition"))
                throw new JSParserError();//return;
            if (tokenizer.ttype != tokenizer.TT_WORD){
                tokenizer.pushBack();
                name="Part_"+ index;
            }   
            else {
                tokenizer.pushBack();
                name= JSUtil.readWord(tokenizer, "Expecting name for the method if/then pair.");
                if (name.equals("%%%")){
                    JSUtil.println("Invalid name for an axiom tail.");
                    throw new JSParserError();//return;  
                }
            }    
            listAtoms = new JSListLogicalAtoms(tokenizer);
            listAtoms.setName(name);
            this.addElement(listAtoms);
            if(!JSUtil.readToken(tokenizer,"Expecting ) for axiom definition"))
                throw new JSParserError();//return;
            index++;
        }
        tokenizer.pushBack();
    }

 public void print()
  {
   
       JSListLogicalAtoms el;
       for (short i = 0; i < this.size(); i++)
       {
        el = (JSListLogicalAtoms) this.elementAt(i);
        JSUtil.println(el.Name());
        el.print();
       }
  }

  public JSListConjuncts standarizerListConjuncts()
    {
        JSListConjuncts newList = new JSListConjuncts();
        JSListLogicalAtoms listAtoms;
        
        for (short i =0; i < this.size(); i++)
        {
            listAtoms = (JSListLogicalAtoms) this.elementAt(i);
            newList.addElement(listAtoms.standarizerListLogicalAtoms());
        }
        return newList;
    }

}

