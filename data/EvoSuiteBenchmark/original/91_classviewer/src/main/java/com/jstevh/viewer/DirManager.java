/* 
 * DirManager - program for managing Sax parser
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
import java.io.*;
import java.net.*;


import com.jstevh.tools.StringList;

/**
* Proof of Concept file for xml, really basic
*
* SAX Parser manager class 
*
* @author   James Harris
* @version  5.0 beta
*/              
public class DirManager
{

    public static String currentDir;
    public static String fileSeparator;

    private SAXDirParser parser;

    private boolean directoryLoaded;
    
    //I need to create an Exceptions class to pass back what happened to ClassInfo and ClassViewer
    //maybe later
    
    public boolean loaded(){ return directoryLoaded;}
    
    private boolean fileNotFoundError;
    public boolean fileNotFoundError(){ return fileNotFoundError;}
    
    private String[] pkgList;
    
    private DirManager(){}
    
    public DirManager(boolean mainrun){
	    
	    if  (mainrun) {
		    try{


			try{
				currentDir = System.getProperty("user.dir");
				fileSeparator = System.getProperty("file.separator");


			}
			catch (java.security.AccessControlException access){
				access.printStackTrace();
				System.out.println("Should never go in here");				

			}


			    parser = new SAXDirParser();
			    
			    if (!directoryLoaded){
				    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
				    SAXParser myParser = saxFactory.newSAXParser();
				    XMLReader myReader = myParser.getXMLReader();
				    myReader.setContentHandler(parser);
				    myReader.parse(currentDir+fileSeparator+"packagedirectory.xml");
				    setPackageList();
				    directoryLoaded=true;
			    }
		    }
		    catch (java.io.FileNotFoundException i){
			    System.out.println("File Not Found");
			    System.out.println("Looking in directory:"+currentDir+fileSeparator);			
			    fileNotFoundError = true;
			    
		    }
		    catch (Exception e){
			    //Will deal with properly later
			    System.out.println("Exception in Directory Manager on load.");
			    e.printStackTrace();
		    }
	    }
	    else{
		    
		    try{
			    
			    parser = new SAXDirParser();
			    
			    if (!directoryLoaded){
				    SAXParserFactory saxFactory = SAXParserFactory.newInstance();
				    SAXParser myParser = saxFactory.newSAXParser();
				    
				    XMLReader myReader = myParser.getXMLReader();
				    myReader.setContentHandler(parser);
				    
				    InputStream dirStream = this.getClass().getResourceAsStream("packagedirectory.xml");
				    
				    if (dirStream==null) myReader.parse(new InputSource(new StringReader(defaultXML)));
				    else myReader.parse(new InputSource(dirStream));
				    
				    setPackageList();
				    directoryLoaded=true;
			    }
		    }
		    catch (Exception e){
			    //Will deal with properly later
			    System.out.println("Exception in Directory Manager on load.");
			    e.printStackTrace();
		    }
	    }
	    
    }

    public void setPackageList(){

	HashMap tempDirectory = parser.getDirectory();

	int size = tempDirectory.size();

	pkgList = new String[size];

	Set tempSet = tempDirectory.keySet();

	Iterator tempIter = tempSet.iterator();

	int i=0;

	while (tempIter.hasNext()){

	  pkgList[i] = (String)tempIter.next()+".";

	  i++;
	}	

	return; 
    }

    public HashMap getDirectory(){

	return parser.getDirectory();
    }

    public String[] getPackageList(){

	return pkgList.clone();
    }


    public StringList getLocalPackages(){

	return parser.getLocalPackages();
    }

    public String getLocalBrowser(){

	return parser.getLocalBrowser();
    }


    public String getEditor(){

	return parser.getEditor();
    }


    public boolean acceptsLineNumber(){

	return parser.acceptsLineNumber();
    }

    public String lineNumberParameter(){

	return parser.lineNumberParameter();
    }


    public static void main(String[] args) throws Exception{

	DirManager test = new DirManager();

	System.out.println(test.pkgList[2]);
	System.out.println(test.getLocalBrowser());
	

    }

    public static String defaultXML="<?xml version='1.0' encoding='ISO-8859-1' standalone='yes'?>"+
"<Base><Documentation><Group><Description>Java SDK 6.0</Description><Owner>Sun Microsystems</Owner>"+
"<Location><Web>http://java.sun.com/javase/6/docs/api/</Web><Local></Local></Location>"+
"<Names><pkg>java.lang.</pkg><pkg>java.io.</pkg><pkg>java.lang.reflect.</pkg><pkg>java.util.</pkg>"+
"<pkg>java.awt.event.</pkg><pkg>java.awt.datatransfer.</pkg><pkg>java.awt.</pkg><pkg>java.net.</pkg>"+
"<pkg>java.applet.</pkg><pkg></pkg><pkg></pkg><pkg></pkg><pkg></pkg></Names></Group><Group>"+
"<Description>Java Enterprise</Description><Owner>Sun Microsystems</Owner><Location>"+
"<Web>http://java.sun.com/j2se/1.4.2/docs/api/</Web></Location><Names><pkg>javax.swing.border.</pkg>"+
"<pkg></pkg><pkg></pkg><pkg></pkg><pkg></pkg></Names></Group></Documentation></Base>";


}