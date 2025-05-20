/* 
 * Copyright (C) 2004  James Harris 
 *
 * This library is free software; you can redistribute it and/or
 *
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 *
 *
 * This library is distributed in the hope that it will be useful,
 *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *
 * Lesser General Public License for more details.
 *
 *
 * You should have received a copy of the GNU Lesser General Public
 *
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

//5-5-04  Added recursive search to find all interfaces.   ___JSH
//7-30-04 Switched to TreeList to keep from getting interfaces twice in list.  ___JSH
//8-01-04 Handled exceptions with fields.   ___JSH

package com.jstevh.viewer;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import com.jstevh.tools.*;


/**
* Obtains the interfaces as well as the public constructors, methods
* and fields from a Class object. 
* <p>
* This class is for obtaining data and handling.
*
* @author   James Harris
* @version  2.0a
*/              
public class ClassInfo{

    public static boolean debug;
	 
    private boolean statusAbstract;
        
    private Class c=javax.swing.WindowConstants.class;

    private DirManager dirMan;
    
    private static int count, length;
    
    public final static int NO_OBJECT_METHODS=1, NO_INHERITED_METHODS=2;
    
    private String[] cMethods,fndMethods=null;
    
    protected String[] knownPackages;

    public boolean isAbstract(){return statusAbstract;}
    public boolean isInterface(){return c.isInterface();}
    
    public String getClassName(){return c.getName();}
    public String getClassPackage(){return c.getPackage()!=null? c.getPackage().getName():null;}    
    public String getSuperClassName(){        
        if (c!=java.lang.Object.class && !c.isInterface()) return c.getSuperclass().getName();
        else return null;
    }
    
    private ClassInfo(){
                
    }  

    /**
    * Constructor for when the class name is known at creation.  
    *
    * @param  name the name of the class on which to get info
    */              
    public ClassInfo(String name) throws ClassNotFoundException{
	    
	    c = getClass(name);
	    
	    if (c==null) throw new ClassNotFoundException();
	    
	    Method[] m = c.getMethods();
	    Object[] obj = null;
	    
	    cMethods = getData(m);
	    
	    if (cMethods!=null){
		    if (c!=java.lang.Object.class){
			    
			    StringList tempList = new StringList();
			    
			    for (count=0; count<cMethods.length; count++){
				    if (cMethods[count].indexOf("Object.")==-1) tempList.add(cMethods[count]);
			    }
			    
			    cMethods = tempList.toArray();
			    
		    }
		    
		    for (count=0; count<cMethods.length; count++){ 
			    
			    if (!statusAbstract && cMethods[count].indexOf("abstract")!=-1) statusAbstract=true;
		    }
	    }
	    
    }       

    /**
    * Constructor for when the class name is known at creation.  
    *
    * @param    name  name of class
    *           locManager    class that manages package information
    */              
    public ClassInfo(String name, DirManager locManager) throws ClassNotFoundException{
	    
	    dirMan = locManager;
	    
	    knownPackages = dirMan.getPackageList();
	    
	    c = getClass(name);
	    
	    if (c==null) throw new ClassNotFoundException();
	    
	    Method[] m = c.getMethods();
	    Object[] obj = null;
	    
	    cMethods = getData(m);
	    
	    if (cMethods!=null){
		    if (c!=java.lang.Object.class){
			    
			    StringList tempList = new StringList();
			    
			    for (count=0; count<cMethods.length; count++){
				    if (cMethods[count].indexOf("Object.")==-1) tempList.add(cMethods[count]);
			    }
			    
			    cMethods = tempList.toArray();
			    
		    }
		    
		    for (count=0; count<cMethods.length; count++){ 
			    
			    if (!statusAbstract && cMethods[count].indexOf("abstract")!=-1) statusAbstract=true;
		    }
	    }
	    
    }

