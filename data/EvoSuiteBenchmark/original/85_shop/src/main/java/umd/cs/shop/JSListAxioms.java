package umd.cs.shop;
import java.util.*;



public class JSListAxioms extends Vector

{

    /*==== instance variables ====*/

    private String label;

    

    JSListAxioms()

    {

        super();

    }
    
public void print()
  {
       
       JSAxiom el;
       for (short i = 0; i < this.size(); i++)
       {
        el = (JSAxiom) this.elementAt(i);
        el.print();
       }
  }
   
    
    
public JSListSubstitution   TheoremProver (JSListLogicalAtoms conds ,JSState S,JSSubstitution       
                                            alpha ,boolean findall)

{
    JSSubstitution     gamma,tetha,kappa;
    JSListSubstitution  answers,subanswers1,subanswers2;
    JSPredicateForm    e1;
    JSTerm             p1;

    JSListLogicalAtoms Rest,conjunct, one;
    JSListConjuncts    TailAxiom ;
    JSAxiom 		   axiom;
    String             str;
    answers = new JSListSubstitution();
    if ( conds.size()== 0)	
     {
        answers.addElement(new JSSubstitution());
        if (JSJshopVars.flagLevel > 5)
          JSUtil.println("Returning successfully from find-satisfiers : No more goals to satisfy");
        return answers;
        
     }  
    
    
    e1  = (JSPredicateForm) conds.elementAt(0);
    Rest = conds.Cdr();
    str  = (String) e1.elementAt(0);
    if (JSJshopVars.flagLevel > 6){
         JSUtil.println(" ");
         JSUtil.print("Searching satisfiers for");
         e1.applySubstitutionPF(alpha).print();
    }
    
    /*********************************************************/
    /* If the first word is 'not' do the following             */
    /*                                                       */
    /*********************************************************/

    if ( str.equalsIgnoreCase("not") )
     {
        e1=(JSPredicateForm)e1.elementAt(1);
        one = new JSListLogicalAtoms();
        one.addElement(e1); 
        subanswers1 = TheoremProver ( one , S , alpha , false);
        
        if ( ! subanswers1.fail()){ 
           if (JSJshopVars.flagLevel > 5)
               JSUtil.println("Returning failure from find-satisfiers: Can not find a satisfier");  
           return answers;
        }   
        else
           return TheoremProver ( Rest, S , alpha , findall);
           
     }
     
    /*********************************************************/
    /* If the first word is 'eval' do the following           */
    /*                                                       */
    /*********************************************************/
     else if ( str.equalsIgnoreCase("call") )
     {
        p1=((JSTerm)e1.elementAt(1)).applySubstitutionT(alpha);
        if (! p1.isGround()){
          if (JSJshopVars.flagLevel > 5)
               JSUtil.println("Returning failure from find-satisfiers: Can not find a satisfier");  
          return answers;
        }
        JSTerm t =p1.call(); 
        if ( t.size()==0){
          if (JSJshopVars.flagLevel > 5)
             JSUtil.println("Returning failure from find-satisfiers: Can not find a satisfier");  
          return answers;
        }        else
           return TheoremProver ( Rest, S , alpha , findall);
           
     }
   
    /*********************************************************/
    /* Else do the following             */
    /*                                                       */
    /*********************************************************/
    subanswers1=  S.satisfiesTAm (e1,alpha);
    if ( ! subanswers1.fail())
      {
	  for(int i=0; i < subanswers1.size() ;i++) 
          {
           /* tetha= (JSSubstitution)alpha.clone();
            tetha.addElements((JSSubstitution)subanswers1.elementAt(i)); */
            tetha=(JSSubstitution)((JSSubstitution)subanswers1.elementAt(i)).clone();
            tetha.addElements((JSSubstitution)alpha.clone());
            
            subanswers2 = TheoremProver ( Rest , S , tetha , findall); 	
              if ( ! subanswers2.fail())
                for (int j=0;j< subanswers2.size();j++) 
                 {
                 /*  kappa= (JSSubstitution)((JSSubstitution)subanswers1.elementAt(i)).clone();
                   kappa.addElements((JSSubstitution)subanswers2.elementAt(j));*/
                  kappa= (JSSubstitution)((JSSubstitution)subanswers2.elementAt(j)).clone();
                  kappa.addElements((JSSubstitution)subanswers1.elementAt(i));
                   answers.addElement (kappa);
                   if ( (answers.size()==1) && ( ! findall))
                       return answers;
                  }
           }       
      }
  
  
    for (int i= 0 ; i<this.size();i++)
      {
       
         axiom= ((JSAxiom) this.elementAt(i)).standarizerAxiom();
         gamma= axiom.head().matches (e1,alpha);
         if (! gamma.fail()  )
          {
            if (JSJshopVars.flagLevel > 8){
                JSUtil.println("Goal matches axiom:");  
                axiom.print();
            }
            JSJshopVars.VarCounter++; 
            TailAxiom= (JSListConjuncts) axiom.tail().clone();
            for (int k=0;k< TailAxiom.size();k++)
            {
                conjunct= (JSListLogicalAtoms) TailAxiom.elementAt(k);
                conjunct.addElements(Rest);
               /* tetha= (JSSubstitution)alpha.clone();
                tetha.addElements(gamma);*/
                 tetha= (JSSubstitution)gamma.clone();
                tetha.addElements(alpha);
                subanswers2= TheoremProver ( conjunct , S , tetha , findall);
                if ( ! subanswers2.fail()){
                  for (int j=0;j< subanswers2.size();j++) 
                   {
                   /*  kappa= (JSSubstitution)gamma.clone();
                     kappa.addElements((JSSubstitution)subanswers2.elementAt(j));*/
                     kappa=(JSSubstitution)((JSSubstitution)subanswers2.elementAt(j)).clone();
                     kappa.addElements((JSSubstitution)gamma.clone());
                     answers.addElement (kappa);
                     if ( (answers.size()== 0) && ( !findall))
                         return answers;
                    }
                    break;
                } 
             } 
          }   
      }
  if (JSJshopVars.flagLevel > 5)
      JSUtil.println("Returning failure from find-satisfiers: Can not find a satisfier");  
 
  return answers;

}
    

}
