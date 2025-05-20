package lotus.core.card;
import lotus.core.*;
import lotus.core.Error;
import lotus.core.cost.Cost;

import java.io.File;
import java.net.URL;
import java.util.*;
import sun.misc.Launcher;
@SuppressWarnings("unchecked")

/* 
 * A card is an abstract type. It is intended to be subclassed by subcategories,
 * such as lands, creatures and so on. It contains the basic properties every card has.
 */
public abstract class Card
{
	//cost to play the card
	public Cost cost;
    //name and text (= rules text) of the card
    public String name, text;
    //reference to the owner
    public Player owner;
    //the zone the card is in
    public CardCollection zone;
    //is true if the card is in the stack. In this case, nothing is guaranteed about zone
    public boolean isInStack = false;
    
    //contains the list of subclasses of this class. Used in effect.ChangeProperty
	public static ArrayList<Class> subclasses = new ArrayList<Class>();
    //static constructor to fill subclasses
    static
    {
    	try
    	{
    		//code from www.javaworld.com
    		
    		//this is the package name from which we want to search subclasses
            String name = "/lotus/core/card";
            
            // Get a File object for the package
            URL url = Launcher.class.getResource(name);
            File directory = new File(url.getFile());
            if (directory.exists())
            {
                // Get the list of the files contained in the package
                String [] files = directory.list();
                for (int i=0;i<files.length;i++)
                {     
                    // we are only interested in .class files
                    if (files[i].endsWith(".class"))
                    {
                        // removes the .class extension
    	                String classname = files[i].substring(0,files[i].length()-6);
                    	Class currentClass = Class.forName("lotus.core.card."+classname);
                    	subclasses.add(currentClass);
                    }
                }
            }
    	}
    	catch(Exception e) {Error.Report(e);}
    	//now let's sort it so the superclasses are in the beginning
    	Collections.sort(subclasses, new compareClasses());
    }
}


//comparator used to order the classes
@SuppressWarnings("unchecked")
class compareClasses implements Comparator<Class>
{
	public int compare(Class arg0, Class arg1) {
		//return -1 if arg0 is superclass of arg1
		if(arg0.isAssignableFrom(arg1))return -1;else return 1;
	}
}