    /**
    * Returns an array of strings that contain the public fields for 
    * the class. The data is pulled from the c private Class object. 
    * <p>
    * This method always returns immediately.
    *
    * @return      string array of public fields
    */              
    public String[] printFields(){
        
        
        Field[] f=c.getFields();
        length=f.length;
        
        String[] StringData=null;
        
        if (length>0){
            StringData = new String[length];
            String tempStr;
            
            Object obj=new Object();
                
            if (debug) System.out.println("****************************************");
            for (count=0; count<length; count++){
                try{
                    tempStr= f[count]+"="+f[count].get(obj);
                    StringData[count]=tempStr;
                    if (debug) System.out.println(tempStr);
                }
                catch (IllegalAccessException e){
			e.printStackTrace();
			
			tempStr= f[count]+"= ILLEGAL ACCESS EXCEPTION, unable to load";
			StringData[count]=tempStr;
		}
		catch (IllegalArgumentException j){
			j.printStackTrace();
			tempStr= f[count]+"= ILLEGAL ARGUMENT EXCEPTION, unable to load";
			StringData[count]=tempStr;
		}
            }
        }
        else if (debug) System.out.println("No public fields.");
        
        
        return StringData;
        
        
    }
    
    /**
    * Returns an array of strings that contain the public methods for 
    * the class. The data is pulled from the c private Class object. 
    * <p>
    * This method always returns immediately.
    *
    * @return      string array of public methods
    */                  
    public String[] printMethods(){
        
        if (debug) System.out.println("****************************************");

        String[] tempArray = null;

       if (cMethods!=null){
            tempArray = new String[cMethods.length];       
        
            System.arraycopy(cMethods, 0, tempArray, 0, cMethods.length);
        
        }
        
                
        if (debug && tempArray==null) System.out.println("No public methods.");        
        else{
            printArray(tempArray);
            
        }
        
        return tempArray;
    }
    
    /**
    * Returns an array of strings that contain the public methods for 
    * the class excluding inherited methods. The data is pulled from
    * the c private Class object. 
    * <p>
    * This method always returns immediately.
    *
    * @param  param (not currently implemented) selects whether inherited
    *               objects are returned
    * @return      string array of public methods
    */              
    public String[] printMethods(int param){
        
        if (cMethods==null) return null;
        
        if (debug) System.out.println("****************************************");
        
        String[] data = cMethods;
        
        StringList tempList = new StringList();
        
        for (int i=0; i<cMethods.length; i++){
            if (cMethods[i].indexOf(getClassName()+'.')!=-1) tempList.add(cMethods[i]);
        }
        
        if (!tempList.isEmpty()) data = tempList.toArray();
        else data=null;
                
        if (debug && data==null) System.out.println("No public methods.");        
        else{
            printArray(data);
            
        }
        
        return data;
    }

    /**
    * Returns an array of strings that contain the public constructors for 
    * the class. The data is pulled from the c private Class object. 
    * <p>
    * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
    *
    * @return      string array of public constructors
    */              
    public String[] printConstructors(){
        
        Constructor[] constr = c.getConstructors();        
        
        if (debug) System.out.println("****************************************");
        
        String[] data = getData(constr);
        
        if (debug && data==null) System.out.println("No public constructors.");
        else printArray(data);
        
        return data;        
    }


    /**
    * Returns an array of strings that contain the interfaces for 
    * the class. The data is pulled from the c private Class object. 
    * <p>
    * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
    *
    * @return      string array of interfaces
    */              
    public String[] printInterfaces(){
        
        Class[] inter=c.getInterfaces();
        
        if (debug) System.out.println("****************************************");
        
        String[] data = getData(inter);

	
	if (c!=java.lang.Object.class && !c.isInterface()){

		Class superC=c.getSuperclass();

		TreeSet <String> hInterfaces = new TreeSet<String>();

		int i=0;

		if (data!=null) for (i=0; i<data.length; i++){		

			hInterfaces.add(data[i]);

		}

		while(superC!=null && superC!=java.lang.Object.class){


        		inter=superC.getInterfaces();
        
        		if (debug) System.out.println("****************************************");
        
        		data = getData(inter);

			if (data!=null) for (i=0; i<data.length; i++){

					hInterfaces.add(data[i]);
			}

			superC=superC.getSuperclass();			

		}

		if (hInterfaces.size()!=0){

			data = new String[hInterfaces.size()];
			
			Iterator it = hInterfaces.iterator();
			
			i=0;

			while (it.hasNext()){
				data[i]= (String)it.next();
				i++;
			}


		}
		

	}
        
        if (debug && data==null) System.out.println("No interfaces.");
        else printArray(data);
        
        return data;        
    }

