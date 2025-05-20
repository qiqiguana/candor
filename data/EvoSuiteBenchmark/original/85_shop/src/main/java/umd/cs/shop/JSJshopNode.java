package umd.cs.shop;
import java.util.*;

public final class JSJshopNode
{
    JSTaskAtom atom;
    Vector   children;   // should have been JSTasks

    JSJshopNode()
    {
        super();
    }
    
    JSJshopNode( JSTaskAtom a, Vector c)
    {
        super();
	atom = a;
	children = c;
    }
    
    JSJshopNode(JSJshopNode rootNode, Vector listNodes)
    {
        /* rootNode has the form
               (task <listTasks>) where task is the root task.
            listNodes has the form 
               ((task <listTasks>) .... (task <list Tasks>))
           
        */
        super();
        atom = rootNode.atom();
        Vector childs = rootNode.children();
        
        if (childs.isEmpty())
        {
            children = childs;
            return;
        }
        
        JSTaskAtom ta;
        JSJshopNode newNode;
        JSJshopNode node_ta;
        children = new Vector();    
        
        for (int i = 0; i < childs.size(); i++)
        {
            ta = (JSTaskAtom) childs.elementAt(i);
            newNode = ta.findInList(listNodes);
            if (!newNode.children().isEmpty())//success in finding a node
            {
                listNodes.removeElement(newNode);
                node_ta = new JSJshopNode(newNode,listNodes);
                children.addElement(node_ta);
            }
            else
            {
                children.addElement(newNode);
            }
        }
        
    }
    
   public void print()
    {
        JSTaskAtom a = this.atom();
        JSJshopNode child;
        Vector c = this.children();
        if (c.size() > 0) {
            JSUtil.print(a.toStr()+" [");
        }
        else{
            a.print();
            JSUtil.flag("");
        }
        for(short i=0;i<c.size();i++)
        {
            child = (JSJshopNode) c.elementAt(i);
            child.print();
        }
        if (c.size() > 0) {
              JSUtil.flag("] "+a.toStr());
        }
    }
    
    public void print2()
    {
        JSTaskAtom a = this.atom();
        JSTasks child;
        if (this.children.size() > 0) {
            child =   (JSTasks) this.children();
            JSUtil.print(a.toStr()+" [ ");
            child.print();
            JSUtil.println(" ]");
        }
        else{
            a.print();
            JSUtil.println("");
        }
        
         
	}
    
    public JSTaskAtom atom()
    {
        return atom;
    }
    
    public Vector children()
    {
        return children;
    }

}

