package umd.cs.shop;
import java.util.*;

public class JSPairPlanTSListNodes
{
    JSPairPlanTState planS;
    Vector listNodes;
    
    JSPairPlanTSListNodes()
    {
        super();
    }
    
    JSPairPlanTSListNodes(JSPairPlanTState pS, Vector l)
    {
        super();
        planS = pS;
        listNodes = l;
    }
    
    public JSPairPlanTState planS()
    {
        return planS;
    }
    
    public Vector listNodes()
    {
        return listNodes;
    }

   
    
    public void print()
    {
        JSPairPlanTState pS = this.planS();
	    Vector l = this.listNodes();
	    JSJshopNode n;
        pS.print();
        JSUtil.println(" ");
        JSUtil.println("Here starts the tree");
        for (short i=0;i<l.size();i++)
        {
            n = (JSJshopNode) l.elementAt(i);
            n.print();
        }
        
    }
    
    
}