    /**
    * Takes an array of Member objects and gets names from toString(). 
    * <p>
    * This method always returns immediately.
    *
    * @param  m  array of Member objects
    * 
    * @return    string array of names
    */              
    public static String[] getData(Member[] m){
        
        length=m.length;
        
        if (length==0) return null;
        
        String[] StringData = new String[length];
        String tempStr;
        
        for (count=0; count<m.length; count++){
                        
            tempStr = m[count].toString();
            StringData[count]=tempStr;
        }
        
        return StringData;
    }

    /**
    * Takes an array of Class objects and gets names from toString(). 
    * <p>
    * This method always returns immediately.
    *
    * @param  m  array of Class objects
    * 
    * @return    string array of names
    */          
    public static String[] getData(Class[] m){
        
        length=m.length;
        
        if (length==0) return null;
        
        String[] StringData = new String[length];
        String tempStr;
        
        for (count=0; count<m.length; count++){
                        
            tempStr = m[count].getName();
            StringData[count]=tempStr;
        }
        
        return StringData;
    }

    /**
    * Searches through cMethods, the private array of public methods
    * for a given string fragment, and selects methods that have that
    * fragment in them. 
    * <p>
    * This method calls searchStrings().
    *
    * @param    tempStr  string fragment with which to search
    * 
    * @return   string array of found methods    
    */              
    public String[] srchMethods(String tempStr){
        
        if (tempStr==null) return null;
        
        fndMethods = StringTools.searchStrings(cMethods, tempStr);
        
        if (fndMethods!=null){
            String[] tempArray = new String[fndMethods.length];       
        
            System.arraycopy(fndMethods, 0, tempArray, 0, fndMethods.length);
        
            return tempArray;
        }
        
        return null;
    }

    /**
    * Searches through cMethods, the private array of public methods
    * for a given string fragment, with a given string index, and searches
    * on the index for the fragment in them but gets method from cMethods. 
    * <p>
    * This method calls searchStrings().
    *
    * @param    tempStr  string fragment with which to search
    *           index    string array that is searched through
    * 
    * @return   string array of found methods    
    */              
    public String[] srchMethods(String tempStr, String[] index){
        
        if (tempStr==null) return null;
        
        fndMethods = StringTools.searchStrings(cMethods, index, tempStr);
        
        if (fndMethods!=null){
            String[] tempArray = new String[fndMethods.length];       
        
            System.arraycopy(fndMethods, 0, tempArray, 0, fndMethods.length);
        
            return tempArray;
        }
        
        return null;
    }

    /**
    * Searches through found methods and returns method at given 
    * position, uses anonymous inner class.  If no methods 
    * have been searched for it returns null.
    *
    * @param    pos  position of found methods in array 0 is first.
    * 
    * @return   MethodData object with information about method            
    */              
    public MethodData getFoundMethod(final int pos){
        
        if (fndMethods==null || pos>fndMethods.length) return null;
                
        return new MethodData(){
            //check to make sure position isn't out of range
            
            String tempStr = fndMethods[pos],
                   tempStr2;
                   
            String className, methodName;
            
            int begin, end;
            
            Class methClass;            
            
            {
                  
                end = tempStr.indexOf(")");            
                tempStr = tempStr.substring(0,end+1);
                
                begin = tempStr.lastIndexOf(" ");            
                tempStr = tempStr.substring(begin+1, tempStr.length());
                
                begin = tempStr.indexOf("(");
                            
                end = tempStr.lastIndexOf(".", begin);
                
                className = tempStr.substring(0, end);
                methodName = tempStr.substring(end+1, tempStr.length());
                
                if (debug) System.out.println("className="+className);
                            
                methClass = ClassInfo.tryClass(className);
                
                if (debug) System.out.println("methClass="+methClass);
            
            }
                        
            
            public String getMethPackage(){
                
               if (methClass==null) return null;
               
               return methClass.getPackage()!=null? methClass.getPackage().getName():null;
                
            }
            
            public String getMethClass(){
                
               if (methClass==null) return null;
               
                return methClass.getName();
                
            }
            
            public String getMethName(){
                
                return methodName;
            }
            
        };
        
    }


