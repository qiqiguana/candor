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


package com.jstevh.tools;



/**
* Some static tools for string manipulation
*
* @author   James Harris
* @version  1.1
*/              
public class StringTools{

    private static int count, length;

    /**
    * Searches through given string array for a given string 
    * fragment and returns an array of the strings that have
    * fragment in them. 
    *
    * @param    strings    string array that is searched through
    *           fragment   string fragment with which to search
    * 
    * @return   string array of found strings            
    */              
    public static String[] searchStrings(String[] strings, String fragment){
        
        String[] tempArray=null;
     
        if (strings!=null && fragment!=null){
            
            String tempStr, tempStr2;
            StringList hold = new StringList();
            
            for (int i=0; i<strings.length; i++) {
                        
                tempStr2 = strings[i].toLowerCase();
                tempStr = fragment.toLowerCase();
                                                
                if (tempStr2.indexOf(tempStr)!=-1){                    
                    hold.add(strings[i]);
                }
            }
            
            if (!hold.isEmpty()) tempArray=hold.toArray();
        }
        
        return tempArray;
    }

    /**
    * Searches through given index string array for a given string 
    * fragment and returns an array of the strings from given base
    * array.
    *
    * @param    strings    string array that values are pulled from
    *           index      string array that is searched through
    *           fragment   string fragment with which to search
    * 
    * @return   string array of found strings    
    */              
    public static String[] searchStrings(String[] strings, String[] index, String fragment){
        
        String[] tempArray=null;
     
        if (strings!=null && index!=null && fragment!=null && index.length<=strings.length){
            
            String tempStr, tempStr2;
            StringList hold = new StringList();
            
            for (int i=0; i<index.length; i++) {
                        
                tempStr2 = index[i].toLowerCase();
                tempStr = fragment.toLowerCase();
                                                
                if (tempStr2.indexOf(tempStr)!=-1){  
                 
                    hold.add(strings[i]);
                }
            }
            
            if (!hold.isEmpty()) tempArray=hold.toArray();
        }
        
        return tempArray;
    }

    /**
    * Takes any given array and prints out with System.out.println  
    *
    * @param    array   an array of objects
    *                 
    */              
    private void printArray(Object[] array){

	if (array==null) return;
        
        length = array.length;
        
        if (length==0) return;
        
        for (count=0; count<length; count++){
            System.out.println(array[count]);
        }
    }

    /**
    * Searches through given string array for a given string 
    * fragment and deletes fragment from strings that have
    * fragment in them. 
    *
    * @param    in        string fragment to be deleted out
    *           strings   array with strings to delete fragment from
    * 
    * @return   toString() on string buffer used, for debugging                  
    */              
    public static String removeStrings(String in, String[] strings){
        
        if (in==null || strings==null) return null;
        
        StringBuffer buff = new StringBuffer(in.trim());
        
        int pos1, length;
                
        for (int i=0; i<strings.length; i++){

        
            pos1 = buff.toString().indexOf(strings[i]);                   
            length = strings[i].length();
            
            while (pos1!=-1){
                buff.delete(pos1, pos1+length);
                pos1 = buff.toString().indexOf(strings[i]);
            } 
        }
        
        String tempStr = buff.toString();     
        
        return tempStr;        
                
    }

}

