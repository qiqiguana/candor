/* 
 * Copyright (C) 2004-2007  James Harris 
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
//5-31-04  Added size() method.  ___JSH
//11-18-07 Changed version.  ___JSH

package com.jstevh.tools;

import java.util.*;



/**
* String holding and manipulation class
*
* @author   James Harris
* @version  5.0 beta
*/              
public class StringList{
        
    private ArrayList<String> tempList;
        
    private Object[] tempArray=null;
        
    private int length;
        
    public StringList(){
        tempList   = new ArrayList<String>();
    }
    
    public boolean isEmpty(){return tempList.isEmpty();}

    public boolean contains(String obj){return tempList.contains(obj);}
        
    public void add(String obj){
        tempList.add(obj);
    }
    public String get(int index){
        return tempList.get(index);
    }


    public int size(){ 
	return tempList.size();

    }
        
    public String[] toArray(){
            
        tempArray=tempList.toArray();
            
        String[] tempString = new String[tempArray.length];
            
        for (int i=0; i<tempArray.length; i++){
            tempString[i]=(String)tempArray[i];                
        }
            
        return tempString;
                        
    }

    public StringList clone(){
            
        tempArray=tempList.toArray();
            
        StringList tempString = new StringList();
            
        for (int i=0; i<tempArray.length; i++){
            tempString.add((String)tempArray[i]);                
        }
            
        return tempString;
                        
    }

	        
}