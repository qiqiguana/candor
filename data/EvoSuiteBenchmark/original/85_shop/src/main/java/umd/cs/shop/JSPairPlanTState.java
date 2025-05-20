package umd.cs.shop;
import java.util.*;

public class JSPairPlanTState 
{
    JSPlan plan;
    JSTState tState;
    
    JSPairPlanTState()
    {
        super();
    }
    
    JSPairPlanTState(JSPlan pl, JSTState tst)
    {
        super();
        plan = pl;
        tState = tst;
    }
    
    public JSPlan plan()
    {
        return plan;
    }
    
    public JSTState tState()
    {
        return tState;
    }

    
    
    public void print()
    {
        JSPlan pl  = this.plan();
	    JSTState tst = this.tState();
        JSUtil.print("[");
        pl.print();
        JSUtil.print("<->");
        tst.print();
        JSUtil.print("]");
        
    }
    
    
}
