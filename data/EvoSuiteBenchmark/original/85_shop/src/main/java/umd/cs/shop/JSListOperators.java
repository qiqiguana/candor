package umd.cs.shop;
import java.util.*;

public class JSListOperators extends Vector
{
    /*==== instance variables ====*/
    private String label;
    
    JSListOperators()
    {
        super();
    }
  
  public void print()
  {
       
       JSOperator el;
       for (short i = 0; i < this.size(); i++)
       {
        el = (JSOperator) this.elementAt(i);
        el.print();
       }
  }
}
