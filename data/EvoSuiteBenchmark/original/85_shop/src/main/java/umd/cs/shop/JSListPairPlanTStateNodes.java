package umd.cs.shop;
import java.util.*;

public class JSListPairPlanTStateNodes extends Vector
{
        
    
   JSListPairPlanTStateNodes()
    {
        super();
    }
    
     
   public void print() {

      JSPairPlanTSListNodes ptn; 
      for (int i=0;i<this.size();i++){
         ptn= (JSPairPlanTSListNodes) this.elementAt(i);
         ptn.print();
      }

  }
    
    
}
