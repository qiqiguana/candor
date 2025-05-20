package umd.cs.shop;
import java.util.*;



public class JSPlan extends JSTasks

{

    /*==== instance variables ====*/

    private boolean isFailure;
    Vector costs;
    

    JSPlan()
    {
       
        super();
        
         isFailure = false;
         costs =new Vector();

    }
    

    public void assignFailure()
    {
      isFailure = true;
    }    
    
    public boolean isFailure()
    {
      return isFailure;
    }
    
    public void addElements(JSPlan pl)
    {
      for (short i = 0; i < pl.size(); i++)
      {
        this.addElement(pl.elementAt(i));
        // Added in May 2
        costs.addElement(pl.costs.elementAt(i));
      }
      
    }
    
    public void printPlan()
    {
      JSTaskAtom t;
      JSUtil.print(" ( ");
      for (short i = 0; i < size(); i++)
      {  
         t = (JSTaskAtom)elementAt(i);
         t.print();
        // Added in May 2
         JSUtil.print(" " + (String) costs.elementAt(i)+ " ");
      }
      JSUtil.println(" ) ");
    }

   public void addWithCost(JSTaskAtom t, double cost) {
    
    addElement(t);
    costs.addElement(String.valueOf(cost));
   } 
  
  public void insertWithCost(int place,JSTaskAtom t, double cost) {
    
    insertElementAt(t,place);
    costs.insertElementAt(String.valueOf(cost),place);
   } 
   public double elementCost(int i){
     return Double.parseDouble((String) costs.elementAt(i));
   }
}

