package umd.cs.shop;
import java.util.*;

import java.io.*;


public class JSState extends JSListLogicalAtoms
{

    /*==== instance variables ====*/

    private String label;

  public
    JSState()
    {
      super();
    }

  public
    JSState(StreamTokenizer tokenizer)
    {
        super(tokenizer);
        return;
    }
  
  // Added 25/01/2001
    public void addElementsToState(JSListLogicalAtoms s)
    {
     /* Appends the contents of this to the end of s */   
      /*for (int i =0; i<s.size(); i++)
      {
        this.insertElementAt(s.elementAt(i),i);
      }*/
      
      int j =0;
      for (int i =0; i<s.size(); i++)
      {
         if (this.contains(s.elementAt(i)))
            continue;
         this.insertElementAt(s.elementAt(i),j);
         j++;
      }
      
    }

    JSState apply(JSPlan pl)
    {
     JSState ns = new JSState();
     
     JSUtil.flag20("<STATE>.apply(<JSPlan>) not implemented yet");
     
     return ns;
    }
    
    JSTState applyOp(JSOperator op, JSSubstitution alpha, JSListLogicalAtoms addL,
                    JSListLogicalAtoms delL)
    {
        JSListLogicalAtoms add = op.addList();
        JSListLogicalAtoms del = op.deleteList();
        JSListLogicalAtoms opAddL = add.applySubstitutionListLogicalAtoms(alpha);
        JSListLogicalAtoms opDelL = del.applySubstitutionListLogicalAtoms(alpha);
        JSState ns = new JSState();
        JSListLogicalAtoms nAddL = new JSListLogicalAtoms();
        JSListLogicalAtoms nDelL = new JSListLogicalAtoms();
        JSPredicateForm el;
        
        if (JSJshopVars.flagLevel > 3  ){
            
            JSUtil.print(" Matching Operator : (");
            JSUtil.print(":Operator ");
            op.head().applySubstitutionTA(alpha).print();
            JSUtil.print("-: ");
            opDelL.print();
            JSUtil.print("+: ");
            opAddL.print();
            JSUtil.println(")");   
        }
      //  JSUtil.flagPlanning("<-- ndelete list");
        
        for (short i =0; i < this.size();i++)//creates a new state
        {
            el = (JSPredicateForm) this.elementAt(i);
            if (!opDelL.contains(el)){
                
             //   ns.insertElementAt(el, 0);// modified 22/01
                ns.addElement(el);
            }
        }
        ns.addElementsToState(opAddL); // previously AddElements
        
        for (short i =0; i < addL.size();i++)//creates a new add list
        {
            el = (JSPredicateForm) addL.elementAt(i);
            if (!opDelL.contains(el)){
                nAddL.addElement(el);
            }
        }
        nAddL.addElements(opAddL);
        
                
        for (short i =0; i < delL.size();i++)//creates a new del list
        {
            el = (JSPredicateForm) delL.elementAt(i);
            if (!opAddL.contains(el)){
                nDelL.addElement(el);
            }
        }
        nDelL.addElements(opDelL);
        
      if (JSJshopVars.flagLevel > 3)  {
        JSUtil.println("The resulting state :");
        ns.print(); 
        JSUtil.println("-----------");
        }
        return new JSTState(ns, nAddL, nDelL);
        
    }
    
    public JSSubstitution satisfies(JSListLogicalAtoms conds, JSSubstitution alpha,
                                     JSListAxioms axioms)
    //Tests if  conds can be infered from this (the current state) and axioms
    //modulo the substitution alpha.
    //
    //If conds can be infered, it returns the first matching substitution
    //
    //else it returns the failure substitution (i.e., calling the method fail()
    //to the returned substitution succeeds)
    {
       
       //Calls Fusun's inferencing method with parameter all set to "false"
       
        JSListSubstitution satisfiers = axioms.TheoremProver(conds, this, alpha, false);
        JSSubstitution answer;
        if (satisfiers.fail())
         {
            answer= new JSSubstitution ();
            answer.assignFailure();
            
         }
         else 
         {
           answer= (JSSubstitution) alpha.clone();
           answer.addElements((JSSubstitution)satisfiers.elementAt(0)); 
       /*  if (conds.size() >0){
            this.print();
             }*/
         } 
        return   answer;
     }
     
     public JSListSubstitution satisfiesAll(JSListLogicalAtoms conds, JSSubstitution alpha,
                                     JSListAxioms axioms)
    //Tests if  conds can be infered from this (the current state) and axioms
    //modulo the substitution alpha.
    //
    //If conds can be infered, it returns all of the matching substitutions
    //
    //else it returns an empty list
    {
        
          //Calls Fusun's inferencing method with parameter all set to "true"
         int i;
         String first=conds.Label();
         boolean findfirst= ! first.equalsIgnoreCase("first");
         
                 
         JSSubstitution beta;
         JSListSubstitution answers=new JSListSubstitution();
         JSListSubstitution satisfiers = axioms.TheoremProver(conds, this, alpha, findfirst);  
         for (i=0; i< satisfiers.size(); i++)
          {
         
            beta=(JSSubstitution)((JSSubstitution)satisfiers.elementAt(i)).clone();
            beta.addElements((JSSubstitution) alpha.clone());
            answers.addElement(beta);
            
          }
        
          return answers;
           
     }

   public JSListSubstitution satisfiesTAm(JSPredicateForm t, JSSubstitution alpha)
   
        // It searches all atoms in this matching t modulo the substitution alpha.
        //
        // If such an atoms are found, the matching substitutions are returned.
        //
        // Otherwise: returns an empty list
    {
        JSPredicateForm el;
        JSSubstitution subs;
        JSListSubstitution answers=new JSListSubstitution();         
        if (JSJshopVars.flagLevel > 7)
          System.out.println(" "); 
        for (int i=0; i<this.size();i++)
        {   
            el = (JSPredicateForm) this.elementAt(i);
            subs = t.matches(el, alpha);
            if (!subs.fail())
            {   
              if (JSJshopVars.flagLevel > 7) {
                JSUtil.println(" Goal matches atom: ");  
                el.print();
              }
               answers.addElement(subs);
             }
            
        }
//        JSUtil.flagPlanning(" NO!");
        if (JSJshopVars.flagLevel > 7 && answers.isEmpty()) 
           JSUtil.println(" Goal does not match any atom."); 
        return answers;
    } 

}
