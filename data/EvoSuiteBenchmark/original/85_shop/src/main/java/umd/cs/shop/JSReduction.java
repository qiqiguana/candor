package umd.cs.shop;
import java.util.*;



public class JSReduction
{
  private JSMethod selectedMethod;
  private JSTasks reduction;

  JSReduction()
  {
      super();
    selectedMethod = new JSMethod();
    reduction = new JSTasks();
  }   

    JSReduction(JSMethod met, JSTasks red)
    {
	super();
	selectedMethod = met;
	reduction = red;
    }
  
  public JSMethod selectedMethod()
  {
    return selectedMethod;
  }
  
  public JSTasks reduction()
  {
   return reduction;
  }

  public boolean isDummy()
    {
	JSMethod met = this.selectedMethod();
	return !met.notDummy();
    }
}
