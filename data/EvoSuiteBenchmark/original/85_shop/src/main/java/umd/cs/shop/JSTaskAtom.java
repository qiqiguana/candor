package umd.cs.shop;
import java.io.*;

import java.util.*;



public class JSTaskAtom extends JSPredicateForm

{

    /*==== instance variables ====*/
    
    boolean isPrimitive;
    boolean isCompound;
  

 public JSTaskAtom( String text)
    {
      super( text);
      
      String taskName = (String) this.elementAt(0);
      if (taskName.charAt(0) == '!')
      {
        isPrimitive = true;
        isCompound = false;
      }
      else{
        isPrimitive = false;
        isCompound = true;
      }
    }

  public JSTaskAtom(StreamTokenizer token)
  {
    super(token);
        
    String taskName = (String) this.elementAt(0);
      if (taskName.charAt(0) == '!')
      {
        isPrimitive = true;
        isCompound = false;
      
      }
      else{
        isPrimitive = false;
        isCompound = true;
       
      }
   }


    

    JSTaskAtom()
    {
        super();
    }
    
    public JSPairPlanTState seekSimplePlan(JSPlanningDomain dom, JSTState ts)
    {
       JSPairPlanTState pair;
       JSSubstitution alpha;
	   JSOperator op ;
	   JSListOperators list = dom.operators();
	   JSTaskAtom top;
	   JSState ns;
	   JSTState tState;
	   JSPlan pl = new JSPlan();
	   boolean thisIsGround = this.isGround();
	   
	  
       
	   for (short i=0;i<list.size();i++)
	    {
		 op = (JSOperator) list.elementAt(i);
		 top = op.head();
		 if (!thisIsGround)
		 {
		    top = top.standarizerTA();
		 }
		 alpha = top.matches(this);
	     if (!alpha.fail()){
	        op = op.standarizerOp();
	        alpha = alpha.standarizerSubs();
	        JSJshopVars.VarCounter++;
	       
	        JSListSubstitution satisfiers = dom.axioms().TheoremProver(op.precondition(),ts.state(),alpha,true); 
	        if ( ! satisfiers.isEmpty())
	          {
	           
		         tState = ts.state().applyOp(op,alpha, ts.addList(), ts.deleteList());
		         ns = tState.state();
		         top = op.head();
	             pl.addWithCost(top.applySubstitutionTA(alpha), op.cost());
		         return new JSPairPlanTState(pl,tState);
		       }
	       }
	     }
	    pl.assignFailure();
	    return new JSPairPlanTState(pl,new JSTState());
      
    }
    
    public JSReduction reduce(JSPlanningDomain dom,JSState s, JSReduction red)
    {
        
        
        //JSUtil.flag("reduce(PlanningDomain dom,State s,Reduction red)");

	JSListMethods mets = dom.methods();
	
	return mets.findReduction(this, s, red,dom.axioms());
	
    }
    
   public JSTaskAtom applySubstitutionTA(JSSubstitution alpha)
  {
    JSTaskAtom nta = new JSTaskAtom();
    
  /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning ){
    JSUtil.print("applyTA subst:");
    alpha.print();
    JSUtil.print("to:");
    this.print();
   }*/
  /* if (JSJshopVars.flagLevel > 9 && JSJshopVars.flagPlanning )
        JSUtil.flag("stop");*/
    
    if (this.isPrimitive()){
        nta.makePrimitive();
    }
    else{
        nta.makeCompound();
    }
    
    
    nta.addElement(this.elementAt(0));
    JSTerm ti,te;
    
   /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
        nta.print();*/
  /*  if (JSJshopVars.flagLevel > 9 && JSJshopVars.flagPlanning )
        JSUtil.flag("<--");*/
     for (short i = 1; i < this.size(); i++)
        {
          /* if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
                 nta.print();*/
         /*  if (JSJshopVars.flagLevel > 9 && JSJshopVars.flagPlanning )
                 JSUtil.flag("<--");*/
            ti = (JSTerm) this.elementAt(i);
            // nta.addElement(ti.applySubstitutionT(alpha));
            // Added 11/29/2000
           te=ti.applySubstitutionT(alpha);
           nta.addElement(te.call());  
            // Additions ended
        }
     
   /*  if (JSJshopVars.flagLevel > 8 && JSJshopVars.flagPlanning )
            nta.print();
     if (JSJshopVars.flagLevel > 9 && JSJshopVars.flagPlanning )
            JSUtil.flag("<-- final applyTA");*/
    return nta;
  }
  
    
  public JSTaskAtom cloneTA()
  {
   JSTaskAtom nTA = new JSTaskAtom();
    
    if (this.isPrimitive()){
        nTA.makePrimitive();
    }
    else{
        nTA.makeCompound();
    }
    
    
    nTA.addElement(this.elementAt(0));
    JSTerm ti;
            
     for (short i = 1; i < this.size(); i++)
        {
            ti = (JSTerm) this.elementAt(i);
            nTA.addElement(ti.cloneT());
        }
     
    return nTA;
    
  }
    
 
 public boolean isGround()
  {
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
  
   public JSTaskAtom standarizerTA()
  {
   JSTaskAtom nTA = new JSTaskAtom();
    
    if (this.isPrimitive()){
        nTA.makePrimitive();
    }
    else{
        nTA.makeCompound();
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
  
  JSJshopNode findInList(Vector list)
  {
    JSJshopNode node; 
    
    
    for (int i = 0; i < list.size(); i++)
    {
        node = (JSJshopNode) list.elementAt(i);
        if (this.equals(node.atom()))
        {
            return node;
        }       
    }
    
    return new JSJshopNode(this,new Vector());
  }
  
  public boolean isPrimitive()
    {
        return isPrimitive;
    }

    public boolean isCompound()
    {
        return isCompound;
    }  

 public void makePrimitive()
    {
        isPrimitive = true;
        isCompound = false;
    }

    public void makeCompound()
    {
        isPrimitive = false;
        isCompound = true;
    }  


}

