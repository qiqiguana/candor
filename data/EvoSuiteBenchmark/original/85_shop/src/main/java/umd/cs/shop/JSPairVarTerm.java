package umd.cs.shop;
import java.util.*;

public class JSPairVarTerm 
{
    JSTerm var;
    JSTerm term;
    
    JSPairVarTerm()
    {
        super();
    }
    
    JSPairVarTerm(JSTerm v, JSTerm t)
    {
        super();
        var = v;
        term = t;
    }
    
    public JSTerm var()
    {
        return var;
    }
    
    public JSTerm term()
    {
        return term;
    }
    public void  setTerm( JSTerm t)
    {
        term=t;
    }
    public JSPairVarTerm clonePVT()
    {
	JSTerm v = this.var();
	JSTerm t = this.term();
	return new JSPairVarTerm(v.cloneT(),t.cloneT());
    }
    
    public void print()
    {
        JSTerm v = this.var();
	    JSTerm t = this.term();
        JSUtil.print("(");
        v.print();
        JSUtil.print("->");
        t.print();
        JSUtil.print(")");
        
    }
    
    public JSPairVarTerm standarizerPVT()
  {
    JSTerm v = this.var();
    JSTerm t = this.term();
    JSPairVarTerm newPVT = new JSPairVarTerm();
    
    newPVT.var = v.standardizerTerm();
    newPVT.term = t.standardizerTerm();
    
    return newPVT;
    
  }
}
