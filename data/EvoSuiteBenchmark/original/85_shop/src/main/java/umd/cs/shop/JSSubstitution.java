package umd.cs.shop;
import java.util.*;

public class JSSubstitution extends Vector
{
    /*==== instance variables ====*/
   
    private boolean fail;
    
    JSSubstitution()
    {
        super();
        fail = false;
    }
    
    // Modified to represent compound substitution
    public  JSTerm instance(JSTerm var)
    {
       JSPairVarTerm pair;
       
        for (short i=0;i<this.size();i++)
        {
            pair = (JSPairVarTerm) this.elementAt(i);
            if (var.equals(pair.var))
               return pair.term();
            
        }
        return var;
        
    }
    
    public JSSubstitution cloneS()
    {
	JSSubstitution newS = new JSSubstitution();
	JSPairVarTerm pair;

	for (short i=0;i<this.size();i++)
        {
            pair = (JSPairVarTerm) this.elementAt(i);
            newS.addElement(pair.clonePVT());
        }
        return newS;
    }

    public boolean fail()
    {
        return fail;
    }
    
    public void assignFailure()
    {
        fail = true; 
    }
    
   
    public void addElements(JSSubstitution l)
    {
      short j,i;
      JSPairVarTerm pair,p1,p2;
      JSTerm t,v1,v2;
      for (j=0; j<this.size(); j++ ){
         pair= (JSPairVarTerm)this.elementAt(j);
         t= pair.term().applySubstitutionT(l);
         pair.setTerm(t);
      
      }
      boolean found;  
      for ( i = 0; i < l.size(); i++) {
        found=false;
        for (j=0; j< this.size();j++){
             p1=(JSPairVarTerm)this.elementAt(j);
             p2=(JSPairVarTerm)l.elementAt(i);
            if ( p1.var().equals(p2.var())) {        
                found=true;
                break;
            }
        }   
         if (!found)
           this.addElement(l.elementAt(i));
      }
            
        
      
      
    }
    
    public void removeElements(JSSubstitution l)
    {
      for (short i = 0; i < l.size(); i++)
      {
        this.removeElement(l.elementAt(i));
      }
      
    }
    
    public void print()
    {
        if (this.fail()){
            JSUtil.print("failed substitution");
            return;
        }
        JSPairVarTerm pair;
        JSUtil.print("[");
       for (short i = 0; i < this.size(); i++)
      {
        pair = (JSPairVarTerm) this.elementAt(i);
        pair.print();
      } 
      JSUtil.println("]");
    }
    
    public JSSubstitution standarizerSubs()
    {
        JSSubstitution newSubs = new JSSubstitution();
        JSPairVarTerm pair;
        
        for (short i =0; i < this.size(); i++)
        {
            pair = (JSPairVarTerm) this.elementAt(i);
            newSubs.addElement(pair.standarizerPVT());
        }
        return newSubs;
    }
    
    
}