    /**
    * Takes any given array and prints out with System.out.println  
    *
    * @param    array   an array of objects
    *                 
    */              
    private void printArray(Object[] array){

	if (!debug) return;
        
        length = array.length;
        
        if (length==0) return;
        
        for (count=0; count<length; count++){
            System.out.println(array[count]);
        }
    }

    /**
    * Takes a given class name and tries to to find the class. 
    * If the initial try does not work it tries all known packages
    * to see if any of them will work with the class name. 
    *
    * @param    name    name of class
    * 
    * @return   found Class                
    */              
    private Class getClass(String name){            
        
        String tempStr = name;
        Class tempClass=null;
        
        tempClass = tryClass(name);
        
        if (tempClass == null){            
            for (int i=0; i<knownPackages.length; i++){
                tempStr = knownPackages[i]+name;
                tempClass=tryClass(tempStr);
                if (tempClass!=null) break;
            }
        }     
        
        return tempClass;
    }    
    
    /**
    * Takes a given class name and tries to to find the class. 
    * If the initial try does not work it returns null. 
    *
    * @param    name    name of class
    * 
    * @return   found Class       
    */              
    private static Class tryClass(String name){
        
            Class tempClass=null;
            
            try{
                if (name!=null) tempClass = Class.forName(name.trim());                                
            }
            catch (java.lang.ClassNotFoundException e){

                return null;
            }
	    catch (java.lang.NoClassDefFoundError f){
		//f.printStackTrace();
		return null;
	    }
	    
            
            return tempClass;
    } 
        
    /**
    * Prints out class info with System.out.println  
    *
    *                             
    */              
    private void printClassInfo(){
                                        
          
        printMethods();                
            
        if (!c.isInterface()){
            
            printConstructors();
                      
            if (debug) System.out.println("****************************************");
            
            printInterfaces();
                
            if (debug) System.out.println("****************************************");
            if (statusAbstract){
                System.out.println("Abstract "+c);
            }
            else System.out.println(c.getName());
            if (c!=java.lang.Object.class) System.out.println("Superclass is "+c.getSuperclass().getName()+".");                        
        }
        else {   
            printFields();
            if (debug) System.out.println("****************************************");            
            System.out.println(c.getName()+" is an interface.");
        }
    }   
    
    /**
    * Main method for getting class information. 
    * Prints out data with System.out.println. 
    *
    * @param    args    string array for main
    *                        
    */              
    public static void main(String[] args)throws Exception{
        long t1 = System.currentTimeMillis();
                
        debug=true;

        String name=null;
        ClassInfo myClassInfo;        
         
        if (args.length>0){            
            
            if (args[0].equals("new")){
                name = args[1];
            }
            else{
                name = args[0];
                
                try{
                    myClassInfo = new ClassInfo(name);
                    myClassInfo.printClassInfo();
                }
                catch(ClassNotFoundException e){                    
                    System.out.println("Class not found.");
                    System.exit(0);
                }                
            }
            
        }
        else {
            
            myClassInfo = new ClassInfo();
            myClassInfo.printClassInfo();
            
        }
        
        
               
                          
        System.out.println("Total time: "+(System.currentTimeMillis()-t1));                        
    }
        
}



     