/* 
 * SAXDirParser - program for viewing public class information
 *
 * Copyright (C) 2004-2007  James Harris 
 *
 * This program is free software; you can redistribute it 
 *
 * and/or modify it under the terms of the GNU General Public
 *
 * License as published by the Free Software Foundation;
 *
 * either version 2 of the License, or (at your option) any 
 *
 * later version.
 *
 *
 * This program is distributed in the hope that it will be 
 *
 * useful, but WITHOUT ANY WARRANTY; without even the implied 
 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 *
 * PURPOSE. See the GNU General Public License for more details.
 *
 *
 * You should have received a copy of the GNU General Public 
 *
 * License along with this program; if not, write to the Free 
 *
 * Software Foundation, Inc., 59 Temple Place, Suite 330, 
 *
 * Boston, MA 02111-1307 USA
 *
 */


package com.jstevh.viewer;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;

import com.jstevh.tools.StringList;

/**
* Proof of Concept file for xml, really basic
*
* SAX Parser class that pulls preference information
* from preference.xml 
*
* @author   James Harris
* @version  5.0.2 beta
*/              
public class SAXDirParser extends DefaultHandler
{
    public String webData="";

    public String getWebData(){return webData;}

    private static HashMap<String,String> directory = new HashMap<String,String>();

    private static StringList localPackages = new StringList();

    private static String localBrowser="";

    private static String file_editor="";
    private static boolean lineNumAccepted=false;
    private static String lineNumParam="";



    private boolean check;

    private boolean local;


    private int level;

    private String location;
   

    public SAXDirParser() throws Exception{

        
    }
    public void startElement(String namespace, String local, String qname, Attributes atts) throws SAXException{



	if (qname.compareTo("BrowserLoc")==0){
		check = true;

		level = 0;
	}


	if (qname.compareTo("Editor")==0){
		check = true;

		level = 10;
	}


	if (qname.compareTo("acceptsLineNumber")==0){
		check = true;

		level = 11;
	}

	if (qname.compareTo("parameter")==0){
		check = true;

		level = 12;
	}

	
        if (qname.compareTo("Group")==0){

		level = 2;
		check=false;
		
	}


	if (qname.compareTo("Web")==0){

		check=true;
		level = 30;
	}

	if (qname.compareTo("Local")==0){

		check=true;
		level = 31;
	}


	if (qname.compareTo("Names")==0){

		check=true;;
	}


	if (qname.compareTo("pkg")==0){

		level = 4;
	}

    }

    public void characters(char[] charArray, int start, int length){

	if (check && level>=0){

	  String tempStr="";

	  for (int i = start; i < start + length; i++) {

	    tempStr += charArray[i];

	  }

	  if (level==0){

	    localBrowser = tempStr;
	    check = false;
	    level=-1;
	  }


	  if (level==10){

	    file_editor = tempStr;
	    check = false;
	    level=-1;
	  }


	  if (level==11){

	    if (tempStr.compareToIgnoreCase("Yes")==0){
	    	 lineNumAccepted = true;
	    }
	    else lineNumAccepted = false;

	    check = false;
	    level=-1;
	  }

	  if (level==12){

	    lineNumParam = tempStr;
	    check = false;
	    level=-1;
	  }

	  if (level==30){

	    location = tempStr;
	    check = false;
	    level=-1;
	  }

	  if (level==31){

	    if (tempStr.compareToIgnoreCase("No")!=0){
	    	local = true;
	    }
	    else local = false;
	    check = false;
	    level=-1;
	  }

	  if (level==4){
	      if (tempStr.trim().compareTo("")!=0){

	      if (tempStr.endsWith(".")) tempStr = tempStr.substring(0,tempStr.length()-1);

	      directory.put(tempStr,location);

	      if (local) localPackages.add(tempStr);	

	    }
	  }


	}

    }

    public void endElement(String namespace, String local, String qname) throws SAXException{

	
        if (qname.compareTo("Group")==0){

		check=false;
		
	}

    }

    public static HashMap getDirectory(){

	return (HashMap)directory.clone();


    }


    public static StringList getLocalPackages(){

	if (localPackages.isEmpty()) return null;

	return localPackages.clone();


    }

    public static String getLocalBrowser(){

	return localBrowser;
    }


    public static String getEditor(){

	return file_editor;
    }

    public static boolean acceptsLineNumber(){

	return lineNumAccepted;
    }

    public static String lineNumberParameter(){

	return lineNumParam;
    }


 
    public static void main(String[] args) throws Exception{

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
	SAXParser myParser = saxFactory.newSAXParser();

	XMLReader myReader = myParser.getXMLReader();
	myReader.setContentHandler(new SAXDirParser());


	myReader.parse("packagedirectory.xml");					


	System.out.println("test directory, test gives "+directory.get("java.lang"));
	System.out.println("test directory, test gives "+directory.get("javax.swing.border"));
	System.out.println("test local browser, test gives "+localBrowser);


    }


}